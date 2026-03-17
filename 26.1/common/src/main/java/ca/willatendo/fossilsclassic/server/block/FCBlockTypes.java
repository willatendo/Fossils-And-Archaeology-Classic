package ca.willatendo.fossilsclassic.server.block;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.blocks.*;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public final class FCBlockTypes {
    public static final SimpleRegistry<MapCodec<? extends Block>> BLOCK_TYPES = new SimpleRegistry<>(Registries.BLOCK_TYPE, FCCoreUtils.ID);

    static {
        BLOCK_TYPES.register("analyzer", () -> AnalyzerBlock.CODEC);
        BLOCK_TYPES.register("archaeology_workbench", () -> ArchaeologyWorkbenchBlock.CODEC);
        BLOCK_TYPES.register("axolotlspawn", () -> AxolotlspawnBlock.CODEC);
        BLOCK_TYPES.register("cultivator", () -> CultivatorBlock.CODEC);
        BLOCK_TYPES.register("drum", () -> DrumBlock.CODEC);
        BLOCK_TYPES.register("feeder", () -> FeederBlock.CODEC);
        BLOCK_TYPES.register("iced_stone", () -> IcedStoneBlock.CODEC);
        BLOCK_TYPES.register("jurassic_fern", () -> JurassicFernBlock.CODEC);
        BLOCK_TYPES.register("permafrost", () -> PermafrostBlock.CODEC);
        BLOCK_TYPES.register("skull", () -> SkullBlock.CODEC);
    }
}
