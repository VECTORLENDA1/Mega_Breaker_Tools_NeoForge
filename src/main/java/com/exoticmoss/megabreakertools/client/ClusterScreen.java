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
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/// The screen of a big Drop Cluster (DropClusterItem.MANAGE_THRESHOLD items or more).
///  - A row with every kind of item inside (most first): icon, amount, a green check (top left) and a
///    red X (top right). Mouse wheel or the arrows move the row.
///  - Clicking the green check of items picks them to TAKE OUT (into the inventory); clicking the red X picks
///    them to DELETE. Several items can be picked at once ("All" picks every kind).
///  - With one item picked, the slider chooses how many; with several, what part of each one (in %).
///  - "Take all" takes everything out into the inventory (like right-click on a small cluster).
/// The screen only asks the server (ClusterActionPacket); the server changes the cluster.
public class ClusterScreen extends Screen {
    /// How many item cells are seen at once, and the size of one cell (pixels).
    private static final int VISIBLE = 7;
    private static final int CELL = 34;
    private static final int GAP = 4;
    /// Size of the red X and of the green check of a cell.
    private static final int MARK_SIZE = 9;
    /// Colors (0xAARRGGBB).
    private static final int PANEL = 0xE0101018;
    private static final int PANEL_BORDER = 0xFF8060C0;
    private static final int CELL_COLOR = 0xFF2A2A38;
    private static final int CELL_DELETE = 0xFF6A2A3A;
    private static final int CELL_TAKE = 0xFF2A5A36;
    private static final int X_COLOR = 0xFFC02030;
    private static final int CHECK_COLOR = 0xFF30A040;
    private static final int TEXT = 0xFFFFFFFF;
    private static final int TEXT_DIM = 0xFFA0A0B0;

    /// What the picked items are for.
    private enum Pick { NONE, TAKE, DELETE }

    private final InteractionHand hand;
    /// First visible cell.
    private int scroll;
    /// The picked items (indexes in the cluster) and what they are for.
    private final Set<Integer> picked = new TreeSet<>();
    private Pick pick = Pick.NONE;
    /// One item picked: how many. Several: what part of each (1 to 100 %).
    private int amount = 1;

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
        ClientPlayNetworking.send(packet);
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

    // ---------------------------------------------------------------- picking

    /// Picks or unpicks an item. Picking for the other action (take <-> delete) starts a new pick.
    private void toggle(int index, Pick forWhat) {
        if (pick != forWhat) {
            picked.clear();
            pick = forWhat;
        }
        if (!picked.remove(index)) picked.add(index);
        if (picked.isEmpty()) pick = Pick.NONE;
        amount = picked.size() == 1 ? 1 : 100;
        rebuildWidgets();
    }

    private void clearPick() {
        picked.clear();
        pick = Pick.NONE;
        rebuildWidgets();
    }

    /// Picks every kind of item (for the action already chosen, or to delete).
    private void pickAll() {
        if (pick == Pick.NONE) pick = Pick.DELETE;
        for (int i = 0; i < contents().entries().size(); i++) picked.add(i);
        amount = picked.size() == 1 ? 1 : 100;
        rebuildWidgets();
    }

    /// How many of the entry at "index" the action uses.
    private int amountFor(ClusterContents contents, int index) {
        int count = contents.entries().get(index).count();
        if (picked.size() == 1) return Math.min(amount, count);
        return amount >= 100 ? count : Math.max(1, (int) Math.round(count * amount / 100.0));
    }

    /// Sends the action for every picked item. From the last index to the first, so the indexes of the
    /// items not done yet don't change when an item goes away.
    private void apply() {
        ClusterContents contents = contents();
        int action = pick == Pick.TAKE ? ClusterActionPacket.TAKE : ClusterActionPacket.DELETE;
        for (int index : ((TreeSet<Integer>) picked).descendingSet()) {
            if (index >= contents.entries().size()) continue;
            String item = BuiltInRegistries.ITEM.getKey(contents.entries().get(index).create(1).getItem()).toString();
            send(new ClusterActionPacket(hand == InteractionHand.MAIN_HAND, action, index, item, amountFor(contents, index)));
        }
        clearPick();
    }

    // ---------------------------------------------------------------- layout and buttons

