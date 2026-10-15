package com.vector.megabreakertools.event;

import com.vector.megabreakertools.MegaBreakerTools;
import com.vector.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
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
    /// While a tool is breaking an area: player -> where the drops are gathered (the block the player broke)
    private static final Map<ServerPlayer, Vec3> DROP_CLUSTER = new HashMap<>();

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

        // All the drops of the area are gathered in one place (see onBlockDrops)
        DROP_CLUSTER.put(serverPlayer, Vec3.atCenterOf(initialBlockPos));
        try {
            for (BlockPos pos : blocksToBreak) {
                serverPlayer.gameMode.destroyBlock(pos);
            }
        } finally {
            DROP_CLUSTER.remove(serverPlayer);
        }

        mainHandItem.setDamageValue(initialDamage);

        blocksToBreak.forEach(HARVESTED_BLOCKS::remove);
    }

    /// Drops of the blocks broken by the area mode: instead of falling where each block was,
    /// they all go to the block the player broke, so they come together in one cluster
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!(event.getBreaker() instanceof ServerPlayer player)) return;
        Vec3 cluster = DROP_CLUSTER.get(player);
        if (cluster == null || !HARVESTED_BLOCKS.contains(event.getPos())) return;

        for (ItemEntity drop : event.getDrops()) {
            drop.setPos(cluster.x, cluster.y, cluster.z);
            drop.setDeltaMovement(Vec3.ZERO);
        }
    }
}
