package ca.willatendo.fossilsclassic.server.block;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.entities.AnalyzerBlockEntity;
import ca.willatendo.fossilsclassic.server.block.entities.ArchaeologyWorkbenchBlockEntity;
import ca.willatendo.fossilsclassic.server.block.entities.CultivatorBlockEntity;
import ca.willatendo.fossilsclassic.server.block.entities.FeederBlockEntity;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public final class FCBlockEntityTypes {
    public static final SimpleRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = new SimpleRegistry<>(Registries.BLOCK_ENTITY_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<BlockEntityType<AnalyzerBlockEntity>> ANALYZER = BLOCK_ENTITY_TYPES.register("analyzer", () -> new BlockEntityType<>(AnalyzerBlockEntity::new, Set.of(FCBlocks.ANALYZER.get())));
    public static final SimpleHolder<BlockEntityType<ArchaeologyWorkbenchBlockEntity>> ARCHAEOLOGY_WORKBENCH = BLOCK_ENTITY_TYPES.register("archaeology_workbench", () -> new BlockEntityType<>(ArchaeologyWorkbenchBlockEntity::new, Set.of(FCBlocks.ARCHAEOLOGY_WORKBENCH.get())));
    public static final SimpleHolder<BlockEntityType<CultivatorBlockEntity>> CULTIVATOR = BLOCK_ENTITY_TYPES.register("cultivator", () -> new BlockEntityType<>(CultivatorBlockEntity::new, Set.of(FCBlocks.CULTIVATOR.get())));
    public static final SimpleHolder<BlockEntityType<FeederBlockEntity>> FEEDER = BLOCK_ENTITY_TYPES.register("feeder", () -> new BlockEntityType<>(FeederBlockEntity::new, Set.of(FCBlocks.FEEDER.get())));
}
