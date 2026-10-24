package com.exoticmoss.megabreakertools.item.custom.shovel;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleShovelItem extends Item implements IModeSwitchable {
    public SimpleShovelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getRange() {
        return 1; /// 3x3 (range=1)
    }

    @Override
    public boolean is3DMining() {
        return false; /// false = 3x3 = 2D, true = 3x3x3 = 3D
    }

    /// This shows the text on the tooltip of the tool
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        addModeTooltip(stack, tooltipComponents);
        super.appendHoverText(stack, context, display, tooltipComponents, tooltipFlag);
    }

    /// Calculates an area of destruction based on the direction of impact
    @Override
    public List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if (traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y));
                }
            }
        }

        if(traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ()));
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                }
            }
        }

        return positions;
    }

    protected static final Map<Block, BlockState> FLATTENABLES = Maps.newHashMap(
            new ImmutableMap.Builder<Block, BlockState>()
                    .put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState())
                    .build()
    );

    /// Method to get the positions for flatten based on the mode
    private List<BlockPos> getFlattenPositions(BlockPos initialPos, Player player, ItemStack stack) {
        List<BlockPos> positions = new ArrayList<>();

        /// Verify the mode
        int mode = IModeSwitchable.getMiningMode(stack);

        /// If it is single blocked, it returns only the clicked position
        if (mode == IModeSwitchable.MODE_SINGLE) {
            positions.add(initialPos);
            return positions;
        }

        /// Area mode – uses the range of the tool
        int range = IModeSwitchable.areaRange(stack);

        /// Determines the direction the player is looking
        BlockHitResult traceResult = player.level().clip(new ClipContext(
                player.getEyePosition(1f),
                player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f)),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player));

        if (traceResult.getType() == HitResult.Type.MISS) {
            positions.add(initialPos);
            return positions;
        }

        Direction direction = traceResult.getDirection();

        /// Flatten always happens in the horizontal plane (XZ) even if you're looking up or down
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                positions.add(new BlockPos(
                        initialPos.getX() + x,
                        initialPos.getY(),
                        initialPos.getZ() + z
                ));
            }
        }

        return positions;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        ItemStack itemStack = context.getItemInHand();

        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            Player player = context.getPlayer();

            /// Gets the positions based on the mode
            List<BlockPos> positionsToFlatten = getFlattenPositions(blockpos, player, itemStack);

            boolean anyFlattened = false;
            boolean anyDouse = false;

            /// Iterate over all positions
            for (BlockPos pos : positionsToFlatten) {
                BlockState currentState = level.getBlockState(pos);
                BlockState modifiedState = null;

                /// Try to make flatten
                BlockState flattenState = com.exoticmoss.megabreakertools.util.ToolActions.getToolModifiedState(currentState, new UseOnContext(player, context.getHand(),
                                new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside())),
                        com.exoticmoss.megabreakertools.util.ToolActions.SHOVEL_FLATTEN,
                        false
                );

                if (flattenState != null && level.getBlockState(pos.above()).isAir()) {
                    modifiedState = flattenState;
                    anyFlattened = true;
                }
                /// Try to put out fire
                else {
                    BlockState douseState = com.exoticmoss.megabreakertools.util.ToolActions.getToolModifiedState(currentState, new UseOnContext(player, context.getHand(),
                                    new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside())),
                            com.exoticmoss.megabreakertools.util.ToolActions.SHOVEL_DOUSE,
                            false
                    );

                    if (douseState != null) {
                        modifiedState = douseState;
                        anyDouse = true;
                    }
                }

                /// Apply the modification
                if (modifiedState != null && !level.isClientSide()) {
                    level.setBlock(pos, modifiedState, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, modifiedState));
                }
            }

            /// Sounds and Tool Damage
            if (anyFlattened || anyDouse) {
                if (anyFlattened) {
                    level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                if (anyDouse && !level.isClientSide()) {
                    level.levelEvent(null, 1009, blockpos, 0);
                }

                /// Applies durability based on the number of blocks modified
                if (player != null && !level.isClientSide()) {
                    itemStack.hurtAndBreak(0, player, context.getHand());
                }

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @org.jspecify.annotations.Nullable
    public static BlockState getShovelPathingState(BlockState originalState) {
        return FLATTENABLES.get(originalState.getBlock());
    }

}

