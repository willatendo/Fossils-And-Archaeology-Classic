package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.FCLayers;
import ca.willatendo.fossilsclassic.client.model.BonesModel;
import ca.willatendo.fossilsclassic.server.entity.entities.Bones;
import ca.willatendo.simplelibrary.core.utils.CoreUtils;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class BonesRenderer extends HumanoidMobRenderer<Bones, HumanoidRenderState, BonesModel> {
    private static final Identifier TEXTURE = CoreUtils.minecraft("textures/entity/skeleton/skeleton.png");

    public BonesRenderer(EntityRendererProvider.Context context) {
        super(context, new BonesModel(context.bakeLayer(FCLayers.BONES)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, ArmorModelSet.bake(FCLayers.BONES_ARMOR, context.getModelSet(), BonesModel::new), context.getEquipmentRenderer()));
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState humanoidRenderState) {
        return TEXTURE;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }
}
