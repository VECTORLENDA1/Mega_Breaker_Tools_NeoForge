package com.exoticmoss.megabreakertools.mixin.client;

import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.client.DropClusterItemRenderer;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// Fabric has no event to register special item model renderers (NeoForge RegisterSpecialModelRendererEvent):
/// the 3D model of the Drop Cluster ("type": "megabreakertools:drop_cluster" in the item model file) is added here.
@Mixin(SpecialModelRenderers.class)
public abstract class SpecialModelRenderersMixin {
    @Shadow
    @Final
    private static ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends SpecialModelRenderer.Unbaked<?>>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void megabreakertools$registerItemRenderers(CallbackInfo ci) {
        ID_MAPPER.put(Identifier.fromNamespaceAndPath(MegaBreakerTools.MODID, "drop_cluster"), DropClusterItemRenderer.Unbaked.MAP_CODEC);
    }
}
