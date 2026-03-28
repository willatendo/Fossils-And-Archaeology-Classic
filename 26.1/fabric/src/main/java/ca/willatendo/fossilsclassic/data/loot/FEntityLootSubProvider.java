package ca.willatendo.fossilsclassic.data.loot;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.loot.predicates.DinosaurPredicate;
import ca.willatendo.simplelibrary.data.providers.loot.SimpleEntityLootSubProvider;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Optional;

public final class FEntityLootSubProvider extends SimpleEntityLootSubProvider {
    public FEntityLootSubProvider(HolderLookup.Provider registries) {
        super(registries, FCCoreUtils.ID);
    }

    @Override
    public void generate() {
        this.add(FCEntityTypes.BONES.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(Items.BONE))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(FCBlocks.SKULL_BLOCK.get()))));
        this.add(FCEntityTypes.FAILURESAURUS.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F)).add(LootItem.lootTableItem(FCItems.FOSSIL.get()))));

        this.add(FCEntityTypes.SMILODON.get(), this.createDinosaurTable(2, 1.0F, 2.0F, FCItems.RAW_SMILODON.get()));
        this.add(FCEntityTypes.STEGOSAURUS.get(), this.createDinosaurTable(12, 1.0F, 3.0F, FCItems.RAW_STEGOSAURUS.get()));
        this.add(FCEntityTypes.TRICERATOPS.get(), this.createDinosaurTable(12, 1.0F, 3.0F, FCItems.RAW_TRICERATOPS.get()));

        this.addEggDrop(FCEntityTypes.TRICERATOPS_EGG.get(), FCItems.TRICERATOPS_EGG.get());
        this.addEggDrop(FCEntityTypes.VELOCIRAPTOR_EGG.get(), FCItems.VELOCIRAPTOR_EGG.get());
        this.addEggDrop(FCEntityTypes.TYRANNOSAURUS_EGG.get(), FCItems.TYRANNOSAURUS_EGG.get());
        this.addEggDrop(FCEntityTypes.PTERANODON_EGG.get(), FCItems.PTERANODON_EGG.get());
        this.addEggDrop(FCEntityTypes.FUTABASAURUS_EGG.get(), FCItems.FUTABASAURUS_EGG.get());
        this.addEggDrop(FCEntityTypes.MOSASAURUS_EGG.get(), FCItems.MOSASAURUS_EGG.get());
        this.addEggDrop(FCEntityTypes.STEGOSAURUS_EGG.get(), FCItems.STEGOSAURUS_EGG.get());
        this.addEggDrop(FCEntityTypes.DILOPHOSAURUS_EGG.get(), FCItems.DILOPHOSAURUS_EGG.get());
        this.addEggDrop(FCEntityTypes.BRACHIOSAURUS_EGG.get(), FCItems.BRACHIOSAURUS_EGG.get());
    }

    private LootTable.Builder createDinosaurTable(int growthStages, float minAdditional, float maxAdditional, ItemLike additionalDrop, float min, float max, ItemLike rawMeat) {
        return this.createDinosaurTable(growthStages, Optional.of(minAdditional), Optional.of(maxAdditional), Optional.of(additionalDrop), min, max, rawMeat);
    }

    private LootTable.Builder createDinosaurTable(int growthStages, float min, float max, ItemLike rawMeat) {
        return this.createDinosaurTable(growthStages, Optional.empty(), Optional.empty(), Optional.empty(), min, max, rawMeat);
    }

    private LootTable.Builder createDinosaurTable(int growthStages, Optional<Float> minAdditional, Optional<Float> maxAdditional, Optional<ItemLike> additionalDrop, float min, float max, ItemLike rawMeat) {
        LootTable.Builder builder = LootTable.lootTable();
        if (additionalDrop.isPresent() && minAdditional.isPresent() && maxAdditional.isPresent()) {
            builder.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(minAdditional.get(), maxAdditional.get())).setBonusRolls(UniformGenerator.between(0.0F, 2.0F)).add(LootItem.lootTableItem(additionalDrop.get())));
        }
        for (int i = 0; i < growthStages + 1; i++) {
            builder.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(rawMeat).apply(SetItemCountFunction.setCount(UniformGenerator.between(min + i, max + i))).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(DinosaurPredicate.growthStage(MinMaxBounds.Ints.exactly(i))))));
        }
        return builder;
    }


    private void addEggDrop(EntityType<?> entityType, ItemLike dropItemLike) {
        this.add(entityType, new LootTable.Builder().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(dropItemLike))));
    }
}
