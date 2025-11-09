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

    //Items\\
    public static final DeferredItem<Item> CELESTINE = ITEMS.register("celestine",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ZENITHRA = ITEMS.register("zenithra",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ASTRALITE = ITEMS.register("astralite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NEXALITE = ITEMS.register("nexalite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IGNITHRA = ITEMS.register("ignithra",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OBSCURITE = ITEMS.register("obscurite",
            () -> new Item(new Item.Properties()));

    //Pickaxe Tools\\
    public static final DeferredItem<SimplePickaxeItem> SIMPLE_PICKAXE = ITEMS.register("simple_pickaxe",
            () -> new SimplePickaxeItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 1, -2.8F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvancePickaxeItem> ADVANCE_PICKAXE = ITEMS.register("advance_pickaxe",
            () -> new AdvancePickaxeItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 3, -2.6F))));
    public static final DeferredItem<UltraPickaxeItem> ULTRA_PICKAXE = ITEMS.register("ultra_pickaxe",
            () -> new UltraPickaxeItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 5, -2.4F))));
    public static final DeferredItem<UltimatePickaxeItem> ULTIMATE_PICKAXE = ITEMS.register("ultimate_pickaxe",
            () -> new UltimatePickaxeItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 7, -2.2F))));
    public static final DeferredItem<LegendaryPickaxeItem> LEGENDARY_PICKAXE = ITEMS.register("legendary_pickaxe",
            () -> new LegendaryPickaxeItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9, -2.0F))));
    public static final DeferredItem<AtomicPickaxeItem> ATOMIC_PICKAXE = ITEMS.register("atomic_pickaxe",
            () -> new AtomicPickaxeItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 11, -1.8F))));

    //Shovel Tools\\
    public static final DeferredItem<SimpleShovelItem> SIMPLE_SHOVEL = ITEMS.register("simple_shovel",
            () -> new SimpleShovelItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.IGNITHRA, 1.5F, -3.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvanceShovelItem> ADVANCE_SHOVEL = ITEMS.register("advance_shovel",
            () -> new AdvanceShovelItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.ASTRALITE, 3.5F, -2.8F))));
    public static final DeferredItem<UltraShovelItem> ULTRA_SHOVEL = ITEMS.register("ultra_shovel",
            () -> new UltraShovelItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.NEXALITE, 5.5F, -2.6F))));
    public static final DeferredItem<UltimateShovelItem> ULTIMATE_SHOVEL = ITEMS.register("ultimate_shovel",
            () -> new UltimateShovelItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.CELESTINE, 7.5F, -2.4F))));
    public static final DeferredItem<LegendaryShovelItem> LEGENDARY_SHOVEL = ITEMS.register("legendary_shovel",
            () -> new LegendaryShovelItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9.5F, -2.2F))));
    public static final DeferredItem<AtomicShovelItem> ATOMIC_SHOVEL = ITEMS.register("atomic_shovel",
            () -> new AtomicShovelItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.OBSCURITE, 11.5F, -2.0F))));

    //Axe Tools\\
    public static final DeferredItem<SimpleAxeItem> SIMPLE_AXE = ITEMS.register("simple_axe",
            () -> new SimpleAxeItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 5, -3.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvanceAxeItem> ADVANCE_AXE = ITEMS.register("advance_axe",
            () -> new AdvanceAxeItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 7, -2.8F))));
    public static final DeferredItem<UltraAxeItem> ULTRA_AXE = ITEMS.register("ultra_axe",
            () -> new UltraAxeItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 9, -2.6F))));
    public static final DeferredItem<UltimateAxeItem> ULTIMATE_AXE = ITEMS.register("ultimate_axe",
            () -> new UltimateAxeItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 11, -2.4F))));
    public static final DeferredItem<LegendaryAxeItem> LEGENDARY_AXE = ITEMS.register("legendary_axe",
            () -> new LegendaryAxeItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 13, -2.2F))));
    public static final DeferredItem<AtomicAxeItem> ATOMIC_AXE = ITEMS.register("atomic_axe",
            () -> new AtomicAxeItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 15, -2.0F))));

    //Hoe Tools\\
    public static final DeferredItem<SimpleHoeItem> SIMPLE_HOE = ITEMS.register("simple_hoe",
            () -> new SimpleHoeItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 3, -1.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvanceHoeItem> ADVANCE_HOE = ITEMS.register("advance_hoe",
            () -> new AdvanceHoeItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 5, -0.8F))));
    public static final DeferredItem<UltraHoeItem> ULTRA_HOE = ITEMS.register("ultra_hoe",
            () -> new UltraHoeItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.NEXALITE, 7, -0.6F))));
    public static final DeferredItem<UltimateHoeItem> ULTIMATE_HOE = ITEMS.register("ultimate_hoe",
            () -> new UltimateHoeItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.CELESTINE, 9, -0.4F))));
    public static final DeferredItem<LegendaryHoeItem> LEGENDARY_HOE = ITEMS.register("legendary_hoe",
            () -> new LegendaryHoeItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 11, -0.2F))));
    public static final DeferredItem<AtomicHoeItem> ATOMIC_HOE = ITEMS.register("atomic_hoe",
            () -> new AtomicHoeItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 13, 0F))));

    //Mega Pickaxe Tools\\
    public static final DeferredItem<MegaSimpleBreakerItem> MEGA_SIMPLE_BREAKER = ITEMS.register("mega_simple_breaker",
            () -> new MegaSimpleBreakerItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 1, -2.8F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceBreakerItem> MEGA_ADVANCE_BREAKER = ITEMS.register("mega_advance_breaker",
            () -> new MegaAdvanceBreakerItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 3, -2.6F))));
    public static final DeferredItem<MegaUltraBreakerItem> MEGA_ULTRA_BREAKER = ITEMS.register("mega_ultra_breaker",
            () -> new MegaUltraBreakerItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 5, -2.4F))));
    public static final DeferredItem<MegaUltimateBreakerItem> MEGA_ULTIMATE_BREAKER = ITEMS.register("mega_ultimate_breaker",
            () -> new MegaUltimateBreakerItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 7, -2.2F))));
    public static final DeferredItem<MegaLegendaryBreakerItem> MEGA_LEGENDARY_BREAKER = ITEMS.register("mega_legendary_breaker",
            () -> new MegaLegendaryBreakerItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9, -2.0F))));
    public static final DeferredItem<MegaAtomicBreakerItem> MEGA_ATOMIC_BREAKER = ITEMS.register("mega_atomic_breaker",
            () -> new MegaAtomicBreakerItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 11, -1.8f))));

    //Mega Shovel Tools\\
    public static final DeferredItem<MegaSimpleExcavatorItem> MEGA_SIMPLE_EXCAVATOR = ITEMS.register("mega_simple_excavator",
            () -> new MegaSimpleExcavatorItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.IGNITHRA, 1.5F, -3.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceExcavatorItem> MEGA_ADVANCE_EXCAVATOR = ITEMS.register("mega_advance_excavator",
            () -> new MegaAdvanceExcavatorItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.ASTRALITE, 4.5F, -2.8F))));
    public static final DeferredItem<MegaUltraExcavatorItem> MEGA_ULTRA_EXCAVATOR = ITEMS.register("mega_ultra_excavator",
            () -> new MegaUltraExcavatorItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.NEXALITE, 5.5F, -2.6F))));
    public static final DeferredItem<MegaUltimateExcavatorItem> MEGA_ULTIMATE_EXCAVATOR = ITEMS.register("mega_ultimate_excavator",
            () -> new MegaUltimateExcavatorItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.CELESTINE, 7.5F, -2.4F))));
    public static final DeferredItem<MegaLegendaryExcavatorItem> MEGA_LEGENDARY_EXCAVATOR = ITEMS.register("mega_legendary_excavator",
            () -> new MegaLegendaryExcavatorItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9.5F, -2.2F))));
    public static final DeferredItem<MegaAtomicExcavatorItem> MEGA_ATOMIC_EXCAVATOR = ITEMS.register("mega_atomic_excavator",
            () -> new MegaAtomicExcavatorItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.OBSCURITE, 11.5F, -2.0f))));

    //Mega Axe Tools\\
    public static final DeferredItem<MegaSimpleLumberjackItem> MEGA_SIMPLE_LUMBERJACK = ITEMS.register("mega_simple_lumberjack",
            () -> new MegaSimpleLumberjackItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 5, -3.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceLumberjackItem> MEGA_ADVANCE_LUMBERJACK = ITEMS.register("mega_advance_lumberjack",
            () -> new MegaAdvanceLumberjackItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 7, -2.8F))));
    public static final DeferredItem<MegaUltraLumberjackItem> MEGA_ULTRA_LUMBERJACK = ITEMS.register("mega_ultra_lumberjack",
            () -> new MegaUltraLumberjackItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 9, -2.6F))));
    public static final DeferredItem<MegaUltimateLumberjackItem> MEGA_ULTIMATE_LUMBERJACK = ITEMS.register("mega_ultimate_lumberjack",
            () -> new MegaUltimateLumberjackItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 11, -2.4F))));
    public static final DeferredItem<MegaLegendaryLumberjackItem> MEGA_LEGENDARY_LUMBERJACK = ITEMS.register("mega_legendary_lumberjack",
            () -> new MegaLegendaryLumberjackItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 13, -2.2F))));
    public static final DeferredItem<MegaAtomicLumberjackItem> MEGA_ATOMIC_LUMBERJACK = ITEMS.register("mega_atomic_lumberjack",
            () -> new MegaAtomicLumberjackItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 15, -2.0f))));

    //Mega Hoe Tools\\
    public static final DeferredItem<MegaSimpleScytheItem> MEGA_SIMPLE_SCYTHE = ITEMS.register("mega_simple_scythe",
            () -> new MegaSimpleScytheItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 3, -1.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceScytheItem> MEGA_ADVANCE_SCYTHE = ITEMS.register("mega_advance_scythe",
            () -> new MegaAdvanceScytheItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 5, -0.8F))));
    public static final DeferredItem<MegaUltraScytheItem> MEGA_ULTRA_SCYTHE = ITEMS.register("mega_ultra_scythe",
            () -> new MegaUltraScytheItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.NEXALITE, 7, -0.6F))));
    public static final DeferredItem<MegaUltimateScytheItem> MEGA_ULTIMATE_SCYTHE = ITEMS.register("mega_ultimate_scythe",
            () -> new MegaUltimateScytheItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.CELESTINE, 9, -0.4F))));
    public static final DeferredItem<MegaLegendaryScytheItem> MEGA_LEGENDARY_SCYTHE = ITEMS.register("mega_legendary_scythe",
            () -> new MegaLegendaryScytheItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 11, -0.2F))));
    public static final DeferredItem<MegaAtomicScytheItem> MEGA_ATOMIC_SCYTHE = ITEMS.register("mega_atomic_scythe",
            () -> new MegaAtomicScytheItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(HoeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 13, 0f))));


    public static void Register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
