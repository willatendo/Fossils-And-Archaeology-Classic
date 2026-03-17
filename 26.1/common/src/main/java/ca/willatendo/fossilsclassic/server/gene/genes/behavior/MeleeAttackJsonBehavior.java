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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public record MeleeAttackJsonBehavior(double speedModifier, boolean follow) implements JsonBehavior<MeleeAttackGoal> {
    public static final MapCodec<MeleeAttackJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.DOUBLE.fieldOf("speed_modifier").forGetter(MeleeAttackJsonBehavior::speedModifier), Codec.BOOL.fieldOf("follow").forGetter(MeleeAttackJsonBehavior::follow)).apply(instance, MeleeAttackJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MeleeAttackJsonBehavior> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, MeleeAttackJsonBehavior::speedModifier, ByteBufCodecs.BOOL, MeleeAttackJsonBehavior::follow, MeleeAttackJsonBehavior::new);

    @Override
    public <D extends Dinosaur> MeleeAttackGoal create(D dinosaur) {
        return new MeleeAttackGoal(dinosaur, this.speedModifier(), this.follow());
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.MELEE_ATTACK.get();
    }
}