package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.FCLayers;
import ca.willatendo.fossilsclassic.client.model.EggModel;
import ca.willatendo.fossilsclassic.server.entity.entities.Egg;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class EggRenderer extends MobRenderer<Egg, LivingEntityRenderState, EggModel> {
    private final Identifier texture;

    public EggRenderer(EntityRendererProvider.Context context, Identifier texture) {
        super(context, new EggModel(context.bakeLayer(FCLayers.EGG)), 0.3F);
        this.texture = texture;
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState livingEntityRenderState) {
        return this.texture;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
