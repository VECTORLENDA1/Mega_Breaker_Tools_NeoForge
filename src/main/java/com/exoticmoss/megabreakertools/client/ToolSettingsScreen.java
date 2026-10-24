package com.exoticmoss.megabreakertools.client;

import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import com.exoticmoss.megabreakertools.network.ToolSettingsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/// The settings screen of the tool in the main hand (opened with the "tool settings" key, G by default).
///  - Left: the tool, big and turning slowly.
///  - Under it: one button per mode (Area, Tree/Vein for axes/pickaxes, Single).
///  - Right: one button per area size the tool can do (a Simple tool only 3x3, an Atomic one 3x3 up to 13x13;
///    Mega tools show cubes, 3x3x3 up to 13x13x13). Smaller sizes are always allowed, never bigger ones.
/// The chosen button is shown pressed (greyed). Changes are saved on the tool right away.
public class ToolSettingsScreen extends Screen {
    /// Size of the box with the turning tool, and of the whole panel (pixels).
    private static final int VIEW = 104;
    private static final int PANEL_WIDTH = 236;
    private static final int PANEL_HEIGHT = 188;
    /// Where the tool box and the size buttons start (under the name of the tool).
    private static final int CONTENT_Y = 34;
    /// Size of the tool inside the box, and how fast it turns.
    private static final float TOOL_SCALE = 74;
    private static final float TURN_SPEED = 1.0F;
    /// Colors (0xAARRGGBB).
    private static final int PANEL = 0xE0101018;
    private static final int PANEL_BORDER = 0xFF8060C0;
    private static final int VIEW_COLOR = 0xFF20202C;
    private static final int TEXT = 0xFFFFFFFF;
    private static final int TEXT_DIM = 0xFFA0A0B0;

    private int left, top;
    private ItemEntity display;

    public ToolSettingsScreen() {
        super(Component.empty());
    }

    /// Opens the screen if the player holds a Mega Breaker tool (client only).
    public static void openIfHolding() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.player.getMainHandItem().getItem() instanceof IModeSwitchable) {
            minecraft.gui.setScreen(new ToolSettingsScreen());
        }
    }

    private static void send(ToolSettingsPacket packet) {
        ClientPlayNetworking.send(packet);
    }

    private ItemStack tool() {
        return minecraft.player == null ? ItemStack.EMPTY : minecraft.player.getMainHandItem();
    }

    @Override
    protected void init() {
        left = (width - PANEL_WIDTH) / 2;
        top = (height - PANEL_HEIGHT) / 2;
        ItemStack stack = tool();
        if (!(stack.getItem() instanceof IModeSwitchable tool)) return;
        int mode = IModeSwitchable.getMiningMode(stack);
        int range = IModeSwitchable.areaRange(stack);

        // Modes: under the tool
        var modes = tool.modes();
        int modeWidth = (VIEW - (modes.size() - 1) * 3) / modes.size();
        for (int i = 0; i < modes.size(); i++) {
            int m = modes.get(i);
            Button button = Button.builder(Component.translatable(modeKey(m)), b -> choose(m, IModeSwitchable.areaRange(tool())))
                    .bounds(left + 10 + i * (modeWidth + 3), top + CONTENT_Y + VIEW + 14, modeWidth, 20).build();
            button.active = m != mode;
            addRenderableWidget(button);
        }

        // Area sizes: on the right, from the smallest to the biggest the tool can do
        int sizesX = left + 10 + VIEW + 12;
        int sizesWidth = PANEL_WIDTH - (sizesX - left) - 10;
        int rowHeight = Math.min(22, (PANEL_HEIGHT - CONTENT_Y - 10) / Math.max(1, tool.getRange()));
        for (int r = 1; r <= tool.getRange(); r++) {
            int chosen = r;
            Button button = Button.builder(Component.literal(tool.sizeName(r)), b -> choose(IModeSwitchable.getMiningMode(tool()), chosen))
                    .bounds(sizesX, top + CONTENT_Y + (r - 1) * rowHeight, sizesWidth, rowHeight - 2).build();
            button.active = r != range;
            addRenderableWidget(button);
        }
    }

    private static String modeKey(int mode) {
        return switch (mode) {
            case IModeSwitchable.MODE_TREE -> "screen.megabreakertools.tool.mode.tree";
            case IModeSwitchable.MODE_VEIN -> "screen.megabreakertools.tool.mode.vein";
            case IModeSwitchable.MODE_SINGLE -> "screen.megabreakertools.tool.mode.single";
            default -> "screen.megabreakertools.tool.mode.area";
        };
    }

    /// Saves the choice (on the client right away, and on the server) and redraws the buttons.
    private void choose(int mode, int range) {
        ToolSettingsPacket packet = new ToolSettingsPacket(mode, range);
        if (minecraft.player != null) packet.apply(minecraft.player);
        send(packet);
        rebuildWidgets();
    }

    @Override
    public void tick() {
        // The tool is no longer in the hand: close
        if (!(tool().getItem() instanceof IModeSwitchable)) onClose();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        ItemStack stack = tool();
        graphics.fill(left, top, left + PANEL_WIDTH, top + PANEL_HEIGHT, PANEL);
        graphics.outline(left, top, PANEL_WIDTH, PANEL_HEIGHT, PANEL_BORDER);
        graphics.centeredText(font, stack.getHoverName(), left + PANEL_WIDTH / 2, top + 8, TEXT);

        // The tool, turning slowly (drawn like an item on the ground, inside the box)
        int vx = left + 10, vy = top + CONTENT_Y;
        graphics.fill(vx, vy, vx + VIEW, vy + VIEW, VIEW_COLOR);
        if (!stack.isEmpty() && minecraft.level != null) {
            if (display == null || !ItemStack.isSameItem(display.getItem(), stack)) {
                display = new ItemEntity(minecraft.level, 0, 0, 0, stack.copy());
                display.setId(Integer.MIN_VALUE + 7);
            }
            EntityRenderer<? super ItemEntity, ?> renderer = minecraft.getEntityRenderDispatcher().getRenderer(display);
            EntityRenderState state = renderer.createRenderState(display, a);
            state.ageInTicks = (minecraft.player != null ? minecraft.player.tickCount : 0) * TURN_SPEED + a;
            state.shadowPieces.clear();
            state.outlineColor = 0;
            graphics.entity(state, TOOL_SCALE, new Vector3f(0, 0.35F, 0), new Quaternionf().rotateZ((float) Math.PI), null,
                    vx, vy, vx + VIEW, vy + VIEW);
        }
        graphics.text(font, Component.translatable("screen.megabreakertools.tool.mode"), vx, vy + VIEW + 4, TEXT_DIM);
        graphics.text(font, Component.translatable("screen.megabreakertools.tool.size"), vx + VIEW + 12, top + CONTENT_Y - 11, TEXT_DIM);

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
