package ca.willatendo.fossilsclassic.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class StegosaurusModel {
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("d_r_2", CubeListBuilder.create().texOffs(12, 0).addBox(-0.5F, 0.0F, -4.0F, 1.0F, 2.0F, 3.0F).mirror(), PartPose.offsetAndRotation(4.0F, 20.0F, -6.0F, 0.8726646F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("t_2", CubeListBuilder.create().texOffs(12, 13).addBox(-0.5F, -3.5F, 2.0F, 2.0F, 5.0F, 4.0F).mirror(), PartPose.offset(0.0F, 14.0F, -1.0F));
        partDefinition.addOrReplaceChild("d_r_1", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -1.5F, -2.0F, 2.0F, 3.0F, 3.0F).mirror(), PartPose.offset(4.0F, 20.0F, -6.0F));
        partDefinition.addOrReplaceChild("t_1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, 8.0F, 1.0F, 5.0F, 3.0F).mirror(), PartPose.offset(0.0F, 14.0F, -1.0F));
        partDefinition.addOrReplaceChild("t_4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, -3.0F, -3.0F, 2.0F, 5.0F, 4.0F).mirror(), PartPose.offsetAndRotation(0.0F, 14.0F, -6.0F, 0.2617994F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("d_l_2", CubeListBuilder.create().texOffs(20, 0).addBox(-1.5F, 0.0F, -4.0F, 1.0F, 2.0F, 3.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 20.0F, -6.0F, 0.8726646F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("t_3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -5.0F, 0.0F, 2.0F, 5.0F, 8.0F).mirror(), PartPose.offset(0.0F, 14.0F, -6.0F));
        partDefinition.addOrReplaceChild("d_l_1", CubeListBuilder.create().texOffs(54, 0).addBox(-2.0F, -1.5F, -2.0F, 2.0F, 3.0F, 3.0F).mirror(), PartPose.offset(-2.0F, 20.0F, -6.0F));
        partDefinition.addOrReplaceChild("d_d_l_2", CubeListBuilder.create().texOffs(24, 21).addBox(-1.5F, 2.5F, -4.0F, 1.0F, 2.0F, 3.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 19.0F, 1.0F, 1.22173F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("d_d_l_1", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, -2.5F, -2.0F, 2.0F, 5.0F, 5.0F).mirror(), PartPose.offset(-2.0F, 19.0F, 1.0F));
        partDefinition.addOrReplaceChild("d_d_r_2", CubeListBuilder.create().texOffs(24, 16).addBox(-0.5F, 2.533333F, -4.0F, 1.0F, 2.0F, 3.0F).mirror(), PartPose.offsetAndRotation(4.0F, 19.0F, 1.0F, 1.22173F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("d_d_r_1", CubeListBuilder.create().texOffs(14, 22).addBox(-1.0F, -2.5F, -2.0F, 2.0F, 5.0F, 5.0F).mirror(), PartPose.offset(4.0F, 19.0F, 1.0F));
        partDefinition.addOrReplaceChild("h_2", CubeListBuilder.create().texOffs(46, 14).addBox(-2.0F, 2.0F, -4.0F, 5.0F, 5.0F, 4.0F).mirror(), PartPose.offsetAndRotation(0.0F, 14.0F, -6.0F, 0.2094395F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("h_1", CubeListBuilder.create().texOffs(20, 0).addBox(-3.0F, 0.0F, 0.0F, 7.0F, 8.0F, 8.0F).mirror(), PartPose.offset(0.0F, 14.0F, -6.0F));
        partDefinition.addOrReplaceChild("h_4", CubeListBuilder.create().texOffs(46, 23).addBox(-2.0F, 1.5F, 2.0F, 5.0F, 5.0F, 4.0F).mirror(), PartPose.offset(0.0F, 14.0F, -1.0F));
        partDefinition.addOrReplaceChild("h_3", CubeListBuilder.create().texOffs(32, 24).addBox(-0.5F, 3.5F, -8.0F, 2.0F, 3.0F, 5.0F).mirror(), PartPose.offsetAndRotation(0.0F, 14.0F, -6.0F, 0.1745329F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("h_6", CubeListBuilder.create().texOffs(52, 6).addBox(-0.5F, 2.5F, 7.5F, 2.0F, 2.0F, 4.0F).mirror(), PartPose.offset(0.0F, 14.0F, -1.0F));
        partDefinition.addOrReplaceChild("h_5", CubeListBuilder.create().texOffs(32, 16).addBox(-1.0F, 2.0F, 4.5F, 3.0F, 3.0F, 4.0F).mirror(), PartPose.offset(0.0F, 14.0F, -1.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
