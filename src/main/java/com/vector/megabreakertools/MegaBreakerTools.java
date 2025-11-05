package com.vector.megabreakertools;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vector.megabreakertools.block.ModBlocks;
import net.vector.megabreakertools.item.ModCreativeModTabs;
import net.vector.megabreakertools.item.ModItems;
import org.slf4j.Logger;


@Mod(MegaBreakerTools.MODID)
public class MegaBreakerTools {
    public static final String MODID = "megabreakertools";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MegaBreakerTools()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);




        ModItems.Register(modEventBus);
        ModBlocks.Register(modEventBus);
        ModCreativeModTabs.register(modEventBus);





        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
    }
}
