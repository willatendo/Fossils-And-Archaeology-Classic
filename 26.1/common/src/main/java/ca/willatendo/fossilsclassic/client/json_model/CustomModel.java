package ca.willatendo.fossilsclassic.client.json_model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

import java.util.*;

public record CustomModel(String layer, int textureWidth, int textureHeight, List<CustomModel.JsonPartDefinition> parts, List<Identifier> animations, Optional<Identifier> parent) {
    public static final Codec<CustomModel> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.optionalFieldOf("layer", "main").forGetter(CustomModel::layer), Codec.INT.fieldOf("texture_width").forGetter(CustomModel::textureWidth), Codec.INT.fieldOf("texture_height").forGetter(CustomModel::textureHeight), Codec.list(CustomModel.JsonPartDefinition.CODEC).fieldOf("parts").forGetter(CustomModel::parts), Codec.list(Identifier.CODEC).optionalFieldOf("animations", List.of()).forGetter(CustomModel::animations), Identifier.CODEC.optionalFieldOf("parent").forGetter(CustomModel::parent)).apply(instance, CustomModel::new));

    public LayerDefinition createModelLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        this.add(root, this.parts());

        this.parent().ifPresent(parent -> {
            if (CustomModelLoader.hasModel(parent)) {
                CustomModel customModel = CustomModelLoader.getModel(parent);
                this.add(root, customModel.parts());
            }
        });

        return LayerDefinition.create(meshDefinition, this.textureWidth, this.textureHeight);
    }

    private void add(PartDefinition partDefinition, List<JsonPartDefinition> children) {
        children.forEach(childrenIn -> {
            PartDefinition child = partDefinition.addOrReplaceChild(childrenIn.name, childrenIn.toCubeListBuilder(), childrenIn.jsonPartPose().toPartPose());
            this.add(child, childrenIn.children());
        });
    }

    public record JsonPartDefinition(String name, List<CustomModel.JsonCube> jsonCubes, CustomModel.JsonPartPose jsonPartPose, List<CustomModel.JsonPartDefinition> children) {
        public static final Codec<CustomModel.JsonPartDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.fieldOf("name").forGetter(CustomModel.JsonPartDefinition::name), Codec.list(CustomModel.JsonCube.CODEC).fieldOf("cubes").forGetter(CustomModel.JsonPartDefinition::jsonCubes), CustomModel.JsonPartPose.CODEC.fieldOf("part_pose").forGetter(CustomModel.JsonPartDefinition::jsonPartPose), Codec.list(Codec.<CustomModel.JsonPartDefinition>recursive(CustomModel.JsonPartDefinition.class.getSimpleName(), codec -> RecordCodecBuilder.create(recusiveInstance -> recusiveInstance.group(Codec.STRING.fieldOf("name").forGetter(CustomModel.JsonPartDefinition::name), Codec.list(CustomModel.JsonCube.CODEC).fieldOf("cubes").forGetter(CustomModel.JsonPartDefinition::jsonCubes), CustomModel.JsonPartPose.CODEC.fieldOf("part_pose").forGetter(CustomModel.JsonPartDefinition::jsonPartPose), Codec.list(codec).optionalFieldOf("children", List.of()).forGetter(CustomModel.JsonPartDefinition::children)).apply(recusiveInstance, CustomModel.JsonPartDefinition::new)))).optionalFieldOf("children", List.of()).forGetter(CustomModel.JsonPartDefinition::children)).apply(instance, CustomModel.JsonPartDefinition::new));

        private CubeListBuilder toCubeListBuilder() {
            CubeListBuilder cubeListBuilder = CubeListBuilder.create();
            this.jsonCubes.forEach(jsonCube -> cubeListBuilder.cubes.add(new CubeDefinition(jsonCube.comment().orElse(null), jsonCube.textureOffsetU(), jsonCube.textureOffsetV(), jsonCube.originX(), jsonCube.originY(), jsonCube.originZ(), jsonCube.dimensionX(), jsonCube.dimensionY(), jsonCube.dimensionZ(), new CubeDeformation(jsonCube.growX(), jsonCube.growY(), jsonCube.growZ()), jsonCube.mirror(), jsonCube.textureScaleU(), jsonCube.textureScaleV(), jsonCube.visibleFaces())));
            return cubeListBuilder;
        }
    }

    public record JsonCube(Optional<String> comment, int textureOffsetU, int textureOffsetV, boolean mirror, float originX, float originY, float originZ, int dimensionX, int dimensionY, int dimensionZ, float growX, float growY, float growZ, float textureScaleU, float textureScaleV, Set<Direction> visibleFaces) {
        public static final Codec<CustomModel.JsonCube> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.optionalFieldOf("comment").forGetter(CustomModel.JsonCube::comment), Codec.INT.fieldOf("texture_offset_u").forGetter(CustomModel.JsonCube::textureOffsetU), Codec.INT.fieldOf("texture_offset_v").forGetter(CustomModel.JsonCube::textureOffsetV), Codec.BOOL.optionalFieldOf("mirror", false).forGetter(CustomModel.JsonCube::mirror), Codec.FLOAT.fieldOf("origin_x").forGetter(CustomModel.JsonCube::originX), Codec.FLOAT.fieldOf("origin_y").forGetter(CustomModel.JsonCube::originY), Codec.FLOAT.fieldOf("origin_z").forGetter(CustomModel.JsonCube::originZ), Codec.INT.fieldOf("dimension_x").forGetter(CustomModel.JsonCube::dimensionX), Codec.INT.fieldOf("dimension_y").forGetter(CustomModel.JsonCube::dimensionY), Codec.INT.fieldOf("dimension_z").forGetter(CustomModel.JsonCube::dimensionZ), Codec.FLOAT.optionalFieldOf("grow_x", 0.0F).forGetter(CustomModel.JsonCube::growX), Codec.FLOAT.optionalFieldOf("grow_y", 0.0F).forGetter(CustomModel.JsonCube::growY), Codec.FLOAT.optionalFieldOf("grow_z", 0.0F).forGetter(CustomModel.JsonCube::growZ), Codec.FLOAT.optionalFieldOf("texture_scale_u", 1.0F).forGetter(CustomModel.JsonCube::textureScaleU), Codec.FLOAT.optionalFieldOf("texture_scale_v", 1.0F).forGetter(CustomModel.JsonCube::textureScaleV), Codec.list(Direction.CODEC).optionalFieldOf("visible_faces", EnumSet.allOf(Direction.class).stream().toList()).forGetter(jsonCube -> jsonCube.visibleFaces().stream().toList())).apply(instance, (comment, textureOffsetU, textureOffsetV, mirror, originX, originY, originZ, dimensionX, dimensionY, dimensionZ, growX, growY, growZ, textureScaleU, textureScaleV, visibleFaces) -> new CustomModel.JsonCube(comment, textureOffsetU, textureOffsetV, mirror, originX, originY, originZ, dimensionX, dimensionY, dimensionZ, growX, growY, growZ, textureScaleU, textureScaleV, new HashSet<>(visibleFaces))));

        public JsonCube(CubeDefinition cubeDefinition) {
            this(cubeDefinition.comment != null ? Optional.of(cubeDefinition.comment) : Optional.empty(), (int) cubeDefinition.texCoord.u(), (int) cubeDefinition.texCoord.v(), cubeDefinition.mirror, cubeDefinition.origin.x(), cubeDefinition.origin.y(), cubeDefinition.origin.z(), (int) cubeDefinition.dimensions.x(), (int) cubeDefinition.dimensions.y(), (int) cubeDefinition.dimensions.z(), cubeDefinition.grow.growX, cubeDefinition.grow.growY, cubeDefinition.grow.growZ, cubeDefinition.texScale.u(), cubeDefinition.texScale.v(), cubeDefinition.visibleFaces);
        }
    }

    public record JsonPartPose(float x, float y, float z, float xRotation, float yRotation, float zRotation, float xScale, float yScale, float zScale) {
        public static final Codec<CustomModel.JsonPartPose> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf("x").forGetter(CustomModel.JsonPartPose::x), Codec.FLOAT.fieldOf("y").forGetter(CustomModel.JsonPartPose::y), Codec.FLOAT.fieldOf("z").forGetter(CustomModel.JsonPartPose::z), Codec.FLOAT.optionalFieldOf("x_rotation", 0.0F).forGetter(CustomModel.JsonPartPose::xRotation), Codec.FLOAT.optionalFieldOf("y_rotation", 0.0F).forGetter(CustomModel.JsonPartPose::yRotation), Codec.FLOAT.optionalFieldOf("z_rotation", 0.0F).forGetter(CustomModel.JsonPartPose::zRotation), Codec.FLOAT.optionalFieldOf("x_scale", 1.0F).forGetter(CustomModel.JsonPartPose::xScale), Codec.FLOAT.optionalFieldOf("y_scale", 1.0F).forGetter(CustomModel.JsonPartPose::yScale), Codec.FLOAT.optionalFieldOf("z_scale", 1.0F).forGetter(CustomModel.JsonPartPose::zScale)).apply(instance, CustomModel.JsonPartPose::new));

        public JsonPartPose(float x, float y, float z, float xRot, float yRot, float zRot) {
            this(x, y, z, xRot, yRot, zRot, 1.0F, 1.0F, 1.0F);
        }

        public JsonPartPose(float x, float y, float z) {
            this(x, y, z, 0.0F, 0.0F, 0.0F);
        }

        public JsonPartPose(PartPose partPose) {
            this(partPose.x(), partPose.y(), partPose.z(), partPose.xRot(), partPose.yRot(), partPose.zRot(), partPose.xScale(), partPose.yScale(), partPose.zScale());
        }

        private PartPose toPartPose() {
            return new PartPose(this.x, this.y, this.z, this.xRotation, this.yRotation, this.zRotation, this.xScale, this.yScale, this.zScale);
        }
    }
}
