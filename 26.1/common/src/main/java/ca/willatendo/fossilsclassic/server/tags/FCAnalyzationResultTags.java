package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.tags.TagKey;

public final class FCAnalyzationResultTags {
    public static final TagKey<AnalyzationResult> BIO_FOSSIL_RESULTS = FCAnalyzationResultTags.create("results/bio_fossil");
    public static final TagKey<AnalyzationResult> FROZEN_MEAT_RESULTS = FCAnalyzationResultTags.create("results/frozen_meat");
    public static final TagKey<AnalyzationResult> RELIC_SCRAP_RESULTS = FCAnalyzationResultTags.create("results/relic_scrap");

    private static TagKey<AnalyzationResult> create(String name) {
        return TagKey.create(FCRegistries.ANALYZATION_RESULT, FCCoreUtils.resource(name));
    }
}
