package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.FCLayers;
import ca.willatendo.fossilsclassic.client.model.ThrownJavelinModel;
import ca.willatendo.fossilsclassic.client.render.state.ThrownJavelinRenderState;
import ca.willatendo.fossilsclassic.server.entity.entities.ThrownJavelin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class ThrownJavelinRenderer extends EntityRenderer<ThrownJavelin, ThrownJavelinRenderState> {
    private final Identifier texture;
    private final ThrownJavelinModel thrownJavelinModel;

    public ThrownJavelinRenderer(EntityRendererProvider.Context context, Identifier texture) {
        super(context);
        this.texture = texture;
        this.thrownJavelinModel = new ThrownJavelinModel(context.bakeLayer(FCLayers.THROWN_JAVELIN));
    }

    @Override
    public ThrownJavelinRenderState createRenderState() {
        return new ThrownJavelinRenderState();
    }

    @Override
    public void extractRenderState(ThrownJavelin thrownJavelin, ThrownJavelinRenderState thrownJavelinRenderState, float partialTicks) {
        super.extractRenderState(thrownJavelin, thrownJavelinRenderState, partialTicks);
        thrownJavelinRenderState.xRot = thrownJavelin.getXRot(partialTicks);
        thrownJavelinRenderState.yRot = thrownJavelin.getYRot(partialTicks);
    }

    @Override
    public void submit(ThrownJavelinRenderState thrownJavelinRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(thrownJavelinRenderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(thrownJavelinRenderState.xRot + 90.0F));
        submitNodeCollector.submitModel(this.thrownJavelinModel, thrownJavelinRenderState, poseStack, this.thrownJavelinModel.renderType(this.texture), thrownJavelinRenderState.lightCoords, OverlayTexture.NO_OVERLAY, thrownJavelinRenderState.outlineColor, null);
        poseStack.popPose();
        super.submit(thrownJavelinRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }
}
