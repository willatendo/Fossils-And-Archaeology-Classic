package ca.willatendo.fossilsclassic.client.json_animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.compress.utils.Lists;
import org.joml.Vector3fc;

import java.util.List;

public record KeyframeAnimationType(float lengthInSeconds, boolean looping, List<KeyframeAnimationType.Animation> animations) implements CustomAnimation {
    public static final MapCodec<KeyframeAnimationType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.FLOAT.fieldOf("length_in_seconds").forGetter(KeyframeAnimationType::lengthInSeconds), Codec.BOOL.fieldOf("looping").forGetter(KeyframeAnimationType::looping), Codec.list(KeyframeAnimationType.Animation.CODEC).fieldOf("animations").forGetter(KeyframeAnimationType::animations)).apply(instance, KeyframeAnimationType::new));

    public AnimationDefinition toAnimationDefinition() {
        AnimationDefinition.Builder builder = AnimationDefinition.Builder.withLength(this.lengthInSeconds());
        if (this.looping()) {
            builder.looping();
        }
        this.animations().forEach(animation -> {
            List<Keyframe> keyframes = Lists.newArrayList();
            animation.animationKeyframes().forEach(animationKeyframe -> keyframes.add(new Keyframe(animationKeyframe.timestamp(), animationKeyframe.preTarget(), animationKeyframe.postTarget(), animationKeyframe.animationInterpolation.getInterpolation())));
            builder.addAnimation(animation.bone(), new AnimationChannel(animation.animationTarget.getTarget(), keyframes.toArray(Keyframe[]::new)));
        });
        return builder.build();
    }

    @Override
    public CustomAnimation.Type getType() {
        return CustomAnimation.Type.KEYFRAME;
    }

    public enum AnimationTarget implements StringRepresentable {
        POSITION("position", AnimationChannel.Targets.POSITION),
        ROTATION("rotation", AnimationChannel.Targets.ROTATION),
        SCALE("scale", AnimationChannel.Targets.SCALE);

        public static final Codec<KeyframeAnimationType.AnimationTarget> CODEC = StringRepresentable.fromValues(KeyframeAnimationType.AnimationTarget::values);

        private final String name;
        private final AnimationChannel.Target target;

        AnimationTarget(String name, AnimationChannel.Target target) {
            this.name = name;
            this.target = target;
        }

        public AnimationChannel.Target getTarget() {
            return this.target;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum AnimationInterpolation implements StringRepresentable {
        CATMULLROM("catmullrom", AnimationChannel.Interpolations.CATMULLROM),
        LINEAR("linear", AnimationChannel.Interpolations.LINEAR);

        public static final Codec<KeyframeAnimationType.AnimationInterpolation> CODEC = StringRepresentable.fromValues(KeyframeAnimationType.AnimationInterpolation::values);

        private final String name;
        private final AnimationChannel.Interpolation interpolation;

        AnimationInterpolation(String name, AnimationChannel.Interpolation interpolation) {
            this.name = name;
            this.interpolation = interpolation;
        }

        public AnimationChannel.Interpolation getInterpolation() {
            return this.interpolation;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public record AnimationKeyframe(float timestamp, Vector3fc preTarget, Vector3fc postTarget, KeyframeAnimationType.AnimationInterpolation animationInterpolation) {
        public static final Codec<AnimationKeyframe> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf("timestamp").forGetter(AnimationKeyframe::timestamp), ExtraCodecs.VECTOR3F.fieldOf("pre_target").forGetter(AnimationKeyframe::preTarget), ExtraCodecs.VECTOR3F.fieldOf("post_target").forGetter(AnimationKeyframe::postTarget), KeyframeAnimationType.AnimationInterpolation.CODEC.fieldOf("interpolation").forGetter(AnimationKeyframe::animationInterpolation)).apply(instance, AnimationKeyframe::new));
    }

    public record Animation(String bone, KeyframeAnimationType.AnimationTarget animationTarget, List<AnimationKeyframe> animationKeyframes) {
        public static final Codec<KeyframeAnimationType.Animation> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.fieldOf("bones").forGetter(KeyframeAnimationType.Animation::bone), KeyframeAnimationType.AnimationTarget.CODEC.fieldOf("target").forGetter(KeyframeAnimationType.Animation::animationTarget), Codec.list(AnimationKeyframe.CODEC).fieldOf("keyframes").forGetter(KeyframeAnimationType.Animation::animationKeyframes)).apply(instance, KeyframeAnimationType.Animation::new));
    }
}
