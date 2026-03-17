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
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

public record FollowParentJsonBehavior(double speedModifier) implements JsonBehavior<FollowParentGoal> {
    public static final MapCodec<FollowParentJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.DOUBLE.fieldOf("speed_modifier").forGetter(FollowParentJsonBehavior::speedModifier)).apply(instance, FollowParentJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FollowParentJsonBehavior> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, FollowParentJsonBehavior::speedModifier, FollowParentJsonBehavior::new);

    @Override
    public <D extends Dinosaur> FollowParentGoal create(D dinosaur) {
        return new FollowParentGoal(dinosaur, this.speedModifier());
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.FOLLOW_PARENT.get();
    }
}
