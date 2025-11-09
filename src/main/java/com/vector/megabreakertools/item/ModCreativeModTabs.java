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
            () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(ModItems.SIMPLE_PICKAXE.get()))
                .title(Component.translatable("creativetab.mega_breaker_tools"))
                .displayItems((pParameters, pOutput) -> {



                    //TOOLS//
                    pOutput.accept(ModItems.SIMPLE_PICKAXE.get());
                    pOutput.accept(ModItems.ADVANCE_PICKAXE.get());
                    pOutput.accept(ModItems.ULTRA_PICKAXE.get());
                    pOutput.accept(ModItems.ULTIMATE_PICKAXE.get());
                    pOutput.accept(ModItems.LEGENDARY_PICKAXE.get());
                    pOutput.accept(ModItems.ATOMIC_PICKAXE.get());
                    pOutput.accept(ModItems.SIMPLE_SHOVEL.get());
                    pOutput.accept(ModItems.ADVANCE_SHOVEL.get());
                    pOutput.accept(ModItems.ULTRA_SHOVEL.get());
                    pOutput.accept(ModItems.ULTIMATE_SHOVEL.get());
                    pOutput.accept(ModItems.LEGENDARY_SHOVEL.get());
                    pOutput.accept(ModItems.ATOMIC_SHOVEL.get());
                    pOutput.accept(ModItems.SIMPLE_AXE.get());
                    pOutput.accept(ModItems.ADVANCE_AXE.get());
                    pOutput.accept(ModItems.ULTRA_AXE.get());
                    pOutput.accept(ModItems.ULTIMATE_AXE.get());
                    pOutput.accept(ModItems.LEGENDARY_AXE.get());
                    pOutput.accept(ModItems.ATOMIC_AXE.get());
                    pOutput.accept(ModItems.SIMPLE_HOE.get());
                    pOutput.accept(ModItems.ADVANCE_HOE.get());
                    pOutput.accept(ModItems.ULTRA_HOE.get());
                    pOutput.accept(ModItems.ULTIMATE_HOE.get());
                    pOutput.accept(ModItems.LEGENDARY_HOE.get());
                    pOutput.accept(ModItems.ATOMIC_HOE.get());


                    //MEGA TOOLS//
                    pOutput.accept(ModItems.MEGA_SIMPLE_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ADVANCE_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ULTRA_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ULTIMATE_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_LEGENDARY_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_ATOMIC_BREAKER.get());
                    pOutput.accept(ModItems.MEGA_SIMPLE_EXCAVATOR.get());
                    pOutput.accept(ModItems.MEGA_ADVANCE_EXCAVATOR.get());
                    pOutput.accept(ModItems.MEGA_ULTRA_EXCAVATOR.get());
                    pOutput.accept(ModItems.MEGA_ULTIMATE_EXCAVATOR.get());
                    pOutput.accept(ModItems.MEGA_LEGENDARY_EXCAVATOR.get());
                    pOutput.accept(ModItems.MEGA_ATOMIC_EXCAVATOR.get());
                    pOutput.accept(ModItems.MEGA_SIMPLE_LUMBERJACK.get());
                    pOutput.accept(ModItems.MEGA_ADVANCE_LUMBERJACK.get());
                    pOutput.accept(ModItems.MEGA_ULTRA_LUMBERJACK.get());
                    pOutput.accept(ModItems.MEGA_ULTIMATE_LUMBERJACK.get());
                    pOutput.accept(ModItems.MEGA_LEGENDARY_LUMBERJACK.get());
                    pOutput.accept(ModItems.MEGA_ATOMIC_LUMBERJACK.get());
                    pOutput.accept(ModItems.MEGA_SIMPLE_SCYTHE.get());
                    pOutput.accept(ModItems.MEGA_ADVANCE_SCYTHE.get());
                    pOutput.accept(ModItems.MEGA_ULTRA_SCYTHE.get());
                    pOutput.accept(ModItems.MEGA_ULTIMATE_SCYTHE.get());
                    pOutput.accept(ModItems.MEGA_LEGENDARY_SCYTHE.get());
                    pOutput.accept(ModItems.MEGA_ATOMIC_SCYTHE.get());

            })
                .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
