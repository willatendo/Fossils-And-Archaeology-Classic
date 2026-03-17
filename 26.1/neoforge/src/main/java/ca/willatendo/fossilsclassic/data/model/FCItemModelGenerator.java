package ca.willatendo.fossilsclassic.data.model;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.simplelibrary.data.model.SimpleItemModelGenerator;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.world.item.Item;

public final class FCItemModelGenerator extends SimpleItemModelGenerator {
    public FCItemModelGenerator(ItemModelGenerators itemModelGenerators) {
        super(itemModelGenerators, FCCoreUtils.ID);
    }

    @Override
    public void run() {
        this.generatedItem(FCItems.FOSSIL.get());
        this.generatedItem(FCItems.TRICERATOPS_DNA.get());
        this.generatedItem(FCItems.VELOCIRAPTOR_DNA.get());
        this.generatedItem(FCItems.TYRANNOSAURUS_DNA.get());
        this.generatedItem(FCItems.PTERANODON_DNA.get());
        this.generatedItem(FCItems.NAUTILUS_DNA.get());
        this.generatedItem(FCItems.FUTABASAURUS_DNA.get());
        this.generatedItem(FCItems.MOSASAURUS_DNA.get());
        this.generatedItem(FCItems.STEGOSAURUS_DNA.get());
        this.generatedItem(FCItems.DILOPHOSAURUS_DNA.get());
        this.generatedItem(FCItems.BRACHIOSAURUS_DNA.get());
        this.generatedItem(FCItems.TRICERATOPS_EGG.get());
        this.generatedItem(FCItems.VELOCIRAPTOR_EGG.get());
        this.generatedItem(FCItems.TYRANNOSAURUS_EGG.get());
        this.generatedItem(FCItems.PTERANODON_EGG.get());
        this.generatedItem(FCItems.FUTABASAURUS_EGG.get());
        this.generatedItem(FCItems.MOSASAURUS_EGG.get());
        this.generatedItem(FCItems.STEGOSAURUS_EGG.get());
        this.generatedItem(FCItems.DILOPHOSAURUS_EGG.get());
        this.generatedItem(FCItems.BRACHIOSAURUS_EGG.get());
        this.generatedItem(FCItems.RAW_TRICERATOPS.get());
        this.generatedItem(FCItems.RAW_VELOCIRAPTOR.get());
        this.generatedItem(FCItems.RAW_TYRANNOSAURUS.get());
        this.generatedItem(FCItems.RAW_PTERANODON.get());
        this.generatedItem(FCItems.RAW_FUTABASAURUS.get());
        this.generatedItem(FCItems.RAW_MOSASAURUS.get());
        this.generatedItem(FCItems.RAW_STEGOSAURUS.get());
        this.generatedItem(FCItems.RAW_DILOPHOSAURUS.get());
        this.generatedItem(FCItems.RAW_BRACHIOSAURUS.get());
        this.generatedItem(FCItems.RAW_SMILODON.get());
        this.generatedItem(FCItems.RAW_MAMMOTH.get());
        this.generatedItem(FCItems.COOKED_TRICERATOPS.get());
        this.generatedItem(FCItems.COOKED_VELOCIRAPTOR.get());
        this.generatedItem(FCItems.COOKED_TYRANNOSAURUS.get());
        this.generatedItem(FCItems.COOKED_PTERANODON.get());
        this.generatedItem(FCItems.COOKED_FUTABASAURUS.get());
        this.generatedItem(FCItems.COOKED_MOSASAURUS.get());
        this.generatedItem(FCItems.COOKED_STEGOSAURUS.get());
        this.generatedItem(FCItems.COOKED_DILOPHOSAURUS.get());
        this.generatedItem(FCItems.COOKED_BRACHIOSAURUS.get());
        this.generatedItem(FCItems.COOKED_SMILODON.get());
        this.generatedItem(FCItems.COOKED_MAMMOTH.get());
        this.generatedItem(FCItems.TYRANNOSAURUS_TOOTH.get());
        this.generatedItem(FCItems.TOOTH_DAGGER.get());
        this.generatedItem(FCItems.SKULL_STICK.get());
        this.generatedItem(FCItems.DINOPEDIA.get());
        this.generatedItem(FCItems.RAW_CHICKEN_SOUP_BUCKET.get());
        this.generatedItem(FCItems.COOKED_CHICKEN_SOUP_BUCKET.get());
        this.generatedItem(FCItems.CHICKEN_ESSENCE_BOTTLE.get());
        this.generatedItem(FCItems.MAGIC_CONCH.get());
        this.handheldItemAndBroken(FCItems.FROZEN_MEAT.get(), FCItems.BROKEN_FROZEN_MEAT.get());
        this.generatedItem(FCItems.AXOLOTL_DNA.get());
        this.generatedItem(FCItems.CAT_DNA.get());
        this.generatedItem(FCItems.CHICKEN_DNA.get());
        this.generatedItem(FCItems.COW_DNA.get());
        this.generatedItem(FCItems.DOLPHIN_DNA.get());
        this.generatedItem(FCItems.DONKEY_DNA.get());
        this.generatedItem(FCItems.FOX_DNA.get());
        this.generatedItem(FCItems.FROG_DNA.get());
        this.generatedItem(FCItems.GOAT_DNA.get());
        this.generatedItem(FCItems.HORSE_DNA.get());
        this.generatedItem(FCItems.LLAMA_DNA.get());
        this.generatedItem(FCItems.MULE_DNA.get());
        this.generatedItem(FCItems.OCELOT_DNA.get());
        this.generatedItem(FCItems.PANDA_DNA.get());
        this.generatedItem(FCItems.PARROT_DNA.get());
        this.generatedItem(FCItems.PIG_DNA.get());
        this.generatedItem(FCItems.POLAR_BEAR_DNA.get());
        this.generatedItem(FCItems.RABBIT_DNA.get());
        this.generatedItem(FCItems.SHEEP_DNA.get());
        this.generatedItem(FCItems.WOLF_DNA.get());
        this.generatedItem(FCItems.SMILODON_DNA.get());
        this.generatedItem(FCItems.MAMMOTH_DNA.get());
        this.generatedItem(FCItems.ARMADILLO_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.BAT_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.CAT_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.CAMEL_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.COW_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.DOLPHIN_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.DONKEY_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.FOX_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.GOAT_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.HORSE_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.LLAMA_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.MULE_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.OCELOT_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.PANDA_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.PIG_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.POLAR_BEAR_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.RABBIT_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.SHEEP_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.WOLF_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.SMILODON_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.MAMMOTH_EMBRYO_SYRINGE.get());
        this.generatedItem(FCItems.JURASSIC_FERN_SPORES.get());
        this.generatedItem(FCItems.RELIC_SCRAP.get());
        this.generatedItem(FCItems.STONE_TABLET.get());
        this.generatedItem(FCItems.BROKEN_ANCIENT_SWORD.get());
        this.generatedItem(FCItems.BROKEN_ANCIENT_HELMET.get());
        this.generatedItem(FCItems.SCARAB_GEM.get());
        this.handheldItem(FCItems.ANCIENT_SWORD.get());
        this.generatedItem(FCItems.ANCIENT_HELMET.get());
        this.handheldItem(FCItems.SCARAB_GEM_SWORD.get());
        this.handheldItem(FCItems.SCARAB_GEM_SHOVEL.get());
        this.handheldItem(FCItems.SCARAB_GEM_PICKAXE.get());
        this.handheldItem(FCItems.SCARAB_GEM_AXE.get());
        this.handheldItem(FCItems.SCARAB_GEM_HOE.get());
        this.handheldItem(FCItems.SCARAB_GEM_UPGRADE_SMITHING_TEMPLATE.get());
        this.handheldItemAndBroken(FCItems.WOODEN_JAVELIN.get(), FCItems.BROKEN_WOODEN_JAVELIN.get());
        this.handheldItemAndBroken(FCItems.STONE_JAVELIN.get(), FCItems.BROKEN_STONE_JAVELIN.get());
        this.handheldItemAndBroken(FCItems.COPPER_JAVELIN.get(), FCItems.BROKEN_COPPER_JAVELIN.get());
        this.handheldItemAndBroken(FCItems.IRON_JAVELIN.get(), FCItems.BROKEN_IRON_JAVELIN.get());
        this.handheldItemAndBroken(FCItems.GOLDEN_JAVELIN.get(), FCItems.BROKEN_GOLDEN_JAVELIN.get());
        this.handheldItemAndBroken(FCItems.DIAMOND_JAVELIN.get(), FCItems.BROKEN_DIAMOND_JAVELIN.get());
        this.handheldItemAndBroken(FCItems.NETHERITE_JAVELIN.get(), FCItems.BROKEN_NETHERITE_JAVELIN.get());
        this.handheldItemAndBroken(FCItems.SCARAB_GEM_JAVELIN.get(), FCItems.BROKEN_SCARAB_GEM_JAVELIN.get());

        this.generatedItem(FCItems.BONES_SPAWN_EGG.get());
        this.generatedItem(FCItems.FAILURESAURUS_SPAWN_EGG.get());

        this.generatedItem(FCItems.TRICERATOPS_SPAWN_EGG.get());

        this.generatedItem(FCItems.CUSTOM_SPAWN_EGG.get());
    }

    private void handheldItemAndBroken(Item baseItem, Item brokenItem) {
        ItemModel.Unbaked unbakedModel = ItemModelUtils.plainModel(this.itemModelGenerators.createFlatItemModel(baseItem, ModelTemplates.FLAT_HANDHELD_ITEM));
        this.itemModelOutput.accept(baseItem, unbakedModel);
        this.itemModelOutput.accept(brokenItem, unbakedModel);
    }

    private void generateItemAndBroken(Item baseItem, Item brokenItem) {
        ItemModel.Unbaked unbakedModel = ItemModelUtils.plainModel(this.itemModelGenerators.createFlatItemModel(baseItem, ModelTemplates.FLAT_ITEM));
        this.itemModelOutput.accept(baseItem, unbakedModel);
        this.itemModelOutput.accept(brokenItem, unbakedModel);
    }
}
