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
import net.minecraft.world.entity.ai.goal.BreedGoal;

public record BreedJsonBehavior(double speedModifier) implements JsonBehavior<BreedGoal> {
    public static final MapCodec<BreedJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.DOUBLE.fieldOf("speed_modifier").forGetter(BreedJsonBehavior::speedModifier)).apply(instance, BreedJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BreedJsonBehavior> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, BreedJsonBehavior::speedModifier, BreedJsonBehavior::new);

    @Override
    public <D extends Dinosaur> BreedGoal create(D dinosaur) {
        return new BreedGoal(dinosaur, this.speedModifier());
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.BREED.get();
    }
}
