package ca.willatendo.fossilsclassic.server.registry;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.archaeology_value.ArchaeologyValue;
import ca.willatendo.fossilsclassic.server.biomass_value.BiomassValue;
import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.feeder_food.FeederFoodValue;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;


public final class FCRegistries {
    public static final ResourceKey<Registry<AnalyzationResult>> ANALYZATION_RESULT = FCRegistries.create("analyzation_result");
    public static final ResourceKey<Registry<ArchaeologyValue>> ARCHAEOLOGY_VALUE = FCRegistries.create("archaeology_value");
    public static final ResourceKey<Registry<BiomassValue>> BIOMASS_VALUE = FCRegistries.create("biomass_value");
    public static final ResourceKey<Registry<Chromosome>> CHROMOSOME = FCRegistries.create("chromosome");
    public static final ResourceKey<Registry<CommandType>> COMMAND_TYPE = FCRegistries.create("command_type");
    public static final ResourceKey<Registry<FeederFoodValue>> FEEDER_FOOD_VALUE = FCRegistries.create("feeder_food_value");
    public static final ResourceKey<Registry<FossilVariant>> FOSSIL_VARIANT = FCRegistries.create("fossil_variant");
    public static final ResourceKey<Registry<Gene>> GENE = FCRegistries.create("gene");
    public static final ResourceKey<Registry<GeneType<?>>> GENE_TYPE = FCRegistries.create("gene_type");
    public static final ResourceKey<Registry<JsonBehaviorType<?>>> JSON_BEHAVIOR_TYPE = FCRegistries.create("json_behavior_type");
    public static final ResourceKey<Registry<StoneTabletVariant>> STONE_TABLET_VARIANT = FCRegistries.create("stone_tablet_variant");

    private static <T> ResourceKey<Registry<T>> create(String name) {
        return ResourceKey.createRegistryKey(FCCoreUtils.resource(name));
    }
}
