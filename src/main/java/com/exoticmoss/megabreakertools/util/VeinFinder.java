package com.exoticmoss.megabreakertools.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/// "Vein Miner" mode of the pickaxes: mining one ore also mines the whole vein (like the Vein Miner mods).
///
/// Which blocks belong to the same vein as the first ore (touching it, also diagonally):
///  - the same block;
///  - or a block with the same ore tag of the common "c" tags (e.g. c:ores/iron = iron ore AND deepslate iron ore);
///  - or, for ores without those tags, a block of the same mod with the same ore name
///    (e.g. simpleores:ignithra_ore and simpleores:deepslate_ignithra_ore).
/// A block counts as an ore if it is in c:ores or its id ends with "_ore".
public final class VeinFinder {
    /// The common tag of all ores (c:ores), the same on NeoForge and Fabric.
    public static final TagKey<Block> ORES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", "ores"));
    /// Stone types written before the ore name in the ids (deepslate_iron_ore, nether_gold_ore...).
    private static final String[] STONE_PREFIXES = {"deepslate_", "end_stone_", "nether_", "netherrack_", "stone_", "bedrock_", "blackstone_", "basalt_", "tuff_"};

    private VeinFinder() {
    }

    /// True if this block is an ore (so the Vein Miner can start from it).
    public static boolean isOre(BlockState state) {
        return state.is(ORES) || BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath().endsWith("_ore");
    }

    /// The ores of the vein: the closest first, up to "maxBlocks" (the first ore counts too).
    public static List<BlockPos> findVein(LevelAccessor level, BlockPos start, int maxBlocks) {
        BlockState first = level.getBlockState(start);
        Set<TagKey<Block>> oreTags = oreTags(first);
        String name = oreName(first.getBlock());

        List<BlockPos> vein = new ArrayList<>();
        Set<BlockPos> seen = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(start);
        seen.add(start);
        while (!queue.isEmpty() && vein.size() < maxBlocks) {
            BlockPos pos = queue.poll();
            vein.add(pos);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        BlockPos next = pos.offset(dx, dy, dz);
                        if (seen.add(next) && sameVein(level.getBlockState(next), first, oreTags, name)) queue.add(next);
                    }
                }
            }
        }
        return vein;
    }

    private static boolean sameVein(BlockState state, BlockState first, Set<TagKey<Block>> oreTags, String name) {
        if (state.isAir()) return false;
        if (state.is(first.getBlock())) return true;
        for (TagKey<Block> tag : oreTags) {
            if (state.is(tag)) return true;
        }
        return oreTags.isEmpty() && isOre(state) && name.equals(oreName(state.getBlock()));
    }

    /// The "c:ores/<something>" tags of the block (e.g. c:ores/iron).
    private static Set<TagKey<Block>> oreTags(BlockState state) {
        Set<TagKey<Block>> tags = new HashSet<>();
        state.getTags().forEach(tag -> {
            Identifier id = tag.location();
            if (id.getNamespace().equals("c") && id.getPath().startsWith("ores/")) tags.add(tag);
        });
        return tags;
    }

    /// The mod and the ore name without the stone type: "simpleores:deepslate_ignithra_ore" -> "simpleores:ignithra".
    private static String oreName(Block block) {
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        String path = id.getPath();
        if (path.endsWith("_ore")) path = path.substring(0, path.length() - 4);
        for (String prefix : STONE_PREFIXES) {
            if (path.startsWith(prefix)) {
                path = path.substring(prefix.length());
                break;
            }
        }
        return id.getNamespace() + ":" + path;
    }
}
