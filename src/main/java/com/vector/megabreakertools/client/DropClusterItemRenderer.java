package com.vector.megabreakertools.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.vector.megabreakertools.MegaBreakerTools;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.function.Consumer;

/// Draws the Drop Cluster item as a round 3D ball (in the hotbar, in the hand and dropped on the ground),
/// like the Gravity Cores of Simple Ores.
///
/// It is used from the item model file: assets/megabreakertools/items/drop_cluster.json
/// ("type": "megabreakertools:drop_cluster"). Size, angle and position in each place
/// (hotbar, hand, ground...) come from the "display" part of models/item/drop_cluster.json.
public class DropClusterItemRenderer implements NoDataSpecialModelRenderer {
    /// Plain white texture: the colors come from the numbers below.
    private static final Identifier WHITE = Identifier.fromNamespaceAndPath(MegaBreakerTools.MODID, "textures/entity/drop_cluster.png");
    /// Size of the ball (0.5 = one block wide).
    private static final float RADIUS = 0.6f;
    /// Colors of the ball: main color, color of the moving spots and of the glow around it.
    private static final int CORE_COLOR = 0xFFC9CDD3;
    private static final int SPOT_COLOR = 0xFF8E949C;
    private static final int GLOW_COLOR = 0xFFE6E9EE;
    /// How many pieces the ball is made of. More = rounder (but slower to draw).
    private static final int DETAIL = 24;
    /// Light of the ball: max light, so it can be seen in the dark (like the cores).
    private static final int FULL_BRIGHT = 0xF000F0;

    @Override
    public void submit(PoseStack pose, SubmitNodeCollector collector, int light, int overlay, boolean hasFoil, int outlineColor) {
        // Items have no world time, so the animation uses the real clock (in ticks: 1 tick = 50 ms)
        float time = (Util.getMillis() % 1_000_000L) / 50f;

        collector.submitCustomGeometry(pose, RenderTypes.entityCutout(WHITE), (p, v) -> ball(p, v, time));
        collector.submitCustomGeometry(pose, RenderTypes.entityTranslucentEmissive(WHITE), (p, v) -> glow(p, v, RADIUS * 1.12f, ARGB.color(90, GLOW_COLOR)));
        collector.submitCustomGeometry(pose, RenderTypes.entityTranslucentEmissive(WHITE), (p, v) -> glow(p, v, RADIUS * 1.3f, ARGB.color(35, GLOW_COLOR)));
    }

    /// The ball: mixes the main and spot colors, darker at the edges so it looks round.
    private static void ball(PoseStack.Pose pose, VertexConsumer v, float time) {
        forEachQuad((x, y, z) -> {
            int color = ARGB.srgbLerp(spots(x, y, z, time), CORE_COLOR, SPOT_COLOR);
            float facing = Math.max(0, pose.normal().transform(new Vector3f(x, y, z)).z());
            color = ARGB.scaleRGB(color, 0.6f + 0.4f * facing);
            vertex(pose, v, x * RADIUS, y * RADIUS, z * RADIUS, x, y, z, color);
        });
    }

    /// A transparent layer around the ball.
    private static void glow(PoseStack.Pose pose, VertexConsumer v, float radius, int color) {
        forEachQuad((x, y, z) -> vertex(pose, v, x * radius, y * radius, z * radius, x, y, z, color));
    }

    /// A value from 0 to 1 that changes slowly over the surface and over time: the moving spots.
    private static float spots(float x, float y, float z, float time) {
        float a = Mth.sin(x * 4 + time * 0.020f) * Mth.sin(y * 5 - time * 0.015f);
        float b = Mth.sin(z * 6 + y * 3 + time * 0.010f);
        return Mth.clamp(0.4f + 0.35f * a + 0.25f * b, 0, 1);
    }

    private interface Point {
        void accept(float x, float y, float z);
    }

    /// Walks over a ball of radius 1 in small squares and gives the 4 corners of each square.
    private static void forEachQuad(Point point) {
        for (int lat = 0; lat < DETAIL; lat++) {
            float t1 = Mth.PI * lat / DETAIL;
            float t2 = Mth.PI * (lat + 1) / DETAIL;
            for (int lon = 0; lon < DETAIL; lon++) {
                float p1 = Mth.TWO_PI * lon / DETAIL;
                float p2 = Mth.TWO_PI * (lon + 1) / DETAIL;
                corner(point, t1, p1);
                corner(point, t1, p2);
                corner(point, t2, p2);
                corner(point, t2, p1);
            }
        }
    }

    private static void corner(Point point, float theta, float phi) {
        point.accept(Mth.sin(theta) * Mth.cos(phi), Mth.cos(theta), Mth.sin(theta) * Mth.sin(phi));
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer v, float x, float y, float z,
                               float nx, float ny, float nz, int color) {
        v.addVertex(pose, x, y, z)
                .setColor(color)
                .setUv(0.5f, 0.5f)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(FULL_BRIGHT)
                .setNormal(pose, nx, ny, nz);
    }

    /// The space the item takes up (a box around the ball).
    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        for (float x : new float[]{-RADIUS, RADIUS}) {
            for (float y : new float[]{-RADIUS, RADIUS}) {
                for (float z : new float[]{-RADIUS, RADIUS}) {
                    output.accept(new Vector3f(x, y, z));
                }
            }
        }
    }

    /// The part that is read from the item model file: { "type": "megabreakertools:drop_cluster" }
    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public SpecialModelRenderer<Void> bake(SpecialModelRenderer.BakingContext context) {
            return new DropClusterItemRenderer();
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
