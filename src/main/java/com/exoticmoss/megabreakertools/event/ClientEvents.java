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
            int currentMode = IModeSwitchable.getMiningMode(mainHand);
            String newModeText;
            if (currentMode == IModeSwitchable.MODE) {
                /// Will switch to Single Block
                newModeText = "Single Block";
            } else {
                /// Will change to area - calculates the correct size
                int range = tool.getRange();
                int size = (range * 2) + 1;

                if (tool.is3DMining()) {
                    newModeText = size + "x" + size + "x" + size;
                } else {
                    newModeText = size + "x" + size;
                }
            }

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
}