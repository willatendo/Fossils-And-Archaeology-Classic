package ca.willatendo.fossilsclassic.server.chromosome;

import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record BuiltInChromosome(CosmeticChooser cosmeticChooser, HolderSet<Gene> cosmeticGenes) implements Chromosome {
    public static final MapCodec<BuiltInChromosome> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(CosmeticChooser.CODEC.fieldOf("cosmetic_chooser").forGetter(BuiltInChromosome::cosmeticChooser), Gene.LIST_CODEC.fieldOf("cosmetic_genes").forGetter(BuiltInChromosome::cosmeticGenes)).apply(instance, BuiltInChromosome::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BuiltInChromosome> STREAM_CODEC = StreamCodec.composite(CosmeticChooser.STREAM_CODEC, BuiltInChromosome::cosmeticChooser, Gene.LIST_STREAM_CODEC, BuiltInChromosome::cosmeticGenes, BuiltInChromosome::new);

    @Override
    public CosmeticChooser getCosmeticChooser() {
        return this.cosmeticChooser();
    }

    @Override
    public HolderSet<Gene> getCosmeticGenes() {
        return this.cosmeticGenes();
    }

    @Override
    public Chromosome.Type getType() {
        return Chromosome.Type.BUILT_IN;
    }
}
