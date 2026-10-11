package com.vector.megabreakertools.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import com.vector.megabreakertools.MegaBreakerTools;

@EventBusSubscriber(modid = MegaBreakerTools.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModBlockTagProvider::new);
    }
}
