package com.exoticmoss.megabreakertools.event;

import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = MegaBreakerTools.MODID, value = Dist.CLIENT)
public class ClientEvents {

    /// Shift + right-click with a Mega Breaker tool in the main hand opens the tool settings screen
    /// (instead of using the tool). Right-click without Shift works as always.
    @SubscribeEvent
    public static void onUseKey(InputEvent.InteractionKeyMappingTriggered event) {
        Player player = Minecraft.getInstance().player;
        if (player == null || !event.isUseItem() || event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        if (!player.isShiftKeyDown() || !(player.getMainHandItem().getItem() instanceof IModeSwitchable)) return;
        event.setCanceled(true);
        event.setSwingHand(false);
        com.exoticmoss.megabreakertools.client.ToolSettingsScreen.openIfHolding();
    }

    /// The tool settings key (G by default)
    @SubscribeEvent
    public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Post event) {
        com.exoticmoss.megabreakertools.client.ToolSettingsKey.tick();
    }
}
