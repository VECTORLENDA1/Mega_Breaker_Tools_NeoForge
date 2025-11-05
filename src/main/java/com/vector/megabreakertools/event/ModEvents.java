package com.vector.megabreakertools.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vector.megabreakertools.MegaBreakerTools;
import net.vector.megabreakertools.item.custom.pickaxe.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MegaBreakerTools.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        // SIMPLE BREAKER //
        if (mainHandItem.getItem() instanceof SimpleBreakerItem SimpleBreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for (BlockPos pos : SimpleBreakerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !SimpleBreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        // MEGA SIMPLE BREAKER //
        if(mainHandItem.getItem() instanceof MegaSimpleBreakerItem MegaSimplebreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaSimpleBreakerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !MegaSimplebreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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


        //ADVANCE BREAKER//
        if(mainHandItem.getItem() instanceof AdvanceBreakerItem Advancebreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AdvanceBreakerItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Advancebreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        // MEGA ADVANCE BREAKER//
        if(mainHandItem.getItem() instanceof MegaAdvanceBreakerItem MegaAdvancebreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAdvanceBreakerItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAdvancebreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        //ULTRA BREAKER//
        if(mainHandItem.getItem() instanceof UltraBreakerItem Ultrabreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltraBreakerItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultrabreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        // MEGA ULTRA BREAKER//
        if(mainHandItem.getItem() instanceof MegaUltraBreakerItem MegaUltrabreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltraBreakerItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltrabreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        //ULTIMATE BREAKER//
        if(mainHandItem.getItem() instanceof UltimateBreakerItem Ultimatebreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltimateBreakerItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultimatebreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        // MEGA ULTIMATE BREAKER//
        if(mainHandItem.getItem() instanceof MegaUltimateBreakerItem MegaUltimatebreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltimateBreakerItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltimatebreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        //LEGENDARY BREAKER//
        if(mainHandItem.getItem() instanceof LegendaryBreakerItem Legendarybreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : LegendaryBreakerItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Legendarybreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        // MEGA LEGENDARY BREAKER//
        if(mainHandItem.getItem() instanceof MegaLegendaryBreakerItem MegaLegendarybreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaLegendaryBreakerItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaLegendarybreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        //ATOMIC BREAKER//
        if(mainHandItem.getItem() instanceof AtomicBreakerItem Atomicbreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AtomicBreakerItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Atomicbreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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

        // MEGA ATOMIC BREAKER//
        if(mainHandItem.getItem() instanceof MegaAtomicBreakerItem MegaAtomicbreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAtomicBreakerItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAtomicbreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
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
}
