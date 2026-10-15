package com.vector.megabreakertools.item;

import com.vector.megabreakertools.item.custom.axe.*;
import com.vector.megabreakertools.item.custom.hoe.*;
import com.vector.megabreakertools.item.custom.pickaxe.*;
import com.vector.megabreakertools.item.custom.pickaxe.SimplePickaxeItem;
import com.vector.megabreakertools.item.custom.shovel.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import com.vector.megabreakertools.item.custom.pickaxe.UltraPickaxeItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.vector.megabreakertools.MegaBreakerTools.MODID;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    //Pickaxe Tools\\
    public static final DeferredItem<SimplePickaxeItem> SIMPLE_PICKAXE = ITEMS.registerItem("simple_pickaxe", SimplePickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.IGNITHRA, 1, -2.8F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvancePickaxeItem> ADVANCE_PICKAXE = ITEMS.registerItem("advance_pickaxe", AdvancePickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ASTRALITE, 2, -2.6F));
    public static final DeferredItem<UltraPickaxeItem> ULTRA_PICKAXE = ITEMS.registerItem("ultra_pickaxe", UltraPickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.NEXALITE, 4, -2.4F));
    public static final DeferredItem<UltimatePickaxeItem> ULTIMATE_PICKAXE = ITEMS.registerItem("ultimate_pickaxe", UltimatePickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.CELESTINE, 6, -2.2F));
    public static final DeferredItem<LegendaryPickaxeItem> LEGENDARY_PICKAXE = ITEMS.registerItem("legendary_pickaxe", LegendaryPickaxeItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ZENITHRA, 8, -2.0F));
    public static final DeferredItem<AtomicPickaxeItem> ATOMIC_PICKAXE = ITEMS.registerItem("atomic_pickaxe", AtomicPickaxeItem::new,
            p -> p.fireResistant().pickaxe(ModMegaToolTiers.OBSCURITE, 9, -1.8F));

    //Shovel Tools\\
    public static final DeferredItem<SimpleShovelItem> SIMPLE_SHOVEL = ITEMS.registerItem("simple_shovel", SimpleShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.IGNITHRA, 1.5F, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvanceShovelItem> ADVANCE_SHOVEL = ITEMS.registerItem("advance_shovel", AdvanceShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.ASTRALITE, 2.5F, -2.8F));
    public static final DeferredItem<UltraShovelItem> ULTRA_SHOVEL = ITEMS.registerItem("ultra_shovel", UltraShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.NEXALITE, 4.5F, -2.6F));
    public static final DeferredItem<UltimateShovelItem> ULTIMATE_SHOVEL = ITEMS.registerItem("ultimate_shovel", UltimateShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.CELESTINE, 6.5F, -2.4F));
    public static final DeferredItem<LegendaryShovelItem> LEGENDARY_SHOVEL = ITEMS.registerItem("legendary_shovel", LegendaryShovelItem::new,
            p -> p.shovel(ModMegaToolTiers.ZENITHRA, 8.5F, -2.2F));
    public static final DeferredItem<AtomicShovelItem> ATOMIC_SHOVEL = ITEMS.registerItem("atomic_shovel", AtomicShovelItem::new,
            p -> p.fireResistant().shovel(ModMegaToolTiers.OBSCURITE, 9.5F, -2.0F));

    //Axe Tools\\
    public static final DeferredItem<SimpleAxeItem> SIMPLE_AXE = ITEMS.registerItem("simple_axe", SimpleAxeItem::new,
            p -> p.axe(ModMegaToolTiers.IGNITHRA, 3, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvanceAxeItem> ADVANCE_AXE = ITEMS.registerItem("advance_axe", AdvanceAxeItem::new,
            p -> p.axe(ModMegaToolTiers.ASTRALITE, 5, -2.8F));
    public static final DeferredItem<UltraAxeItem> ULTRA_AXE = ITEMS.registerItem("ultra_axe", UltraAxeItem::new,
            p -> p.axe(ModMegaToolTiers.NEXALITE, 7, -2.6F));
    public static final DeferredItem<UltimateAxeItem> ULTIMATE_AXE = ITEMS.registerItem("ultimate_axe", UltimateAxeItem::new,
            p -> p.axe(ModMegaToolTiers.CELESTINE, 8, -2.4F));
    public static final DeferredItem<LegendaryAxeItem> LEGENDARY_AXE = ITEMS.registerItem("legendary_axe", LegendaryAxeItem::new,
            p -> p.axe(ModMegaToolTiers.ZENITHRA, 9, -2.2F));
    public static final DeferredItem<AtomicAxeItem> ATOMIC_AXE = ITEMS.registerItem("atomic_axe", AtomicAxeItem::new,
            p -> p.fireResistant().axe(ModMegaToolTiers.OBSCURITE, 10, -2.0F));

    //Hoe Tools\\
    public static final DeferredItem<SimpleHoeItem> SIMPLE_HOE = ITEMS.registerItem("simple_hoe", SimpleHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.IGNITHRA, 4, -1.0F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvanceHoeItem> ADVANCE_HOE = ITEMS.registerItem("advance_hoe", AdvanceHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.ASTRALITE, 6, -0.8F));
    public static final DeferredItem<UltraHoeItem> ULTRA_HOE = ITEMS.registerItem("ultra_hoe", UltraHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.NEXALITE, 7, -0.6F));
    public static final DeferredItem<UltimateHoeItem> ULTIMATE_HOE = ITEMS.registerItem("ultimate_hoe", UltimateHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.CELESTINE, 8, -0.4F));
    public static final DeferredItem<LegendaryHoeItem> LEGENDARY_HOE = ITEMS.registerItem("legendary_hoe", LegendaryHoeItem::new,
            p -> p.hoe(ModMegaToolTiers.ZENITHRA, 9, -0.2F));
    public static final DeferredItem<AtomicHoeItem> ATOMIC_HOE = ITEMS.registerItem("atomic_hoe", AtomicHoeItem::new,
            p -> p.fireResistant().hoe(ModMegaToolTiers.OBSCURITE, 10, 0F));

    //Mega Pickaxe Tools\\
    public static final DeferredItem<MegaSimpleBreakerItem> MEGA_SIMPLE_BREAKER = ITEMS.registerItem("mega_simple_breaker", MegaSimpleBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.IGNITHRA, 2, -2.8F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceBreakerItem> MEGA_ADVANCE_BREAKER = ITEMS.registerItem("mega_advance_breaker", MegaAdvanceBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ASTRALITE, 4, -2.6F));
    public static final DeferredItem<MegaUltraBreakerItem> MEGA_ULTRA_BREAKER = ITEMS.registerItem("mega_ultra_breaker", MegaUltraBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.NEXALITE, 6, -2.4F));
    public static final DeferredItem<MegaUltimateBreakerItem> MEGA_ULTIMATE_BREAKER = ITEMS.registerItem("mega_ultimate_breaker", MegaUltimateBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.CELESTINE, 8, -2.2F));
    public static final DeferredItem<MegaLegendaryBreakerItem> MEGA_LEGENDARY_BREAKER = ITEMS.registerItem("mega_legendary_breaker", MegaLegendaryBreakerItem::new,
            p -> p.pickaxe(ModMegaToolTiers.ZENITHRA, 10, -2.0F));
    public static final DeferredItem<MegaAtomicBreakerItem> MEGA_ATOMIC_BREAKER = ITEMS.registerItem("mega_atomic_breaker", MegaAtomicBreakerItem::new,
            p -> p.fireResistant().pickaxe(ModMegaToolTiers.OBSCURITE, 12, -1.8f));

    //Mega Shovel Tools\\
    public static final DeferredItem<MegaSimpleExcavatorItem> MEGA_SIMPLE_EXCAVATOR = ITEMS.registerItem("mega_simple_excavator", MegaSimpleExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.IGNITHRA, 2.5F, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceExcavatorItem> MEGA_ADVANCE_EXCAVATOR = ITEMS.registerItem("mega_advance_excavator", MegaAdvanceExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.ASTRALITE, 5.5F, -2.8F));
    public static final DeferredItem<MegaUltraExcavatorItem> MEGA_ULTRA_EXCAVATOR = ITEMS.registerItem("mega_ultra_excavator", MegaUltraExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.NEXALITE, 6.5F, -2.6F));
    public static final DeferredItem<MegaUltimateExcavatorItem> MEGA_ULTIMATE_EXCAVATOR = ITEMS.registerItem("mega_ultimate_excavator", MegaUltimateExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.CELESTINE, 8.5F, -2.4F));
    public static final DeferredItem<MegaLegendaryExcavatorItem> MEGA_LEGENDARY_EXCAVATOR = ITEMS.registerItem("mega_legendary_excavator", MegaLegendaryExcavatorItem::new,
            p -> p.shovel(ModMegaToolTiers.ZENITHRA, 10.5F, -2.2F));
    public static final DeferredItem<MegaAtomicExcavatorItem> MEGA_ATOMIC_EXCAVATOR = ITEMS.registerItem("mega_atomic_excavator", MegaAtomicExcavatorItem::new,
            p -> p.fireResistant().shovel(ModMegaToolTiers.OBSCURITE, 12.5F, -2.0f));

    //Mega Axe Tools\\
    public static final DeferredItem<MegaSimpleLumberjackItem> MEGA_SIMPLE_LUMBERJACK = ITEMS.registerItem("mega_simple_lumberjack", MegaSimpleLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.IGNITHRA, 6, -3.0F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceLumberjackItem> MEGA_ADVANCE_LUMBERJACK = ITEMS.registerItem("mega_advance_lumberjack", MegaAdvanceLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.ASTRALITE, 8, -2.8F));
    public static final DeferredItem<MegaUltraLumberjackItem> MEGA_ULTRA_LUMBERJACK = ITEMS.registerItem("mega_ultra_lumberjack", MegaUltraLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.NEXALITE, 10, -2.6F));
    public static final DeferredItem<MegaUltimateLumberjackItem> MEGA_ULTIMATE_LUMBERJACK = ITEMS.registerItem("mega_ultimate_lumberjack", MegaUltimateLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.CELESTINE, 12, -2.4F));
    public static final DeferredItem<MegaLegendaryLumberjackItem> MEGA_LEGENDARY_LUMBERJACK = ITEMS.registerItem("mega_legendary_lumberjack", MegaLegendaryLumberjackItem::new,
            p -> p.axe(ModMegaToolTiers.ZENITHRA, 14, -2.2F));
    public static final DeferredItem<MegaAtomicLumberjackItem> MEGA_ATOMIC_LUMBERJACK = ITEMS.registerItem("mega_atomic_lumberjack", MegaAtomicLumberjackItem::new,
            p -> p.fireResistant().axe(ModMegaToolTiers.OBSCURITE, 16, -2.0f));

    //Mega Hoe Tools\\
    public static final DeferredItem<MegaSimpleScytheItem> MEGA_SIMPLE_SCYTHE = ITEMS.registerItem("mega_simple_scythe", MegaSimpleScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.IGNITHRA, 4, -1.0F));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceScytheItem> MEGA_ADVANCE_SCYTHE = ITEMS.registerItem("mega_advance_scythe", MegaAdvanceScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.ASTRALITE, 6, -0.8F));
    public static final DeferredItem<MegaUltraScytheItem> MEGA_ULTRA_SCYTHE = ITEMS.registerItem("mega_ultra_scythe", MegaUltraScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.NEXALITE, 8, -0.6F));
    public static final DeferredItem<MegaUltimateScytheItem> MEGA_ULTIMATE_SCYTHE = ITEMS.registerItem("mega_ultimate_scythe", MegaUltimateScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.CELESTINE, 10, -0.4F));
    public static final DeferredItem<MegaLegendaryScytheItem> MEGA_LEGENDARY_SCYTHE = ITEMS.registerItem("mega_legendary_scythe", MegaLegendaryScytheItem::new,
            p -> p.hoe(ModMegaToolTiers.ZENITHRA, 12, -0.2F));
    public static final DeferredItem<MegaAtomicScytheItem> MEGA_ATOMIC_SCYTHE = ITEMS.registerItem("mega_atomic_scythe", MegaAtomicScytheItem::new,
            p -> p.fireResistant().hoe(ModMegaToolTiers.OBSCURITE, 14, 0f));


    public static void Register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
