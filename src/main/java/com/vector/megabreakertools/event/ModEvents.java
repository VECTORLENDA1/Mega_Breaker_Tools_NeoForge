package com.vector.megabreakertools.event;


import com.vector.megabreakertools.MegaBreakerTools;
import com.vector.megabreakertools.item.custom.pickaxe.*;

import com.vector.megabreakertools.item.custom.shovel.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onToolsUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        //**********************************************************************************************************************//

                                                    //Pickaxe and Mega Pickaxe//

        //**********************************************************************************************************************//




        // SIMPLE SHOVEL //
        if (mainHandItem.getItem() instanceof SimpleBreakerItem Simplebreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for (BlockPos pos : SimpleBreakerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !Simplebreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA SIMPLE SHOVEL //
        if(mainHandItem.getItem() instanceof MegaSimpleBreakerItem MegaSimpleBreaker && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaSimpleBreakerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !MegaSimpleBreaker.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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


        //ADVANCE SHOVEL//
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

        // MEGA ADVANCE SHOVEL//
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

        //ULTRA SHOVEL//
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

        // MEGA ULTRA SHOVEL//
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

        //ULTIMATE SHOVEL//
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

        // MEGA ULTIMATE SHOVEL//
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

        //LEGENDARY SHOVEL//
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

        // MEGA LEGENDARY SHOVEL//
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

        //ATOMIC SHOVEL//
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

        // MEGA ATOMIC SHOVEL//
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


        //**********************************************************************************************************************//

                                                    //Shovels and Mega Shovels//

        //**********************************************************************************************************************//


        // SIMPLE SHOVEL //
        if (mainHandItem.getItem() instanceof SimpleShovelItem SimpleShovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for (BlockPos pos : SimpleShovelItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !SimpleShovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA SIMPLE SHOVEL //
        if(mainHandItem.getItem() instanceof MegaSimpleShovelItem MegaSimpleshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaSimpleShovelItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !MegaSimpleshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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


        //ADVANCE SHOVEL//
        if(mainHandItem.getItem() instanceof AdvanceShovelItem Advanceshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AdvanceShovelItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Advanceshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ADVANCE SHOVEL//
        if(mainHandItem.getItem() instanceof MegaAdvanceShovelItem MegaAdvanceshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAdvanceShovelItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAdvanceshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ULTRA SHOVEL//
        if(mainHandItem.getItem() instanceof UltraShovelItem Ultrashovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltraShovelItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultrashovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ULTRA SHOVEL//
        if(mainHandItem.getItem() instanceof MegaUltraShovelItem MegaUltrashovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltraShovelItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltrashovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ULTIMATE SHOVEL//
        if(mainHandItem.getItem() instanceof UltimateShovelItem Ultimateshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltimateShovelItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultimateshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ULTIMATE SHOVEL//
        if(mainHandItem.getItem() instanceof MegaUltimateShovelItem MegaUltimateshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltimateShovelItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltimateshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //LEGENDARY SHOVEL//
        if(mainHandItem.getItem() instanceof LegendaryShovelItem Legendaryshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : LegendaryShovelItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Legendaryshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA LEGENDARY SHOVEL//
        if(mainHandItem.getItem() instanceof MegaLegendaryShovelItem MegaLegendaryshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaLegendaryShovelItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaLegendaryshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ATOMIC SHOVEL//
        if(mainHandItem.getItem() instanceof AtomicShovelItem Atomicshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AtomicShovelItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Atomicshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ATOMIC SHOVEL//
        if(mainHandItem.getItem() instanceof MegaAtomicShovelItem MegaAtomicshovel && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAtomicShovelItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAtomicshovel.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
