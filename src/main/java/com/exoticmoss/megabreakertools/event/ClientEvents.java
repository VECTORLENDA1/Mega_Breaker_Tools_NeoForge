package com.exoticmoss.megabreakertools.event;

import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import com.exoticmoss.megabreakertools.network.ToggleMiningModePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.player.ClientHotbarScrollEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ClientEvents {

    public static void register() {
        ClientHotbarScrollEvents.ALLOW.register((inventory, currentSlot, newSlot, xOffset, yOffset) -> onMouseScroll(yOffset));
    }

    /// Returns false to stop the hotbar slot from changing
    public static boolean onMouseScroll(double scrollDeltaY) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return true;

        /// Check if you are crouching
        if (!player.isShiftKeyDown()) return true;

        ItemStack mainHand = player.getMainHandItem();

        /// Checks if the tool implements IModeSwitchable
        if (!(mainHand.getItem() instanceof IModeSwitchable tool)) return true;

        /// Sends packet to server switch mode
        if (scrollDeltaY != 0) {
            // Gets CURRENT mode before sending the packet
            String newModeText = tool.modeName(IModeSwitchable.nextMode(mainHand), IModeSwitchable.areaRange(mainHand));

            /// Send it to the server
            ClientPlayNetworking.send(new ToggleMiningModePacket());

            // Visual feedback
            player.sendOverlayMessage(
                    Component.literal("Mode: " + newModeText)
                            .withStyle(ChatFormatting.GOLD)
            );

            /// Sound feedback
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        }

        /// Cancels the event so as not to change the slot
        return false;
    }
}
