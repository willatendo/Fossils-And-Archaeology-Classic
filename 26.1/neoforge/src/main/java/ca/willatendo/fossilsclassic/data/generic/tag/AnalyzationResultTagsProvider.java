package ca.willatendo.fossilsclassic.data.generic.tag;

import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;

import java.util.concurrent.CompletableFuture;

public abstract class AnalyzationResultTagsProvider extends KeyTagProvider<AnalyzationResult> {
    public AnalyzationResultTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId) {
        super(packOutput, FCRegistries.ANALYZATION_RESULT, registries, modId);
    }
}
