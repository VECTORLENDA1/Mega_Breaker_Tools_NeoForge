package com.exoticmoss.megabreakertools.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.function.Consumer;

public interface IModeSwitchable {
    String MINING_MODE_KEY = "MiningMode";
    /// The area size chosen in the tool settings screen (range: 1 = 3x3, 2 = 5x5...). Missing = the biggest.
    String AREA_RANGE_KEY = "AreaRange";
    int MODE = 0;
    int MODE_SINGLE = 1;
    /// Axes only: breaking a log cuts the whole tree (up to maxBlocks)
    int MODE_TREE = 2;
    /// Pickaxes only: mining an ore mines the whole vein (up to maxBlocks)
    int MODE_VEIN = 3;

    /// Each tool defines its range
    int getRange();

    /// Defines whether the tool is 3x3x3 or not
    default boolean is3DMining() {
        return false; // By default it is 2D = 3x3 - 3D = 3x3x3 / false = 3x3 / true = 3x3x3
    }

    /// True for the axes: they have the "Tree Chopper" mode
    default boolean fellsTrees() {
        return false;
    }

    /// True for the pickaxes: they have the "Vein Miner" mode
    default boolean minesVeins() {
        return false;
    }

    /// How many blocks the area mode breaks (3x3 = 9, 3x3x3 = 27...)
    default int maxBlocks() {
        return maxBlocks(getRange());
    }

    /// How many blocks an area of this range breaks.
    default int maxBlocks(int range) {
        int size = range * 2 + 1;
        return is3DMining() ? size * size * size : size * size;
    }

    /// The area range chosen for this tool (never bigger than the tool can do).
    static int areaRange(ItemStack stack) {
        if (!(stack.getItem() instanceof IModeSwitchable tool)) return 1;
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return Math.clamp(tag.getIntOr(AREA_RANGE_KEY, tool.getRange()), 1, tool.getRange());
    }

    /// Chooses the area range of the tool (1 = 3x3, 2 = 5x5...).
    static void setAreaRange(ItemStack stack, int range) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, tag -> {
            CompoundTag nbt = tag.copyTag();
            nbt.putInt(AREA_RANGE_KEY, range);
            return CustomData.of(nbt);
        });
    }

    /// The modes this tool has, in order: Area, (Tree Chopper / Vein Miner), Single Block.
    default java.util.List<Integer> modes() {
        if (fellsTrees()) return java.util.List.of(MODE, MODE_TREE, MODE_SINGLE);
        if (minesVeins()) return java.util.List.of(MODE, MODE_VEIN, MODE_SINGLE);
        return java.util.List.of(MODE, MODE_SINGLE);
    }

    /// "3x3" or "3x3x3" for an area of this range.
    default String sizeName(int range) {
        int size = range * 2 + 1;
        return is3DMining() ? size + "x" + size + "x" + size : size + "x" + size;
    }

    /// Each tool implements how to pick up the blocks
    List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialBlockPos, ServerPlayer player);

    /// Method to get the current mode
    static int getMiningMode(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.getIntOr(MINING_MODE_KEY, MODE);
    }

    /// Method to set the mode
    static void setMiningMode(ItemStack stack, int mode) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, tag -> {
            CompoundTag nbt = tag.copyTag();
            nbt.putInt(MINING_MODE_KEY, mode);
            return CustomData.of(nbt);
        });
    }

    /// The next mode: Area -> (Tree Chopper, axes only / Vein Miner, pickaxes only) -> Single Block -> Area
    static int nextMode(ItemStack stack) {
        int currentMode = getMiningMode(stack);
        IModeSwitchable tool = stack.getItem() instanceof IModeSwitchable t ? t : null;
        if (currentMode == MODE) {
            if (tool != null && tool.fellsTrees()) return MODE_TREE;
            if (tool != null && tool.minesVeins()) return MODE_VEIN;
            return MODE_SINGLE;
        }
        if (currentMode == MODE_TREE || currentMode == MODE_VEIN) return MODE_SINGLE;
        return MODE;
    }

    /// Method to alternate the mode
    static void toggleMiningMode(ItemStack stack) {
        setMiningMode(stack, nextMode(stack));
    }

    /// The name of a mode, as shown to the player ("5x5", "5x5x5", "Tree Chopper", "Single Block")
    default String modeName(int mode) {
        return modeName(mode, getRange());
    }

    /// The same, with the area of the chosen range.
    default String modeName(int mode, int range) {
        if (mode == MODE_SINGLE) return "Single Block";
        if (mode == MODE_TREE) return "Tree Chopper";
        if (mode == MODE_VEIN) return "Vein Miner";
        return sizeName(range);
    }

    /// Standard method for adding tooltip
    default void addModeTooltip(ItemStack stack, Consumer<Component> tooltipComponents) {
        String modeText = modeName(IModeSwitchable.getMiningMode(stack), IModeSwitchable.areaRange(stack));

        tooltipComponents.accept(Component.literal("Mode: " + modeText)
                .withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.megabreakertools.settings_hint")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
}