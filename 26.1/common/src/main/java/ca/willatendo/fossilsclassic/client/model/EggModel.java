package ca.willatendo.fossilsclassic.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class EggModel extends EntityModel<LivingEntityRenderState> {
    public EggModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(15, 13).addBox(-2.75F, -1.0F, -2.5F, 5.0F, 1.0F, 5.0F).texOffs(0, 11).addBox(-2.75F, -8.0F, -2.5F, 5.0F, 2.0F, 5.0F).texOffs(0, 0).addBox(-3.25F, -6.0F, -3.0F, 6.0F, 5.0F, 6.0F).texOffs(0, 18).addBox(-1.75F, -9.0F, -1.5F, 3.0F, 2.0F, 3.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
