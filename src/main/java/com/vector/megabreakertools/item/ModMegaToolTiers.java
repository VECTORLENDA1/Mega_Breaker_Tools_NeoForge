package com.vector.megabreakertools.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import com.vector.megabreakertools.util.ModTags;
import net.neoforged.neoforge.common.SimpleTier;

public class ModMegaToolTiers {
    public static final Tier IGNITHRA = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_IGNITHRA_TOOL,
            6000, 4f, 0f, 15, () -> Ingredient.of(ModItems.IGNITHRA));

    public static final Tier ASTRALITE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_ASTRALITE_TOOL,
            1200, 6f, 0f, 18, () -> Ingredient.of(ModItems.ASTRALITE));

    public static final Tier NEXALITE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_NEXALITE_TOOL,
            1800, 8f, 0f, 20, () -> Ingredient.of(ModItems.NEXALITE));

    public static final Tier ZENITHRA = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_ZENITHRA_TOOL,
            2400, 10f, 0f, 23, () -> Ingredient.of(ModItems.ZENITHRA));

    public static final Tier CELESTINE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_CELESTINE_TOOL,
            3000, 12f, 0f, 25, () -> Ingredient.of(ModItems.CELESTINE));

    public static final Tier OBSCURITE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_OBSCURITE_TOOL,
            5000, 14f, 0f, 30, () -> Ingredient.of(ModItems.OBSCURITE));
}