    @Override
    protected void init() {
        // Picked items that are not in the cluster anymore (deleted, taken out)
        int size = contents().entries().size();
        picked.removeIf(index -> index >= size);
        if (picked.isEmpty()) pick = Pick.NONE;

        panelWidth = VISIBLE * CELL + (VISIBLE - 1) * GAP + 2 * 28;
        panelHeight = pick != Pick.NONE ? 150 : 118;
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

        if (pick != Pick.NONE) {
            ClusterContents contents = contents();
            int max = picked.size() == 1 ? contents.entries().get(picked.iterator().next()).count() : 100;
            amount = Mth.clamp(amount, 1, max);
            int sliderY = rowY + CELL + 30;
            addRenderableWidget(Button.builder(Component.translatable("screen.megabreakertools.cluster.all"), b -> pickAll())
                    .bounds(left + 6, sliderY, 32, 20).build());
            addRenderableWidget(new AmountSlider(left + 42, sliderY, panelWidth - 42 - 128, max));
            String actionKey = pick == Pick.TAKE ? "screen.megabreakertools.cluster.take" : "screen.megabreakertools.cluster.delete";
            addRenderableWidget(Button.builder(Component.translatable(actionKey), b -> apply())
                    .bounds(left + panelWidth - 122, sliderY, 56, 20).build());
            addRenderableWidget(Button.builder(Component.translatable("screen.megabreakertools.cluster.cancel"), b -> clearPick())
                    .bounds(left + panelWidth - 62, sliderY, 56, 20).build());
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
            boolean markRow = event.y() >= rowY + 1 && event.y() < rowY + 1 + MARK_SIZE;
            // The red X in the top right corner of the cell: delete
            if (markRow && event.x() >= x + CELL - MARK_SIZE - 1 && event.x() < x + CELL - 1) {
                toggle(order.get(scroll + slot), Pick.DELETE);
                return true;
            }
            // The green check in the top left corner of the cell: take out
            if (markRow && event.x() >= x + 1 && event.x() < x + 1 + MARK_SIZE) {
                toggle(order.get(scroll + slot), Pick.TAKE);
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
            int color = index >= 0 && picked.contains(index) ? (pick == Pick.TAKE ? CELL_TAKE : CELL_DELETE) : CELL_COLOR;
            graphics.fill(x, rowY, x + CELL, rowY + CELL, color);
            if (index < 0) continue;

            ClusterContents.Entry entry = contents.entries().get(index);
            ItemStack icon = entry.create(1);
            graphics.item(icon, x + (CELL - 16) / 2, rowY + 5);
            graphics.centeredText(font, shortCount(entry.count()), x + CELL / 2, rowY + CELL - 10, TEXT);
            // Red X (top right)
            int xx = x + CELL - MARK_SIZE - 1;
            graphics.fill(xx, rowY + 1, xx + MARK_SIZE, rowY + 1 + MARK_SIZE, X_COLOR);
            graphics.centeredText(font, "x", xx + MARK_SIZE / 2 + 1, rowY + 1, TEXT);
            // Green check (top left)
            drawCheck(graphics, x + 1, rowY + 1);

            if (mouseX >= x && mouseX < x + CELL && mouseY >= rowY && mouseY < rowY + CELL) {
                graphics.setTooltipForNextFrame(font, List.of(icon.getHoverName(), Component.literal(entry.count() + "x")),
                        java.util.Optional.empty(), mouseX, mouseY);
            }
        }

        // Pick panel: what will happen (the slider is a widget)
        if (pick != Pick.NONE) {
            int y = rowY + CELL + 30;
            Component text;
            if (picked.size() == 1) {
                ClusterContents.Entry entry = contents.entries().get(picked.iterator().next());
                String key = pick == Pick.TAKE ? "screen.megabreakertools.cluster.take_amount" : "screen.megabreakertools.cluster.amount";
                text = Component.translatable(key, amount, entry.count());
            } else {
                long total = 0;
                for (int index : picked) total += amountFor(contents, index);
                String key = pick == Pick.TAKE ? "screen.megabreakertools.cluster.take_percent" : "screen.megabreakertools.cluster.delete_percent";
                text = Component.translatable(key, amount, picked.size(), total);
            }
            graphics.centeredText(font, text, left + panelWidth / 2, y + 26, TEXT_DIM);
        } else if (!order.isEmpty()) {
            graphics.centeredText(font, Component.translatable("screen.megabreakertools.cluster.delete_hint"),
                    left + panelWidth / 2, rowY + CELL + 18, TEXT_DIM);
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    /// A green square with a white check mark (drawn with small rectangles).
    private static void drawCheck(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.fill(x, y, x + MARK_SIZE, y + MARK_SIZE, CHECK_COLOR);
        int[][] points = {{1, 4}, {2, 5}, {3, 6}, {4, 5}, {5, 4}, {6, 3}, {7, 2}};
        for (int[] p : points) {
            graphics.fill(x + p[0], y + p[1], x + p[0] + 1, y + p[1] + 2, TEXT);
        }
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

    /// One item picked: how many (1 to all of that kind). Several: what part of each (1 to 100 %).
    private class AmountSlider extends AbstractSliderButton {
        private final int max;

        AmountSlider(int x, int y, int width, int max) {
            super(x, y, width, 20, Component.empty(), max <= 1 ? 1 : (amount - 1) / (double) (max - 1));
            this.max = max;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(Component.literal(picked.size() == 1 ? amount + " / " + max : amount + "%"));
        }

        @Override
        protected void applyValue() {
            amount = 1 + (int) Math.round(value * (max - 1));
        }
    }
}
