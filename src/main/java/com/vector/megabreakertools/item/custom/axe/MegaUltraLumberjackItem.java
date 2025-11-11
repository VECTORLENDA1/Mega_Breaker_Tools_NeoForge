package com.vector.megabreakertools.item.custom.axe;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MegaUltraLumberjackItem extends DiggerItem {
    public MegaUltraLumberjackItem(Tier pTier, Properties pProperties) {
        super(pTier, BlockTags.MINEABLE_WITH_AXE, pProperties);
    }

    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialBlockPos, ServerPlayer player) {
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
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y + 1, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO UP
        if (traceResult.getDirection() == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {  // A destruição vai a partir do bloco para cima
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y - 1, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO NORTH
        if (traceResult.getDirection() == Direction.NORTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -1; y <= 5; y++) {
                    for (int z = -range; z <= range; z++) {  // A destruição vai para frente (no eixo Z negativo)
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ() + z + 3));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO SOUTH
        if (traceResult.getDirection() == Direction.SOUTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -1; y <= 5; y++) {
                    for (int z = -range; z <= range; z++) {  // A destruição vai para frente (no eixo Z positivo)
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ() + z - 3));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO EAST
        if (traceResult.getDirection() == Direction.EAST) {
            for (int x = -range; x <= range; x++) {  // A destruição vai para frente (no eixo X positivo)
                for (int y = -1; y <= 5; y++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x - 3, initialBlockPos.getY() + y, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO WEST
        if (traceResult.getDirection() == Direction.WEST) {
            for (int x = -range; x <= range; x++) {  // A destruição vai para frente (no eixo X negativo)
                for (int y = -1; y <= 5; y++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x + 3, initialBlockPos.getY() + y, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        return positions;
    }

    protected static final Map<Block, Block> STRIPPABLES = new ImmutableMap.Builder<Block, Block>()
            .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
            .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
            .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
            .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
            .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
            .put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
            .put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD)
            .put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG)
            .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
            .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
            .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
            .put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
            .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
            .put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
            .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM)
            .put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
            .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
            .put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
            .put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD)
            .put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG)
            .put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK)
            .build();

    /**
     * Called when this item is used when targeting a Block
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();

        if (player == null || level.isClientSide) return InteractionResult.PASS;
        if (playerHasShieldUseIntent(context)) return InteractionResult.PASS;

        int range = 3; // 7x7x7 equivalente
        List<BlockPos> targets = getBlocksToBeDestroyed(range, clickedPos, (ServerPlayer) player);
        boolean changedAny = false;

        for (BlockPos pos : targets) {
            BlockState state = level.getBlockState(pos);
            Optional<BlockState> opt = this.evaluateNewBlockState(level, pos, player, state,
                    new UseOnContext(player, context.getHand(), new BlockHitResult(player.position(), context.getClickedFace(), pos, false)));
            if (opt.isPresent()) {
                BlockState newState = opt.get();
                level.setBlock(pos, newState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
                changedAny = true;
            }
        }

        if (changedAny) {
            // som principal (strip/scrape/wax é decidido no evaluate)
            Optional<BlockState> headOpt = this.evaluateNewBlockState(level, clickedPos, player, level.getBlockState(clickedPos), context);
            headOpt.ifPresent(s -> level.playSound(null, clickedPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F));
            context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            if (player instanceof ServerPlayer sp) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sp, clickedPos, context.getItemInHand());
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private static boolean playerHasShieldUseIntent(UseOnContext context) {
        Player player = context.getPlayer();
        return context.getHand().equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().is(Items.SHIELD) && !player.isSecondaryUseActive();
    }

    private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, @Nullable Player player, BlockState state, UseOnContext p_40529_) {
        Optional<BlockState> optional = Optional.ofNullable(state.getToolModifiedState(p_40529_, net.neoforged.neoforge.common.ItemAbilities.AXE_STRIP, false));
        if (optional.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional1 = Optional.ofNullable(state.getToolModifiedState(p_40529_, net.neoforged.neoforge.common.ItemAbilities.AXE_SCRAPE, false));
            if (optional1.isPresent()) {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3005, pos, 0);
                return optional1;
            } else {
                Optional<BlockState> optional2 = Optional.ofNullable(state.getToolModifiedState(p_40529_, net.neoforged.neoforge.common.ItemAbilities.AXE_WAX_OFF, false));
                if (optional2.isPresent()) {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(player, 3004, pos, 0);
                    return optional2;
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    public static BlockState getAxeStrippingState(BlockState originalState) {
        Block block = STRIPPABLES.get(originalState.getBlock());
        return block != null ? block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, originalState.getValue(RotatedPillarBlock.AXIS)) : null;
    }

    private Optional<BlockState> getStripped(BlockState unstrippedState) {
        return Optional.ofNullable(STRIPPABLES.get(unstrippedState.getBlock()))
                .map(p_150689_ -> p_150689_.defaultBlockState().setValue(RotatedPillarBlock.AXIS, unstrippedState.getValue(RotatedPillarBlock.AXIS)));
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility);
    }
}

