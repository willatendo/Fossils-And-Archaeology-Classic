package ca.willatendo.fossilsclassic.data.generic.tag;

import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.simplelibrary.data.providers.tag.SimpleKeyTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public abstract class FossilVariantTagsProvider extends SimpleKeyTagProvider<FossilVariant> {
    public FossilVariantTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, FCRegistries.FOSSIL_VARIANT, registries);
    }
}
