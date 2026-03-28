package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.data.generic.tag.AnalyzationResultTagsProvider;
import ca.willatendo.fossilsclassic.server.analyzation_result.FCAnalyzationResults;
import ca.willatendo.fossilsclassic.server.tags.FCAnalyzationResultTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class FCAnalyzationResultTagsProvider extends AnalyzationResultTagsProvider {
    public FCAnalyzationResultTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCAnalyzationResultTags.BIO_FOSSIL_RESULTS).add(FCAnalyzationResults.BONE_MEAL, FCAnalyzationResults.JURASSIC_FERN_SPORES, FCAnalyzationResults.TRICERATOPS_DNA, FCAnalyzationResults.VELOCIRAPTOR_DNA, FCAnalyzationResults.TYRANNOSAURUS_DNA, FCAnalyzationResults.PTERANODON_DNA, FCAnalyzationResults.NAUTILUS_DNA, FCAnalyzationResults.FUTABASAURUS_DNA, FCAnalyzationResults.MOSASAURUS_DNA, FCAnalyzationResults.STEGOSAURUS_DNA, FCAnalyzationResults.DILOPHOSAURUS_DNA, FCAnalyzationResults.BRACHIOSAURUS_DNA);
        this.tag(FCAnalyzationResultTags.FROZEN_MEAT_RESULTS).add(FCAnalyzationResults.BEEF, FCAnalyzationResults.SMILODON_DNA, FCAnalyzationResults.MAMMOTH_DNA);
        this.tag(FCAnalyzationResultTags.RELIC_SCRAP_RESULTS).add(FCAnalyzationResults.GRAVEL, FCAnalyzationResults.STONE_TABLET, FCAnalyzationResults.FLINT, FCAnalyzationResults.BROKEN_ANCIENT_HELMET, FCAnalyzationResults.BROKEN_ANCIENT_SWORD);
    }
}
