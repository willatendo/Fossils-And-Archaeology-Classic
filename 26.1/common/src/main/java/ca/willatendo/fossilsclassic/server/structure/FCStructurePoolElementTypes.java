package ca.willatendo.fossilsclassic.server.structure;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.structure.pools.AcademyPoolElement;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public final class FCStructurePoolElementTypes {
    public static final SimpleRegistry<StructurePoolElementType<?>> STRUCTURE_POOL_ELEMENT_TYPES = new SimpleRegistry<>(Registries.STRUCTURE_POOL_ELEMENT, FCCoreUtils.ID);

    public static final SimpleHolder<StructurePoolElementType<AcademyPoolElement>> ACADEMY = STRUCTURE_POOL_ELEMENT_TYPES.register("single_pool_element", () -> () -> AcademyPoolElement.CODEC);
}
