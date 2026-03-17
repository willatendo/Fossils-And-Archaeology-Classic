package ca.willatendo.fossilsclassic.data.generic.tag;

import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;

import java.util.concurrent.CompletableFuture;

public abstract class StoneTabletVariantTagsProvider extends KeyTagProvider<StoneTabletVariant> {
    public StoneTabletVariantTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId) {
        super(packOutput, FCRegistries.STONE_TABLET_VARIANT, registries, modId);
    }
}
