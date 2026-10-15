package com.vector.megabreakertools.event;

import com.vector.megabreakertools.MegaBreakerTools;
import com.vector.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import com.vector.megabreakertools.component.ClusterContents;
import com.vector.megabreakertools.item.ModItems;
import com.vector.megabreakertools.item.custom.DropClusterItem;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = MegaBreakerTools.MODID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    /// While a tool is breaking an area: player -> the drops gathered for the cluster
    private static final Map<ServerPlayer, ClusterContents.Builder> DROP_CLUSTER = new HashMap<>();
    /// The block the player broke with the tool (its drops come after the area): player -> where and when
    private static final Map<ServerPlayer, InitialBlock> INITIAL_BLOCK = new HashMap<>();
    /// Clusters on the ground closer than this (in blocks) join into one.
    public static final double CLUSTER_MERGE_RADIUS = 8.0;

    private record InitialBlock(BlockPos pos, long gameTime) {}

    @SubscribeEvent
    public static void onToolsUsage(BreakBlockEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (!(mainHandItem.getItem() instanceof IModeSwitchable tool)) return;

        BlockPos initialBlockPos = event.getPos();
        if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
            return;
        }

        // Verification or Mode
        int miningMode = IModeSwitchable.getMiningMode(mainHandItem);

        // If in single block mode, does not break additional blocks
        if (miningMode == IModeSwitchable.MODE_SINGLE) {
            return;
        }

        // ONE CALL - WORKS FOR ALL TOOLS!
        List<BlockPos> allPositions = tool.getBlocksToBeDestroyed(tool.getRange(), initialBlockPos, serverPlayer);

        List<BlockPos> blocksToBreak = new ArrayList<>();

        for (BlockPos pos : allPositions) {
            if (pos.equals(initialBlockPos)) continue;

            // Checks if the tool can break the block
            if (!mainHandItem.isCorrectToolForDrops(event.getLevel().getBlockState(pos))) {
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
        addToCluster(level, Vec3.atCenterOf(initialBlockPos), drops.build());
        // The block the player broke drops its items right after this: they go to the cluster too
        INITIAL_BLOCK.put(serverPlayer, new InitialBlock(initialBlockPos, level.getGameTime()));
    }

    /// Drops of the blocks broken by the area mode (and of the block the player broke):
    /// instead of falling on the ground, they go inside the Drop Cluster
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!(event.getBreaker() instanceof ServerPlayer player)) return;

        ClusterContents.Builder cluster = DROP_CLUSTER.get(player);
        if (cluster != null && HARVESTED_BLOCKS.contains(event.getPos())) {
            for (ItemEntity drop : event.getDrops()) cluster.add(drop.getItem());
            event.getDrops().clear();
            return;
        }

        InitialBlock initial = INITIAL_BLOCK.get(player);
        if (initial != null && initial.pos().equals(event.getPos()) && initial.gameTime() == event.getLevel().getGameTime()) {
            INITIAL_BLOCK.remove(player);
            ClusterContents.Builder drops = new ClusterContents.Builder(ClusterContents.EMPTY);
            for (ItemEntity drop : event.getDrops()) drops.add(drop.getItem());
            event.getDrops().clear();
            addToCluster(event.getLevel(), Vec3.atCenterOf(event.getPos()), drops.build());
        }
    }

    /// Makes a cluster with these items here. The old clusters close by join this new one
    /// (their items move here), so the cluster always follows the last area broken.
    private static void addToCluster(ServerLevel level, Vec3 pos, ClusterContents contents) {
        if (contents.isEmpty()) return;

        ClusterContents merged = contents;
        ItemEntity old;
        while ((old = findNearbyCluster(level, pos, null)) != null) {
            merged = DropClusterItem.getContents(old.getItem()).merge(merged);
            old.discard();
        }

        ItemEntity cluster = new ItemEntity(level, pos.x, pos.y, pos.z, DropClusterItem.create(ModItems.DROP_CLUSTER.get(), merged), 0, 0, 0);
        cluster.setDefaultPickUpDelay();
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
    @SubscribeEvent
    public static void onClusterSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ItemEntity item && item.getItem().getItem() instanceof DropClusterItem) {
            item.setUnlimitedLifetime();
        }
    }

    /// Two clusters on the ground close to each other join into one: the older one goes into the newer one
    @SubscribeEvent
    public static void onClusterTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof ItemEntity cluster) || cluster.level().isClientSide()) return;
        if (cluster.tickCount % 10 != 0 || !cluster.isAlive()) return;
        if (!(cluster.getItem().getItem() instanceof DropClusterItem)) return;

        ItemEntity other = findNearbyCluster(cluster.level(), cluster.position(), cluster);
        if (other == null) return;
        // Only the newer cluster (fewer ticks alive) takes the other one
        if (other.tickCount < cluster.tickCount) return;

        ClusterContents merged = DropClusterItem.getContents(cluster.getItem()).merge(DropClusterItem.getContents(other.getItem()));
        cluster.setItem(DropClusterItem.create(ModItems.DROP_CLUSTER.get(), merged));
        other.discard();
    }
}
