package com.vector.megabreakertools.event;


import com.vector.megabreakertools.MegaBreakerTools;
import com.vector.megabreakertools.item.custom.axe.*;
import com.vector.megabreakertools.item.custom.hoe.*;
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
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Vec3i;

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




        // SIMPLE BREAKER //
        if (mainHandItem.getItem() instanceof SimplePickaxeItem Simplepickaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for (BlockPos pos : SimplePickaxeItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !Simplepickaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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


        //ADVANCE BREAKER//
        if(mainHandItem.getItem() instanceof AdvancePickaxeItem Advancepickaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AdvancePickaxeItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Advancepickaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof UltraPickaxeItem Ultrapickaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltraPickaxeItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultrapickaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof UltimatePickaxeItem Ultimatepickaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltimatePickaxeItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultimatepickaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof LegendaryPickaxeItem Legendarypickaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : LegendaryPickaxeItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Legendarypickaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof AtomicPickaxeItem Atomicpickaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AtomicPickaxeItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Atomicpickaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof MegaSimpleExcavatorItem MegaSimpleexcavator && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaSimpleExcavatorItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !MegaSimpleexcavator.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof MegaAdvanceExcavatorItem MegaAdvanceexcavator && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAdvanceExcavatorItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAdvanceexcavator.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof MegaUltraExcavatorItem MegaUltraexcavator && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltraExcavatorItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltraexcavator.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof MegaUltimateExcavatorItem MegaUltimateexcavator && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltimateExcavatorItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltimateexcavator.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof MegaLegendaryExcavatorItem MegaLegendaryexcavator && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaLegendaryExcavatorItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaLegendaryexcavator.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
        if(mainHandItem.getItem() instanceof MegaAtomicExcavatorItem MegaAtomicexcavator && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAtomicExcavatorItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAtomicexcavator.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

                                                    //Axes and Mega Axes//

        //**********************************************************************************************************************//


        // SIMPLE AXE //
        if (mainHandItem.getItem() instanceof SimpleAxeItem Simpleaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for (BlockPos pos : SimpleAxeItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !Simpleaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA SIMPLE AXE //
        if(mainHandItem.getItem() instanceof MegaSimpleLumberjackItem MegaSimplelumberjack && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaSimpleLumberjackItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !MegaSimplelumberjack.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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


        //ADVANCE AXE//
        if(mainHandItem.getItem() instanceof AdvanceAxeItem Advanceaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AdvanceAxeItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Advanceaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ADVANCE AXE//
        if(mainHandItem.getItem() instanceof MegaAdvanceLumberjackItem MegaAdvancelumberjack && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAdvanceLumberjackItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAdvancelumberjack.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ULTRA AXE//
        if(mainHandItem.getItem() instanceof UltraAxeItem Ultraaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltraAxeItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultraaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ULTRA AXE//
        if(mainHandItem.getItem() instanceof MegaUltraLumberjackItem MegaUltralumberjack && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltraLumberjackItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltralumberjack.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ULTIMATE AXE//
        if(mainHandItem.getItem() instanceof UltimateAxeItem Ultimateaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltimateAxeItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultimateaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ULTIMATE AXE//
        if(mainHandItem.getItem() instanceof MegaUltimateLumberjackItem MegaUltimatelumberjack && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltimateLumberjackItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltimatelumberjack.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //LEGENDARY AXE//
        if(mainHandItem.getItem() instanceof LegendaryAxeItem Legendaryaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : LegendaryAxeItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Legendaryaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA LEGENDARY AXE//
        if(mainHandItem.getItem() instanceof MegaLegendaryLumberjackItem MegaLegendarylumberjack && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaLegendaryLumberjackItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaLegendarylumberjack.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ATOMIC AXE//
        if(mainHandItem.getItem() instanceof AtomicAxeItem Atomicaxe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AtomicAxeItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Atomicaxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ATOMIC AXE//
        if(mainHandItem.getItem() instanceof MegaAtomicLumberjackItem MegaAtomiclumberjack && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAtomicLumberjackItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAtomiclumberjack.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

                                                    //Hoes and Mega Hoes//

        //**********************************************************************************************************************//


        // SIMPLE HOE //
        if (mainHandItem.getItem() instanceof SimpleHoeItem Simplehoe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for (BlockPos pos : SimpleHoeItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !Simplehoe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA SIMPLE HOE //
        if(mainHandItem.getItem() instanceof MegaSimpleScytheItem MegaSimplescythe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaSimpleScytheItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || !MegaSimplescythe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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


        //ADVANCE HOE//
        if(mainHandItem.getItem() instanceof AdvanceHoeItem Advancehoe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AdvanceHoeItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Advancehoe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ADVANCE HOE//
        if(mainHandItem.getItem() instanceof MegaAdvanceScytheItem MegaAdvancescythe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAdvanceScytheItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAdvancescythe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ULTRA HOE//
        if(mainHandItem.getItem() instanceof UltraHoeItem Ultrahoe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltraHoeItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultrahoe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ULTRA HOE//
        if(mainHandItem.getItem() instanceof MegaUltraScytheItem MegaUltrascythe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltraScytheItem.getBlocksToBeDestroyed(3, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltrascythe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ULTIMATE HOE//
        if(mainHandItem.getItem() instanceof UltimateHoeItem Ultimatehoe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : UltimateHoeItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Ultimatehoe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ULTIMATE HOE//
        if(mainHandItem.getItem() instanceof MegaUltimateScytheItem MegaUltimatescythe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaUltimateScytheItem.getBlocksToBeDestroyed(4, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaUltimatescythe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //LEGENDARY HOE//
        if(mainHandItem.getItem() instanceof LegendaryHoeItem Legendaryhoe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : LegendaryHoeItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Legendaryhoe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA LEGENDARY HOE//
        if(mainHandItem.getItem() instanceof MegaLegendaryScytheItem MegaLegendaryscythe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaLegendaryScytheItem.getBlocksToBeDestroyed(5, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaLegendaryscythe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        //ATOMIC HOE//
        if(mainHandItem.getItem() instanceof AtomicHoeItem Atomichoe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : AtomicHoeItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !Atomichoe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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

        // MEGA ATOMIC HOE//
        if(mainHandItem.getItem() instanceof MegaAtomicScytheItem MegaAtomicscythe && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            List<BlockPos> blocksToBreak = new ArrayList<>();

            for(BlockPos pos : MegaAtomicScytheItem.getBlocksToBeDestroyed(6, initialBlockPos, serverPlayer)) {
                if(pos.equals(initialBlockPos) || !MegaAtomicscythe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
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
