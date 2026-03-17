package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.server.tags.FCBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public final class FCBiomeTagsProvider extends BiomeTagsProvider {
    public FCBiomeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId) {
        super(packOutput, registries, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCBiomeTags.HAS_ACADEMY).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.JUNGLE);
        this.tag(FCBiomeTags.HAS_VIKING_SHIP).add(Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
        this.tag(FCBiomeTags.HAS_WEAPON_SHOP).add(Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.TAIGA);
    }
}
