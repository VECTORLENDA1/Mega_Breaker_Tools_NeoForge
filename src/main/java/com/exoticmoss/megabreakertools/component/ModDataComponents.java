package com.exoticmoss.megabreakertools.component;

import com.exoticmoss.megabreakertools.util.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import static com.exoticmoss.megabreakertools.MegaBreakerTools.MODID;

public class ModDataComponents {
    /// The items stored inside a Drop Cluster
    public static final RegistryHolder<DataComponentType<ClusterContents>> CLUSTER_CONTENTS = new RegistryHolder<>(
            Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MODID, "cluster_contents"),
                    DataComponentType.<ClusterContents>builder()
                            .persistent(ClusterContents.CODEC)
                            .networkSynchronized(ClusterContents.STREAM_CODEC)
                            .cacheEncoding()
                            .build()));

    /// Loads the class (the component is registered above)
    public static void register() {
    }
}
