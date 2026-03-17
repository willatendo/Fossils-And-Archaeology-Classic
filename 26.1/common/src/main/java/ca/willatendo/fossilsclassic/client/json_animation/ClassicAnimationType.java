package ca.willatendo.fossilsclassic.client.json_animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public record ClassicAnimationType(List<ClassicAnimationType.Parameters> requiredParameters, List<ClassicAnimationType.AnimationEvent> animationEvents) implements CustomAnimation {
    public static final MapCodec<ClassicAnimationType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.list(ClassicAnimationType.Parameters.CODEC).fieldOf("required_parameters").forGetter(ClassicAnimationType::requiredParameters), Codec.list(ClassicAnimationType.AnimationEvent.CODEC).fieldOf("animation_events").forGetter(ClassicAnimationType::animationEvents)).apply(instance, ClassicAnimationType::new));

    @Override
    public CustomAnimation.Type getType() {
        return CustomAnimation.Type.CLASSIC;
    }

    public record AnimationEvent(List<String> bones, Axis axis, FloatReturn floatReturn) {
        public static final Codec<ClassicAnimationType.AnimationEvent> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.list(Codec.STRING).fieldOf("bones").forGetter(ClassicAnimationType.AnimationEvent::bones), ClassicAnimationType.Axis.CODEC.fieldOf("axis").forGetter(ClassicAnimationType.AnimationEvent::axis), ClassicAnimationType.FloatReturn.CODEC.fieldOf("value").forGetter(ClassicAnimationType.AnimationEvent::floatReturn)).apply(instance, ClassicAnimationType.AnimationEvent::new));

        public AnimationEvent(String bone, Axis axis, FloatReturn floatReturn) {
            this(List.of(bone), axis, floatReturn);
        }
    }

    public enum Axis implements StringRepresentable {
        X_ROTATION("x_rotation"),
        Y_ROTATION("y_rotation"),
        Z_ROTATION("z_rotation");

        public static final Codec<ClassicAnimationType.Axis> CODEC = StringRepresentable.fromValues(ClassicAnimationType.Axis::values);

        private final String name;

        Axis(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum Parameters implements StringRepresentable {
        HEAD_X_ROTATION("head_x_rotation"),
        HEAD_Y_ROTATION("head_y_rotation"),
        WALK_POSITION("walk_position"),
        WALK_SPEED("walk_speed");

        public static final Codec<ClassicAnimationType.Parameters> CODEC = StringRepresentable.fromValues(ClassicAnimationType.Parameters::values);

        private final String name;

        Parameters(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum FloatReturnType implements StringRepresentable {
        COS("cos", () -> ClassicAnimationType.CosFloatReturn.CODEC),
        SIMPLE("simple", () -> ClassicAnimationType.SimpleFloatReturn.CODEC),
        OPERATED("operated", () -> ClassicAnimationType.OperatedFloatReturn.CODEC),
        PARAMETERED("parametered", () -> ClassicAnimationType.ParameteredFloatReturn.CODEC),
        PARAMETER("parameter", () -> ClassicAnimationType.ParameterFloatReturn.CODEC);

        public static final Codec<ClassicAnimationType.FloatReturnType> CODEC = StringRepresentable.fromValues(ClassicAnimationType.FloatReturnType::values);

        private final String name;
        private final Supplier<MapCodec<? extends ClassicAnimationType.FloatReturn>> codec;

        FloatReturnType(String name, Supplier<MapCodec<? extends ClassicAnimationType.FloatReturn>> codec) {
            this.name = name;
            this.codec = codec;
        }

        public MapCodec<? extends FloatReturn> getCodec() {
            return this.codec.get();
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public interface FloatReturn {
        Codec<FloatReturn> CODEC = ClassicAnimationType.FloatReturnType.CODEC.dispatch(ClassicAnimationType.FloatReturn::getType, ClassicAnimationType.FloatReturnType::getCodec);

        float value(float[] parameters, Function<ClassicAnimationType.Parameters, Integer> indexLookup);

        ClassicAnimationType.FloatReturnType getType();
    }

    public record SimpleFloatReturn(float in) implements ClassicAnimationType.FloatReturn {
        public static final MapCodec<ClassicAnimationType.SimpleFloatReturn> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.FLOAT.fieldOf("value").forGetter(ClassicAnimationType.SimpleFloatReturn::in)).apply(instance, ClassicAnimationType.SimpleFloatReturn::new));

        @Override
        public float value(float[] parameters, Function<ClassicAnimationType.Parameters, Integer> indexLookup) {
            return this.in;
        }

        @Override
        public ClassicAnimationType.FloatReturnType getType() {
            return ClassicAnimationType.FloatReturnType.SIMPLE;
        }
    }

    public record CosFloatReturn(FloatReturn in) implements ClassicAnimationType.FloatReturn {
        public static final MapCodec<ClassicAnimationType.CosFloatReturn> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(FloatReturn.CODEC.fieldOf("value").forGetter(ClassicAnimationType.CosFloatReturn::in)).apply(instance, ClassicAnimationType.CosFloatReturn::new));

        @Override
        public float value(float[] parameters, Function<ClassicAnimationType.Parameters, Integer> indexLookup) {
            return Mth.cos(this.in.value(parameters, indexLookup));
        }

        @Override
        public ClassicAnimationType.FloatReturnType getType() {
            return ClassicAnimationType.FloatReturnType.COS;
        }
    }

    public enum Operation implements StringRepresentable {
        ADD("add"),
        SUBTRACT("subtract"),
        MULTIPLY("multiply"),
        DIVIDE("divide"),
        SUBTRACT_REVERSE("subtract_reverse"),
        DIVIDE_REVERSE("divide_reverse");

        public static final Codec<ClassicAnimationType.Operation> CODEC = StringRepresentable.fromValues(ClassicAnimationType.Operation::values);

        private final String name;

        Operation(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public record ParameteredFloatReturn(ClassicAnimationType.Parameters parameter, ClassicAnimationType.FloatReturn in, ClassicAnimationType.Operation operation) implements ClassicAnimationType.FloatReturn {
        public static final MapCodec<ClassicAnimationType.ParameteredFloatReturn> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ClassicAnimationType.Parameters.CODEC.fieldOf("parameter").forGetter(ClassicAnimationType.ParameteredFloatReturn::parameter), FloatReturn.CODEC.fieldOf("value").forGetter(ClassicAnimationType.ParameteredFloatReturn::in), ClassicAnimationType.Operation.CODEC.fieldOf("operation").forGetter(ClassicAnimationType.ParameteredFloatReturn::operation)).apply(instance, ClassicAnimationType.ParameteredFloatReturn::new));

        @Override
        public float value(float[] parameters, Function<ClassicAnimationType.Parameters, Integer> indexLookup) {
            int parameter = indexLookup.apply(this.parameter);
            return switch (this.operation) {
                case ADD -> this.in.value(parameters, indexLookup) + parameters[parameter];
                case SUBTRACT -> this.in.value(parameters, indexLookup) - parameters[parameter];
                case MULTIPLY -> this.in.value(parameters, indexLookup) * parameters[parameter];
                case DIVIDE -> this.in.value(parameters, indexLookup) / parameters[parameter];
                case SUBTRACT_REVERSE -> parameters[parameter] - this.in.value(parameters, indexLookup);
                case DIVIDE_REVERSE -> parameters[parameter] / this.in.value(parameters, indexLookup);
            };
        }

        @Override
        public ClassicAnimationType.FloatReturnType getType() {
            return ClassicAnimationType.FloatReturnType.PARAMETERED;
        }
    }

    public record OperatedFloatReturn(ClassicAnimationType.FloatReturn value1, ClassicAnimationType.FloatReturn value2, ClassicAnimationType.Operation operation) implements ClassicAnimationType.FloatReturn {
        public static final MapCodec<ClassicAnimationType.OperatedFloatReturn> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(FloatReturn.CODEC.fieldOf("value_1").forGetter(ClassicAnimationType.OperatedFloatReturn::value1), FloatReturn.CODEC.fieldOf("value_2").forGetter(ClassicAnimationType.OperatedFloatReturn::value2), ClassicAnimationType.Operation.CODEC.fieldOf("operation").forGetter(ClassicAnimationType.OperatedFloatReturn::operation)).apply(instance, ClassicAnimationType.OperatedFloatReturn::new));

        @Override
        public float value(float[] parameters, Function<ClassicAnimationType.Parameters, Integer> indexLookup) {
            return switch (this.operation) {
                case ADD -> this.value1.value(parameters, indexLookup) + this.value2.value(parameters, indexLookup);
                case SUBTRACT ->
                        this.value1.value(parameters, indexLookup) - this.value2.value(parameters, indexLookup);
                case MULTIPLY ->
                        this.value1.value(parameters, indexLookup) * this.value2.value(parameters, indexLookup);
                case DIVIDE -> this.value1.value(parameters, indexLookup) / this.value2.value(parameters, indexLookup);
                case SUBTRACT_REVERSE ->
                        this.value2.value(parameters, indexLookup) - this.value1.value(parameters, indexLookup);
                case DIVIDE_REVERSE ->
                        this.value2.value(parameters, indexLookup) / this.value1.value(parameters, indexLookup);
            };
        }

        @Override
        public ClassicAnimationType.FloatReturnType getType() {
            return ClassicAnimationType.FloatReturnType.OPERATED;
        }
    }

    public record ParameterFloatReturn(ClassicAnimationType.Parameters parameter) implements ClassicAnimationType.FloatReturn {
        public static final MapCodec<ClassicAnimationType.ParameterFloatReturn> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ClassicAnimationType.Parameters.CODEC.fieldOf("parameter").forGetter(ClassicAnimationType.ParameterFloatReturn::parameter)).apply(instance, ClassicAnimationType.ParameterFloatReturn::new));

        @Override
        public float value(float[] parameters, Function<ClassicAnimationType.Parameters, Integer> indexLookup) {
            return parameters[indexLookup.apply(this.parameter)];
        }

        @Override
        public ClassicAnimationType.FloatReturnType getType() {
            return ClassicAnimationType.FloatReturnType.PARAMETER;
        }
    }
}
