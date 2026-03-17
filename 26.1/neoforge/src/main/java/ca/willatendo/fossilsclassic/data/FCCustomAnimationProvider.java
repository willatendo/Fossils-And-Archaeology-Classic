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
        this.add("citipati/head", new ClassicAnimationType(List.of(
                ClassicAnimationType.Parameters.HEAD_X_ROTATION,
                ClassicAnimationType.Parameters.HEAD_Y_ROTATION
        ), List.of(
                new ClassicAnimationType.AnimationEvent(
                        "head",
                        ClassicAnimationType.Axis.X_ROTATION,
                        new ClassicAnimationType.ParameterFloatReturn(
                                ClassicAnimationType.Parameters.HEAD_X_ROTATION
                        )
                ),
                new ClassicAnimationType.AnimationEvent(
                        "head",
                        ClassicAnimationType.Axis.Y_ROTATION,
                        new ClassicAnimationType.ParameterFloatReturn(
                                ClassicAnimationType.Parameters.HEAD_Y_ROTATION
                        )
                ),
                new ClassicAnimationType.AnimationEvent(
                        "crest",
                        ClassicAnimationType.Axis.X_ROTATION,
                        new ClassicAnimationType.ParameterFloatReturn(
                                ClassicAnimationType.Parameters.HEAD_X_ROTATION
                        )
                ),
                new ClassicAnimationType.AnimationEvent(
                        "crest",
                        ClassicAnimationType.Axis.Y_ROTATION,
                        new ClassicAnimationType.ParameterFloatReturn(
                                ClassicAnimationType.Parameters.HEAD_Y_ROTATION
                        )
                ),
                new ClassicAnimationType.AnimationEvent(
                        "beak",
                        ClassicAnimationType.Axis.X_ROTATION,
                        new ClassicAnimationType.ParameterFloatReturn(
                                ClassicAnimationType.Parameters.HEAD_X_ROTATION
                        )
                ),
                new ClassicAnimationType.AnimationEvent(
                        "beak",
                        ClassicAnimationType.Axis.Y_ROTATION,
                        new ClassicAnimationType.ParameterFloatReturn(
                                ClassicAnimationType.Parameters.HEAD_Y_ROTATION
                        )
                )
        )));
        this.add("citipati/walk", new ClassicAnimationType(List.of(
                ClassicAnimationType.Parameters.WALK_POSITION,
                ClassicAnimationType.Parameters.WALK_SPEED
        ), List.of(
                new ClassicAnimationType.AnimationEvent(
                        List.of("left_thigh", "left_leg", "left_foot"),
                        ClassicAnimationType.Axis.X_ROTATION,
                        new ClassicAnimationType.ParameteredFloatReturn(
                                ClassicAnimationType.Parameters.WALK_SPEED,
                                new ClassicAnimationType.OperatedFloatReturn(
                                        new ClassicAnimationType.CosFloatReturn(
                                                new ClassicAnimationType.ParameteredFloatReturn(
                                                        ClassicAnimationType.Parameters.WALK_POSITION,
                                                        new ClassicAnimationType.SimpleFloatReturn(
                                                                0.6662F
                                                        ),
                                                        ClassicAnimationType.Operation.MULTIPLY
                                                )
                                        ),
                                        new ClassicAnimationType.SimpleFloatReturn(
                                                1.4F
                                        ),
                                        ClassicAnimationType.Operation.MULTIPLY
                                ),
                                ClassicAnimationType.Operation.MULTIPLY
                        )
                ),
                new ClassicAnimationType.AnimationEvent(
                        List.of("right_thigh", "right_leg", "right_foot"),
                        ClassicAnimationType.Axis.X_ROTATION,
                        new ClassicAnimationType.ParameteredFloatReturn(
                                ClassicAnimationType.Parameters.WALK_SPEED,
                                new ClassicAnimationType.OperatedFloatReturn(
                                        new ClassicAnimationType.CosFloatReturn(
                                                new ClassicAnimationType.OperatedFloatReturn(
                                                        new ClassicAnimationType.ParameteredFloatReturn(
                                                                ClassicAnimationType.Parameters.WALK_POSITION,
                                                                new ClassicAnimationType.SimpleFloatReturn(
                                                                        0.6662F
                                                                ),
                                                                ClassicAnimationType.Operation.MULTIPLY
                                                        ),
                                                        new ClassicAnimationType.SimpleFloatReturn(
                                                                (float)Math.PI
                                                        ),
                                                        ClassicAnimationType.Operation.ADD
                                                )
                                        ),
                                        new ClassicAnimationType.SimpleFloatReturn(
                                                1.4F
                                        ),
                                        ClassicAnimationType.Operation.MULTIPLY
                                ),
                                ClassicAnimationType.Operation.MULTIPLY
                        )
                )
        )));
    }
}
