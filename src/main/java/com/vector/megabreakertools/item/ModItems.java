package com.vector.megabreakertools.item;

import com.vector.megabreakertools.item.custom.axe.*;
import com.vector.megabreakertools.item.custom.pickaxe.*;
import com.vector.megabreakertools.item.custom.pickaxe.SimpleBreakerItem;
import com.vector.megabreakertools.item.custom.shovel.*;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import com.vector.megabreakertools.item.custom.pickaxe.UltraBreakerItem;
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
    public static final DeferredItem<SimpleBreakerItem> SIMPLE_BREAKER = ITEMS.register("simple_breaker",
            () -> new SimpleBreakerItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 1, -2.8F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<AdvanceBreakerItem> ADVANCE_BREAKER = ITEMS.register("advance_breaker",
            () -> new AdvanceBreakerItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 3, -2.6F))));
    public static final DeferredItem<UltraBreakerItem> ULTRA_BREAKER = ITEMS.register("ultra_breaker",
            () -> new UltraBreakerItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 5, -2.4F))));
    public static final DeferredItem<UltimateBreakerItem> ULTIMATE_BREAKER = ITEMS.register("ultimate_breaker",
            () -> new UltimateBreakerItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 7, -2.2F))));
    public static final DeferredItem<LegendaryBreakerItem> LEGENDARY_BREAKER = ITEMS.register("legendary_breaker",
            () -> new LegendaryBreakerItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9, -2.0F))));
    public static final DeferredItem<AtomicBreakerItem> ATOMIC_BREAKER = ITEMS.register("atomic_breaker",
            () -> new AtomicBreakerItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
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
    public static final DeferredItem<MegaSimpleShovelItem> MEGA_SIMPLE_SHOVEL = ITEMS.register("mega_simple_shovel",
            () -> new MegaSimpleShovelItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.IGNITHRA, 1.5F, -3.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceShovelItem> MEGA_ADVANCE_SHOVEL = ITEMS.register("mega_advance_shovel",
            () -> new MegaAdvanceShovelItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.ASTRALITE, 4.5F, -2.8F))));
    public static final DeferredItem<MegaUltraShovelItem> MEGA_ULTRA_SHOVEL = ITEMS.register("mega_ultra_shovel",
            () -> new MegaUltraShovelItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.NEXALITE, 5.5F, -2.6F))));
    public static final DeferredItem<MegaUltimateShovelItem> MEGA_ULTIMATE_SHOVEL = ITEMS.register("mega_ultimate_shovel",
            () -> new MegaUltimateShovelItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.CELESTINE, 7.5F, -2.4F))));
    public static final DeferredItem<MegaLegendaryShovelItem> MEGA_LEGENDARY_SHOVEL = ITEMS.register("mega_legendary_shovel",
            () -> new MegaLegendaryShovelItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9.5F, -2.2F))));
    public static final DeferredItem<MegaAtomicShovelItem> MEGA_ATOMIC_SHOVEL = ITEMS.register("mega_atomic_shovel",
            () -> new MegaAtomicShovelItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(ShovelItem.createAttributes(ModMegaToolTiers.OBSCURITE, 11.5F, -2.0f))));

    //Mega Axe Tools\\
    public static final DeferredItem<MegaSimpleAxeItem> MEGA_SIMPLE_AXE = ITEMS.register("mega_simple_axe",
            () -> new MegaSimpleAxeItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 5, -3.0F))));//defaut - 1 damage, 4 attack speed
    public static final DeferredItem<MegaAdvanceAxeItem> MEGA_ADVANCE_AXE = ITEMS.register("mega_advance_axe",
            () -> new MegaAdvanceAxeItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 7, -2.8F))));
    public static final DeferredItem<MegaUltraAxeItem> MEGA_ULTRA_AXE = ITEMS.register("mega_ultra_axe",
            () -> new MegaUltraAxeItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 9, -2.6F))));
    public static final DeferredItem<MegaUltimateAxeItem> MEGA_ULTIMATE_AXE = ITEMS.register("mega_ultimate_axe",
            () -> new MegaUltimateAxeItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 11, -2.4F))));
    public static final DeferredItem<MegaLegendaryAxeItem> MEGA_LEGENDARY_AXE = ITEMS.register("mega_legendary_axe",
            () -> new MegaLegendaryAxeItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 13, -2.2F))));
    public static final DeferredItem<MegaAtomicAxeItem> MEGA_ATOMIC_AXE = ITEMS.register("mega_atomic_axe",
            () -> new MegaAtomicAxeItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(AxeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 15, -2.0f))));


    public static void Register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
