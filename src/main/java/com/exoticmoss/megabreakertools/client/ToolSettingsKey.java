package com.exoticmoss.megabreakertools.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

/// The key that opens the tool settings screen (G by default; it can be changed in Options > Controls).
public final class ToolSettingsKey {
    public static final KeyMapping KEY = new KeyMapping("key.megabreakertools.tool_settings",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KeyMapping.Category.GAMEPLAY);

    private ToolSettingsKey() {
    }

    /// Call it every client tick: opens the screen when the key is pressed with a tool in the hand.
    public static void tick() {
        while (KEY.consumeClick()) {
            ToolSettingsScreen.openIfHolding();
        }
    }
}
