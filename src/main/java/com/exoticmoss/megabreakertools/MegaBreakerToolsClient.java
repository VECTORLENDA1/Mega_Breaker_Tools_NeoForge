package com.exoticmoss.megabreakertools;

import com.exoticmoss.megabreakertools.event.ClientEvents;
import net.fabricmc.api.ClientModInitializer;

/// Client side of the mod: Shift + Scroll to change the mode of the tools.
/// The 3D item model of the Drop Cluster ("type": "megabreakertools:drop_cluster" in the item model file)
/// is registered by SpecialModelRenderersMixin.
public class MegaBreakerToolsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientEvents.register();
        // The tool settings key (G by default, can be changed in Controls)
        net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(com.exoticmoss.megabreakertools.client.ToolSettingsKey.KEY);
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(
                client -> com.exoticmoss.megabreakertools.client.ToolSettingsKey.tick());
    }
}
