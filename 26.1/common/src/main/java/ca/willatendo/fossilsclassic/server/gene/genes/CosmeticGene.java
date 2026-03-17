package ca.willatendo.fossilsclassic.server.gene.genes;

import ca.willatendo.fossilsclassic.server.gene.FCGeneTypes;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;

public record CosmeticGene(Component name) implements Gene {
    public static final MapCodec<CosmeticGene> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ComponentSerialization.CODEC.fieldOf("name").forGetter(CosmeticGene::getName)).apply(instance, CosmeticGene::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CosmeticGene> STREAM_CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_STREAM_CODEC, CosmeticGene::getName, CosmeticGene::new);

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public GeneType<?> getType() {
        return FCGeneTypes.COSMETIC.get();
    }
}
