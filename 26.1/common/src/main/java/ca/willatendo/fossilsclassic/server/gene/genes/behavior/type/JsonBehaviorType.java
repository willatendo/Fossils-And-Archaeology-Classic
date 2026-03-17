package ca.willatendo.fossilsclassic.server.gene.genes.behavior.type;

import ca.willatendo.fossilsclassic.server.gene.genes.behavior.JsonBehavior;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record JsonBehaviorType<T extends JsonBehavior<?>>(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
}