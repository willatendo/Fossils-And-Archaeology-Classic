package ca.willatendo.fossilsclassic.data.generic.tag;

import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.simplelibrary.data.providers.tag.SimpleKeyTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public abstract class GeneTagsProvider extends SimpleKeyTagProvider<Gene> {
    public GeneTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, FCRegistries.GENE, registries);
    }
}
