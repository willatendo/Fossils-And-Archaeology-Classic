package ca.willatendo.fossilsclassic.server.block;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.blocks.*;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.sub.BlockSubRegistry;
import ca.willatendo.simplelibrary.server.utils.BlockUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class FCBlocks {
    public static final BlockSubRegistry BLOCKS = new BlockSubRegistry(FCCoreUtils.ID);

    public static final SimpleHolder<Block> FOSSIL_ORE = BLOCKS.registerBlock("fossil_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F));
    public static final SimpleHolder<Block> DEEPSLATE_FOSSIL_ORE = BLOCKS.registerBlock("deepslate_fossil_ore", BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE));
    public static final SimpleHolder<SkullBlock> SKULL_BLOCK = BLOCKS.registerBlock("skull_block", SkullBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.XYLOPHONE).strength(1.0F).sound(SoundType.BONE_BLOCK));
    public static final SimpleHolder<SkullBlock> SKULL_LANTERN_BLOCK = BLOCKS.registerBlock("skull_lantern_block", SkullBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.XYLOPHONE).strength(1.0F).lightLevel(blockState -> 15).sound(SoundType.BONE_BLOCK));
    public static final SimpleHolder<AnalyzerBlock> ANALYZER = BLOCKS.registerBlock("analyzer", AnalyzerBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final SimpleHolder<CultivatorBlock> CULTIVATOR = BLOCKS.registerBlock("cultivator", CultivatorBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.HAT).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(BlockUtils::never).isRedstoneConductor(BlockUtils::never).isSuffocating(BlockUtils::never).isViewBlocking(BlockUtils::never).lightLevel(blockState -> 13));
    public static final SimpleHolder<ArchaeologyWorkbenchBlock> ARCHAEOLOGY_WORKBENCH = BLOCKS.registerBlock("archaeology_workbench", ArchaeologyWorkbenchBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final SimpleHolder<JurassicFernBlock> JURASSIC_FERN = BLOCKS.registerBlock("jurassic_fern", JurassicFernBlock::new, () -> BlockBehaviour.Properties.of().noCollision().instabreak().randomTicks().sound(SoundType.GRASS));
    public static final SimpleHolder<DrumBlock> DRUM = BLOCKS.registerBlock("drum", DrumBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final SimpleHolder<FeederBlock> FEEDER = BLOCKS.registerBlock("feeder", FeederBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final SimpleHolder<PermafrostBlock> PERMAFROST = BLOCKS.registerBlock("permafrost", PermafrostBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CHIME).strength(0.5F).randomTicks().sound(SoundType.GRAVEL));
    public static final SimpleHolder<IcedStoneBlock> ICED_STONE = BLOCKS.registerBlock("iced_stone", IcedStoneBlock::new, () -> BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CHIME).requiresCorrectToolForDrops().strength(1.5F, 6.0F).randomTicks());
    public static final SimpleHolder<AxolotlspawnBlock> AXOLOTLSPAWN = BLOCKS.registerBlock("axolotlspawn", AxolotlspawnBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.WATER).instabreak().noOcclusion().noCollision().sound(SoundType.FROGSPAWN).pushReaction(PushReaction.DESTROY));
}
