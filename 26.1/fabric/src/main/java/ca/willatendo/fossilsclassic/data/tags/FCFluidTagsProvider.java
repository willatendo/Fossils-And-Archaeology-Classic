package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.server.tags.FCFluidTags;
import ca.willatendo.simplelibrary.data.providers.tag.SimpleFluidTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public final class FCFluidTagsProvider extends SimpleFluidTagsProvider {
    public FCFluidTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCFluidTags.PERMAFROST_FREEZABLE).add(Fluids.WATER, Fluids.FLOWING_WATER);
    }
}
