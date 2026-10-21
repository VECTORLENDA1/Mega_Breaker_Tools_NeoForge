package com.exoticmoss.megabreakertools.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.exoticmoss.megabreakertools.MegaBreakerTools.MODID;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);

    /// The items stored inside a Drop Cluster
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ClusterContents>> CLUSTER_CONTENTS =
            DATA_COMPONENTS.registerComponentType("cluster_contents", builder -> builder
                    .persistent(ClusterContents.CODEC)
                    .networkSynchronized(ClusterContents.STREAM_CODEC)
                    .cacheEncoding());

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
