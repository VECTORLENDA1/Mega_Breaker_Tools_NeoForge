package com.exoticmoss.megabreakertools.item;

import com.exoticmoss.megabreakertools.item.custom.DropClusterItem;
import com.exoticmoss.megabreakertools.item.custom.axe.*;
import com.exoticmoss.megabreakertools.item.custom.hoe.*;
import com.exoticmoss.megabreakertools.item.custom.pickaxe.*;
import com.exoticmoss.megabreakertools.item.custom.pickaxe.SimplePickaxeItem;
import com.exoticmoss.megabreakertools.item.custom.shovel.*;
import com.exoticmoss.megabreakertools.item.custom.pickaxe.UltraPickaxeItem;
import com.exoticmoss.megabreakertools.util.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.exoticmoss.megabreakertools.MegaBreakerTools.MODID;


public class ModItems {
    /// All the items of the mod, in the order they were registered
    private static final List<RegistryHolder<? extends Item>> ITEMS = new ArrayList<>();

    private static <T extends Item> RegistryHolder<T> registerItem(String name, Function<Item.Properties, T> factory,
                                                                    UnaryOperator<Item.Properties> properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, name));
        T item = Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties.apply(new Item.Properties().setId(key))));
        RegistryHolder<T> holder = new RegistryHolder<>(item);
        ITEMS.add(holder);
        return holder;
    }

    public static List<RegistryHolder<? extends Item>> getEntries() {
        return Collections.unmodifiableList(ITEMS);
    }

    //Pickaxe Tools\\
    public static final RegistryHolder<SimplePickaxeItem> SIMPLE_PICKAXE = registerItem("simple_pickaxe", SimplePickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.IGNITHRA, 1, -2.8F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<AdvancePickaxeItem> ADVANCE_PICKAXE = registerItem("advance_pickaxe", AdvancePickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ASTRALITE, 2, -2.6F));
    public static final RegistryHolder<UltraPickaxeItem> ULTRA_PICKAXE = registerItem("ultra_pickaxe", UltraPickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.NEXALITE, 4, -2.4F));
    public static final RegistryHolder<UltimatePickaxeItem> ULTIMATE_PICKAXE = registerItem("ultimate_pickaxe", UltimatePickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.CELESTINE, 6, -2.2F));
    public static final RegistryHolder<LegendaryPickaxeItem> LEGENDARY_PICKAXE = registerItem("legendary_pickaxe", LegendaryPickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ZENITHRA, 8, -2.0F));
    public static final RegistryHolder<AtomicPickaxeItem> ATOMIC_PICKAXE = registerItem("atomic_pickaxe", AtomicPickaxeItem::new,
            p -> p.fireResistant().pickaxe(ModMegaToolTiers.OBSCURITE, 9, -1.8F));

    //Shovel Tools\\
    public static final RegistryHolder<SimpleShovelItem> SIMPLE_SHOVEL = registerItem("simple_shovel", SimpleShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.IGNITHRA, 1.5F, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<AdvanceShovelItem> ADVANCE_SHOVEL = registerItem("advance_shovel", AdvanceShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.ASTRALITE, 2.5F, -2.8F));
    public static final RegistryHolder<UltraShovelItem> ULTRA_SHOVEL = registerItem("ultra_shovel", UltraShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.NEXALITE, 4.5F, -2.6F));
    public static final RegistryHolder<UltimateShovelItem> ULTIMATE_SHOVEL = registerItem("ultimate_shovel", UltimateShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.CELESTINE, 6.5F, -2.4F));
    public static final RegistryHolder<LegendaryShovelItem> LEGENDARY_SHOVEL = registerItem("legendary_shovel", LegendaryShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.ZENITHRA, 8.5F, -2.2F));
    public static final RegistryHolder<AtomicShovelItem> ATOMIC_SHOVEL = registerItem("atomic_shovel", AtomicShovelItem::new,
            p -> p.fireResistant().shovel(ModMegaToolTiers.OBSCURITE, 9.5F, -2.0F));

    //Axe Tools\\
    public static final RegistryHolder<SimpleAxeItem> SIMPLE_AXE = registerItem("simple_axe", SimpleAxeItem::new,
            p -> p.axe(ModMegaToolTiers.IGNITHRA, 3, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<AdvanceAxeItem> ADVANCE_AXE = registerItem("advance_axe", AdvanceAxeItem::new,
            p -> p.axe(ModMegaToolTiers.ASTRALITE, 5, -2.8F));
    public static final RegistryHolder<UltraAxeItem> ULTRA_AXE = registerItem("ultra_axe", UltraAxeItem::new,
            p -> p.axe(ModMegaToolTiers.NEXALITE, 7, -2.6F));
    public static final RegistryHolder<UltimateAxeItem> ULTIMATE_AXE = registerItem("ultimate_axe", UltimateAxeItem::new,
            p -> p.axe(ModMegaToolTiers.CELESTINE, 8, -2.4F));
    public static final RegistryHolder<LegendaryAxeItem> LEGENDARY_AXE = registerItem("legendary_axe", LegendaryAxeItem::new,
            p -> p.axe(ModMegaToolTiers.ZENITHRA, 9, -2.2F));
    public static final RegistryHolder<AtomicAxeItem> ATOMIC_AXE = registerItem("atomic_axe", AtomicAxeItem::new,
            p -> p.fireResistant().axe(ModMegaToolTiers.OBSCURITE, 10, -2.0F));

    //Hoe Tools\\
    public static final RegistryHolder<SimpleHoeItem> SIMPLE_HOE = registerItem("simple_hoe", SimpleHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.IGNITHRA, 4, -1.0F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<AdvanceHoeItem> ADVANCE_HOE = registerItem("advance_hoe", AdvanceHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.ASTRALITE, 6, -0.8F));
    public static final RegistryHolder<UltraHoeItem> ULTRA_HOE = registerItem("ultra_hoe", UltraHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.NEXALITE, 7, -0.6F));
    public static final RegistryHolder<UltimateHoeItem> ULTIMATE_HOE = registerItem("ultimate_hoe", UltimateHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.CELESTINE, 8, -0.4F));
    public static final RegistryHolder<LegendaryHoeItem> LEGENDARY_HOE = registerItem("legendary_hoe", LegendaryHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.ZENITHRA, 9, -0.2F));
    public static final RegistryHolder<AtomicHoeItem> ATOMIC_HOE = registerItem("atomic_hoe", AtomicHoeItem::new,
            p -> p.fireResistant().hoe(ModMegaToolTiers.OBSCURITE, 10, 0F));

    //Mega Pickaxe Tools\\
    public static final RegistryHolder<MegaSimpleBreakerItem> MEGA_SIMPLE_BREAKER = registerItem("mega_simple_breaker", MegaSimpleBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.IGNITHRA, 2, -2.8F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<MegaAdvanceBreakerItem> MEGA_ADVANCE_BREAKER = registerItem("mega_advance_breaker", MegaAdvanceBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ASTRALITE, 4, -2.6F));
    public static final RegistryHolder<MegaUltraBreakerItem> MEGA_ULTRA_BREAKER = registerItem("mega_ultra_breaker", MegaUltraBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.NEXALITE, 6, -2.4F));
    public static final RegistryHolder<MegaUltimateBreakerItem> MEGA_ULTIMATE_BREAKER = registerItem("mega_ultimate_breaker", MegaUltimateBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.CELESTINE, 8, -2.2F));
    public static final RegistryHolder<MegaLegendaryBreakerItem> MEGA_LEGENDARY_BREAKER = registerItem("mega_legendary_breaker", MegaLegendaryBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ZENITHRA, 10, -2.0F));
    public static final RegistryHolder<MegaAtomicBreakerItem> MEGA_ATOMIC_BREAKER = registerItem("mega_atomic_breaker", MegaAtomicBreakerItem::new,
            p -> p.fireResistant().pickaxe(ModMegaToolTiers.OBSCURITE, 12, -1.8f));

    //Mega Shovel Tools\\
    public static final RegistryHolder<MegaSimpleExcavatorItem> MEGA_SIMPLE_EXCAVATOR = registerItem("mega_simple_excavator", MegaSimpleExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.IGNITHRA, 2.5F, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<MegaAdvanceExcavatorItem> MEGA_ADVANCE_EXCAVATOR = registerItem("mega_advance_excavator", MegaAdvanceExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.ASTRALITE, 5.5F, -2.8F));
    public static final RegistryHolder<MegaUltraExcavatorItem> MEGA_ULTRA_EXCAVATOR = registerItem("mega_ultra_excavator", MegaUltraExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.NEXALITE, 6.5F, -2.6F));
    public static final RegistryHolder<MegaUltimateExcavatorItem> MEGA_ULTIMATE_EXCAVATOR = registerItem("mega_ultimate_excavator", MegaUltimateExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.CELESTINE, 8.5F, -2.4F));
    public static final RegistryHolder<MegaLegendaryExcavatorItem> MEGA_LEGENDARY_EXCAVATOR = registerItem("mega_legendary_excavator", MegaLegendaryExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.ZENITHRA, 10.5F, -2.2F));
    public static final RegistryHolder<MegaAtomicExcavatorItem> MEGA_ATOMIC_EXCAVATOR = registerItem("mega_atomic_excavator", MegaAtomicExcavatorItem::new,
            p -> p.fireResistant().shovel(ModMegaToolTiers.OBSCURITE, 12.5F, -2.0f));

    //Mega Axe Tools\\
    public static final RegistryHolder<MegaSimpleLumberjackItem> MEGA_SIMPLE_LUMBERJACK = registerItem("mega_simple_lumberjack", MegaSimpleLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.IGNITHRA, 6, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<MegaAdvanceLumberjackItem> MEGA_ADVANCE_LUMBERJACK = registerItem("mega_advance_lumberjack", MegaAdvanceLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.ASTRALITE, 8, -2.8F));
    public static final RegistryHolder<MegaUltraLumberjackItem> MEGA_ULTRA_LUMBERJACK = registerItem("mega_ultra_lumberjack", MegaUltraLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.NEXALITE, 10, -2.6F));
    public static final RegistryHolder<MegaUltimateLumberjackItem> MEGA_ULTIMATE_LUMBERJACK = registerItem("mega_ultimate_lumberjack", MegaUltimateLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.CELESTINE, 12, -2.4F));
    public static final RegistryHolder<MegaLegendaryLumberjackItem> MEGA_LEGENDARY_LUMBERJACK = registerItem("mega_legendary_lumberjack", MegaLegendaryLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.ZENITHRA, 14, -2.2F));
    public static final RegistryHolder<MegaAtomicLumberjackItem> MEGA_ATOMIC_LUMBERJACK = registerItem("mega_atomic_lumberjack", MegaAtomicLumberjackItem::new,
            p -> p.fireResistant().axe(ModMegaToolTiers.OBSCURITE, 16, -2.0f));

    //Mega Hoe Tools\\
    public static final RegistryHolder<MegaSimpleScytheItem> MEGA_SIMPLE_SCYTHE = registerItem("mega_simple_scythe", MegaSimpleScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.IGNITHRA, 4, -1.0F));//defaut - 1 damage, 4 attack speed
    public static final RegistryHolder<MegaAdvanceScytheItem> MEGA_ADVANCE_SCYTHE = registerItem("mega_advance_scythe", MegaAdvanceScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.ASTRALITE, 6, -0.8F));
    public static final RegistryHolder<MegaUltraScytheItem> MEGA_ULTRA_SCYTHE = registerItem("mega_ultra_scythe", MegaUltraScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.NEXALITE, 8, -0.6F));
    public static final RegistryHolder<MegaUltimateScytheItem> MEGA_ULTIMATE_SCYTHE = registerItem("mega_ultimate_scythe", MegaUltimateScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.CELESTINE, 10, -0.4F));
    public static final RegistryHolder<MegaLegendaryScytheItem> MEGA_LEGENDARY_SCYTHE = registerItem("mega_legendary_scythe", MegaLegendaryScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.ZENITHRA, 12, -0.2F));
    public static final RegistryHolder<MegaAtomicScytheItem> MEGA_ATOMIC_SCYTHE = registerItem("mega_atomic_scythe", MegaAtomicScytheItem::new,
            p -> p.fireResistant().hoe(ModMegaToolTiers.OBSCURITE, 14, 0f));

    //Drop Cluster\\ (all the drops of an area broken by a Mega tool)
    public static final RegistryHolder<DropClusterItem> DROP_CLUSTER = registerItem("drop_cluster", DropClusterItem::new,
            p -> p.stacksTo(1).fireResistant());


    /// Loads the class (the items are registered above)
    public static void Register() {
    }
}
