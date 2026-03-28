package ca.willatendo.fossilsclassic.data.generic.tag;

import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.simplelibrary.data.providers.tag.SimpleKeyTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public abstract class StoneTabletVariantTagsProvider extends SimpleKeyTagProvider<StoneTabletVariant> {
    public StoneTabletVariantTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, FCRegistries.STONE_TABLET_VARIANT, registries);
    }
}
