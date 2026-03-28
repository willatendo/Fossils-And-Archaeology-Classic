package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.data.generic.tag.GeneTagsProvider;
import ca.willatendo.fossilsclassic.server.gene.FCGenes;
import ca.willatendo.fossilsclassic.server.tags.FCGeneTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class FCGeneTagsProvider extends GeneTagsProvider {
    public FCGeneTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCGeneTags.TRICERATOPS).add(FCGenes.COSMETICS_BROWN_TRICERATOPS, FCGenes.COSMETICS_GREEN_TRICERATOPS);
    }
}
