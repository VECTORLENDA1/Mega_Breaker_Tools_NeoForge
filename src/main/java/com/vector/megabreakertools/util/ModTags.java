package com.vector.megabreakertools.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_IGNITHRA_TOOL = createTag("needs_ignithra_tool");
        public static final TagKey<Block> INCORRECT_FOR_IGNITHRA_TOOL = createTag("incorrect_for_ignithra_tool");
        public static final TagKey<Block> NEEDS_ASTRALITE_TOOL = createTag("needs_astralite_tool");
        public static final TagKey<Block> INCORRECT_FOR_ASTRALITE_TOOL = createTag("incorrect_for_astralite_tool");
        public static final TagKey<Block> NEEDS_NEXALITE_TOOL = createTag("needs_nexalite_tool");
        public static final TagKey<Block> INCORRECT_FOR_NEXALITE_TOOL = createTag("incorrect_for_nexalite_tool");
        public static final TagKey<Block> NEEDS_CELESTINE_TOOL = createTag("needs_celestine_tool");
        public static final TagKey<Block> INCORRECT_FOR_CELESTINE_TOOL = createTag("incorrect_for_celestine_tool");
        public static final TagKey<Block> NEEDS_ZENITHRA_TOOL = createTag("needs_zenithra_tool");
        public static final TagKey<Block> INCORRECT_FOR_ZENITHRA_TOOL = createTag("incorrect_for_zenithra_tool");
        public static final TagKey<Block> NEEDS_OBSCURITE_TOOL = createTag("needs_obscurite_tool");
        public static final TagKey<Block> INCORRECT_FOR_OBSCURITE_TOOL = createTag("incorrect_for_obscurite_tool");


        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("megabreakertools", name));
        }
    }

    public static class Items {

        public static final TagKey<Item> PICKAXES = createVanillaTag("pickaxes");
        public static final TagKey<Item> AXES = createVanillaTag("axes");
        public static final TagKey<Item> SHOVELS = createVanillaTag("shovels");
        public static final TagKey<Item> HOES = createVanillaTag("hoes");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("megabreakertools", name));
        }

        private static TagKey<Item> createVanillaTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", name));
        }
    }
}
