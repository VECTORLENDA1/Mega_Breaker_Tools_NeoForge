package com.exoticmoss.megabreakertools.network;

import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record ToggleMiningModePacket() implements CustomPacketPayload {

    public static final Type<ToggleMiningModePacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MegaBreakerTools.MODID, "toggle_mining_mode"));

    public static final StreamCodec<ByteBuf, ToggleMiningModePacket> STREAM_CODEC =
            StreamCodec.unit(new ToggleMiningModePacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /// Runs on the server thread (Fabric calls the receiver there)
    public static void handle(ToggleMiningModePacket packet, ServerPlayNetworking.Context context) {
        Player player = context.player();
        ItemStack mainHand = player.getMainHandItem();

        /// Works with anything that implements IModeSwitchable
        if (mainHand.getItem() instanceof IModeSwitchable) {
            IModeSwitchable.toggleMiningMode(mainHand);
        }
    }
}
