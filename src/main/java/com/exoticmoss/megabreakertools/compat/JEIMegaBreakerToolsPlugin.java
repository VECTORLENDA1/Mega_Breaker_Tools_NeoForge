package com.exoticmoss.megabreakertools.compat;


import com.exoticmoss.megabreakertools.MegaBreakerTools;
import com.exoticmoss.megabreakertools.item.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.exoticmoss.megabreakertools.util.RegistryHolder;

/// The tool recipes are "simpleores:gravity_craft" recipes, so they show up in the
/// Gravity Core category that Simple Ores adds to JEI. This plugin adds an info page to every tool.
/// Registered with the "jei_mod_plugin" entrypoint in fabric.mod.json.
@JeiPlugin
public class JEIMegaBreakerToolsPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(MegaBreakerTools.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        for (RegistryHolder<? extends Item> tool : ModItems.getEntries()) {
            if (!(tool.get() instanceof com.exoticmoss.megabreakertools.item.custom.IModeSwitchable)) continue;
            registration.addIngredientInfo(tool.get(),
                    Component.translatable("jei.megabreakertools.crafted_in"),
                    Component.literal(" "),
                    Component.translatable("jei.megabreakertools.mode_info"));
        }
    }
}
