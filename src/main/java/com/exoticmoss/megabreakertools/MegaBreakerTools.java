package com.exoticmoss.megabreakertools;

import com.mojang.logging.LogUtils;
import com.exoticmoss.megabreakertools.component.ModDataComponents;
import com.exoticmoss.megabreakertools.event.ModEvents;
import com.exoticmoss.megabreakertools.item.ModCreativeModTabs;
import com.exoticmoss.megabreakertools.item.ModItems;
import com.exoticmoss.megabreakertools.network.ToggleMiningModePacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;


public class MegaBreakerTools implements ModInitializer {
    public static final String MODID = "megabreakertools";
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ModDataComponents.register();
        ModItems.Register();
        ModCreativeModTabs.register();

        registerPackets();

        ModEvents.register();
    }

    private void registerPackets() {
        PayloadTypeRegistry.serverboundPlay().register(
                ToggleMiningModePacket.TYPE,
                ToggleMiningModePacket.STREAM_CODEC
        );
        ServerPlayNetworking.registerGlobalReceiver(
                ToggleMiningModePacket.TYPE,
                ToggleMiningModePacket::handle
        );

        // Cluster screen: take everything out / delete items
        PayloadTypeRegistry.serverboundPlay().register(
                com.exoticmoss.megabreakertools.network.ClusterActionPacket.TYPE,
                com.exoticmoss.megabreakertools.network.ClusterActionPacket.STREAM_CODEC
        );
        ServerPlayNetworking.registerGlobalReceiver(
                com.exoticmoss.megabreakertools.network.ClusterActionPacket.TYPE,
                com.exoticmoss.megabreakertools.network.ClusterActionPacket::handle
        );
    }

}
