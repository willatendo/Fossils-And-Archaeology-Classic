package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.FCAtlasIds;
import ca.willatendo.fossilsclassic.client.render.state.StoneTabletRenderState;
import ca.willatendo.fossilsclassic.server.entity.entities.StoneTablet;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class StoneTabletRenderer extends EntityRenderer<StoneTablet, StoneTabletRenderState> {
    private final TextureAtlas stoneTabletsAtlas;

    public StoneTabletRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.stoneTabletsAtlas = context.getAtlas(FCAtlasIds.STONE_TABLETS);
    }

    @Override
    public void submit(StoneTabletRenderState stoneTabletRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        StoneTabletVariant stoneTabletVariant = stoneTabletRenderState.variant;
        if (stoneTabletVariant != null) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (180 - stoneTabletRenderState.direction.get2DDataValue() * 90)));
            TextureAtlasSprite textureAtlasSprite = this.stoneTabletsAtlas.getSprite(stoneTabletVariant.texture());
            this.renderStoneTablet(poseStack, submitNodeCollector, RenderTypes.entityCutout(textureAtlasSprite.atlasLocation()), stoneTabletRenderState.lightCoordsPerBlock, stoneTabletVariant.width(), stoneTabletVariant.height(), textureAtlasSprite);
            poseStack.popPose();
            super.submit(stoneTabletRenderState, poseStack, submitNodeCollector, cameraRenderState);
        }
    }

    @Override
    public StoneTabletRenderState createRenderState() {
        return new StoneTabletRenderState();
    }

    @Override
    public void extractRenderState(StoneTablet stoneTablet, StoneTabletRenderState stoneTabletRenderState, float partialTicks) {
        super.extractRenderState(stoneTablet, stoneTabletRenderState, partialTicks);
        Direction direction = stoneTablet.getDirection();
        StoneTabletVariant stoneTabletVariant = stoneTablet.getVariant().value();
        stoneTabletRenderState.direction = direction;
        stoneTabletRenderState.variant = stoneTabletVariant;
        int width = stoneTabletVariant.width();
        int height = stoneTabletVariant.height();
        if (stoneTabletRenderState.lightCoordsPerBlock.length != width * height) {
            stoneTabletRenderState.lightCoordsPerBlock = new int[width * height];
        }

        float f = (float) (-width) / 2.0F;
        float f1 = (float) (-height) / 2.0F;
        Level level = stoneTablet.level();

        for (int blockZ = 0; blockZ < height; ++blockZ) {
            for (int blockX = 0; blockX < width; ++blockX) {
                float xOffset = (float) blockX + f + 0.5F;
                float yOffset = (float) blockZ + f1 + 0.5F;
                int x = stoneTablet.getBlockX();
                int y = Mth.floor(stoneTablet.getY() + (double) yOffset);
                int z = stoneTablet.getBlockZ();
                switch (direction) {
                    case NORTH -> x = Mth.floor(stoneTablet.getX() + (double) xOffset);
                    case WEST -> z = Mth.floor(stoneTablet.getZ() - (double) xOffset);
                    case SOUTH -> x = Mth.floor(stoneTablet.getX() - (double) xOffset);
                    case EAST -> z = Mth.floor(stoneTablet.getZ() + (double) xOffset);
                }

                stoneTabletRenderState.lightCoordsPerBlock[blockX + blockZ * width] = LevelRenderer.getLightColor(level, new BlockPos(x, y, z));
            }
        }
    }

    private void renderStoneTablet(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, RenderType renderType, int[] lightCoords, int width, int height, TextureAtlasSprite stoneTabletAtlas) {
        submitNodeCollector.submitCustomGeometry(poseStack, renderType, (pose, vertexConsumer) -> {
            float f = (float) (-width) / 2.0F;
            float f1 = (float) (-height) / 2.0F;
            double d0 = 1.0 / (double) width;
            double d1 = 1.0 / (double) height;

            for (int i = 0; i < width; ++i) {
                for (int j = 0; j < height; ++j) {
                    float x1 = f + (float) (i + 1);
                    float x2 = f + (float) i;
                    float y2 = f1 + (float) (j + 1);
                    float y1 = f1 + (float) j;
                    int lightColor = lightCoords[i + j * width];
                    float u2 = stoneTabletAtlas.getU((float) (d0 * (double) (width - i)));
                    float u1 = stoneTabletAtlas.getU((float) (d0 * (double) (width - (i + 1))));
                    float v1 = stoneTabletAtlas.getV((float) (d1 * (double) (height - j)));
                    float v2 = stoneTabletAtlas.getV((float) (d1 * (double) (height - (j + 1))));
                    this.vertex(pose, vertexConsumer, x1, y1, u1, v1, lightColor);
                    this.vertex(pose, vertexConsumer, x2, y1, u2, v1, lightColor);
                    this.vertex(pose, vertexConsumer, x2, y2, u2, v2, lightColor);
                    this.vertex(pose, vertexConsumer, x1, y2, u1, v2, lightColor);
                }
            }
        });
    }

    private void vertex(PoseStack.Pose pose, VertexConsumer vertexConsumer, float x, float y, float u, float v, int lightColor) {
        vertexConsumer.addVertex(pose, x, y, (float) -0.03125).setColor(-1).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(lightColor).setNormal(pose, (float) 0, (float) 0, (float) -1);
    }
}
