package ca.willatendo.fossilsclassic.data.model;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.data.FCModelTemplates;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.block.blocks.*;
import ca.willatendo.simplelibrary.data.providers.model.SimpleBlockModelGenerator;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public final class FCBlockModelGenerator extends SimpleBlockModelGenerator {
    public FCBlockModelGenerator(BlockModelGenerators blockModelGenerators) {
        super(blockModelGenerators, FCCoreUtils.ID);
    }

    @Override
    public void run() {
        this.createTrivialCube(FCBlocks.FOSSIL_ORE.get());
        this.createTrivialCube(FCBlocks.DEEPSLATE_FOSSIL_ORE.get());
        this.createSkull(FCBlocks.SKULL_BLOCK.get(), FCCoreUtils.resource("block/skull_front"));
        this.createSkull(FCBlocks.SKULL_LANTERN_BLOCK.get(), FCCoreUtils.resource("block/skull_lantern_front"));
        this.createAnalyzer(FCBlocks.ANALYZER.get());
        this.createCultivator(FCBlocks.CULTIVATOR.get());
        this.createArchaeologyWorkbench(FCBlocks.ARCHAEOLOGY_WORKBENCH.get());
        this.createJurassicFern(FCBlocks.JURASSIC_FERN.get());
        this.createDrum(FCBlocks.DRUM.get(), "stay", "follow", "free_move");
        this.createFeeder(FCBlocks.FEEDER.get());
        this.createTrivialCube(FCBlocks.PERMAFROST.get());
        this.createTrivialCube(FCBlocks.ICED_STONE.get());
        this.createAxolotlspawn(FCBlocks.AXOLOTLSPAWN.get());
    }

    private void createSkull(Block block, Identifier frontTexture) {
        Identifier sideTexture = FCCoreUtils.resource("block/skull_crack");
        Identifier capTexture = FCCoreUtils.resource("block/skull_cap");
        this.block(MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(block, new TextureMapping().put(TextureSlot.NORTH, new Material(frontTexture)).put(TextureSlot.EAST, new Material(sideTexture)).put(TextureSlot.SOUTH, new Material(sideTexture)).put(TextureSlot.WEST, new Material(sideTexture)).put(TextureSlot.UP, new Material(capTexture)).put(TextureSlot.DOWN, new Material(capTexture)).put(TextureSlot.PARTICLE, new Material(sideTexture)), this.modelOutput))).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void createAnalyzer(Block block) {
        Material sideTexture = TextureMapping.getBlockTexture(block, "_side");
        Material capTexture = TextureMapping.getBlockTexture(block, "_cap");
        Material frontTexture = TextureMapping.getBlockTexture(block, "_front");
        Material frontTextureActive = TextureMapping.getBlockTexture(block, "_front_active");
        MultiVariant analyzer = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(block, new TextureMapping().put(TextureSlot.NORTH, frontTexture).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, capTexture).put(TextureSlot.DOWN, capTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput));
        MultiVariant analyzerActive = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.createWithSuffix(block, "_active", new TextureMapping().put(TextureSlot.NORTH, frontTextureActive).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, capTexture).put(TextureSlot.DOWN, capTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput));
        this.block(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(AnalyzerBlock.ACTIVE, analyzerActive, analyzer)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void createCultivator(Block block) {
        Material sideTexture = TextureMapping.getBlockTexture(block, "_side");
        Material sideTextureActive = TextureMapping.getBlockTexture(block, "_side_active");
        Material topTexture = TextureMapping.getBlockTexture(block, "_top");
        Material bottomTexture = TextureMapping.getBlockTexture(block, "_bottom");
        MultiVariant cultivator = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(block, new TextureMapping().put(TextureSlot.NORTH, sideTexture).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, topTexture).put(TextureSlot.DOWN, bottomTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput));
        MultiVariant cultivatorActive = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.createWithSuffix(block, "_active", new TextureMapping().put(TextureSlot.NORTH, sideTextureActive).put(TextureSlot.EAST, sideTextureActive).put(TextureSlot.SOUTH, sideTextureActive).put(TextureSlot.WEST, sideTextureActive).put(TextureSlot.UP, topTexture).put(TextureSlot.DOWN, bottomTexture).put(TextureSlot.PARTICLE, sideTextureActive), this.modelOutput));
        this.block(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(CultivatorBlock.ACTIVE, cultivatorActive, cultivator)));
    }

    private void createArchaeologyWorkbench(Block block) {
        Material booksTexture = TextureMapping.getBlockTexture(block, "_books");
        Material sideTexture = TextureMapping.getBlockTexture(block, "_side");
        Material topTexture = TextureMapping.getBlockTexture(block, "_top");
        Material topTextureActive = TextureMapping.getBlockTexture(block, "_top_active");
        Material bottomTexture = TextureMapping.getBlockTexture(Blocks.SPRUCE_PLANKS);
        MultiVariant archaeologyWorkbench = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(block, new TextureMapping().put(TextureSlot.NORTH, booksTexture).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, topTexture).put(TextureSlot.DOWN, bottomTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput));
        MultiVariant archaeologyWorkbenchActive = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.createWithSuffix(block, "_active", new TextureMapping().put(TextureSlot.NORTH, booksTexture).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, topTextureActive).put(TextureSlot.DOWN, bottomTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput));
        this.block(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(ArchaeologyWorkbenchBlock.ACTIVE, archaeologyWorkbenchActive, archaeologyWorkbench)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void createJurassicFern(Block block) {
        this.blockModelGenerators.registerSimpleItemModel(block, this.blockModelGenerators.createFlatItemModelWithBlockTexture(block.asItem(), block, "_lower_3"));
        PropertyDispatch.C2<MultiVariant, DoubleBlockHalf, Integer> propertyDispatch = PropertyDispatch.initial(JurassicFernBlock.HALF, JurassicFernBlock.GROWTH);
        for (DoubleBlockHalf doubleBlockHalf : DoubleBlockHalf.values()) {
            for (int growthStage = 0; growthStage < 6; growthStage++) {
                int labledGrowthStage = (doubleBlockHalf == DoubleBlockHalf.UPPER && growthStage < 4) ? 4 : growthStage;
                MultiVariant jurassicFern = BlockModelGenerators.plainVariant(ModelTemplates.CROP.createWithSuffix(block, "_" + doubleBlockHalf.getSerializedName() + "_" + growthStage, new TextureMapping().put(TextureSlot.CROP, new Material(this.modLocation("block/jurassic_fern_" + doubleBlockHalf.getSerializedName() + "_" + labledGrowthStage))), this.modelOutput));

                propertyDispatch.select(doubleBlockHalf, growthStage, jurassicFern);
            }
        }
        this.block(MultiVariantGenerator.dispatch(block).with(propertyDispatch));
    }

    private void createDrum(Block block, String... commands) {
        Material sideTexture = TextureMapping.getBlockTexture(block, "_side");
        Material bottomTexture = TextureMapping.getBlockTexture(Blocks.SPRUCE_PLANKS);
        PropertyDispatch.C1<MultiVariant, String> propertyDispatch = PropertyDispatch.initial(DrumBlock.COMMAND_TYPE_PROPERTY);
        for (String command : commands) {
            Material topTexture = TextureMapping.getBlockTexture(block, "_top_" + command);
            Identifier drum = ModelTemplates.CUBE.createWithSuffix(block, "_" + command, new TextureMapping().put(TextureSlot.NORTH, sideTexture).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, topTexture).put(TextureSlot.DOWN, bottomTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput);
            propertyDispatch.select(command, BlockModelGenerators.plainVariant(drum));
            if (command.equals("stay")) {
                this.registerSimpleItemModel(block, drum);
            }
        }
        this.block(MultiVariantGenerator.dispatch(block).with(propertyDispatch));
    }

    private void createFeeder(Block block) {
        Material sideTexture = TextureMapping.getBlockTexture(block, "_side");
        Material frontTexture = TextureMapping.getBlockTexture(block, "_front");
        Material topTextureEmpty = TextureMapping.getBlockTexture(block, "_top_empty");
        Material topTextureFull = TextureMapping.getBlockTexture(block, "_top_full");
        Material bottomTexture = TextureMapping.getBlockTexture(Blocks.IRON_BLOCK);
        Identifier feederEmpty = ModelTemplates.CUBE.create(block, new TextureMapping().put(TextureSlot.NORTH, frontTexture).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, topTextureEmpty).put(TextureSlot.DOWN, bottomTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput);
        this.registerSimpleItemModel(block, feederEmpty);
        MultiVariant feederFull = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.createWithSuffix(block, "_full", new TextureMapping().put(TextureSlot.NORTH, frontTexture).put(TextureSlot.EAST, sideTexture).put(TextureSlot.SOUTH, sideTexture).put(TextureSlot.WEST, sideTexture).put(TextureSlot.UP, topTextureFull).put(TextureSlot.DOWN, bottomTexture).put(TextureSlot.PARTICLE, sideTexture), this.modelOutput));
        this.block(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(FeederBlock.HAS_FOOD, feederFull, BlockModelGenerators.plainVariant(feederEmpty))).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private void createAxolotlspawn(Block block) {
        this.blockModelGenerators.registerSimpleFlatItemModel(block);
        MultiVariant axolotlspawn = BlockModelGenerators.plainVariant(FCModelTemplates.FROGSPAWN.create(block, new TextureMapping().put(TextureSlot.TEXTURE, new Material(this.modLocation("block/axolotlspawn"))), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, axolotlspawn));
    }
}
