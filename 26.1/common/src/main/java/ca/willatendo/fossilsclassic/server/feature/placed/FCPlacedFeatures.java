package ca.willatendo.fossilsclassic.server.feature.placed;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.feature.configured.FCConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public final class FCPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_FOSSIL = FCPlacedFeatures.create("ore_fossil");
    public static final ResourceKey<PlacedFeature> ORE_PERMAFROST = FCPlacedFeatures.create("ore_permafrost");

    private static ResourceKey<PlacedFeature> create(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, FCCoreUtils.resource(name));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }


    public static void bootstrap(BootstrapContext<PlacedFeature> bootstrapContext) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = bootstrapContext.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(bootstrapContext, ORE_FOSSIL, configuredFeatures.getOrThrow(FCConfiguredFeatures.ORE_FOSSIL), FCPlacedFeatures.commonOrePlacement(25, HeightRangePlacement.triangle(VerticalAnchor.absolute(10), VerticalAnchor.absolute(128))));
        PlacementUtils.register(bootstrapContext, ORE_PERMAFROST, configuredFeatures.getOrThrow(FCConfiguredFeatures.ORE_PERMAFROST), FCPlacedFeatures.commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.absolute(-5), VerticalAnchor.absolute(60))));
    }
}
