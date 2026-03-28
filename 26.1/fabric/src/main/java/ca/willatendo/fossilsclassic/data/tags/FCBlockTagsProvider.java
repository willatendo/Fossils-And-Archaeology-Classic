package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.tags.FCBlockTags;
import ca.willatendo.simplelibrary.data.providers.tag.SimpleBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public final class FCBlockTagsProvider extends SimpleBlockTagsProvider {
    public FCBlockTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(FCBlocks.FOSSIL_ORE.get(), FCBlocks.DEEPSLATE_FOSSIL_ORE.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(FCBlocks.FOSSIL_ORE.get(), FCBlocks.DEEPSLATE_FOSSIL_ORE.get(), FCBlocks.SKULL_BLOCK.get(), FCBlocks.SKULL_LANTERN_BLOCK.get(), FCBlocks.ANALYZER.get(), FCBlocks.CULTIVATOR.get(), FCBlocks.FEEDER.get(), FCBlocks.ICED_STONE.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(FCBlocks.ARCHAEOLOGY_WORKBENCH.get(), FCBlocks.DRUM.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(FCBlocks.PERMAFROST.get());

        this.tag(FCBlockTags.FEEDER).add(FCBlocks.FEEDER.get());
        this.tag(FCBlockTags.JURASSIC_FERN_PLANTABLE_ON).addTag(BlockTags.DIRT);
        this.tag(FCBlockTags.PERMAFROST_FROSTABLE).add(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.TUFF, Blocks.DEEPSLATE, Blocks.CALCITE);
    }
}
