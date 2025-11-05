package com.vector.megabreakertools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.vector.megabreakertools.MegaBreakerTools;
import net.vector.megabreakertools.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MegaBreakerTools.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Blocks.NEEDS_IGNITHRA_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_IGNITHRA_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .remove(BlockTags.NEEDS_STONE_TOOL);



        tag(ModTags.Blocks.NEEDS_ASTRALITE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ASTRALITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(BlockTags.NEEDS_STONE_TOOL);

        tag(ModTags.Blocks.NEEDS_NEXALITE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_NEXALITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(BlockTags.NEEDS_IRON_TOOL);



        tag(ModTags.Blocks.NEEDS_CELESTINE_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_CELESTINE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.NEEDS_ZENITHRA_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ZENITHRA_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(BlockTags.NEEDS_DIAMOND_TOOL);



        tag(ModTags.Blocks.NEEDS_OBSCURITE_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_OBSCURITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
                .remove(BlockTags.NEEDS_DIAMOND_TOOL);
    }
}
