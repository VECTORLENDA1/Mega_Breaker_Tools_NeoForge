package com.vector.megabreakertools.item.custom.shovel;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.vector.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
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

public class MegaLegendaryExcavatorItem extends DiggerItem implements IModeSwitchable{
    public MegaLegendaryExcavatorItem(Tier pTier, Properties pProperties) {
        super(pTier, BlockTags.MINEABLE_WITH_SHOVEL, pProperties);
    }

    @Override
    public int getRange() {
        return 5; /// 11x11x11 (range=5)
    }

    @Override
    public boolean is3DMining() {
        return true; /// false = 11x11 = 2D, true = 11x11x11 = 3D
    }

    /// This shows the text on the tooltip of the tool
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        addModeTooltip(stack, tooltipComponents);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    /// Calculates an area of destruction based on the direction of impact
    @Override
    public List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if (traceResult.getDirection() == Direction.DOWN) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {  // A destruição vai a partir do bloco para baixo
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y + 5, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO UP
        if (traceResult.getDirection() == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {  // A destruição vai a partir do bloco para cima
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y - 5, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO NORTH
        if (traceResult.getDirection() == Direction.NORTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -1; y <= 9; y++) {
                    for (int z = -range; z <= range; z++) {  // A destruição vai para frente (no eixo Z negativo)
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ() + z + 5));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO SOUTH
        if (traceResult.getDirection() == Direction.SOUTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -1; y <= 9; y++) {
                    for (int z = -range; z <= range; z++) {  // A destruição vai para frente (no eixo Z positivo)
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ() + z - 5));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO EAST
        if (traceResult.getDirection() == Direction.EAST) {
            for (int x = -range; x <= range; x++) {  // A destruição vai para frente (no eixo X positivo)
                for (int y = -1; y <= 9; y++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x - 5, initialBlockPos.getY() + y, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO WEST
        if (traceResult.getDirection() == Direction.WEST) {
            for (int x = -range; x <= range; x++) {  // A destruição vai para frente (no eixo X negativo)
                for (int y = -1; y <= 9; y++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x + 5, initialBlockPos.getY() + y, initialBlockPos.getZ() + z));
                    }
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

        /// If it is single block mode, it returns only the clicked position
        if (mode == IModeSwitchable.MODE_SINGLE) {
            positions.add(initialPos);
            return positions;
        }

        /// Area mode – uses the range of the tool and respects the direction
        if (!(player instanceof ServerPlayer serverPlayer)) {
            positions.add(initialPos);
            return positions;
        }

        /// Use the same logic as getBlocksToBeDestroyed for 3D area
        return getBlocksToBeDestroyed(getRange(), initialPos, serverPlayer);
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
                BlockState flattenState = currentState.getToolModifiedState(
                        new UseOnContext(level, player, context.getHand(), itemStack,
                                new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside())),
                        net.neoforged.neoforge.common.ItemAbilities.SHOVEL_FLATTEN,
                        false
                );

                if (flattenState != null && level.getBlockState(pos.above()).isAir()) {
                    modifiedState = flattenState;
                    anyFlattened = true;
                }
                /// Try to put out fire
                else {
                    BlockState douseState = currentState.getToolModifiedState(
                            new UseOnContext(level, player, context.getHand(), itemStack,
                                    new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside())),
                            net.neoforged.neoforge.common.ItemAbilities.SHOVEL_DOUSE,
                            false
                    );

                    if (douseState != null) {
                        modifiedState = douseState;
                        anyDouse = true;
                    }
                }

                /// Apply the modification
                if (modifiedState != null && !level.isClientSide) {
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
                if (player != null && !level.isClientSide) {
                    itemStack.hurtAndBreak(0, player, LivingEntity.getSlotForHand(context.getHand()));
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    public static BlockState getShovelPathingState(BlockState originalState) {
        return FLATTENABLES.get(originalState.getBlock());
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
    }
}

