package com.vector.megabreakertools.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public interface IModeSwitchable {
    String MINING_MODE_KEY = "MiningMode";
    int MODE = 0;
    int MODE_SINGLE = 1;

    /// Each tool defines its range
    int getRange();

    /// Defines whether the tool is 3x3x3 or not
    default boolean is3DMining() {
        return false; // By default it is 2D = 3x3 - 3D = 3x3x3 / false = 3x3 / true = 3x3x3
    }

    /// Each tool implements how to pick up the blocks
    List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialBlockPos, ServerPlayer player);

    /// Method to get the current mode
    static int getMiningMode(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.getInt(MINING_MODE_KEY);
    }

    /// Method to set the mode
    static void setMiningMode(ItemStack stack, int mode) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, tag -> {
            CompoundTag nbt = tag.copyTag();
            nbt.putInt(MINING_MODE_KEY, mode);
            return CustomData.of(nbt);
        });
    }

    /// Method to alternate the mode
    static void toggleMiningMode(ItemStack stack) {
        int currentMode = getMiningMode(stack);
        setMiningMode(stack, currentMode == MODE ? MODE_SINGLE : MODE);
    }

    /// Standard method for adding tooltip
    default void addModeTooltip(ItemStack stack, List<Component> tooltipComponents) {
        int mode = IModeSwitchable.getMiningMode(stack);
        int range = getRange();
        int size = (range * 2) + 1; /// Calculates the size (e.g. range=1 -> 3x3, range=2 -> 5x5)

        String modeText;
        if (mode == MODE_SINGLE) {
            modeText = "Single Block";
        } else {
            /// Shows "3x3x3" for 3D and "3x3" for 2D
            if (is3DMining()) {
                modeText = size + "x" + size + "x" + size;
            } else {
                modeText = size + "x" + size;
            }
        }

        tooltipComponents.add(Component.literal("Mode: " + modeText)
                .withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("Shift + Scroll to change")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
}