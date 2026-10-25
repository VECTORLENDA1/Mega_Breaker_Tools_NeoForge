package com.exoticmoss.megabreakertools.network;

import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.component.ClusterContents;
import com.exoticmoss.megabreakertools.component.ModDataComponents;
import com.exoticmoss.megabreakertools.item.custom.DropClusterItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/// Sent by the cluster screen (ClusterScreen) when the player takes everything out, takes some items out or deletes items.
///  - mainHand: the cluster is in the main hand (false = off hand)
///  - action: TAKE_ALL, DELETE or TAKE
///  - index / item: which kind of item (the item id is checked, in case the cluster changed)
///  - amount: how many to delete or take out
public record ClusterActionPacket(boolean mainHand, int action, int index, String item, int amount) implements CustomPacketPayload {
    public static final int TAKE_ALL = 0;
    public static final int DELETE = 1;
    /// Takes "amount" of one kind of item into the inventory (what doesn't fit stays in the cluster).
    public static final int TAKE = 2;

    public static final Type<ClusterActionPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MegaBreakerTools.MODID, "cluster_action"));

    public static final StreamCodec<ByteBuf, ClusterActionPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ClusterActionPacket::mainHand,
            ByteBufCodecs.VAR_INT, ClusterActionPacket::action,
            ByteBufCodecs.VAR_INT, ClusterActionPacket::index,
            ByteBufCodecs.STRING_UTF8, ClusterActionPacket::item,
            ByteBufCodecs.VAR_INT, ClusterActionPacket::amount,
            ClusterActionPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClusterActionPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> packet.apply(context.player()));
    }

    /// Does the action on the server.
    public void apply(Player player) {
        ItemStack cluster = player.getItemInHand(mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
        if (!(cluster.getItem() instanceof DropClusterItem)) return;

        if (action == TAKE_ALL) {
            DropClusterItem.takeAll(player.level(), player, cluster);
            return;
        }
        if (action != DELETE && action != TAKE) return;

        ClusterContents contents = DropClusterItem.getContents(cluster);
        if (index < 0 || index >= contents.entries().size()) return;
        ClusterContents.Entry entry = contents.entries().get(index);
        if (!BuiltInRegistries.ITEM.getKey(entry.create(1).getItem()).toString().equals(item)) return;

        int wanted = Math.min(amount, entry.count());
        int removed = action == TAKE ? DropClusterItem.give(player, entry, wanted) : wanted;
        if (removed <= 0) return;
        ClusterContents rest = contents.without(index, removed);
        if (rest.isEmpty()) cluster.shrink(1);
        else cluster.set(ModDataComponents.CLUSTER_CONTENTS.get(), rest);
        if (action == TAKE) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.4F, 1.0F);
        } else {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 0.4F, 1.4F);
        }
    }
}
