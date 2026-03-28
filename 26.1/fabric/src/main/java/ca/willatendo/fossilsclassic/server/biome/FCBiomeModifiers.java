package ca.willatendo.fossilsclassic.server.biome;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.feature.placed.FCPlacedFeatures;
import ca.willatendo.simplelibrary.core.registry.SimpleLibraryRegistries;
import ca.willatendo.simplelibrary.server.biome_modifier.BiomeModifier;
import ca.willatendo.simplelibrary.server.biome_modifier.BiomeModifiers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class FCBiomeModifiers {
    private static final ResourceKey<BiomeModifier> ADD_FOSSIL_ORE = FCBiomeModifiers.create("add_fossil_ore");
    private static final ResourceKey<BiomeModifier> ADD_PERMAFROST_ORE = FCBiomeModifiers.create("add_permafrost_ore");

    private static ResourceKey<BiomeModifier> create(String name) {
        return ResourceKey.create(SimpleLibraryRegistries.BIOME_MODIFIERS, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> bootstrapContext) {
        HolderGetter<Biome> biomes = bootstrapContext.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = bootstrapContext.lookup(Registries.PLACED_FEATURE);

        bootstrapContext.register(ADD_FOSSIL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(FCPlacedFeatures.ORE_FOSSIL)), GenerationStep.Decoration.UNDERGROUND_ORES));
        bootstrapContext.register(ADD_PERMAFROST_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(FCPlacedFeatures.ORE_PERMAFROST)), GenerationStep.Decoration.UNDERGROUND_ORES));
    }
}
