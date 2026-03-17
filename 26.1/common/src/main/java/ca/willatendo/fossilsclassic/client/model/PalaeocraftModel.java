package ca.willatendo.fossilsclassic.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PalaeocraftModel {
    public static LayerDefinition citipati() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("neck_2", CubeListBuilder.create().texOffs(15, 52).addBox(-1.0F, -2.0F, -7.0F, 2.0F, 3.0F, 10.0F).mirror(), PartPose.offsetAndRotation(0.0F, 6.0F, -7.0F, -0.9948377F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("neck_1", CubeListBuilder.create().texOffs(72, 41).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 3.0F).mirror(), PartPose.offsetAndRotation(0.0F, 10.0F, -2.5F, -0.6283185F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(17, 21).addBox(-1.5F, -1.0F, -5.0F, 3.0F, 4.0F, 1.0F).mirror(), PartPose.offset(0.0F, 1.0F, -11.0F));
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(128, 120).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 13.0F, 11.0F).mirror(), PartPose.offsetAndRotation(-3.0F, 12.0F, -4.0F, 0.6806784F, -0.3665191F, 0.5410521F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(42, 41).addBox(0.0F, 5.0F, 2.0F, 2.0F, 7.0F, 2.0F).mirror(), PartPose.offset(2.0F, 11.0F, 3.0F));
        partDefinition.addOrReplaceChild("left_thigh", CubeListBuilder.create().texOffs(10, 42).addBox(0.0F, -3.0F, -1.0F, 2.0F, 9.0F, 4.0F).mirror(), PartPose.offset(2.0F, 11.0F, 3.0F));
        partDefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(51, 41).addBox(0.0F, 12.0F, 0.0F, 2.0F, 1.0F, 4.0F).mirror(), PartPose.offset(2.0F, 11.0F, 3.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(46, 59).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 7.0F, 11.0F).mirror(), PartPose.offset(0.0F, 9.0F, -5.0F));
        partDefinition.addOrReplaceChild("right_thigh", CubeListBuilder.create().texOffs(3, 28).addBox(-2.0F, -3.0F, -1.0F, 2.0F, 9.0F, 4.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0174533F));
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(27, 6).addBox(-1.5F, -2.0F, -4.0F, 3.0F, 5.0F, 6.0F).mirror(), PartPose.offset(0.0F, 1.0F, -11.0F));
        partDefinition.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(51, 41).addBox(-2.0F, 12.0F, 0.0F, 2.0F, 1.0F, 4.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0174533F));
        partDefinition.addOrReplaceChild("tail_fan", CubeListBuilder.create().texOffs(32, 1).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 30.0F).mirror(), PartPose.offsetAndRotation(0.0F, 7.0F, 12.0F, 0.0174533F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(51, 120).addBox(0.0F, 0.0F, -7.0F, 1.0F, 13.0F, 11.0F).mirror(), PartPose.offsetAndRotation(3.0F, 12.0F, -4.0F, 0.6806784F, 0.3665191F, -0.5410521F));
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(42, 41).addBox(-2.0F, 5.0F, 2.0F, 2.0F, 7.0F, 2.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0174533F));
        partDefinition.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(17, 69).addBox(-3.0F, -2.0F, 0.0F, 5.0F, 6.0F, 4.0F).mirror(), PartPose.offsetAndRotation(0.5F, 8.0F, 4.5F, 0.0872665F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(37, 82).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 5.0F, 5.0F).mirror(), PartPose.offsetAndRotation(0.0F, 8.0F, 7.0F, 0.0872665F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("wattle_2", CubeListBuilder.create().texOffs(32, 34).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 1.0F).mirror(), PartPose.offset(0.0F, 6.0F, -7.0F));
        partDefinition.addOrReplaceChild("wattle_1", CubeListBuilder.create().texOffs(21, 33).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F).mirror(), PartPose.offset(0.0F, 4.0F, -10.0F));
        partDefinition.addOrReplaceChild("tail_5", CubeListBuilder.create().texOffs(63, 85).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 9.0F).mirror(), PartPose.offset(0.0F, 6.0F, 27.0F));
        partDefinition.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(12, 82).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 4.0F, 8.0F).mirror(), PartPose.offset(0.0F, 7.0F, 11.0F));
        partDefinition.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(73, 53).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 3.0F, 8.0F).mirror(), PartPose.offset(0.0F, 7.0F, 19.0F));
        partDefinition.addOrReplaceChild("crest", CubeListBuilder.create().texOffs(39, 18).addBox(0.0F, -5.0F, -5.0F, 0.0F, 4.0F, 7.0F).mirror(), PartPose.offset(0.0F, 1.0F, -11.0F));

        return LayerDefinition.create(meshDefinition, 256, 256);
    }

    public static LayerDefinition dromaeosaurus() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("neck_2", CubeListBuilder.create().texOffs(12, 41).addBox(-1.0F, -2.0F, -7.0F, 2.0F, 4.0F, 7.0F).mirror(), PartPose.offsetAndRotation(0.0F, 9.0F, -5.0F, -1.186824F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_claw", CubeListBuilder.create().texOffs(37, -1).addBox(0.0F, 10.0F, 0.0F, 0.0F, 2.0F, 2.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0174533F));
        partDefinition.addOrReplaceChild("neck_1", CubeListBuilder.create().texOffs(9, 23).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 5.0F, 4.0F).mirror(), PartPose.offsetAndRotation(0.0F, 11.0F, -3.0F, -0.418879F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, -9).addBox(0.0F, 0.0F, -3.0F, 1.0F, 15.0F, 9.0F).mirror(), PartPose.offsetAndRotation(-3.0F, 12.0F, -4.0F, 0.6806784F, -0.3665191F, 0.5410521F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(47, 33).addBox(0.0F, 5.0F, 2.0F, 2.0F, 7.0F, 2.0F).mirror(), PartPose.offset(2.0F, 11.0F, 3.0F));
        partDefinition.addOrReplaceChild("left_thigh", CubeListBuilder.create().texOffs(35, 18).addBox(0.0F, -2.0F, -2.0F, 2.0F, 8.0F, 5.0F).mirror(), PartPose.offset(2.0F, 11.0F, 3.0F));
        partDefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(56, 33).addBox(0.0F, 12.0F, 0.0F, 2.0F, 1.0F, 4.0F).mirror(), PartPose.offset(2.0F, 11.0F, 3.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 8.0F, 11.0F).mirror(), PartPose.offset(0.0F, 9.0F, -5.0F));
        partDefinition.addOrReplaceChild("right_thigh", CubeListBuilder.create().texOffs(51, 18).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 8.0F, 5.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0174533F));
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(57, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 5.0F).mirror(), PartPose.offset(0.0F, 2.0F, -7.0F));
        partDefinition.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(56, 33).addBox(-2.0F, 12.0F, 0.0F, 2.0F, 1.0F, 4.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0174533F));
        partDefinition.addOrReplaceChild("tail_fan", CubeListBuilder.create().texOffs(52, 71).addBox(-1.0F, 0.0F, 0.0F, 7.0F, 1.0F, 17.0F).mirror(), PartPose.offset(-2.5F, 6.0F, 32.0F));
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, -9).addBox(0.0F, 0.0F, -3.0F, 1.0F, 15.0F, 9.0F).mirror(), PartPose.offsetAndRotation(3.0F, 12.0F, -4.0F, 0.6806784F, 0.3665191F, -0.5410521F));
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 33).addBox(-2.0F, 5.0F, 2.0F, 2.0F, 7.0F, 2.0F).mirror(), PartPose.offsetAndRotation(-2.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0174533F));
        partDefinition.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(75, 12).addBox(-3.0F, 4.0F, 0.0F, 6.0F, 7.0F, 3.0F).mirror(), PartPose.offsetAndRotation(0.0F, 2.0F, 4.5F, 0.122173F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(72, 23).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 6.0F, 5.0F).mirror(), PartPose.offsetAndRotation(0.0F, 8.0F, 7.0F, 0.0872665F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(25, 8).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 3.0F, 4.0F).mirror(), PartPose.offset(0.0F, 2.0F, -7.0F));
        partDefinition.addOrReplaceChild("left_claw", CubeListBuilder.create().texOffs(37, -1).addBox(0.0F, 10.0F, 0.0F, 0.0F, 2.0F, 2.0F).mirror(), PartPose.offset(2.0F, 11.0F, 3.0F));
        partDefinition.addOrReplaceChild("tail_5", CubeListBuilder.create().texOffs(17, 57).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 9.0F).mirror(), PartPose.offset(0.0F, 6.0F, 29.0F));
        partDefinition.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(42, 48).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 4.0F, 9.0F).mirror(), PartPose.offset(0.0F, 7.0F, 11.0F));
        partDefinition.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(65, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 3.0F, 9.0F).mirror(), PartPose.offset(0.0F, 7.0F, 20.0F));
        partDefinition.addOrReplaceChild("crest", CubeListBuilder.create().texOffs(19, -8).addBox(0.0F, -5.0F, -4.0F, 0.0F, 5.0F, 8.0F).mirror(), PartPose.offset(0.0F, 2.0F, -7.0F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }
}
