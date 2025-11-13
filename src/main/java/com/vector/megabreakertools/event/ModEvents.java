package com.vector.megabreakertools.event;

import com.vector.megabreakertools.MegaBreakerTools;
import com.vector.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = MegaBreakerTools.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onToolsUsage(BlockEvent.BreakEvent event) {
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
            if (tool instanceof DiggerItem digger) {
                if (!digger.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }
            }

            blocksToBreak.add(pos);
        }

        HARVESTED_BLOCKS.addAll(blocksToBreak);

        int initialDamage = mainHandItem.getDamageValue();

        for (BlockPos pos : blocksToBreak) {
            serverPlayer.gameMode.destroyBlock(pos);
        }

        mainHandItem.setDamageValue(initialDamage);

        blocksToBreak.forEach(HARVESTED_BLOCKS::remove);
    }
}