package com.exoticmoss.megabreakertools.client;

import com.exoticmoss.megabreakertools.component.ClusterContents;
import com.exoticmoss.megabreakertools.item.custom.DropClusterItem;
import com.exoticmoss.megabreakertools.network.ClusterActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;

/// The screen of a big Drop Cluster (DropClusterItem.MANAGE_THRESHOLD items or more).
///  - A row with every kind of item inside (most first): icon, amount and a red X. Mouse wheel or the
///    arrows move the row.
///  - Clicking the red X of an item opens a slider to choose how many of that item to delete.
///  - "Take all" takes everything out into the inventory (like right-click on a small cluster).
/// The screen only asks the server (ClusterActionPacket); the server changes the cluster.
public class ClusterScreen extends Screen {
    /// How many item cells are seen at once, and the size of one cell (pixels).
    private static final int VISIBLE = 7;
    private static final int CELL = 34;
    private static final int GAP = 4;
    /// Size of the red X of a cell.
    private static final int X_SIZE = 9;
    /// Colors (0xAARRGGBB).
    private static final int PANEL = 0xE0101018;
    private static final int PANEL_BORDER = 0xFF8060C0;
    private static final int CELL_COLOR = 0xFF2A2A38;
    private static final int CELL_SELECTED = 0xFF5A3A80;
    private static final int X_COLOR = 0xFFC02030;
    private static final int TEXT = 0xFFFFFFFF;
    private static final int TEXT_DIM = 0xFFA0A0B0;

    private final InteractionHand hand;
    /// First visible cell.
    private int scroll;
    /// The item chosen to delete (index in the cluster), or -1.
    private int selected = -1;
    private String selectedItem = "";
    private int deleteAmount = 1;

    private int left, top, panelWidth, panelHeight, rowY;

    public ClusterScreen(InteractionHand hand) {
        super(Component.translatable("screen.megabreakertools.cluster.title"));
        this.hand = hand;
    }

    /// Opens the screen for the cluster in this hand (client only).
    public static void open(InteractionHand hand) {
        Minecraft.getInstance().gui.setScreen(new ClusterScreen(hand));
    }

    private static void send(ClusterActionPacket packet) {
        ClientPacketDistributor.sendToServer(packet);
    }

    // ---------------------------------------------------------------- the cluster

    private ItemStack cluster() {
        return minecraft.player == null ? ItemStack.EMPTY : minecraft.player.getItemInHand(hand);
    }

    private ClusterContents contents() {
        ItemStack stack = cluster();
        return stack.getItem() instanceof DropClusterItem ? DropClusterItem.getContents(stack) : ClusterContents.EMPTY;
    }

