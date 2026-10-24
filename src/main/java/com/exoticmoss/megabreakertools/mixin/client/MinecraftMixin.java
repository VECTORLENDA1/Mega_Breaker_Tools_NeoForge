package com.exoticmoss.megabreakertools.mixin.client;

import com.exoticmoss.megabreakertools.client.ToolSettingsScreen;
import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// Shift + right-click with a Mega Breaker tool in the main hand opens the tool settings screen
/// (instead of using the tool). Replaces NeoForge's InputEvent.InteractionKeyMappingTriggered.
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public LocalPlayer player;

    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void megabreakertools$openToolSettings(CallbackInfo ci) {
        if (player != null && player.isShiftKeyDown() && player.getMainHandItem().getItem() instanceof IModeSwitchable) {
            ToolSettingsScreen.openIfHolding();
            ci.cancel();
        }
    }
}
