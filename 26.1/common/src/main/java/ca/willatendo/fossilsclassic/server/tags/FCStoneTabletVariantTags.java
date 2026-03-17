package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.tags.TagKey;

public final class FCStoneTabletVariantTags {
    public static final TagKey<StoneTabletVariant> PLACEABLE = FCStoneTabletVariantTags.create("placeable");

    private static TagKey<StoneTabletVariant> create(String name) {
        return TagKey.create(FCRegistries.STONE_TABLET_VARIANT, FCCoreUtils.resource(name));
    }
}