    /// The indexes of the entries, the kinds with more items first.
    private List<Integer> order(ClusterContents contents) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < contents.entries().size(); i++) order.add(i);
        order.sort((a, b) -> Integer.compare(contents.entries().get(b).count(), contents.entries().get(a).count()));
        return order;
    }

    // ---------------------------------------------------------------- layout and buttons

    @Override
    protected void init() {
        // The chosen item is not in the cluster anymore (deleted, taken out)
        if (selected >= contents().entries().size()) selected = -1;
        panelWidth = VISIBLE * CELL + (VISIBLE - 1) * GAP + 2 * 28;
        panelHeight = selected >= 0 ? 150 : 100;
        left = (width - panelWidth) / 2;
        top = (height - panelHeight) / 2;
        rowY = top + 24;
        int arrowY = rowY + CELL / 2 - 10;

        addRenderableWidget(Button.builder(Component.literal("<"), b -> scrollBy(-1)).bounds(left + 4, arrowY, 20, 20).build());
        addRenderableWidget(Button.builder(Component.literal(">"), b -> scrollBy(1)).bounds(left + panelWidth - 24, arrowY, 20, 20).build());

        int buttonsY = top + panelHeight - 26;
        addRenderableWidget(Button.builder(Component.translatable("screen.megabreakertools.cluster.take_all"), b -> {
            send(new ClusterActionPacket(hand == InteractionHand.MAIN_HAND, ClusterActionPacket.TAKE_ALL, 0, "", 0));
            onClose();
        }).bounds(left + panelWidth / 2 - 104, buttonsY, 100, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("screen.megabreakertools.cluster.close"), b -> onClose())
                .bounds(left + panelWidth / 2 + 4, buttonsY, 100, 20).build());

        if (selected >= 0) {
            ClusterContents contents = contents();
            int count = contents.entries().get(selected).count();
            deleteAmount = Mth.clamp(deleteAmount, 1, count);
            int sliderY = rowY + CELL + 30;
            addRenderableWidget(new AmountSlider(left + 40, sliderY, panelWidth - 80 - 120, count));
            addRenderableWidget(Button.builder(Component.translatable("screen.megabreakertools.cluster.delete"), b -> {
                send(new ClusterActionPacket(hand == InteractionHand.MAIN_HAND, ClusterActionPacket.DELETE, selected, selectedItem, deleteAmount));
                selected = -1;
                rebuildWidgets();
            }).bounds(left + panelWidth - 156, sliderY, 56, 20).build());
            addRenderableWidget(Button.builder(Component.translatable("screen.megabreakertools.cluster.cancel"), b -> {
                selected = -1;
                rebuildWidgets();
            }).bounds(left + panelWidth - 96, sliderY, 56, 20).build());
        }
    }

    private void scrollBy(int cells) {
        int max = Math.max(0, contents().entries().size() - VISIBLE);
        scroll = Mth.clamp(scroll + cells, 0, max);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
        if (scrollY != 0) scrollBy(scrollY > 0 ? -1 : 1);
        return true;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        ClusterContents contents = contents();
        List<Integer> order = order(contents);
        for (int slot = 0; slot < VISIBLE && scroll + slot < order.size(); slot++) {
            int x = cellX(slot);
            // The red X in the top right corner of the cell
            if (event.x() >= x + CELL - X_SIZE - 1 && event.x() < x + CELL - 1 && event.y() >= rowY + 1 && event.y() < rowY + 1 + X_SIZE) {
                int index = order.get(scroll + slot);
                selected = index;
                selectedItem = BuiltInRegistries.ITEM.getKey(contents.entries().get(index).create(1).getItem()).toString();
                deleteAmount = 1;
                rebuildWidgets();
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    private int cellX(int slot) {
        return left + 28 + slot * (CELL + GAP);
    }

    // ---------------------------------------------------------------- drawing

    @Override
    public void tick() {
        // The cluster is gone (all taken out, deleted or dropped): close
        if (!(cluster().getItem() instanceof DropClusterItem)) onClose();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        ClusterContents contents = contents();
        List<Integer> order = order(contents);
        scroll = Mth.clamp(scroll, 0, Math.max(0, order.size() - VISIBLE));

        // Panel
        graphics.fill(left, top, left + panelWidth, top + panelHeight, PANEL);
        graphics.outline(left, top, panelWidth, panelHeight, PANEL_BORDER);
        graphics.centeredText(font, title, left + panelWidth / 2, top + 6, TEXT);
        graphics.centeredText(font, Component.translatable("screen.megabreakertools.cluster.info", contents.totalCount(), contents.experience()),
                left + panelWidth / 2, rowY + CELL + 6, TEXT_DIM);

        // Item cells
        for (int slot = 0; slot < VISIBLE; slot++) {
            int x = cellX(slot);
            int index = scroll + slot < order.size() ? order.get(scroll + slot) : -1;
            graphics.fill(x, rowY, x + CELL, rowY + CELL, index >= 0 && index == selected ? CELL_SELECTED : CELL_COLOR);
            if (index < 0) continue;

            ClusterContents.Entry entry = contents.entries().get(index);
            ItemStack icon = entry.create(1);
            graphics.item(icon, x + (CELL - 16) / 2, rowY + 4);
            graphics.centeredText(font, shortCount(entry.count()), x + CELL / 2, rowY + CELL - 10, TEXT);
            // Red X
            int xx = x + CELL - X_SIZE - 1;
            graphics.fill(xx, rowY + 1, xx + X_SIZE, rowY + 1 + X_SIZE, X_COLOR);
            graphics.centeredText(font, "x", xx + X_SIZE / 2 + 1, rowY + 1, TEXT);

            if (mouseX >= x && mouseX < x + CELL && mouseY >= rowY && mouseY < rowY + CELL) {
                graphics.setTooltipForNextFrame(font, List.of(icon.getHoverName(), Component.literal(entry.count() + "x")),
                        java.util.Optional.empty(), mouseX, mouseY);
            }
        }

        // Delete panel: the item and its amount (the slider is a widget)
        if (selected >= 0 && selected < contents.entries().size()) {
            ClusterContents.Entry entry = contents.entries().get(selected);
            int y = rowY + CELL + 30;
            graphics.item(entry.create(1), left + 14, y + 2);
            graphics.centeredText(font, Component.translatable("screen.megabreakertools.cluster.amount", deleteAmount, entry.count()),
                    left + panelWidth / 2, y + 26, TEXT_DIM);
        } else if (!order.isEmpty()) {
            graphics.centeredText(font, Component.translatable("screen.megabreakertools.cluster.delete_hint"),
                    left + panelWidth / 2, rowY + CELL + 18, TEXT_DIM);
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    /// "950", "14.4k", "2.1M".
    private static String shortCount(int count) {
        if (count < 1000) return String.valueOf(count);
        if (count < 1_000_000) return String.format(java.util.Locale.ROOT, "%.1fk", count / 1000.0);
        return String.format(java.util.Locale.ROOT, "%.1fM", count / 1_000_000.0);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // ---------------------------------------------------------------- the slider

    /// Chooses how many items to delete, from 1 to all of that kind.
    private class AmountSlider extends AbstractSliderButton {
        private final int count;

        AmountSlider(int x, int y, int width, int count) {
            super(x, y, width, 20, Component.empty(), count <= 1 ? 1 : (deleteAmount - 1) / (double) (count - 1));
            this.count = count;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(Component.literal(deleteAmount + " / " + count));
        }

        @Override
        protected void applyValue() {
            deleteAmount = 1 + (int) Math.round(value * (count - 1));
        }
    }
}
