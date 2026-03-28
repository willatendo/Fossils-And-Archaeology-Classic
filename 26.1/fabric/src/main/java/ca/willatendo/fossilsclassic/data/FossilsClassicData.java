package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.data.loot.FCBlockLootSubProvider;
import ca.willatendo.fossilsclassic.data.loot.FCChestLootSubProvider;
import ca.willatendo.fossilsclassic.data.loot.FEntityLootSubProvider;
import ca.willatendo.fossilsclassic.data.model.FCBlockModelGenerator;
import ca.willatendo.fossilsclassic.data.model.FCItemModelGenerator;
import ca.willatendo.fossilsclassic.data.tags.*;
import ca.willatendo.fossilsclassic.server.FCFeatureFlags;
import ca.willatendo.fossilsclassic.server.analyzation_result.FCAnalyzationResults;
import ca.willatendo.fossilsclassic.server.archaeology_value.FCArchaeologyValues;
import ca.willatendo.fossilsclassic.server.biomass_value.FCBiomassValues;
import ca.willatendo.fossilsclassic.server.biome.FCBiomeModifiers;
import ca.willatendo.fossilsclassic.server.chromosome.FCChromosomes;
import ca.willatendo.fossilsclassic.server.entity.FCDamageTypes;
import ca.willatendo.fossilsclassic.server.entity.FCFossilVariants;
import ca.willatendo.fossilsclassic.server.entity.FCStoneTabletVariants;
import ca.willatendo.fossilsclassic.server.feature.configured.FCConfiguredFeatures;
import ca.willatendo.fossilsclassic.server.feature.placed.FCPlacedFeatures;
import ca.willatendo.fossilsclassic.server.feeder_food.FCFeederFoodValues;
import ca.willatendo.fossilsclassic.server.gene.FCGenes;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.fossilsclassic.server.structure.FCProcessorLists;
import ca.willatendo.fossilsclassic.server.structure.FCStructureSets;
import ca.willatendo.fossilsclassic.server.structure.FCStructureTemplatePools;
import ca.willatendo.fossilsclassic.server.structure.FCStructures;
import ca.willatendo.simplelibrary.core.registry.SimpleLibraryRegistries;
import ca.willatendo.simplelibrary.data.ResourcePackGenerator;
import ca.willatendo.simplelibrary.data.SimpleDataGenerator;
import ca.willatendo.simplelibrary.data.providers.SimpleLootTableProvider;
import ca.willatendo.simplelibrary.data.providers.SimpleModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.DetectedVersion;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public final class FossilsClassicData implements DataGeneratorEntrypoint {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(Registries.CONFIGURED_FEATURE, FCConfiguredFeatures::bootstrap).add(Registries.DAMAGE_TYPE, FCDamageTypes::bootstrap).add(Registries.PLACED_FEATURE, FCPlacedFeatures::bootstrap).add(Registries.PROCESSOR_LIST, FCProcessorLists::bootstrap).add(Registries.STRUCTURE, FCStructures::bootstrap).add(Registries.STRUCTURE_SET, FCStructureSets::bootstrap).add(Registries.TEMPLATE_POOL, FCStructureTemplatePools::bootstrap).add(SimpleLibraryRegistries.BIOME_MODIFIERS, FCBiomeModifiers::bootstrap).add(FCRegistries.ANALYZATION_RESULT, FCAnalyzationResults::bootstrap).add(FCRegistries.ARCHAEOLOGY_VALUE, FCArchaeologyValues::bootstrap).add(FCRegistries.BIOMASS_VALUE, FCBiomassValues::bootstrap).add(FCRegistries.CHROMOSOME, FCChromosomes::bootstrap).add(FCRegistries.FEEDER_FOOD_VALUE, FCFeederFoodValues::bootstrap).add(FCRegistries.FOSSIL_VARIANT, FCFossilVariants::bootstrap).add(FCRegistries.GENE, FCGenes::bootstrap).add(FCRegistries.STONE_TABLET_VARIANT, FCStoneTabletVariants::bootstrap);

    private static void generatePalaeocraftExample(ResourcePackGenerator resourcePackGenerator) {
        resourcePackGenerator.addProvider(packOutputIn -> PackMetadataGenerator.forFeaturePack(packOutputIn, Component.empty(), FeatureFlagSet.of(FCFeatureFlags.CUSTOM_DINOSAURS)).add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(Component.literal("Palaeocraft example."), new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA)))));
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        SimpleDataGenerator simpleDataGenerator = new SimpleDataGenerator(fabricDataGenerator, FCCoreUtils.ID, FCCoreUtils.translation("resourcePack", "description"));
        simpleDataGenerator.addResource(FCAtlasProvider::new);
        simpleDataGenerator.addResource((fabricPackOutput, modId) -> new SimpleModelProvider(FCItemModelGenerator::new, FCBlockModelGenerator::new, fabricPackOutput, modId));
        simpleDataGenerator.addResource(FCEquipmentAssetProvider::new);
        simpleDataGenerator.addResource((fabricPackOutput, modId) -> new FCLanguageProvider(fabricPackOutput, modId, "en_us"));
        simpleDataGenerator.addResource(FCSoundDefinitionsProvider::new);
        simpleDataGenerator.addResource(FCCustomAnimationProvider::new);
        simpleDataGenerator.addResource(FCCustomModelProvider::new);
        simpleDataGenerator.addResource(FCFossilRenderProvider::new);
        simpleDataGenerator.addResource(FCGeneCosmeticProvider::new);

        simpleDataGenerator.addRegistries(BUILDER);

        simpleDataGenerator.addData(FCRecipeProvider.Runner::new);
        simpleDataGenerator.addData((fabricPackOutput, modId, registries) -> new SimpleLootTableProvider(fabricPackOutput, modId, registries, new LootTableProvider.SubProviderEntry(FCBlockLootSubProvider::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(FEntityLootSubProvider::new, LootContextParamSets.ENTITY), new LootTableProvider.SubProviderEntry(FCChestLootSubProvider::new, LootContextParamSets.CHEST)));
        simpleDataGenerator.addData(FCDataMapProvider::new);

        simpleDataGenerator.addData(FCBlockTagsProvider::new);
        simpleDataGenerator.addData(FCItemTagsProvider::new);
        simpleDataGenerator.addData(FCFluidTagsProvider::new);
        simpleDataGenerator.addData(FCEntityTypeTagsProvider::new);
        simpleDataGenerator.addData(FCBiomeTagsProvider::new);
        simpleDataGenerator.addData(FCAnalyzationResultTagsProvider::new);
        simpleDataGenerator.addData(FCFossilVariantTagsProvider::new);
        simpleDataGenerator.addData(FCStoneTabletVariantTagsProvider::new);
        simpleDataGenerator.addData(FCGeneTagsProvider::new);

        simpleDataGenerator.generate();
    }
}
