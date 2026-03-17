package ca.willatendo.fossilsclassic.data.loot;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.simplelibrary.data.loot.SimpleEntityLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class FEntityLootSubProvider extends SimpleEntityLootSubProvider {
    public FEntityLootSubProvider(HolderLookup.Provider registries) {
        super(registries, FCCoreUtils.ID);
    }

    @Override
    public void generate() {
        this.add(FCEntityTypes.BONES.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(Items.BONE))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(FCBlocks.SKULL_BLOCK.get()))));
        this.add(FCEntityTypes.FAILURESAURUS.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F)).add(LootItem.lootTableItem(FCItems.FOSSIL.get()))));

        this.add(FCEntityTypes.TRICERATOPS.get(), LootTable.lootTable());

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

    private void addEggDrop(EntityType<?> entityType, ItemLike dropItemLike) {
        this.add(entityType, new LootTable.Builder().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(dropItemLike))));
    }
}
