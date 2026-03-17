package ca.willatendo.fossilsclassic.server.chromosome;

import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public record CustomChromosome(CosmeticChooser cosmeticChooser, HolderSet<Gene> cosmeticGenes, Holder<Gene> boundingBoxGene, Holder<Gene> behaviorGene, Holder<Gene> attributeGene, Holder<Gene> otherGene, Optional<Holder<Gene>> soundGene) implements Chromosome {
    public static final MapCodec<CustomChromosome> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(CosmeticChooser.CODEC.fieldOf("cosmetic_chooser").forGetter(CustomChromosome::cosmeticChooser), Gene.LIST_CODEC.fieldOf("cosmetic_genes").forGetter(CustomChromosome::cosmeticGenes), Gene.CODEC.fieldOf("bounding_box_gene").forGetter(CustomChromosome::boundingBoxGene), Gene.CODEC.fieldOf("behavior_gene").forGetter(CustomChromosome::behaviorGene), Gene.CODEC.fieldOf("attribute_gene").forGetter(CustomChromosome::attributeGene), Gene.CODEC.fieldOf("other_gene").forGetter(CustomChromosome::otherGene), Gene.CODEC.optionalFieldOf("sound_gene").forGetter(CustomChromosome::soundGene)).apply(instance, CustomChromosome::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CustomChromosome> STREAM_CODEC = StreamCodec.composite(CosmeticChooser.STREAM_CODEC, CustomChromosome::cosmeticChooser, Gene.LIST_STREAM_CODEC, CustomChromosome::cosmeticGenes, Gene.STREAM_CODEC, CustomChromosome::boundingBoxGene, Gene.STREAM_CODEC, CustomChromosome::behaviorGene, Gene.STREAM_CODEC, CustomChromosome::attributeGene, Gene.STREAM_CODEC, CustomChromosome::otherGene, ByteBufCodecs.optional(Gene.STREAM_CODEC), CustomChromosome::soundGene, CustomChromosome::new);

    public CustomChromosome(CosmeticChooser cosmeticChooser, HolderSet<Gene> cosmeticGene, Holder<Gene> boundingBoxGene, Holder<Gene> behaviorGene, Holder<Gene> attributeGene, Holder<Gene> otherGene, Holder<Gene> soundGene) {
        this(cosmeticChooser, cosmeticGene, boundingBoxGene, behaviorGene, attributeGene, otherGene, Optional.of(soundGene));
    }

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
        return Chromosome.Type.CUSTOM;
    }
}
