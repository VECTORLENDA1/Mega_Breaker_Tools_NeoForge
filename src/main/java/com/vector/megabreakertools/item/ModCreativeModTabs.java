package com.vector.megabreakertools.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.vector.megabreakertools.MegaBreakerTools.MODID;


//Isto serve para cria um guia no modo creativo do jogo, para os teus items
public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            net.neoforged.neoforge.registries.DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> MEGA_BREAKER_TOOLS = CREATIVE_MODE_TABS.register("mega_breaker_tools",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SIMPLE_BREAKER.get()))
                .title(Component.translatable("creativetab.mega_breaker_tools"))
                .displayItems((pParameters, pOutput) -> {



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
