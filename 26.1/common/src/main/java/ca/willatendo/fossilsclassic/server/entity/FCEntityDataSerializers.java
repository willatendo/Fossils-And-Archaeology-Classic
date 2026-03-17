package ca.willatendo.fossilsclassic.server.entity;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.simplelibrary.core.registry.sub.EntityDataSerializerSubRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.function.Supplier;

public final class FCEntityDataSerializers {
    public static final EntityDataSerializerSubRegistry ENTITY_DATA_SERIALIZERS = EntityDataSerializerSubRegistry.create(FCCoreUtils.ID);

    public static final Supplier<EntityDataSerializer<Holder<Chromosome>>> CHROMOSOME = ENTITY_DATA_SERIALIZERS.register("chromosome", () -> EntityDataSerializer.forValueType(Chromosome.STREAM_CODEC));
    public static final Supplier<EntityDataSerializer<Holder<CommandType>>> COMMAND_TYPE = ENTITY_DATA_SERIALIZERS.register("command_type", () -> EntityDataSerializer.forValueType(CommandType.STREAM_CODEC));
    public static final Supplier<EntityDataSerializer<Holder<FossilVariant>>> FOSSIL_VARIANT = ENTITY_DATA_SERIALIZERS.register("fossil_variant", () -> EntityDataSerializer.forValueType(FossilVariant.STREAM_CODEC));
    public static final Supplier<EntityDataSerializer<Holder<Gene>>> GENE = ENTITY_DATA_SERIALIZERS.register("gene", () -> EntityDataSerializer.forValueType(Gene.STREAM_CODEC));
    public static final Supplier<EntityDataSerializer<Holder<StoneTabletVariant>>> STONE_TABLET_VARIANT = ENTITY_DATA_SERIALIZERS.register("stone_tablet_variant", () -> EntityDataSerializer.forValueType(StoneTabletVariant.STREAM_CODEC));
}
