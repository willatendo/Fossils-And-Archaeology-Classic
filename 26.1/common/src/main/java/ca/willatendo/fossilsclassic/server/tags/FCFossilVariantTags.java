package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.tags.TagKey;

public final class FCFossilVariantTags {
    public static final TagKey<FossilVariant> PLACEABLE = FCFossilVariantTags.create("placeable");

    private static TagKey<FossilVariant> create(String name) {
        return TagKey.create(FCRegistries.FOSSIL_VARIANT, FCCoreUtils.resource(name));
    }
}
