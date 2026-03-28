package ca.willatendo.fossilsclassic.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class SmilodonModel {
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("t_6__r", CubeListBuilder.create().texOffs(6, 8).addBox(-2.5F, -2.5F, -3.0F, 1.0F, 1.0F, 2.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_5__l_1", CubeListBuilder.create().texOffs(44, 14).addBox(0.5F, 2.0F, -6.0F, 1.0F, 2.0F, 1.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_5__l_2", CubeListBuilder.create().texOffs(44, 17).addBox(0.5F, 4.0F, -6.0F, 1.0F, 2.0F, 1.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_2", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 1.0F, 3.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -4.0F, 5.0F, 4.0F, 4.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_6__l", CubeListBuilder.create().texOffs(0, 8).addBox(1.5F, -2.5F, -3.0F, 1.0F, 1.0F, 2.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_4", CubeListBuilder.create().texOffs(48, 7).addBox(-1.0F, 0.0F, -3.5F, 2.0F, 1.0F, 3.0F).mirror(), PartPose.offset(0.0F, 16.5F, -6.0F));
        partDefinition.addOrReplaceChild("t_3", CubeListBuilder.create().texOffs(18, 5).addBox(-2.0F, 0.0F, -7.0F, 4.0F, 2.0F, 3.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_5__r_1", CubeListBuilder.create().texOffs(44, 14).addBox(-1.5F, 2.0F, -6.0F, 1.0F, 2.0F, 1.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("t_5__r_2", CubeListBuilder.create().texOffs(44, 17).addBox(-1.5F, 4.0F, -6.0F, 1.0F, 2.0F, 1.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("d__r_1", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F).mirror(), PartPose.offset(-1.5F, 19.0F, -2.0F));
        partDefinition.addOrReplaceChild("d__r_2", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F).mirror(), PartPose.offset(-1.5F, 19.0F, 6.0F));
        partDefinition.addOrReplaceChild("c_1", CubeListBuilder.create().texOffs(22, 20).addBox(-4.0F, -3.0F, -3.5F, 8.0F, 7.0F, 5.0F).mirror(), PartPose.offset(0.0F, 15.0F, 0.0F));
        partDefinition.addOrReplaceChild("d__l_1", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F).mirror(), PartPose.offset(1.5F, 19.0F, -2.0F));
        partDefinition.addOrReplaceChild("d__l_2", CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F).mirror(), PartPose.offset(1.5F, 19.0F, 6.0F));
        partDefinition.addOrReplaceChild("c_2", CubeListBuilder.create().texOffs(22, 10).addBox(-3.0F, -2.0F, -4.5F, 6.0F, 5.0F, 5.0F).mirror(), PartPose.offset(0.0F, 15.0F, -3.0F));
        partDefinition.addOrReplaceChild("h_2", CubeListBuilder.create().texOffs(0, 21).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 6.0F).mirror(), PartPose.offset(0.0F, 16.0F, 1.0F));
        partDefinition.addOrReplaceChild("h_1", CubeListBuilder.create().texOffs(0, 11).addBox(-3.5F, -2.5F, -3.0F, 7.0F, 6.0F, 4.0F).mirror(), PartPose.offset(0.0F, 15.0F, 0.0F));
        partDefinition.addOrReplaceChild("h_3", CubeListBuilder.create().texOffs(44, 7).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F).mirror(), PartPose.offset(0.0F, 14.0F, 6.5F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
