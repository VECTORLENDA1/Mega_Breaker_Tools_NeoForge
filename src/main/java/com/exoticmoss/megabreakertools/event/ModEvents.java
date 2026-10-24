package com.exoticmoss.megabreakertools.event;

import com.exoticmoss.megabreakertools.util.VeinFinder;
import java.util.ArrayDeque;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import com.exoticmoss.megabreakertools.component.ClusterContents;
import com.exoticmoss.megabreakertools.item.ModItems;
import com.exoticmoss.megabreakertools.item.custom.DropClusterItem;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/// The events of the tools and of the Drop Cluster.
/// Fabric events: PlayerBlockBreakEvents.BEFORE (area breaking), ServerEntityEvents.ENTITY_LOAD (cluster spawn),
/// ServerTickEvents.END_SERVER_TICK (inventory merge). Where Fabric has no event, mixins call these methods:
/// BlockMixin (block drops and their experience), ItemEntityMixin (cluster tick and pickup).
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    /// While a tool is breaking an area: player -> the drops gathered for the cluster
    private static final Map<ServerPlayer, ClusterContents.Builder> DROP_CLUSTER = new HashMap<>();
    /// The block the player broke with the tool (its drops come after the area): player -> where and when
    private static final Map<ServerPlayer, InitialBlock> INITIAL_BLOCK = new HashMap<>();
    /// Clusters on the ground closer than this (in blocks) join into one.
    public static final double CLUSTER_MERGE_RADIUS = 20.0;
    /// How many blocks under the bottom of the world a cluster falls before it is saved from the void
    /// (the game deletes items 64 blocks under the bottom).
    public static final int VOID_RESCUE_DEPTH = 16;
    /// Where the player who made the cluster is saved (in the cluster on the ground).
    private static final AttachmentType<String> CLUSTER_OWNER = AttachmentRegistry.createPersistent(
            Identifier.fromNamespaceAndPath(MegaBreakerTools.MODID, "owner"), Codec.STRING);
    /// While the drops of a block are being captured: the experience it dropped (see BlockMixin)
    private static int capturedExperience = -1;

    private record InitialBlock(BlockPos pos, long gameTime) {}

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, blockEntity) -> {
            onToolsUsage(level, player, pos);
            return true;
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> onClusterSpawn(entity));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) onPlayerTick(player);
        });
    }

    public static void onToolsUsage(Level eventLevel, Player player, BlockPos eventPos) {
        ItemStack mainHandItem = player.getMainHandItem();

        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (!(mainHandItem.getItem() instanceof IModeSwitchable tool)) return;

        BlockPos initialBlockPos = eventPos;
        if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
            return;
        }

        // Verification or Mode
        int miningMode = IModeSwitchable.getMiningMode(mainHandItem);

        // If in single block mode, does not break additional blocks
        if (miningMode == IModeSwitchable.MODE_SINGLE) {
            return;
        }

        // Tree Chopper mode (axes): the whole tree (like Tree Capitator), up to the number of blocks
        // of the area. On a block that is not a log it breaks only that block
        List<BlockPos> allPositions;
        if (miningMode == IModeSwitchable.MODE_TREE) {
            BlockState initialState = eventLevel.getBlockState(initialBlockPos);
            if (!initialState.is(BlockTags.LOGS)) return;
            allPositions = findTree(eventLevel, initialBlockPos, initialState.getBlock(), tool.maxBlocks());
        } else if (miningMode == IModeSwitchable.MODE_VEIN) {
            // Vein Miner mode (pickaxes): the whole vein of ore, up to the number of blocks of the area.
            // On a block that is not an ore it breaks only that block
            if (!VeinFinder.isOre(eventLevel.getBlockState(initialBlockPos))) return;
            allPositions = VeinFinder.findVein(eventLevel, initialBlockPos, tool.maxBlocks());
        } else {
            // ONE CALL - WORKS FOR ALL TOOLS!
            allPositions = tool.getBlocksToBeDestroyed(tool.getRange(), initialBlockPos, serverPlayer);
        }

        List<BlockPos> blocksToBreak = new ArrayList<>();

        for (BlockPos pos : allPositions) {
            if (pos.equals(initialBlockPos)) continue;

            // Checks if the tool can break the block
            if (!mainHandItem.isCorrectToolForDrops(eventLevel.getBlockState(pos))) {
                continue;
            }

            blocksToBreak.add(pos);
        }

        HARVESTED_BLOCKS.addAll(blocksToBreak);

        int initialDamage = mainHandItem.getDamageValue();

        // All the drops of the area go inside one Drop Cluster (see onBlockDrops)
        ClusterContents.Builder drops = new ClusterContents.Builder(ClusterContents.EMPTY);
        DROP_CLUSTER.put(serverPlayer, drops);
        try {
            for (BlockPos pos : blocksToBreak) {
                serverPlayer.gameMode.destroyBlock(pos);
            }
        } finally {
            DROP_CLUSTER.remove(serverPlayer);
        }

        mainHandItem.setDamageValue(initialDamage);

        blocksToBreak.forEach(HARVESTED_BLOCKS::remove);

        ServerLevel level = serverPlayer.level();
        addToCluster(level, Vec3.atCenterOf(initialBlockPos), drops.build(), serverPlayer);
        // The block the player broke drops its items right after this: they go to the cluster too
        INITIAL_BLOCK.put(serverPlayer, new InitialBlock(initialBlockPos, level.getGameTime()));
    }

    /// The logs of the tree: every log of the same kind touching the first one (also diagonally),
    /// the closest first, up to "maxBlocks" (the first log counts too)
    private static List<BlockPos> findTree(LevelAccessor level, BlockPos start, Block log, int maxBlocks) {
        List<BlockPos> tree = new ArrayList<>();
        Set<BlockPos> seen = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(start);
        seen.add(start);
        while (!queue.isEmpty() && tree.size() < maxBlocks) {
            BlockPos pos = queue.poll();
            tree.add(pos);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        BlockPos next = pos.offset(dx, dy, dz);
                        if (seen.add(next) && level.getBlockState(next).is(log)) queue.add(next);
                    }
                }
            }
        }
        return tree;
    }

    /// Drops of the blocks broken by the area mode (and of the block the player broke):
    /// instead of falling on the ground, they (and their experience) go inside the Drop Cluster
    /// True if the drops of this block go into a Drop Cluster (then BlockMixin gives them to onBlockDrops
    /// instead of dropping them on the ground)
    public static boolean capturesDrops(Entity breaker, ServerLevel level, BlockPos pos) {
        if (!(breaker instanceof ServerPlayer player)) return false;
        if (DROP_CLUSTER.get(player) != null && HARVESTED_BLOCKS.contains(pos)) return true;
        InitialBlock initial = INITIAL_BLOCK.get(player);
        return initial != null && initial.pos().equals(pos) && initial.gameTime() == level.getGameTime();
    }

    /// Starts counting the experience the block drops (Block#popExperience, see BlockMixin)
    public static void startCapturingExperience() {
        capturedExperience = 0;
    }

    /// True if the experience was counted (it does not fall on the ground)
    public static boolean captureExperience(int amount) {
        if (capturedExperience < 0) return false;
        if (amount > 0) capturedExperience = (int) Math.min(Integer.MAX_VALUE, (long) capturedExperience + amount);
        return true;
    }

    /// Stops counting and gives the experience counted
    public static int stopCapturingExperience() {
        int experience = Math.max(0, capturedExperience);
        capturedExperience = -1;
        return experience;
    }

    public static void onBlockDrops(Entity breaker, ServerLevel level, BlockPos pos, List<ItemStack> eventDrops, int droppedExperience) {
        if (!(breaker instanceof ServerPlayer player)) return;

        ClusterContents.Builder cluster = DROP_CLUSTER.get(player);
        if (cluster != null && HARVESTED_BLOCKS.contains(pos)) {
            for (ItemStack drop : eventDrops) cluster.add(drop);
            // The experience of the block (e.g. ores) also goes into the cluster
            cluster.addExperience(droppedExperience);
            return;
        }

        InitialBlock initial = INITIAL_BLOCK.get(player);
        if (initial != null && initial.pos().equals(pos) && initial.gameTime() == level.getGameTime()) {
            INITIAL_BLOCK.remove(player);
            ClusterContents.Builder drops = new ClusterContents.Builder(ClusterContents.EMPTY);
            for (ItemStack drop : eventDrops) drops.add(drop);
            drops.addExperience(droppedExperience);
            addToCluster(level, Vec3.atCenterOf(pos), drops.build(), player);
        }
    }

    /// Makes a cluster with these items here. The old clusters close by join this new one
    /// (their items move here), so the cluster always follows the last area broken.
    private static void addToCluster(ServerLevel level, Vec3 pos, ClusterContents contents, ServerPlayer owner) {
        if (contents.isEmpty()) return;

        ClusterContents merged = contents;
        ItemEntity old;
        while ((old = findNearbyCluster(level, pos, null)) != null) {
            merged = DropClusterItem.getContents(old.getItem()).merge(merged);
            old.discard();
        }

        ItemEntity cluster = new ItemEntity(level, pos.x, pos.y, pos.z, DropClusterItem.create(ModItems.DROP_CLUSTER.get(), merged), 0, 0, 0);
        cluster.setDefaultPickUpDelay();
        cluster.setThrower(owner);
        level.addFreshEntity(cluster);
    }

    private static ItemEntity findNearbyCluster(Level level, Vec3 pos, ItemEntity except) {
        AABB area = new AABB(pos, pos).inflate(CLUSTER_MERGE_RADIUS);
        for (ItemEntity entity : level.getEntitiesOfClass(ItemEntity.class, area)) {
            if (entity != except && entity.isAlive() && entity.getItem().getItem() instanceof DropClusterItem
                    && entity.position().distanceTo(pos) <= CLUSTER_MERGE_RADIUS) {
                return entity;
            }
        }
        return null;
    }

    /// Clusters never disappear from the ground (they can have a lot of items inside)
    /// and nothing can destroy them: fire, lava, explosions, cactus, lightning...
    /// The player who made (or threw) the cluster is remembered, to give it back if it falls into the void.
    public static void onClusterSpawn(Entity entity) {
        if (entity instanceof ItemEntity item && item.getItem().getItem() instanceof DropClusterItem) {
            item.setUnlimitedLifetime();
            item.setInvulnerable(true);
            if (item.getOwner() instanceof Player owner && item.getAttachedOrElse(CLUSTER_OWNER, "").isEmpty()) {
                item.setAttached(CLUSTER_OWNER, owner.getUUID().toString());
            }
        }
    }

    /// A cluster that falls into the void goes to the inventory of its player (or to their feet if the
    /// inventory is full). If that player is not in the game, it goes to the world spawn.
    private static void rescueFromVoid(ItemEntity cluster) {
        MinecraftServer server = cluster.level().getServer();
        if (server == null) return;
        ItemStack stack = cluster.getItem().copy();
        cluster.discard();

        ServerPlayer owner = null;
        String id = cluster.getAttachedOrElse(CLUSTER_OWNER, "");
        if (!id.isEmpty()) {
            try {
                owner = server.getPlayerList().getPlayer(UUID.fromString(id));
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (owner != null && owner.isAlive()) {
            if (!owner.getInventory().add(stack)) {
                spawnCluster(owner.level(), owner.position(), stack, owner);
            }
            owner.level().playSound(null, owner.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.6F, 1.0F);
            return;
        }
        LevelData.RespawnData spawn = server.getRespawnData();
        ServerLevel level = server.getLevel(spawn.dimension());
        if (level == null) level = server.overworld();
        BlockPos top = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, spawn.pos());
        spawnCluster(level, Vec3.atBottomCenterOf(top), stack, null);
    }

    private static void spawnCluster(ServerLevel level, Vec3 pos, ItemStack stack, ServerPlayer owner) {
        ItemEntity cluster = new ItemEntity(level, pos.x, pos.y + 0.5, pos.z, stack, 0, 0, 0);
        if (owner != null) cluster.setThrower(owner);
        level.addFreshEntity(cluster);
    }

    /// Two clusters on the ground close to each other join into one: the older one goes into the newer one
    /// Called by ItemEntityMixin after the item on the ground ticks
    public static void onClusterTick(Entity entity) {
        if (!(entity instanceof ItemEntity cluster) || cluster.level().isClientSide()) return;
        if (!cluster.isAlive() || !(cluster.getItem().getItem() instanceof DropClusterItem)) return;

        // Fell into the void: saved before the game deletes it
        if (cluster.getY() < cluster.level().getMinY() - VOID_RESCUE_DEPTH) {
            rescueFromVoid(cluster);
            return;
        }
        if (cluster.tickCount % 10 != 0) return;

        ItemEntity other = findNearbyCluster(cluster.level(), cluster.position(), cluster);
        if (other == null) return;
        // Only the newer cluster (fewer ticks alive) takes the other one
        if (other.tickCount < cluster.tickCount) return;

        ClusterContents merged = DropClusterItem.getContents(cluster.getItem()).merge(DropClusterItem.getContents(other.getItem()));
        cluster.setItem(DropClusterItem.create(ModItems.DROP_CLUSTER.get(), merged));
        other.discard();
    }

    /// Clusters in the inventory join into one, like on the ground (checked every few ticks)
    public static void onPlayerTick(ServerPlayer player) {
        if (player.tickCount % 5 != 0) return;
        Inventory inventory = player.getInventory();
        int first = -1;
        ClusterContents merged = null;
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (!(stack.getItem() instanceof DropClusterItem)) continue;
            if (first < 0) {
                first = slot;
                merged = DropClusterItem.getContents(stack);
            } else {
                merged = merged.merge(DropClusterItem.getContents(stack));
                inventory.setItem(slot, ItemStack.EMPTY);
                inventory.setItem(first, DropClusterItem.create(ModItems.DROP_CLUSTER.get(), merged));
            }
        }
    }

    /// Picking up a cluster when you already have one: it goes straight into the one in the inventory
    /// (works even with a full inventory). Called by ItemEntityMixin when a player touches an item:
    /// returns true if the cluster was taken (then the normal pickup does not happen)
    public static boolean onClusterPickup(ItemEntity cluster, Player player) {
        if (player.level().isClientSide() || cluster.hasPickUpDelay() || !cluster.isAlive()) return false;
        if (!(cluster.getItem().getItem() instanceof DropClusterItem)) return false;

        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.getItem() instanceof DropClusterItem) {
                ClusterContents merged = DropClusterItem.getContents(stack).merge(DropClusterItem.getContents(cluster.getItem()));
                inventory.setItem(slot, DropClusterItem.create(ModItems.DROP_CLUSTER.get(), merged));
                player.take(cluster, 1);
                cluster.discard();
                return true;
            }
        }
        return false;
    }
}
