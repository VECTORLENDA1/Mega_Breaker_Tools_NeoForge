package com.exoticmoss.megabreakertools.util;

import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/// What the tools do when used on a block (strip a log, make a path, till the ground...).
/// On NeoForge this was BlockState#getToolModifiedState with an ItemAbility; on Fabric it reads the
/// same vanilla tables (STRIPPABLES, FLATTENABLES, WAX_OFF_BY_BLOCK...), so blocks other mods add with the
/// Fabric registries (StrippableBlockRegistry, FlattenableBlockRegistry, OxidizableBlocksRegistry) work too.
public final class ToolActions {
    public enum Action { AXE_STRIP, AXE_SCRAPE, AXE_WAX_OFF, SHOVEL_FLATTEN, SHOVEL_DOUSE, HOE_TILL }

    public static final Action AXE_STRIP = Action.AXE_STRIP;
    public static final Action AXE_SCRAPE = Action.AXE_SCRAPE;
    public static final Action AXE_WAX_OFF = Action.AXE_WAX_OFF;
    public static final Action SHOVEL_FLATTEN = Action.SHOVEL_FLATTEN;
    public static final Action SHOVEL_DOUSE = Action.SHOVEL_DOUSE;
    public static final Action HOE_TILL = Action.HOE_TILL;

    private ToolActions() {
    }

    /// The new state of the block after the action, or null if the action does nothing on it.
    /// "simulate" = only check, without side effects (dropping the hanging roots, putting out the campfire)
    public static @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, Action action, boolean simulate) {
        switch (action) {
            case AXE_STRIP -> {
                Block stripped = Tables.strippables().get(state.getBlock());
                if (stripped == null) return null;
                BlockState result = stripped.defaultBlockState();
                if (state.hasProperty(RotatedPillarBlock.AXIS) && result.hasProperty(RotatedPillarBlock.AXIS)) {
                    result = result.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
                }
                return result;
            }
            case AXE_SCRAPE -> {
                return WeatheringCopper.getPrevious(state).orElse(null);
            }
            case AXE_WAX_OFF -> {
                Block waxOff = HoneycombItem.WAX_OFF_BY_BLOCK.get().get(state.getBlock());
                return waxOff == null ? null : waxOff.withPropertiesOf(state);
            }
            case SHOVEL_FLATTEN -> {
                return Tables.flattenables().get(state.getBlock());
            }
            case SHOVEL_DOUSE -> {
                if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
                    if (!simulate) {
                        CampfireBlock.dowse(context.getPlayer(), context.getLevel(), context.getClickedPos(), state);
                    }
                    return state.setValue(CampfireBlock.LIT, Boolean.FALSE);
                }
                return null;
            }
            case HOE_TILL -> {
                // Same as the tillable blocks of the vanilla hoe
                Block block = state.getBlock();
                if (block == Blocks.ROOTED_DIRT) {
                    if (!simulate && !context.getLevel().isClientSide()) {
                        Direction face = context.getClickedFace();
                        Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), face, new ItemStack(Items.HANGING_ROOTS));
                    }
                    return Blocks.DIRT.defaultBlockState();
                } else if ((block == Blocks.GRASS_BLOCK || block == Blocks.DIRT_PATH || block == Blocks.DIRT || block == Blocks.COARSE_DIRT)
                        && context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
                    return block == Blocks.COARSE_DIRT ? Blocks.DIRT.defaultBlockState() : Blocks.FARMLAND.defaultBlockState();
                }
                return null;
            }
        }
        return null;
    }

    /// Reads the protected tables of the vanilla tools (a subclass can see them; it is never created)
    private static final class Tables {
        static Map<Block, Block> strippables() {
            return Axe.strippables();
        }

        static Map<Block, BlockState> flattenables() {
            return Shovel.flattenables();
        }

        private abstract static class Axe extends AxeItem {
            private Axe(Properties properties) {
                super(null, 0, 0, properties);
            }

            static Map<Block, Block> strippables() {
                return STRIPPABLES;
            }
        }

        private abstract static class Shovel extends ShovelItem {
            private Shovel(Properties properties) {
                super(null, 0, 0, properties);
            }

            static Map<Block, BlockState> flattenables() {
                return FLATTENABLES;
            }
        }
    }
}
