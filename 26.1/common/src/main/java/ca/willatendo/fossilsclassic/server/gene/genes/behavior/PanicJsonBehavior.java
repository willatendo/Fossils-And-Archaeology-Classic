package ca.willatendo.fossilsclassic.server.gene.genes.behavior;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.gene.FCJsonBehaviorTypes;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public record PanicJsonBehavior(double speedModifier, TagKey<DamageType> panicCausingDamageTypes) implements JsonBehavior<PanicGoal> {
    public static final MapCodec<PanicJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.DOUBLE.fieldOf("speed_modifier").forGetter(PanicJsonBehavior::speedModifier), TagKey.codec(Registries.DAMAGE_TYPE).optionalFieldOf("panic_causing_damage_types", DamageTypeTags.PANIC_CAUSES).forGetter(PanicJsonBehavior::panicCausingDamageTypes)).apply(instance, PanicJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PanicJsonBehavior> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, PanicJsonBehavior::speedModifier, TagKey.streamCodec(Registries.DAMAGE_TYPE), PanicJsonBehavior::panicCausingDamageTypes, PanicJsonBehavior::new);

    public PanicJsonBehavior(double speedModifier) {
        this(speedModifier, DamageTypeTags.PANIC_CAUSES);
    }

    @Override
    public <D extends Dinosaur> PanicGoal create(D dinosaur) {
        return new PanicGoal(dinosaur, this.speedModifier(), this.panicCausingDamageTypes());
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.PANIC.get();
    }
}
