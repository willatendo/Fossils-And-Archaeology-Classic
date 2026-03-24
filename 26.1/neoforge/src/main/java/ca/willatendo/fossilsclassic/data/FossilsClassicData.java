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
import ca.willatendo.simplelibrary.data.PackSubDataProvider;
import ca.willatendo.simplelibrary.data.ResourcePackGenerator;
import ca.willatendo.simplelibrary.data.SimpleLootTableProvider;
import ca.willatendo.simplelibrary.data.SimpleModelProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = FCCoreUtils.ID)
public final class FossilsClassicData {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(Registries.CONFIGURED_FEATURE, FCConfiguredFeatures::bootstrap).add(Registries.DAMAGE_TYPE, FCDamageTypes::bootstrap).add(Registries.PLACED_FEATURE, FCPlacedFeatures::bootstrap).add(Registries.PROCESSOR_LIST, FCProcessorLists::bootstrap).add(Registries.STRUCTURE, FCStructures::bootstrap).add(Registries.STRUCTURE_SET, FCStructureSets::bootstrap).add(Registries.TEMPLATE_POOL, FCStructureTemplatePools::bootstrap).add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FCBiomeModifiers::bootstrap).add(FCRegistries.ANALYZATION_RESULT, FCAnalyzationResults::bootstrap).add(FCRegistries.ARCHAEOLOGY_VALUE, FCArchaeologyValues::bootstrap).add(FCRegistries.BIOMASS_VALUE, FCBiomassValues::bootstrap).add(FCRegistries.CHROMOSOME, FCChromosomes::bootstrap).add(FCRegistries.FEEDER_FOOD_VALUE, FCFeederFoodValues::bootstrap).add(FCRegistries.FOSSIL_VARIANT, FCFossilVariants::bootstrap).add(FCRegistries.GENE, FCGenes::bootstrap).add(FCRegistries.STONE_TABLET_VARIANT, FCStoneTabletVariants::bootstrap);

    @SubscribeEvent
    public static void gatherDataEvent(GatherDataEvent.Client event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();

        event.addProvider(new FCAtlasProvider(packOutput, FCCoreUtils.ID));
        event.addProvider(new PackMetadataGenerator(packOutput).add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(FCCoreUtils.translation("resourcePack", "description"), new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA)))));
        event.addProvider(new SimpleModelProvider(FCItemModelGenerator::new, FCBlockModelGenerator::new, packOutput, FCCoreUtils.ID));
        event.addProvider(new FCEquipmentAssetProvider(packOutput, FCCoreUtils.ID));
        event.addProvider(new FCLanguageProvider(packOutput, FCCoreUtils.ID, "en_us"));
        event.addProvider(new FCSoundDefinitionsProvider(packOutput, FCCoreUtils.ID));
        event.addProvider(new FCCustomAnimationProvider(packOutput, FCCoreUtils.ID));
        event.addProvider(new FCCustomModelProvider(packOutput, FCCoreUtils.ID));
        event.addProvider(new FCFossilRenderProvider(packOutput, FCCoreUtils.ID));
        event.addProvider(new FCGeneCosmeticProvider(packOutput, FCCoreUtils.ID));

        event.createDatapackRegistryObjects(BUILDER);

        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();

        event.addProvider(new FCRecipeProvider.Runner(packOutput, registries));
        event.addProvider(new SimpleLootTableProvider(packOutput, registries, new LootTableProvider.SubProviderEntry(FCBlockLootSubProvider::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(FEntityLootSubProvider::new, LootContextParamSets.ENTITY), new LootTableProvider.SubProviderEntry(FCChestLootSubProvider::new, LootContextParamSets.CHEST)));
        event.addProvider(new FCDataMapProvider(packOutput, registries));

        FCBlockTagsProvider fcBlockTagsProvider = new FCBlockTagsProvider(packOutput, registries, FCCoreUtils.ID);
        event.addProvider(fcBlockTagsProvider);
        event.addProvider(new FCItemTagsProvider(packOutput, registries, FCCoreUtils.ID));
        event.addProvider(new FCFluidTagsProvider(packOutput, registries, FCCoreUtils.ID));
        event.addProvider(new FCEntityTypeTagsProvider(packOutput, registries, FCCoreUtils.ID));
        event.addProvider(new FCBiomeTagsProvider(packOutput, registries, FCCoreUtils.ID));
        event.addProvider(new FCAnalyzationResultTagsProvider(packOutput, registries, FCCoreUtils.ID));
        event.addProvider(new FCFossilVariantTagsProvider(packOutput, registries, FCCoreUtils.ID));
        event.addProvider(new FCStoneTabletVariantTagsProvider(packOutput, registries, FCCoreUtils.ID));
        event.addProvider(new FCGeneTagsProvider(packOutput, registries, FCCoreUtils.ID));

        PackSubDataProvider packSubDataProvider = PackSubDataProvider.createFeaturePack(FCCoreUtils.ID, "palaeocraft_example", dataGenerator, Component.literal("Palaeocraft example."), FeatureFlagSet.of(FCFeatureFlags.CUSTOM_DINOSAURS), c -> {
        });
    }

    private static void generatePalaeocraftExample(ResourcePackGenerator resourcePackGenerator) {
        resourcePackGenerator.addProvider(packOutputIn -> PackMetadataGenerator.forFeaturePack(packOutputIn, Component.empty(), FeatureFlagSet.of(FCFeatureFlags.CUSTOM_DINOSAURS)).add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(Component.literal("Palaeocraft example."), new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA)))));
    }
}
