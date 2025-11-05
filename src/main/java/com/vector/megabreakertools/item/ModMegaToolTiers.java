package com.vector.megabreakertools.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.vector.megabreakertools.util.ModTags;

public class ModMegaToolTiers {
    public static final Tier IGNITHRA = new ForgeTier(400, 4f, 0f, 15,
            ModTags.Blocks.NEEDS_IGNITHRA_TOOL, () -> Ingredient.of(ModItems.IGNITHRA.get()),
            ModTags.Blocks.INCORRECT_FOR_IGNITHRA_TOOL);

    public static final Tier ASTRALITE = new ForgeTier(1000, 6f, 0f, 20,
            ModTags.Blocks.NEEDS_ASTRALITE_TOOL, () -> Ingredient.of(ModItems.ASTRALITE.get()),
            ModTags.Blocks.INCORRECT_FOR_ASTRALITE_TOOL);

    public static final Tier NEXALITE = new ForgeTier(1000, 8f, 0f, 20,
            ModTags.Blocks.NEEDS_NEXALITE_TOOL, () -> Ingredient.of(ModItems.NEXALITE.get()),
            ModTags.Blocks.INCORRECT_FOR_NEXALITE_TOOL);

    public static final Tier ZENITHRA = new ForgeTier(2000, 10f, 0f, 25,
            ModTags.Blocks.NEEDS_ZENITHRA_TOOL, () -> Ingredient.of(ModItems.ZENITHRA.get()),
            ModTags.Blocks.INCORRECT_FOR_ZENITHRA_TOOL);

    public static final Tier CELESTINE = new ForgeTier(2000, 12f, 0f, 25,
            ModTags.Blocks.NEEDS_CELESTINE_TOOL, () -> Ingredient.of(ModItems.CELESTINE.get()),
            ModTags.Blocks.INCORRECT_FOR_CELESTINE_TOOL);

    public static final Tier OBSCURITE = new ForgeTier(4500, 14f, 0f, 30,
            ModTags.Blocks.NEEDS_OBSCURITE_TOOL, () -> Ingredient.of(ModItems.OBSCURITE.get()),
            ModTags.Blocks.INCORRECT_FOR_OBSCURITE_TOOL);
}