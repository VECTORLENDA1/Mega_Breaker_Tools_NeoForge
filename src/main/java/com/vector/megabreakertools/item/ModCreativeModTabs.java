package com.vector.megabreakertools.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.vector.megabreakertools.MegaBreakerTools;
import net.vector.megabreakertools.block.ModBlocks;


//Isto serve para cria um guia no modo creativo dop jogo, para os teus items
public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MegaBreakerTools.MODID);

    public static final RegistryObject<CreativeModeTab> MEGABREAKERTOOLS = CREATIVE_MODE_TABS.register("mega_breaker_tools",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CELESTINE.get()))
                .title(Component.translatable("creativetab.mega_breaker_tools"))
                .displayItems((pParameters, pOutput) -> {

                    //ITEMS//
                    pOutput.accept(ModItems.CELESTINE.get());
                    pOutput.accept(ModItems.ZENITHRA.get());
                    pOutput.accept(ModItems.ASTRALITE.get());
                    pOutput.accept(ModItems.RAW_ASTRALITE.get());
                    pOutput.accept(ModItems.RAW_NEXALITE.get());
                    pOutput.accept(ModItems.NEXALITE.get());
                    pOutput.accept(ModItems.IGNITHRA.get());
                    pOutput.accept(ModItems.RAW_IGNITHRA.get());
                    pOutput.accept(ModItems.ANTRACITE.get());
                    pOutput.accept(ModItems.RAW_OBSCURIDIUM.get());
                    pOutput.accept(ModItems.OBSCURIDIUM.get());
                    pOutput.accept(ModItems.OBSCURITE.get());


                    //BLOCKS//
                    pOutput.accept(ModBlocks.END_STONE_CELESTINE_ORE.get());
                    pOutput.accept(ModBlocks.NEXALITE_BLOCK.get());
                    pOutput.accept(ModBlocks.IGNITHRA_BLOCK.get());
                    pOutput.accept(ModBlocks.RAW_IGNITHRA_BLOCK.get());
                    pOutput.accept(ModBlocks.DEEPSLATE_IGNITHRA_ORE.get());
                    pOutput.accept(ModBlocks.IGNITHRA_ORE.get());
                    pOutput.accept(ModBlocks.ASTRALITE_BLOCK.get());
                    pOutput.accept(ModBlocks.DEEPSLATE_ASTRALITE_ORE.get());
                    pOutput.accept(ModBlocks.END_STONE_ZENITHRA_ORE.get());
                    pOutput.accept(ModBlocks.RAW_NEXALITE_BLOCK.get());
                    pOutput.accept(ModBlocks.RAW_ASTRALITE_BLOCK.get());
                    pOutput.accept(ModBlocks.ZENITHRA_BLOCK.get());
                    pOutput.accept(ModBlocks.CELESTINE_BLOCK.get());
                    pOutput.accept(ModBlocks.DEEPSLATE_NEXALITE_ORE.get());
                    pOutput.accept(ModBlocks.NETHER_ANTRACITE_ORE.get());
                    pOutput.accept(ModBlocks.ANTRACITE_BLOCK.get());
                    pOutput.accept(ModBlocks.BEDROCK_OBSCURIDIUM_ORE.get());
                    pOutput.accept(ModBlocks.OBSCURIDIUM_BLOCK.get());
                    pOutput.accept(ModBlocks.RAW_OBSCURIDIUM_BLOCK.get());
                    pOutput.accept(ModBlocks.OBSCURITE_BLOCK.get());


                    //TOOLS
                    pOutput.accept(ModItems.SIMPLE_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_SIMPLE_BREAKER.get());
                    pOutput.accept(ModItems.ADVANCE_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ADVANCE_BREAKER.get());
                    pOutput.accept(ModItems.ULTRA_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ULTRA_BREAKER.get());
                    pOutput.accept(ModItems.ULTIMATE_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ULTIMATE_BREAKER.get());
                    pOutput.accept(ModItems.LEGENDARY_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_LEGENDARY_BREAKER.get());
                    pOutput.accept(ModItems.ATOMIC_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ATOMIC_BREAKER.get());

            })
                .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
