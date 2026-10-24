package com.exoticmoss.megabreakertools.mixin;

import com.exoticmoss.megabreakertools.event.ModEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/// Fabric has no "block drops" event (NeoForge BlockDropsEvent): the drops (and the experience) of the blocks
/// broken by a tool in area mode are taken here and go into the Drop Cluster (see ModEvents#onBlockDrops).
@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At("HEAD"), cancellable = true)
    private static void megabreakertools$captureDrops(BlockState state, Level level, BlockPos pos, @Nullable BlockEntity blockEntity,
                                                      @Nullable Entity breaker, ItemStack tool, CallbackInfo ci) {
        if (!(level instanceof ServerLevel serverLevel) || breaker == null) return;
        if (!ModEvents.capturesDrops(breaker, serverLevel, pos)) return;

        List<ItemStack> drops = serverLevel.getGameRules().get(GameRules.BLOCK_DROPS)
                ? Block.getDrops(state, serverLevel, pos, blockEntity, breaker, tool)
                : List.of();
        ModEvents.startCapturingExperience();
        int experience;
        try {
            state.spawnAfterBreak(serverLevel, pos, tool, true);
        } finally {
            experience = ModEvents.stopCapturingExperience();
        }
        ModEvents.onBlockDrops(breaker, serverLevel, pos, drops, experience);
        ci.cancel();
    }

    @Inject(method = "popExperience", at = @At("HEAD"), cancellable = true)
    private void megabreakertools$captureExperience(ServerLevel level, BlockPos pos, int amount, CallbackInfo ci) {
        if (!level.getGameRules().get(GameRules.BLOCK_DROPS)) return;
        if (ModEvents.captureExperience(amount)) ci.cancel();
    }
}
