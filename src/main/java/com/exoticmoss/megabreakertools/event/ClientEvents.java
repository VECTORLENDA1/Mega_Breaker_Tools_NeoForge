package com.exoticmoss.megabreakertools.event;

import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import com.exoticmoss.megabreakertools.network.ToggleMiningModePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@EventBusSubscriber(modid = MegaBreakerTools.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        /// Check if you are crouching
        if (!player.isShiftKeyDown()) return;

        ItemStack mainHand = player.getMainHandItem();

        /// Checks if the tool implements IModeSwitchable
        if (!(mainHand.getItem() instanceof IModeSwitchable tool)) return;

        /// Cancels the event so as not to change the slot
        event.setCanceled(true);

        /// Sends packet to server switch mode
        double scrollDelta = event.getScrollDeltaY();
        if (scrollDelta != 0) {
            // Gets CURRENT mode before sending the packet
            String newModeText = tool.modeName(IModeSwitchable.nextMode(mainHand), IModeSwitchable.areaRange(mainHand));

            /// Send it to the server
            ClientPacketDistributor.sendToServer(new ToggleMiningModePacket());

            // Visual feedback
            player.sendOverlayMessage(
                    Component.literal("Mode: " + newModeText)
                            .withStyle(ChatFormatting.GOLD)
            );

            /// Sound feedback
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        }
    }

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
