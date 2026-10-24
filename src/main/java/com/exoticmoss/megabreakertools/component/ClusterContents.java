package com.exoticmoss.megabreakertools.component;

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
/// It also keeps the experience of the broken blocks (ores), given to the player when the items are collected.
public record ClusterContents(List<Entry> entries, int experience) {
    public static final ClusterContents EMPTY = new ClusterContents(List.of(), 0);

    public ClusterContents(List<Entry> entries) {
        this(entries, 0);
    }

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

    private static final Codec<ClusterContents> FULL_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Entry.CODEC.listOf().fieldOf("items").forGetter(ClusterContents::entries),
            Codec.INT.optionalFieldOf("experience", 0).forGetter(ClusterContents::experience)
    ).apply(inst, ClusterContents::new));
    /// Clusters saved before the experience was added were only a list of items
    public static final Codec<ClusterContents> CODEC = Codec.withAlternative(FULL_CODEC,
            Entry.CODEC.listOf().xmap(ClusterContents::new, ClusterContents::entries));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClusterContents> STREAM_CODEC = StreamCodec.composite(
            Entry.STREAM_CODEC.apply(ByteBufCodecs.list()), ClusterContents::entries,
            ByteBufCodecs.VAR_INT, ClusterContents::experience,
            ClusterContents::new);

    public boolean isEmpty() {
        return entries.isEmpty() && experience <= 0;
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

    /// A new cluster with "amount" items less of the entry at "index" (the entry goes away at 0).
    public ClusterContents without(int index, int amount) {
        if (index < 0 || index >= entries.size() || amount <= 0) return this;
        List<Entry> list = new ArrayList<>(entries);
        Entry entry = list.get(index);
        int left = entry.count() - amount;
        if (left > 0) list.set(index, new Entry(entry.item(), left));
        else list.remove(index);
        return new ClusterContents(List.copyOf(list), experience);
    }

    /// A new cluster with the items of both clusters.
    public ClusterContents merge(ClusterContents other) {
        Builder builder = new Builder(this);
        for (Entry entry : other.entries) builder.add(entry);
        builder.addExperience(other.experience);
        return builder.build();
    }

    /// Joins equal items together (same item and same components).
    public static class Builder {
        private final List<Entry> entries;
        private int experience;

        public Builder(ClusterContents start) {
            this.entries = new ArrayList<>(start.entries);
            this.experience = start.experience;
        }

        /// Experience of the broken blocks (never goes over the int limit).
        public void addExperience(int amount) {
            if (amount > 0) experience = (int) Math.min(Integer.MAX_VALUE, (long) experience + amount);
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
            return new ClusterContents(List.copyOf(entries), experience);
        }
    }
}
