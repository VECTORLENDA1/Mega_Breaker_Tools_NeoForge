package com.exoticmoss.megabreakertools.datagen;

import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MegaBreakerTools.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Blocks.NEEDS_IGNITHRA_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_IGNITHRA_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .remove(ModTags.Blocks.NEEDS_IGNITHRA_TOOL);



        tag(ModTags.Blocks.NEEDS_ASTRALITE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ASTRALITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_ASTRALITE_TOOL);



        tag(ModTags.Blocks.NEEDS_NEXALITE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_NEXALITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_NEXALITE_TOOL);



        tag(ModTags.Blocks.NEEDS_CELESTINE_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_CELESTINE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_CELESTINE_TOOL);



        tag(ModTags.Blocks.NEEDS_ZENITHRA_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ZENITHRA_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_ZENITHRA_TOOL);



        tag(ModTags.Blocks.NEEDS_OBSCURITE_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_OBSCURITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
                .remove(ModTags.Blocks.NEEDS_OBSCURITE_TOOL);
    }
}
