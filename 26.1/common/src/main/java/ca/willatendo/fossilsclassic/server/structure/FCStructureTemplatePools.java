package ca.willatendo.fossilsclassic.server.structure;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.structure.pools.AcademyPoolElement;
import ca.willatendo.fossilsclassic.server.structure.pools.VikingShipPools;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public final class FCStructureTemplatePools {
    public static final ResourceKey<StructureTemplatePool> ACADEMY = FCStructureTemplatePools.create("academy");
    public static final ResourceKey<StructureTemplatePool> WEAPON_SHOP = FCStructureTemplatePools.create("weapon_shop");

    public static ResourceKey<StructureTemplatePool> create(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, FCCoreUtils.resource(name));
    }

    public static void register(BootstrapContext<StructureTemplatePool> bootstrapContext, String name, StructureTemplatePool structureTemplatePool) {
        bootstrapContext.register(FCStructureTemplatePools.create(name), structureTemplatePool);
    }

    public static void bootstrap(BootstrapContext<StructureTemplatePool> bootstrapContext) {
        HolderGetter<StructureProcessorList> structureProcessorLists = bootstrapContext.lookup(Registries.PROCESSOR_LIST);
        HolderGetter<StructureTemplatePool> structureTemplatePools = bootstrapContext.lookup(Registries.TEMPLATE_POOL);

        bootstrapContext.register(ACADEMY, new StructureTemplatePool(structureTemplatePools.getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(AcademyPoolElement.academy("fossilsclassic:academy/bricks_academy", structureProcessorLists.getOrThrow(FCProcessorLists.ACADEMY_DEGRADATION)), 1), Pair.of(AcademyPoolElement.academy("fossilsclassic:academy/stone_bricks_academy", structureProcessorLists.getOrThrow(FCProcessorLists.ACADEMY_DEGRADATION)), 9)), StructureTemplatePool.Projection.RIGID));
        VikingShipPools.bootstrap(bootstrapContext, structureTemplatePools, structureProcessorLists);
        bootstrapContext.register(WEAPON_SHOP, new StructureTemplatePool(structureTemplatePools.getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("fossilsclassic:weapon_shop"), 1)), StructureTemplatePool.Projection.RIGID));
    }
}
