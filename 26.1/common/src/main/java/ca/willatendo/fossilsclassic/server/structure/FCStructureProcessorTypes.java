package ca.willatendo.fossilsclassic.server.structure;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.structure.processor.HolesProcessor;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public final class FCStructureProcessorTypes {
    public static final SimpleRegistry<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = new SimpleRegistry<>(Registries.STRUCTURE_PROCESSOR, FCCoreUtils.ID);

    public static final SimpleHolder<StructureProcessorType<HolesProcessor>> HOLES_PROCESSOR = STRUCTURE_PROCESSOR_TYPES.register("holes_processor", () -> () -> HolesProcessor.CODEC);
}
