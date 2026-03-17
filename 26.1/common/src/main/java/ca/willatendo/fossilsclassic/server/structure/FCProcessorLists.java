package ca.willatendo.fossilsclassic.server.structure;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.structure.processor.HolesProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public final class FCProcessorLists {
    public static final ResourceKey<StructureProcessorList> ACADEMY_DEGRADATION = FCProcessorLists.create("academy_degradation");
    public static final ResourceKey<StructureProcessorList> SHIP_DEGRADATION = FCProcessorLists.create("ship_degradation");

    private static ResourceKey<StructureProcessorList> create(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<StructureProcessorList> bootstrapContext) {
        bootstrapContext.register(ACADEMY_DEGRADATION, new StructureProcessorList(List.of(new RuleProcessor(ImmutableList.of(new ProcessorRule(new RandomBlockMatchTest(Blocks.GLOWSTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()))), new HolesProcessor(false, UniformInt.of(5, 10), UniformInt.of(1, 3)))));
        bootstrapContext.register(SHIP_DEGRADATION, new StructureProcessorList(List.of(new HolesProcessor(true, UniformInt.of(7, 20), UniformInt.of(1, 3)))));
    }
}
