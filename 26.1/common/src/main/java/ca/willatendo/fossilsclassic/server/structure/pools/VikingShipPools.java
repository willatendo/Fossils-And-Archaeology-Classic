package ca.willatendo.fossilsclassic.server.structure.pools;

import ca.willatendo.fossilsclassic.server.structure.FCProcessorLists;
import ca.willatendo.fossilsclassic.server.structure.FCStructureTemplatePools;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public final class VikingShipPools {
    public static final ResourceKey<StructureTemplatePool> START = FCStructureTemplatePools.create("viking_ship/start");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> bootstrapContext, HolderGetter<StructureTemplatePool> structureTemplatePools, HolderGetter<StructureProcessorList> structureProcessorLists) {
        bootstrapContext.register(START, new StructureTemplatePool(structureTemplatePools.getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.legacy("fossilsclassic:viking_ship/viking_ship_middle", structureProcessorLists.getOrThrow(FCProcessorLists.SHIP_DEGRADATION)), 1)), StructureTemplatePool.Projection.RIGID));
        FCStructureTemplatePools.register(bootstrapContext, "viking_ship/front", new StructureTemplatePool(structureTemplatePools.getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.legacy("fossilsclassic:viking_ship/viking_ship_front", structureProcessorLists.getOrThrow(FCProcessorLists.SHIP_DEGRADATION)), 1)), StructureTemplatePool.Projection.RIGID));
        FCStructureTemplatePools.register(bootstrapContext, "viking_ship/back", new StructureTemplatePool(structureTemplatePools.getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.legacy("fossilsclassic:viking_ship/viking_ship_back", structureProcessorLists.getOrThrow(FCProcessorLists.SHIP_DEGRADATION)), 1)), StructureTemplatePool.Projection.RIGID));
    }
}
