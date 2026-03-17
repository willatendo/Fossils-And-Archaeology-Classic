package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.server.tags.FCFluidTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public final class FCFluidTagsProvider extends FluidTagsProvider {
    public FCFluidTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId) {
        super(packOutput, registries, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCFluidTags.PERMAFROST_FREEZABLE).add(Fluids.WATER, Fluids.FLOWING_WATER);
    }
}
