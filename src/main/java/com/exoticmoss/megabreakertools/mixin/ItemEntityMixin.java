package com.exoticmoss.megabreakertools.mixin;

import com.exoticmoss.megabreakertools.event.ModEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// Fabric has no entity tick or item pickup events (NeoForge EntityTickEvent / ItemEntityPickupEvent):
/// the Drop Cluster on the ground uses these hooks (see ModEvents#onClusterTick and ModEvents#onClusterPickup).
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void megabreakertools$afterTick(CallbackInfo ci) {
        ModEvents.onClusterTick((ItemEntity) (Object) this);
    }

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void megabreakertools$onPickup(Player player, CallbackInfo ci) {
        if (ModEvents.onClusterPickup((ItemEntity) (Object) this, player)) ci.cancel();
    }
}
