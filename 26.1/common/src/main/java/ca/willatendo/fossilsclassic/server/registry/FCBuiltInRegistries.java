package ca.willatendo.fossilsclassic.server.registry;

import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistryBuilder;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import net.minecraft.core.Registry;

public final class FCBuiltInRegistries {
    public static final Registry<CommandType> COMMAND_TYPES = ServerUtils.createRegistry(FCRegistries.COMMAND_TYPE, SimpleRegistryBuilder.of().sync());
    public static final Registry<GeneType<?>> GENE_TYPES = ServerUtils.createRegistry(FCRegistries.GENE_TYPE, SimpleRegistryBuilder.of().sync());
    public static final Registry<JsonBehaviorType<?>> JSON_BEHAVIOR_TYPES = ServerUtils.createRegistry(FCRegistries.JSON_BEHAVIOR_TYPE, SimpleRegistryBuilder.of().sync());

    public static void init() {
    }
}
