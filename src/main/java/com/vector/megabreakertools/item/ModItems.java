package com.vector.megabreakertools.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vector.megabreakertools.MegaBreakerTools;
import net.vector.megabreakertools.item.custom.*;
import net.vector.megabreakertools.item.custom.pickaxe.*;
import net.vector.megabreakertools.item.custom.pickaxe.SimpleBreakerItem;
import net.vector.megabreakertools.item.custom.pickaxe.UltraBreakerItem;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MegaBreakerTools.MODID);



    //Items//
    public static final RegistryObject<Item> CELESTINE = ITEMS.register("celestine",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZENITHRA = ITEMS.register("zenithra",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ASTRALITE = ITEMS.register("astralite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ASTRALITE = ITEMS.register("raw_astralite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_NEXALITE = ITEMS.register("raw_nexalite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NEXALITE = ITEMS.register("nexalite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IGNITHRA = ITEMS.register("ignithra",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_IGNITHRA = ITEMS.register("raw_ignithra",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANTRACITE = ITEMS.register("antracite",
            () -> new FuelItem(new Item.Properties(), 3200));
    public static final RegistryObject<Item> RAW_OBSCURIDIUM = ITEMS.register("raw_obscuridium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OBSCURIDIUM = ITEMS.register("obscuridium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OBSCURITE = ITEMS.register("obscurite",
            () -> new Item(new Item.Properties()));


    //Mega Tools
    public static final RegistryObject<Item> SIMPLE_BREAKER = ITEMS.register("simple_breaker",
            () -> new SimpleBreakerItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 2, -3.3F))));//defaut - 1 damage, 4 attack speed
    public static final RegistryObject<Item> MEGA_SIMPLE_BREAKER = ITEMS.register("mega_simple_breaker",
            () -> new MegaSimpleBreakerItem(ModMegaToolTiers.IGNITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.IGNITHRA, 2, -3.3F))));
    public static final RegistryObject<Item> ADVANCE_BREAKER = ITEMS.register("advance_breaker",
            () -> new AdvanceBreakerItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 4, -3.2F))));
    public static final RegistryObject<Item> MEGA_ADVANCE_BREAKER = ITEMS.register("mega_advance_breaker",
            () -> new MegaAdvanceBreakerItem(ModMegaToolTiers.ASTRALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ASTRALITE, 4, -3.2F))));
    public static final RegistryObject<Item> ULTRA_BREAKER = ITEMS.register("ultra_breaker",
            () -> new UltraBreakerItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 5, -3.1F))));
    public static final RegistryObject<Item> MEGA_ULTRA_BREAKER = ITEMS.register("mega_ultra_breaker",
            () -> new MegaUltraBreakerItem(ModMegaToolTiers.NEXALITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.NEXALITE, 5, -3.1F))));
    public static final RegistryObject<Item> ULTIMATE_BREAKER = ITEMS.register("ultimate_breaker",
            () -> new UltimateBreakerItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 7, -3.0F))));
    public static final RegistryObject<Item> MEGA_ULTIMATE_BREAKER = ITEMS.register("mega_ultimate_breaker",
            () -> new MegaUltimateBreakerItem(ModMegaToolTiers.CELESTINE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.CELESTINE, 7, -3.0F))));
    public static final RegistryObject<Item> LEGENDARY_BREAKER = ITEMS.register("legendary_breaker",
            () -> new LegendaryBreakerItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9, -2.9F))));
    public static final RegistryObject<Item> MEGA_LEGENDARY_BREAKER = ITEMS.register("mega_legendary_breaker",
            () -> new MegaLegendaryBreakerItem(ModMegaToolTiers.ZENITHRA, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.ZENITHRA, 9, -2.9F))));
    public static final RegistryObject<Item> ATOMIC_BREAKER = ITEMS.register("atomic_breaker",
            () -> new AtomicBreakerItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 11, -2.8F))));
    public static final RegistryObject<Item> MEGA_ATOMIC_BREAKER = ITEMS.register("mega_atomic_breaker",
            () -> new MegaAtomicBreakerItem(ModMegaToolTiers.OBSCURITE, new Item.Properties().fireResistant()
                    .attributes(PickaxeItem.createAttributes(ModMegaToolTiers.OBSCURITE, 11, -2.8f))));



    public static void Register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
