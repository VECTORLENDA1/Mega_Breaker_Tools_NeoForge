package com.vector.megabreakertools.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.ArrayList;
import java.util.List;

/// The items stored inside a Drop Cluster. Every kind of item is kept once with its amount,
/// so a cluster can hold a HUGE amount of items (up to ~2 billion of each kind).
public record ClusterContents(List<Entry> entries) {
    public static final ClusterContents EMPTY = new ClusterContents(List.of());

    /// One kind of item (with its components) and how many of it the cluster has.
    public record Entry(ItemStackTemplate item, int count) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ItemStackTemplate.CODEC.fieldOf("item").forGetter(Entry::item),
                Codec.INT.fieldOf("count").forGetter(Entry::count)
        ).apply(inst, Entry::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Entry> STREAM_CODEC = StreamCodec.composite(
                ItemStackTemplate.STREAM_CODEC, Entry::item,
                ByteBufCodecs.VAR_INT, Entry::count,
                Entry::new);

        /// An ItemStack of this item (the amount is the given count).
        public ItemStack create(int amount) {
            ItemStack stack = item.create();
            stack.setCount(amount);
            return stack;
        }
    }

    public static final Codec<ClusterContents> CODEC = Entry.CODEC.listOf().xmap(ClusterContents::new, ClusterContents::entries);
    public static final StreamCodec<RegistryFriendlyByteBuf, ClusterContents> STREAM_CODEC =
            Entry.STREAM_CODEC.apply(ByteBufCodecs.list()).map(ClusterContents::new, ClusterContents::entries);

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /// Total amount of items inside.
    public long totalCount() {
        long total = 0;
        for (Entry entry : entries) total += entry.count();
        return total;
    }

    /// A new cluster with these items added.
    public ClusterContents with(List<ItemStack> stacks) {
        Builder builder = new Builder(this);
        for (ItemStack stack : stacks) builder.add(stack);
        return builder.build();
    }

    /// A new cluster with the items of both clusters.
    public ClusterContents merge(ClusterContents other) {
        Builder builder = new Builder(this);
        for (Entry entry : other.entries) builder.add(entry);
        return builder.build();
    }

    /// Joins equal items together (same item and same components).
    public static class Builder {
        private final List<Entry> entries;

        public Builder(ClusterContents start) {
            this.entries = new ArrayList<>(start.entries);
        }

        public void add(ItemStack stack) {
            if (stack.isEmpty()) return;
            add(new Entry(ItemStackTemplate.fromNonEmptyStack(stack.copyWithCount(1)), stack.getCount()));
        }

        public void add(Entry added) {
            ItemStack addedStack = added.create(1);
            for (int i = 0; i < entries.size(); i++) {
                Entry entry = entries.get(i);
                // Same item: join the amounts (unless that goes over the limit of one entry)
                if (ItemStack.isSameItemSameComponents(entry.create(1), addedStack)
                        && (long) entry.count() + added.count() <= Integer.MAX_VALUE) {
                    entries.set(i, new Entry(entry.item(), entry.count() + added.count()));
                    return;
                }
            }
            entries.add(added);
        }

        public ClusterContents build() {
            return new ClusterContents(List.copyOf(entries));
        }
    }
}
