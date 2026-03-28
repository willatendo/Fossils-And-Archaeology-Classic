package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.client.json_animation.ClassicAnimationType;
import ca.willatendo.fossilsclassic.data.generic.CustomAnimationProvider;
import net.minecraft.data.PackOutput;

import java.util.List;

public final class FCCustomAnimationProvider extends CustomAnimationProvider {
    public FCCustomAnimationProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void addAll() {
        this.add("smilodon/walk", new ClassicAnimationType(
                List.of(
                        ClassicAnimationType.Parameters.WALK_POSITION,
                        ClassicAnimationType.Parameters.WALK_SPEED
                ),
                List.of(
                        new ClassicAnimationType.AnimationEvent(
                                "h_3",
                                ClassicAnimationType.Axis.Y_ROTATION,
                                new ClassicAnimationType.ParameteredFloatReturn(
                                        ClassicAnimationType.Parameters.WALK_POSITION,
                                        new ClassicAnimationType.CosFloatReturn(
                                                new ClassicAnimationType.OperatedFloatReturn(
                                                        new ClassicAnimationType.ParameteredFloatReturn(
                                                                ClassicAnimationType.Parameters.WALK_SPEED,
                                                                new ClassicAnimationType.SimpleFloatReturn(
                                                                        0.6662F
                                                                ),
                                                                ClassicAnimationType.Operation.MULTIPLY
                                                        ),
                                                        new ClassicAnimationType.SimpleFloatReturn(1.4F),
                                                        ClassicAnimationType.Operation.MULTIPLY
                                                )
                                        ),
                                        ClassicAnimationType.Operation.MULTIPLY
                                )
                        )
                )
        ));
        this.add("stegosaurus/walk", new ClassicAnimationType(
                List.of(
                        ClassicAnimationType.Parameters.WALK_POSITION,
                        ClassicAnimationType.Parameters.WALK_SPEED
                ),
                List.of(
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "h_4",
                                0.349065850398866F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "h_5",
                                0.436332312998582F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "h_6",
                                0.488692190558412F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_l_1",
                                -0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_r_1",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_l_2",
                                -0.174532925199433F,
                                true,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_r_2",
                                0.174532925199433F,
                                true,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_d_l_1",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "d_d_l_1",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_d_r_1",
                                -0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "d_d_r_1",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_d_l_2",
                                0.174532925199433F,
                                false,
                                true
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "d_d_l_2",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.X_ROTATION,
                                "d_d_r_2",
                                -0.174532925199433F,
                                false,
                                true
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "d_d_r_2",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "t_1",
                                0.488692190558412F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "t_2",
                                0.349065850398866F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Z_ROTATION,
                                "t_2",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "t_3",
                                0.174532925199433F,
                                false,
                                false
                        ),
                        this.stegosaurusWalk(
                                ClassicAnimationType.Axis.Y_ROTATION,
                                "t_4",
                                0.174532925199433F,
                                false,
                                false
                        )
                )));
        this.add("triceratops/walk", new ClassicAnimationType(List.of(ClassicAnimationType.Parameters.WALK_POSITION, ClassicAnimationType.Parameters.WALK_SPEED), List.of(this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "lower_body", 0.174532925199433F, false), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "back", 0.261799387799149F, false), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "tail", 0.349065850398866F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "right_front_thigh", -0.174532925199433F, false), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "right_front_thigh", 0.0872664625997165F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "right_front_leg", -0.174532925199433F, true), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "right_front_leg", 0.0872664625997165F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "left_front_thigh", 0.174532925199433F, false), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "left_front_thigh", 0.0872664625997165F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "left_front_leg", 0.174532925199433F, true), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "left_front_leg", 0.0872664625997165F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "right_back_thigh", 0.174532925199433F, false), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "right_back_thigh", 0.0872664625997165F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "right_back_leg", 0.174532925199433F, true), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "right_back_leg", 0.0872664625997165F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "left_back_thigh", -0.174532925199433F, false), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "left_back_thigh", 0.0872664625997165F, false), this.triceratopsWalk(ClassicAnimationType.Axis.X_ROTATION, "left_back_leg", -0.174532925199433F, true), this.triceratopsWalk(ClassicAnimationType.Axis.Y_ROTATION, "left_back_leg", 0.0872664625997165F, false))));

        this.add("citipati/head", new ClassicAnimationType(List.of(ClassicAnimationType.Parameters.HEAD_X_ROTATION, ClassicAnimationType.Parameters.HEAD_Y_ROTATION), List.of(new ClassicAnimationType.AnimationEvent("head", ClassicAnimationType.Axis.X_ROTATION, new ClassicAnimationType.ParameterFloatReturn(ClassicAnimationType.Parameters.HEAD_X_ROTATION)), new ClassicAnimationType.AnimationEvent("head", ClassicAnimationType.Axis.Y_ROTATION, new ClassicAnimationType.ParameterFloatReturn(ClassicAnimationType.Parameters.HEAD_Y_ROTATION)), new ClassicAnimationType.AnimationEvent("crest", ClassicAnimationType.Axis.X_ROTATION, new ClassicAnimationType.ParameterFloatReturn(ClassicAnimationType.Parameters.HEAD_X_ROTATION)), new ClassicAnimationType.AnimationEvent("crest", ClassicAnimationType.Axis.Y_ROTATION, new ClassicAnimationType.ParameterFloatReturn(ClassicAnimationType.Parameters.HEAD_Y_ROTATION)), new ClassicAnimationType.AnimationEvent("beak", ClassicAnimationType.Axis.X_ROTATION, new ClassicAnimationType.ParameterFloatReturn(ClassicAnimationType.Parameters.HEAD_X_ROTATION)), new ClassicAnimationType.AnimationEvent("beak", ClassicAnimationType.Axis.Y_ROTATION, new ClassicAnimationType.ParameterFloatReturn(ClassicAnimationType.Parameters.HEAD_Y_ROTATION)))));
        this.add("citipati/walk", new ClassicAnimationType(List.of(ClassicAnimationType.Parameters.WALK_POSITION, ClassicAnimationType.Parameters.WALK_SPEED), List.of(new ClassicAnimationType.AnimationEvent(List.of("left_thigh", "left_leg", "left_foot"), ClassicAnimationType.Axis.X_ROTATION, new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_SPEED, new ClassicAnimationType.OperatedFloatReturn(new ClassicAnimationType.CosFloatReturn(new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_POSITION, new ClassicAnimationType.SimpleFloatReturn(0.6662F), ClassicAnimationType.Operation.MULTIPLY)), new ClassicAnimationType.SimpleFloatReturn(1.4F), ClassicAnimationType.Operation.MULTIPLY), ClassicAnimationType.Operation.MULTIPLY)), new ClassicAnimationType.AnimationEvent(List.of("right_thigh", "right_leg", "right_foot"), ClassicAnimationType.Axis.X_ROTATION, new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_SPEED, new ClassicAnimationType.OperatedFloatReturn(new ClassicAnimationType.CosFloatReturn(new ClassicAnimationType.OperatedFloatReturn(new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_POSITION, new ClassicAnimationType.SimpleFloatReturn(0.6662F), ClassicAnimationType.Operation.MULTIPLY), new ClassicAnimationType.SimpleFloatReturn((float) Math.PI), ClassicAnimationType.Operation.ADD)), new ClassicAnimationType.SimpleFloatReturn(1.4F), ClassicAnimationType.Operation.MULTIPLY), ClassicAnimationType.Operation.MULTIPLY)))));
    }

    private ClassicAnimationType.AnimationEvent stegosaurusWalk(ClassicAnimationType.Axis axis, String bone, float a, boolean hasConstant1, boolean hasConstant2) {
        ClassicAnimationType.FloatReturn floatReturn = new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_SPEED, new ClassicAnimationType.OperatedFloatReturn(new ClassicAnimationType.CosFloatReturn(new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_POSITION, new ClassicAnimationType.OperatedFloatReturn(new ClassicAnimationType.SimpleFloatReturn(1.919107651F), new ClassicAnimationType.SimpleFloatReturn(0.5F), ClassicAnimationType.Operation.MULTIPLY), ClassicAnimationType.Operation.DIVIDE_REVERSE)), new ClassicAnimationType.SimpleFloatReturn(a), ClassicAnimationType.Operation.MULTIPLY), ClassicAnimationType.Operation.MULTIPLY);
        return new ClassicAnimationType.AnimationEvent(List.of(bone), axis, (hasConstant1 || hasConstant2) ? new ClassicAnimationType.OperatedFloatReturn(floatReturn, new ClassicAnimationType.SimpleFloatReturn(hasConstant1 ?0.872664625997162F :1.22173047639603F ), ClassicAnimationType.Operation.ADD) : floatReturn);
    }

    private ClassicAnimationType.AnimationEvent triceratopsWalk(ClassicAnimationType.Axis axis, String bone, float a, boolean hasConstant) {
        ClassicAnimationType.FloatReturn floatReturn = new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_SPEED, new ClassicAnimationType.OperatedFloatReturn(new ClassicAnimationType.CosFloatReturn(new ClassicAnimationType.ParameteredFloatReturn(ClassicAnimationType.Parameters.WALK_POSITION, new ClassicAnimationType.OperatedFloatReturn(new ClassicAnimationType.SimpleFloatReturn(1.919107651F), new ClassicAnimationType.SimpleFloatReturn(0.5F), ClassicAnimationType.Operation.MULTIPLY), ClassicAnimationType.Operation.DIVIDE_REVERSE)), new ClassicAnimationType.SimpleFloatReturn(a), ClassicAnimationType.Operation.MULTIPLY), ClassicAnimationType.Operation.MULTIPLY);
        return new ClassicAnimationType.AnimationEvent(List.of(bone), axis, hasConstant ? new ClassicAnimationType.OperatedFloatReturn(floatReturn, new ClassicAnimationType.SimpleFloatReturn(0.994460983870151F), ClassicAnimationType.Operation.ADD) : floatReturn);
    }
}
