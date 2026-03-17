package ca.willatendo.fossilsclassic.server.gene.genes.behavior;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.gene.FCJsonBehaviorTypes;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public record WaterAvoidingRandomStrollJsonBehavior(double speedModifier, float probability) implements JsonBehavior<WaterAvoidingRandomStrollGoal> {
    public static final MapCodec<WaterAvoidingRandomStrollJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.DOUBLE.fieldOf("speed_modifier").forGetter(WaterAvoidingRandomStrollJsonBehavior::speedModifier), Codec.FLOAT.optionalFieldOf("panic_causing_damage_types", 0.001F).forGetter(WaterAvoidingRandomStrollJsonBehavior::probability)).apply(instance, WaterAvoidingRandomStrollJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, WaterAvoidingRandomStrollJsonBehavior> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, WaterAvoidingRandomStrollJsonBehavior::speedModifier, ByteBufCodecs.FLOAT, WaterAvoidingRandomStrollJsonBehavior::probability, WaterAvoidingRandomStrollJsonBehavior::new);

    public WaterAvoidingRandomStrollJsonBehavior(double speedModifier) {
        this(speedModifier, 0.001F);
    }

    @Override
    public <D extends Dinosaur> WaterAvoidingRandomStrollGoal create(D dinosaur) {
        return new WaterAvoidingRandomStrollGoal(dinosaur, this.speedModifier(), this.probability());
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.WATER_AVOIDING_RANDOM_STROLL.get();
    }
}
