package ca.willatendo.fossilsclassic.server.gene;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.gene.genes.CosmeticGene;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class FCGenes {
    public static final ResourceKey<Gene> COSMETICS_SMILODON = FCGenes.create("cosmetics/smilodon");
    public static final ResourceKey<Gene> COSMETICS_STEGOSAURUS = FCGenes.create("cosmetics/stegosaurus");
    public static final ResourceKey<Gene> COSMETICS_BROWN_TRICERATOPS = FCGenes.create("cosmetics/brown_triceratops");
    public static final ResourceKey<Gene> COSMETICS_GREEN_TRICERATOPS = FCGenes.create("cosmetics/green_triceratops");

    private static ResourceKey<Gene> create(String name) {
        return ResourceKey.create(FCRegistries.GENE, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<Gene> bootstrapContext) {
        bootstrapContext.register(COSMETICS_SMILODON, new CosmeticGene(FCCoreUtils.translation("gene", "cosmetic.smilodon")));
        bootstrapContext.register(COSMETICS_STEGOSAURUS, new CosmeticGene(FCCoreUtils.translation("gene", "cosmetic.stegosaurus")));
        bootstrapContext.register(COSMETICS_BROWN_TRICERATOPS, new CosmeticGene(FCCoreUtils.translation("gene", "cosmetic.brown_triceratops")));
        bootstrapContext.register(COSMETICS_GREEN_TRICERATOPS, new CosmeticGene(FCCoreUtils.translation("gene", "cosmetic.green_triceratops")));
    }
}
