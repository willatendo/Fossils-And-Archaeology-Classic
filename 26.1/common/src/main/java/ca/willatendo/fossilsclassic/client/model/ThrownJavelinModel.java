package ca.willatendo.fossilsclassic.client.model;

import ca.willatendo.fossilsclassic.client.render.state.ThrownJavelinRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ThrownJavelinModel extends EntityModel<ThrownJavelinRenderState> {
    public ThrownJavelinModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition bit = partDefinition.addOrReplaceChild("bit", CubeListBuilder.create().texOffs(10, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 0.0F, 10.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        bit.addOrReplaceChild("cross_1", CubeListBuilder.create().texOffs(0, -10).addBox(0.0F, -36.0F, -5.0F, 0.0F, 36.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        bit.addOrReplaceChild("cross_2", CubeListBuilder.create().texOffs(0, -10).addBox(0.0F, -36.0F, -5.0F, 0.0F, 36.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
