package com.vector.megabreakertools;

import com.mojang.logging.LogUtils;
import com.vector.megabreakertools.block.ModBlocks;
import com.vector.megabreakertools.block.entity.ModBlockEntities;
import com.vector.megabreakertools.item.ModCreativeModTabs;
import com.vector.megabreakertools.item.ModItems;
import com.vector.megabreakertools.network.ToggleMiningModePacket;
import com.vector.megabreakertools.recipe.ModRecipes;
import com.vector.megabreakertools.screen.ModMenuTypes;
import com.vector.megabreakertools.screen.custom.SimpleCraftingTableScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;


@Mod(MegaBreakerTools.MODID)
public class MegaBreakerTools {
    public static final String MODID = "megabreakertools";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MegaBreakerTools(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);


        ModItems.Register(modEventBus);
        ModBlocks.Register(modEventBus);
        ModRecipes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);




        modEventBus.addListener(this::registerPackets);

        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.SIMPLE_CRAFTING_TABLE_MENU.get(), SimpleCraftingTableScreen::new);
        }

    }

    private void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID);

        registrar.playToServer(
                ToggleMiningModePacket.TYPE,
                ToggleMiningModePacket.STREAM_CODEC,
                ToggleMiningModePacket::handle
        );
    }

}
