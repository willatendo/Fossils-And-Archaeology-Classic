package ca.willatendo.fossilsclassic.server.chromosome;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.fossilsclassic.server.tags.FCGeneTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class FCChromosomes {
    public static final ResourceKey<Chromosome> TRICERATOPS = FCChromosomes.create("triceratops");

    private static ResourceKey<Chromosome> create(String name) {
        return ResourceKey.create(FCRegistries.CHROMOSOME, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<Chromosome> bootstrapContext) {
        HolderGetter<Gene> genes = bootstrapContext.lookup(FCRegistries.GENE);

        bootstrapContext.register(TRICERATOPS, new BuiltInChromosome(
                CosmeticChooser.RANDOM,
                genes.getOrThrow(FCGeneTags.TRICERATOPS)
        ));
    }
}
