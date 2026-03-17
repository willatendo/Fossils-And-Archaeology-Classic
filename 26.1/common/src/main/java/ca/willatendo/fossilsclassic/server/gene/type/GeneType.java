package ca.willatendo.fossilsclassic.server.gene.type;

import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record GeneType<T extends Gene>(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
}
