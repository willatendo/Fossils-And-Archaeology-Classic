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
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;

public record LookAtPlayerJsonBehavior(float lookDistance) implements JsonBehavior<LookAtPlayerGoal> {
    public static final MapCodec<LookAtPlayerJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.FLOAT.fieldOf("look_distance").forGetter(LookAtPlayerJsonBehavior::lookDistance)).apply(instance, LookAtPlayerJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, LookAtPlayerJsonBehavior> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, LookAtPlayerJsonBehavior::lookDistance, LookAtPlayerJsonBehavior::new);

    @Override
    public <D extends Dinosaur> LookAtPlayerGoal create(D dinosaur) {
        return new LookAtPlayerGoal(dinosaur, Player.class, this.lookDistance());
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.LOOK_AT_PLAYER.get();
    }
}
