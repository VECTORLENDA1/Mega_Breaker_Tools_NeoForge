package com.exoticmoss.megabreakertools.item.custom;

import com.exoticmoss.megabreakertools.component.ClusterContents;
import com.exoticmoss.megabreakertools.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// The cluster made when a Mega tool breaks an area: all the drops of the broken blocks are inside it.
/// Right-click to take the items out (what doesn't fit in the inventory stays inside).
/// Two clusters on the ground close to each other join into one (see ModEvents).
public class DropClusterItem extends Item {
    /// How many kinds of items the tooltip shows (the rest is "...and X more").
    private static final int TOOLTIP_LINES = 8;
    /// From this many items inside, right-click opens the cluster screen (to delete items) instead of
    /// taking everything out. Below it, right-click works as always.
    public static final int MANAGE_THRESHOLD = 5000;

    public DropClusterItem(Properties properties) {
        super(properties);
    }

    public static ClusterContents getContents(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CLUSTER_CONTENTS.get(), ClusterContents.EMPTY);
    }

    /// A cluster with these items inside.
    public static ItemStack create(Item clusterItem, ClusterContents contents) {
        ItemStack stack = new ItemStack(clusterItem);
        stack.set(ModDataComponents.CLUSTER_CONTENTS.get(), contents);
        return stack;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack cluster = player.getItemInHand(hand);
        // Big clusters (MANAGE_THRESHOLD items or more): right-click opens the cluster screen, where items
        // can be deleted or all taken out. Shift + right-click always takes everything out right away
        if (getContents(cluster).totalCount() >= MANAGE_THRESHOLD && !player.isShiftKeyDown()) {
            if (level.isClientSide()) com.exoticmoss.megabreakertools.client.ClusterScreen.open(hand);
            return InteractionResult.SUCCESS;
        }
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        takeAll(level, player, cluster);
        return InteractionResult.SUCCESS;
    }

    /// Takes the items out of the cluster into the player's inventory (what doesn't fit stays inside)
    /// and gives all the experience. Used by right-click and by the "Take all" button of the cluster screen.
    public static void takeAll(Level level, Player player, ItemStack cluster) {
        ClusterContents contents = getContents(cluster);
        ClusterContents.Builder left = new ClusterContents.Builder(ClusterContents.EMPTY);
        boolean collectedAny = false;

        for (ClusterContents.Entry entry : contents.entries()) {
            int remaining = entry.count();
            // Gives the items in full stacks until the inventory is full
            while (remaining > 0) {
                ItemStack sample = entry.create(1);
                int amount = Math.min(remaining, sample.getMaxStackSize());
                ItemStack stack = entry.create(amount);
                player.getInventory().add(stack);
                int given = amount - stack.getCount();
                remaining -= given;
                if (given > 0) collectedAny = true;
                if (given < amount) break; // inventory full
            }
            if (remaining > 0) left.add(new ClusterContents.Entry(entry.item(), remaining));
        }

        // All the experience of the broken blocks is given at once
        if (contents.experience() > 0) {
            player.giveExperiencePoints(contents.experience());
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS,
                    0.5F, 0.9F + level.getRandom().nextFloat() * 0.2F);
        }

        ClusterContents rest = left.build();
        if (rest.isEmpty()) {
            cluster.shrink(1);
        } else {
            cluster.set(ModDataComponents.CLUSTER_CONTENTS.get(), rest);
            player.sendOverlayMessage(Component.translatable("item.megabreakertools.drop_cluster.inventory_full").withStyle(ChatFormatting.RED));
        }

        if (collectedAny) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,
                    0.4F, 1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.4F);
        }
    }

    /// Gives up to "amount" items of one kind to the player, in full stacks, until the inventory is full.
    /// Returns how many were given (the caller takes them out of the cluster).
    public static int give(Player player, ClusterContents.Entry entry, int amount) {
        int given = 0;
        while (given < amount) {
            int size = Math.min(amount - given, entry.create(1).getMaxStackSize());
            ItemStack stack = entry.create(size);
            player.getInventory().add(stack);
            given += size - stack.getCount();
            if (!stack.isEmpty()) {
                player.sendOverlayMessage(Component.translatable("item.megabreakertools.drop_cluster.inventory_full").withStyle(ChatFormatting.RED));
                break;
            }
        }
        return given;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        ClusterContents contents = getContents(stack);
        if (contents.totalCount() >= MANAGE_THRESHOLD) {
            tooltip.accept(Component.translatable("item.megabreakertools.drop_cluster.tooltip_manage").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.accept(Component.translatable("item.megabreakertools.drop_cluster.tooltip").withStyle(ChatFormatting.GOLD));
        }
        tooltip.accept(Component.translatable("item.megabreakertools.drop_cluster.total", contents.totalCount()).withStyle(ChatFormatting.GRAY));
        if (contents.experience() > 0) {
            tooltip.accept(Component.translatable("item.megabreakertools.drop_cluster.experience", contents.experience()).withStyle(ChatFormatting.GREEN));
        }

        // The kinds of items with more first
        List<ClusterContents.Entry> sorted = new ArrayList<>(contents.entries());
        sorted.sort((a, b) -> Integer.compare(b.count(), a.count()));
        for (int i = 0; i < Math.min(TOOLTIP_LINES, sorted.size()); i++) {
            ClusterContents.Entry entry = sorted.get(i);
            tooltip.accept(Component.literal(entry.count() + "x ").append(entry.create(1).getHoverName()).withStyle(ChatFormatting.DARK_GRAY));
        }
        if (sorted.size() > TOOLTIP_LINES) {
            tooltip.accept(Component.translatable("item.megabreakertools.drop_cluster.more", sorted.size() - TOOLTIP_LINES).withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, context, display, tooltip, flag);
    }
}
