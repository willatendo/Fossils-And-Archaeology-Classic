package ca.willatendo.fossilsclassic.server.gene.genes.behavior;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.gene.FCJsonBehaviorTypes;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.Item;

public record TemptJsonBehavior(double speedModifier, Either<Holder<Item>, TagKey<Item>> items, boolean canScare, double stopDistance) implements JsonBehavior<TemptGoal> {
    public static final MapCodec<TemptJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.DOUBLE.fieldOf("speed_modifier").forGetter(TemptJsonBehavior::speedModifier), Codec.either(Item.CODEC, TagKey.codec(Registries.ITEM)).fieldOf("item").forGetter(TemptJsonBehavior::items), Codec.BOOL.fieldOf("can_scare").forGetter(TemptJsonBehavior::canScare), Codec.DOUBLE.optionalFieldOf("stop_distance", 2.5D).forGetter(TemptJsonBehavior::stopDistance)).apply(instance, TemptJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, TemptJsonBehavior> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, TemptJsonBehavior::speedModifier, ByteBufCodecs.either(Item.STREAM_CODEC, TagKey.streamCodec(Registries.ITEM)), TemptJsonBehavior::items, ByteBufCodecs.BOOL, TemptJsonBehavior::canScare, ByteBufCodecs.DOUBLE, TemptJsonBehavior::stopDistance, TemptJsonBehavior::new);

    public TemptJsonBehavior(double speedModifier, Either<Holder<Item>, TagKey<Item>> items, boolean canScare) {
        this(speedModifier, items, canScare, 2.5D);
    }

    @Override
    public <D extends Dinosaur> TemptGoal create(D dinosaur) {
        return new TemptGoal(dinosaur, this.speedModifier(), itemStack -> {
            if (this.items().left().isPresent()) {
                return itemStack.is(this.items().left().get());
            } else {
                return itemStack.is(this.items().right().get());
            }
        }, this.canScare(), this.stopDistance());
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.TEMPT.get();
    }
}
