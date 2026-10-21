package com.exoticmoss.megabreakertools;

import com.mojang.logging.LogUtils;
import com.exoticmoss.megabreakertools.client.DropClusterItemRenderer;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import com.exoticmoss.megabreakertools.component.ModDataComponents;
import com.exoticmoss.megabreakertools.item.ModCreativeModTabs;
import com.exoticmoss.megabreakertools.item.ModItems;
import com.exoticmoss.megabreakertools.network.ToggleMiningModePacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
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
        ModDataComponents.register(modEventBus);
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

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        // 3D item model of the Drop Cluster ("type": "megabreakertools:drop_cluster" in the item model file)
        @SubscribeEvent
        public static void registerItemRenderers(RegisterSpecialModelRendererEvent event) {
            event.register(Identifier.fromNamespaceAndPath(MODID, "drop_cluster"), DropClusterItemRenderer.Unbaked.MAP_CODEC);
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
