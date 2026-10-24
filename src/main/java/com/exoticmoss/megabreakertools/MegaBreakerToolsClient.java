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
    }
}
