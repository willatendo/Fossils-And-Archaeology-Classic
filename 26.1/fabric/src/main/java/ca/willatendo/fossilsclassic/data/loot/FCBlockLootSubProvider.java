package ca.willatendo.fossilsclassic.data.loot;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.loot.entries.LootRandomItem;
import ca.willatendo.simplelibrary.data.providers.loot.SimpleBlockLootSubProvider;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public final class FCBlockLootSubProvider extends SimpleBlockLootSubProvider {
    public FCBlockLootSubProvider(HolderLookup.Provider registries) {
        super(registries, FCCoreUtils.ID);
    }

    @Override
    public void generate() {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.add(FCBlocks.FOSSIL_ORE.get(), block -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootRandomItem.randomItem(2000, new LootRandomItem.RandomItemEntry(FCItems.SCARAB_GEM.get(), 0, 1), new LootRandomItem.RandomItemEntry(FCItems.FOSSIL.get(), 1, 450), new LootRandomItem.RandomItemEntry(FCItems.RELIC_SCRAP.get(), 450, 980), new LootRandomItem.RandomItemEntry(Items.BONE, 980, 1780), new LootRandomItem.RandomItemEntry(FCBlocks.SKULL_BLOCK.get(), 1780, 1980), new LootRandomItem.RandomItemEntry(FCItems.BROKEN_ANCIENT_SWORD.get(), 1980, 1990), new LootRandomItem.RandomItemEntry(FCItems.BROKEN_ANCIENT_HELMET.get(), 1990, 2000)).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        this.add(FCBlocks.DEEPSLATE_FOSSIL_ORE.get(), block -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootRandomItem.randomItem(2000, new LootRandomItem.RandomItemEntry(FCItems.SCARAB_GEM.get(), 0, 1), new LootRandomItem.RandomItemEntry(FCItems.FOSSIL.get(), 1, 450), new LootRandomItem.RandomItemEntry(FCItems.RELIC_SCRAP.get(), 450, 980), new LootRandomItem.RandomItemEntry(Items.BONE, 980, 1780), new LootRandomItem.RandomItemEntry(FCBlocks.SKULL_BLOCK.get(), 1780, 1980), new LootRandomItem.RandomItemEntry(FCItems.BROKEN_ANCIENT_SWORD.get(), 1980, 1990), new LootRandomItem.RandomItemEntry(FCItems.BROKEN_ANCIENT_HELMET.get(), 1990, 2000)).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        this.dropSelf(FCBlocks.SKULL_BLOCK.get());
        this.dropSelf(FCBlocks.SKULL_LANTERN_BLOCK.get());
        this.dropSelf(FCBlocks.ANALYZER.get());
        this.dropSelf(FCBlocks.CULTIVATOR.get());
        this.dropSelf(FCBlocks.ARCHAEOLOGY_WORKBENCH.get());
        this.add(FCBlocks.JURASSIC_FERN.get(), block -> this.createJurassicFernWithSeedDrops(block, FCBlocks.JURASSIC_FERN.get()));
        this.dropSelf(FCBlocks.DRUM.get());
        this.dropSelf(FCBlocks.FEEDER.get());
        this.add(FCBlocks.PERMAFROST.get(), block -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootRandomItem.randomItem(5, new LootRandomItem.RandomItemEntry(FCItems.JURASSIC_FERN_SPORES.get(), 0, 1), new LootRandomItem.RandomItemEntry(FCBlocks.SKULL_BLOCK.get(), 1, 2), new LootRandomItem.RandomItemEntry(FCItems.FROZEN_MEAT.get(), 2, 3), new LootRandomItem.RandomItemEntry(Items.BONE, 3, 4), new LootRandomItem.RandomItemEntry(Items.BOOK, 4, 5)).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        this.add(FCBlocks.ICED_STONE.get(), block -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(Blocks.COBBLESTONE))));
        this.add(FCBlocks.AXOLOTLSPAWN.get(), BlockLootSubProvider.noDrop());
    }

    private LootTable.Builder createJurassicFernWithSeedDrops(Block block, Block shearedDrop) {
        HolderLookup.RegistryLookup<Block> blockRegistry = this.registries.lookupOrThrow(Registries.BLOCK);
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(shearedDrop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))).when(this.hasShears()).otherwise(this.applyExplosionCondition(block, LootItem.lootTableItem(FCItems.JURASSIC_FERN_SPORES.get())).when(LootItemRandomChanceCondition.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockRegistry, block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockRegistry, block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0))));
    }
}
