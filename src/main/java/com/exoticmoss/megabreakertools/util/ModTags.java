package com.exoticmoss.megabreakertools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("megabreakertools", name));
        }
    }

    public static class Items {

        public static final TagKey<Item> PICKAXES = createVanillaTag("pickaxes");
        public static final TagKey<Item> AXES = createVanillaTag("axes");
        public static final TagKey<Item> SHOVELS = createVanillaTag("shovels");
        public static final TagKey<Item> HOES = createVanillaTag("hoes");

        public static final TagKey<Item> IGNITHRA_REPAIR = createTag("repairs/ignithra");
        public static final TagKey<Item> ASTRALITE_REPAIR = createTag("repairs/astralite");
        public static final TagKey<Item> NEXALITE_REPAIR = createTag("repairs/nexalite");
        public static final TagKey<Item> ZENITHRA_REPAIR = createTag("repairs/zenithra");
        public static final TagKey<Item> CELESTINE_REPAIR = createTag("repairs/celestine");
        public static final TagKey<Item> OBSCURITE_REPAIR = createTag("repairs/obscurite");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("megabreakertools", name));
        }

        private static TagKey<Item> createVanillaTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", name));
        }
    }
}
