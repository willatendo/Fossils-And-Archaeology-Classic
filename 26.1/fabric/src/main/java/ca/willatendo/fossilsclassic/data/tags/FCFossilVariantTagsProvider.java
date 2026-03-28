package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.data.generic.tag.FossilVariantTagsProvider;
import ca.willatendo.fossilsclassic.server.entity.FCFossilVariants;
import ca.willatendo.fossilsclassic.server.tags.FCFossilVariantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class FCFossilVariantTagsProvider extends FossilVariantTagsProvider {
    public FCFossilVariantTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCFossilVariantTags.PLACEABLE).add(FCFossilVariants.BRACHIOSAURUS, FCFossilVariants.FUTABASAURUS, FCFossilVariants.PTERANODON, FCFossilVariants.TRICERATOPS);
    }
}
