package ca.willatendo.fossilsclassic.client.json_animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.StringRepresentable;

public interface CustomAnimation {
    Codec<CustomAnimation> CODEC = CustomAnimation.Type.CODEC.dispatch(CustomAnimation::getType, CustomAnimation.Type::getCodec);

    CustomAnimation.Type getType();

    enum Type implements StringRepresentable {
        KEYFRAME("keyframe", KeyframeAnimationType.CODEC),
        CLASSIC("classic", ClassicAnimationType.CODEC);

        public static final Codec<CustomAnimation.Type> CODEC = StringRepresentable.fromValues(CustomAnimation.Type::values);

        private final String name;
        private final MapCodec<? extends CustomAnimation> codec;

        Type(String name, MapCodec<? extends CustomAnimation> codec) {
            this.name = name;
            this.codec = codec;
        }

        public MapCodec<? extends CustomAnimation> getCodec() {
            return this.codec;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
