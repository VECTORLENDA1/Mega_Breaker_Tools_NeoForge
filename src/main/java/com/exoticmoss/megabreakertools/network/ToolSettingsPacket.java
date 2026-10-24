package com.exoticmoss.megabreakertools.network;

import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/// Sent by the tool settings screen (ToolSettingsScreen): the mode and the area size chosen for the tool
/// in the main hand. The server checks both (a tool can never get a bigger area than it has).
public record ToolSettingsPacket(int mode, int range) implements CustomPacketPayload {

    public static final Type<ToolSettingsPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MegaBreakerTools.MODID, "tool_settings"));

    public static final StreamCodec<ByteBuf, ToolSettingsPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ToolSettingsPacket::mode,
            ByteBufCodecs.VAR_INT, ToolSettingsPacket::range,
            ToolSettingsPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /// Runs on the server thread (Fabric calls the receiver there)
    public static void handle(ToolSettingsPacket packet, ServerPlayNetworking.Context context) {
        packet.apply(context.player());
    }

    /// Saves the settings on the tool (also used on the client, so the screen changes right away).
    public void apply(Player player) {
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof IModeSwitchable tool)) return;
        if (tool.modes().contains(mode)) IModeSwitchable.setMiningMode(stack, mode);
        IModeSwitchable.setAreaRange(stack, Math.clamp(range, 1, tool.getRange()));
    }
}
