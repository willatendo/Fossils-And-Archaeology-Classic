package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class FCFluidTags {
    public static final TagKey<Fluid> PERMAFROST_FREEZABLE = FCFluidTags.create("permafrost_freezable");

    private static TagKey<Fluid> create(String name) {
        return TagKey.create(Registries.FLUID, FCCoreUtils.resource(name));
    }
}
