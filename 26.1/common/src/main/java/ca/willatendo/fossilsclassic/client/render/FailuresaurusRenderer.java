package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.FCLayers;
import ca.willatendo.fossilsclassic.client.model.FailuresaurusModel;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.entities.Failuresaurs;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class FailuresaurusRenderer extends MobRenderer<Failuresaurs, LivingEntityRenderState, FailuresaurusModel> {
    private static final Identifier TEXTURE = FCCoreUtils.resource("textures/entity/failuresaurus.png");

    public FailuresaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new FailuresaurusModel(context.bakeLayer(FCLayers.FAILURESAURUS)), 0.5F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState livingEntityRenderState) {
        return TEXTURE;
    }
}
