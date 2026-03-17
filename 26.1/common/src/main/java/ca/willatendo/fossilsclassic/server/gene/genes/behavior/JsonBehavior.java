package ca.willatendo.fossilsclassic.server.gene.genes.behavior;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import ca.willatendo.fossilsclassic.server.registry.FCBuiltInRegistries;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.goal.Goal;

public interface JsonBehavior<T extends Goal> {
    Codec<JsonBehavior<?>> CODEC = FCBuiltInRegistries.JSON_BEHAVIOR_TYPES.byNameCodec().dispatch(JsonBehavior::getType, JsonBehaviorType::codec);
    StreamCodec<RegistryFriendlyByteBuf, JsonBehavior<?>> STREAM_CODEC = ByteBufCodecs.registry(FCRegistries.JSON_BEHAVIOR_TYPE).dispatch(JsonBehavior::getType, JsonBehaviorType::streamCodec);

    <D extends Dinosaur> T create(D dinosaur);

    JsonBehaviorType<?> getType();
}