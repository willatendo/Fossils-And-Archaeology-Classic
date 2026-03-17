package ca.willatendo.fossilsclassic.server.feature.configured;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public final class FCConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FOSSIL = FCConfiguredFeatures.create("ore_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PERMAFROST = FCConfiguredFeatures.create("ore_permafrost");

    private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> bootstrapContext) {
        RuleTest isStoneRuleTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest isDeepslateRuleTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        FeatureUtils.register(bootstrapContext, ORE_FOSSIL, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(isStoneRuleTest, FCBlocks.FOSSIL_ORE.get().defaultBlockState()), OreConfiguration.target(isDeepslateRuleTest, FCBlocks.DEEPSLATE_FOSSIL_ORE.get().defaultBlockState())), 8));
        FeatureUtils.register(bootstrapContext, ORE_PERMAFROST, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(isStoneRuleTest, FCBlocks.PERMAFROST.get().defaultBlockState()), OreConfiguration.target(isDeepslateRuleTest, FCBlocks.PERMAFROST.get().defaultBlockState())), 8));
    }
}
