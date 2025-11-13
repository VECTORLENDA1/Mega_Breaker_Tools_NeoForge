package com.vector.megabreakertools.item.custom.hoe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.vector.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class UltimateHoeItem extends DiggerItem implements IModeSwitchable{
    public UltimateHoeItem(Tier pTier, Properties pProperties) {
        super(pTier, BlockTags.MINEABLE_WITH_HOE, pProperties);
    }

    @Override
    public int getRange() {
        return 4; /// 9x9 (range=4)
    }

    @Override
    public boolean is3DMining() {
        return false; /// false = 9x9 = 2D, true = 9x9x9 = 3D
    }

    /// This shows the text on the tooltip of the tool
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        addModeTooltip(stack, tooltipComponents);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
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
                for(int y = -1; y <= 7; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ()));
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -1; y <= 7; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                }
            }
        }

        return positions;
    }

    /// List of blocks that can be tillable
    @Deprecated
    protected static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> TILLABLES = Maps.newHashMap(
            ImmutableMap.of(
                    Blocks.GRASS_BLOCK,
                    Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())),
                    Blocks.DIRT_PATH,
                    Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())),
                    Blocks.DIRT,
                    Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())),
                    Blocks.COARSE_DIRT,
                    Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.DIRT.defaultBlockState())),
                    Blocks.ROOTED_DIRT,
                    Pair.of(p_238242_ -> true, changeIntoStateAndDropItem(Blocks.DIRT.defaultBlockState(), Items.HANGING_ROOTS))
            )
    );

    /// Method for obtaining positions based on the mode
    private List<BlockPos> getHoePositions(BlockPos initialPos, Player player, ItemStack stack) {
        List<BlockPos> positions = new ArrayList<>();

        /// Verify the Mode
        int mode = IModeSwitchable.getMiningMode(stack);

        /// If it is single blocked, it returns only the clicked position
        if (mode == IModeSwitchable.MODE_SINGLE) {
            positions.add(initialPos);
            return positions;
        }

        /// Area mode – uses the range of the tool
        if (!(player instanceof ServerPlayer serverPlayer)) {
            positions.add(initialPos);
            return positions;
        }

        return getBlocksToBeDestroyed(getRange(), initialPos, serverPlayer);
    }

    /// Checks if the block is a mature plantation
    private boolean isMatureCrop(BlockState state) {
        Block block = state.getBlock();

        /// Normal crops (Wheat, Carrots, tatoes, Beetroot)
        if (block instanceof CropBlock cropBlock) {
            return cropBlock.isMaxAge(state);
        }

        /// NetherWart
        if (block instanceof NetherWartBlock) {
            return state.getValue(NetherWartBlock.AGE) == 3;
        }

        /// Cocoa
        if (block instanceof CocoaBlock) {
            return state.getValue(CocoaBlock.AGE) == 2;
        }

        /// Sweet Berry Bush
        if (block instanceof SweetBerryBushBlock) {
            return state.getValue(SweetBerryBushBlock.AGE) == 3;
        }

        return false;
    }

    /// Harvest the crop and replant
    private boolean harvestAndReplant(Level level, BlockPos pos, BlockState state, Player player) {
        Block block = state.getBlock();

        /// Do nothing on the client
        if (level.isClientSide) return false;

        ServerLevel serverLevel = (ServerLevel) level;

        /// Normal crops (Wheat, Carrots, tatoes, Beetroot)
        if (block instanceof CropBlock cropBlock) {
            /// Drop the items
            List<ItemStack> drops = state.getDrops(new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos)));

            for (ItemStack drop : drops) {
                Block.popResource(level, pos, drop);
            }

            /// Solution for the "getAgeProperty()" since is protected, usa BlockStateProperties.AGE_7
            /// Replante (age = 0)
            if (state.hasProperty(BlockStateProperties.AGE_7)) {
                level.setBlock(pos, state.setValue(BlockStateProperties.AGE_7, 0), 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                return true;
            } else if (state.hasProperty(BlockStateProperties.AGE_3)) {
                level.setBlock(pos, state.setValue(BlockStateProperties.AGE_3, 0), 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                return true;
            } else if (state.hasProperty(BlockStateProperties.AGE_5)) {
                level.setBlock(pos, state.setValue(BlockStateProperties.AGE_5, 0), 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                return true;
            }
        }

        /// NetherWart
        if (block instanceof NetherWartBlock) {
            List<ItemStack> drops = state.getDrops(new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos)));

            for (ItemStack drop : drops) {
                Block.popResource(level, pos, drop);
            }

            level.setBlock(pos, state.setValue(NetherWartBlock.AGE, 0), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            return true;
        }

        /// Cocoa
        if (block instanceof CocoaBlock) {
            List<ItemStack> drops = state.getDrops(new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos)));

            for (ItemStack drop : drops) {
                Block.popResource(level, pos, drop);
            }

            level.setBlock(pos, state.setValue(CocoaBlock.AGE, 0), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            return true;
        }

        /// Sweet Berry Bush
        if (block instanceof SweetBerryBushBlock) {
            List<ItemStack> drops = state.getDrops(new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos)));

            for (ItemStack drop : drops) {
                Block.popResource(level, pos, drop);
            }

            level.setBlock(pos, state.setValue(SweetBerryBushBlock.AGE, 1), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            return true;
        }

        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();

        if (player == null) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.SUCCESS;

        /// Gets the positions based on the mode
        List<BlockPos> positions = getHoePositions(clickedPos, player, itemStack);

        boolean harvestedAny = false;
        boolean tilledAny = false;

        /// PRIORITY 1: Check if there are mature crops to harvest
        for (BlockPos pos : positions) {
            BlockState state = level.getBlockState(pos);

            if (isMatureCrop(state)) {
                if (harvestAndReplant(level, pos, state, player)) {
                    harvestedAny = true;
                }
            }
        }

        /// If he picked something, he plays sound and returns
        if (harvestedAny) {
            level.playSound(null, clickedPos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            itemStack.hurtAndBreak(0, player, LivingEntity.getSlotForHand(context.getHand()));
            return InteractionResult.SUCCESS;
        }

        /// PRIORITY 2: If there are no mature plantations, try to make till
        for (BlockPos pos : positions) {
            BlockState state = level.getBlockState(pos);
            BlockState modifiedState = state.getToolModifiedState(
                    new UseOnContext(player, context.getHand(),
                            new BlockHitResult(player.position(), context.getClickedFace(), pos, false)),
                    net.neoforged.neoforge.common.ItemAbilities.HOE_TILL,
                    false
            );

            if (modifiedState != null && onlyIfAirAbove(new UseOnContext(player, context.getHand(),
                    new BlockHitResult(player.position(), context.getClickedFace(), pos, false)))) {

                level.setBlock(pos, modifiedState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, modifiedState));
                tilledAny = true;
            }
        }

        if (tilledAny) {
            level.playSound(null, clickedPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            itemStack.hurtAndBreak(0, player, LivingEntity.getSlotForHand(context.getHand()));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static Consumer<UseOnContext> changeIntoState(BlockState state) {
        return p_316061_ -> {
            p_316061_.getLevel().setBlock(p_316061_.getClickedPos(), state, 11);
            p_316061_.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, p_316061_.getClickedPos(), GameEvent.Context.of(p_316061_.getPlayer(), state));
        };
    }

    public static Consumer<UseOnContext> changeIntoStateAndDropItem(BlockState state, ItemLike itemToDrop) {
        return p_316064_ -> {
            p_316064_.getLevel().setBlock(p_316064_.getClickedPos(), state, 11);
            p_316064_.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, p_316064_.getClickedPos(), GameEvent.Context.of(p_316064_.getPlayer(), state));
            Block.popResourceFromFace(p_316064_.getLevel(), p_316064_.getClickedPos(), p_316064_.getClickedFace(), new ItemStack(itemToDrop));
        };
    }

    public static boolean onlyIfAirAbove(UseOnContext context) {
        return context.getClickedFace() != Direction.DOWN && context.getLevel().getBlockState(context.getClickedPos().above()).isAir();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility);
    }
}

