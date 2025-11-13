package com.vector.megabreakertools.network;

import com.vector.megabreakertools.MegaBreakerTools;
import com.vector.megabreakertools.item.custom.IModeSwitchable;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleMiningModePacket() implements CustomPacketPayload {

    public static final Type<ToggleMiningModePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MegaBreakerTools.MODID, "toggle_mining_mode"));

    public static final StreamCodec<ByteBuf, ToggleMiningModePacket> STREAM_CODEC =
            StreamCodec.unit(new ToggleMiningModePacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ToggleMiningModePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            ItemStack mainHand = player.getMainHandItem();

            /// Works with anything that implements IModeSwitchable
            if (mainHand.getItem() instanceof IModeSwitchable) {
                IModeSwitchable.toggleMiningMode(mainHand);
            }
        });
    }
}