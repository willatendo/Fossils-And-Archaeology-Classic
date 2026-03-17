package ca.willatendo.fossilsclassic.server.gene;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.gene.genes.*;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;

public final class FCGeneTypes {
    public static final SimpleRegistry<GeneType<?>> GENE_TYPES = new SimpleRegistry<>(FCRegistries.GENE_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<GeneType<AttributeGene>> ATTRIBUTE = GENE_TYPES.register("attribute", () -> new GeneType<>(AttributeGene.CODEC, AttributeGene.STREAM_CODEC));
    public static final SimpleHolder<GeneType<BehaviourGene>> BEHAVIOUR = GENE_TYPES.register("behaviour", () -> new GeneType<>(BehaviourGene.CODEC, BehaviourGene.STREAM_CODEC));
    public static final SimpleHolder<GeneType<BoundingBoxGene>> BOUNDING_BOX = GENE_TYPES.register("bounding_box", () -> new GeneType<>(BoundingBoxGene.CODEC, BoundingBoxGene.STREAM_CODEC));
    public static final SimpleHolder<GeneType<CosmeticGene>> COSMETIC = GENE_TYPES.register("cosmetic", () -> new GeneType<>(CosmeticGene.CODEC, CosmeticGene.STREAM_CODEC));
    public static final SimpleHolder<GeneType<OtherGene>> OTHER = GENE_TYPES.register("other", () -> new GeneType<>(OtherGene.CODEC, OtherGene.STREAM_CODEC));
    public static final SimpleHolder<GeneType<SoundGene>> SOUND = GENE_TYPES.register("sound", () -> new GeneType<>(SoundGene.CODEC, SoundGene.STREAM_CODEC));
}
