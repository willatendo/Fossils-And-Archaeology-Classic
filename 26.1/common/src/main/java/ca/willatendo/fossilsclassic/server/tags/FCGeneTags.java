package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.tags.TagKey;

public final class FCGeneTags {
    public static final TagKey<Gene> TRICERATOPS = FCGeneTags.create("triceratops");

    private static TagKey<Gene> create(String name) {
        return TagKey.create(FCRegistries.GENE, FCCoreUtils.resource(name));
    }
}
