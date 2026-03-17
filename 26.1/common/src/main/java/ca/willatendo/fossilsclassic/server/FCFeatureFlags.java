package ca.willatendo.fossilsclassic.server;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.server.utils.FeatureFlagUtils;
import net.minecraft.world.flag.FeatureFlag;

public final class FCFeatureFlags {
    public static final FeatureFlag CUSTOM_DINOSAURS = FeatureFlagUtils.getFeatureFlag(FCCoreUtils.resource("custom_dinosaurs"));
}
