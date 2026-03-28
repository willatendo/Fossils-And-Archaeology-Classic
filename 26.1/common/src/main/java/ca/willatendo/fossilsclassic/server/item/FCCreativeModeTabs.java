package ca.willatendo.fossilsclassic.server.item;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.FCFeatureFlags;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.fossilsclassic.server.tags.FCFossilVariantTags;
import ca.willatendo.fossilsclassic.server.tags.FCStoneTabletVariantTags;
import ca.willatendo.fossilsclassic.server.utils.CustomItemUtils;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.sub.CreativeModeTabSubRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Predicate;

public final class FCCreativeModeTabs {
    public static final CreativeModeTabSubRegistry CREATIVE_MODE_TABS = new CreativeModeTabSubRegistry(FCCoreUtils.ID);

    public static final SimpleHolder<CreativeModeTab> FOSSILS_CLASSIC_BLOCKS = CREATIVE_MODE_TABS.register("fossils_classic_blocks", () -> new ItemStack(FCBlocks.FOSSIL_ORE.get()), (itemDisplayParameters, output) -> {
        output.accept(FCBlocks.FOSSIL_ORE.get());
        output.accept(FCBlocks.DEEPSLATE_FOSSIL_ORE.get());
        output.accept(FCBlocks.PERMAFROST.get());
        output.accept(FCBlocks.ICED_STONE.get());
        output.accept(FCBlocks.SKULL_BLOCK.get());
        output.accept(FCBlocks.SKULL_LANTERN_BLOCK.get());
        output.accept(FCBlocks.ANALYZER.get());
        output.accept(FCBlocks.CULTIVATOR.get());
        output.accept(FCBlocks.ARCHAEOLOGY_WORKBENCH.get());
        output.accept(FCBlocks.FEEDER.get());
        output.accept(FCBlocks.DRUM.get());
        output.accept(FCBlocks.JURASSIC_FERN.get());
    });
    public static final SimpleHolder<CreativeModeTab> FOSSILS_CLASSIC_PALAEONTOLOGY_ITEMS = CREATIVE_MODE_TABS.register("fossils_classic_palaeontology_items", () -> new ItemStack(FCItems.FOSSIL.get()), (itemDisplayParameters, output) -> {
        output.accept(FCItems.FOSSIL.get());
        itemDisplayParameters.holders().lookup(FCRegistries.FOSSIL_VARIANT).ifPresent(fossilVariants -> FCCreativeModeTabs.generatePresetFossils(output, fossilVariants, fossilVariantHolder -> fossilVariantHolder.is(FCFossilVariantTags.PLACEABLE)));
        output.accept(FCItems.FROZEN_MEAT.get());
        output.accept(FCItems.ARMADILLO_DNA.get());
        output.accept(FCItems.ARMADILLO_EMBRYO_SYRINGE.get());
        output.accept(FCItems.AXOLOTL_DNA.get());
        output.accept(FCBlocks.AXOLOTLSPAWN.get());
        output.accept(FCItems.BAT_DNA.get());
        output.accept(FCItems.BAT_EMBRYO_SYRINGE.get());
        output.accept(FCItems.CAMEL_DNA.get());
        output.accept(FCItems.CAMEL_EMBRYO_SYRINGE.get());
        output.accept(FCItems.CAT_DNA.get());
        output.accept(FCItems.CAT_EMBRYO_SYRINGE.get());
        output.accept(FCItems.CHICKEN_DNA.get());
        output.accept(FCItems.INCUBATED_CHICKEN_EGG.get());
        output.accept(FCItems.COW_DNA.get());
        output.accept(FCItems.COW_EMBRYO_SYRINGE.get());
        output.accept(FCItems.DOLPHIN_DNA.get());
        output.accept(FCItems.DOLPHIN_EMBRYO_SYRINGE.get());
        output.accept(FCItems.DONKEY_DNA.get());
        output.accept(FCItems.DONKEY_EMBRYO_SYRINGE.get());
        output.accept(FCItems.FOX_DNA.get());
        output.accept(FCItems.FOX_EMBRYO_SYRINGE.get());
        output.accept(FCItems.FROG_DNA.get());
        output.accept(Blocks.FROGSPAWN);
        output.accept(FCItems.GOAT_DNA.get());
        output.accept(FCItems.GOAT_EMBRYO_SYRINGE.get());
        output.accept(FCItems.HORSE_DNA.get());
        output.accept(FCItems.HORSE_EMBRYO_SYRINGE.get());
        output.accept(FCItems.LLAMA_DNA.get());
        output.accept(FCItems.LLAMA_EMBRYO_SYRINGE.get());
        output.accept(FCItems.NAUTILUS_DNA.get());
        output.accept(FCItems.NAUTILUS_EGGS.get());
        output.accept(FCItems.LIVING_BABY_NAUTILUS.get());
        output.accept(FCItems.SIO_CHIU_LE.get());
        output.accept(FCItems.MULE_DNA.get());
        output.accept(FCItems.MULE_EMBRYO_SYRINGE.get());
        output.accept(FCItems.OCELOT_DNA.get());
        output.accept(FCItems.OCELOT_EMBRYO_SYRINGE.get());
        output.accept(FCItems.PANDA_DNA.get());
        output.accept(FCItems.PANDA_EMBRYO_SYRINGE.get());
        output.accept(FCItems.PARROT_DNA.get());
        output.accept(FCItems.INCUBATED_PARROT_EGG.get());
        output.accept(FCItems.PIG_DNA.get());
        output.accept(FCItems.PIG_EMBRYO_SYRINGE.get());
        output.accept(FCItems.POLAR_BEAR_DNA.get());
        output.accept(FCItems.POLAR_BEAR_EMBRYO_SYRINGE.get());
        output.accept(FCItems.RABBIT_DNA.get());
        output.accept(FCItems.RABBIT_EMBRYO_SYRINGE.get());
        output.accept(FCItems.SHEEP_DNA.get());
        output.accept(FCItems.SHEEP_EMBRYO_SYRINGE.get());
        output.accept(FCItems.WOLF_DNA.get());
        output.accept(FCItems.WOLF_EMBRYO_SYRINGE.get());
        output.accept(FCItems.MAMMOTH_DNA.get());
        output.accept(FCItems.MAMMOTH_EMBRYO_SYRINGE.get());
        output.accept(FCItems.RAW_MAMMOTH.get());
        output.accept(FCItems.COOKED_MAMMOTH.get());
        output.accept(FCItems.SMILODON_DNA.get());
        output.accept(FCItems.SMILODON_EMBRYO_SYRINGE.get());
        output.accept(FCItems.RAW_SMILODON.get());
        output.accept(FCItems.COOKED_SMILODON.get());
        output.accept(FCItems.BRACHIOSAURUS_DNA.get());
        output.accept(FCItems.BRACHIOSAURUS_EGG.get());
        output.accept(FCItems.RAW_BRACHIOSAURUS.get());
        output.accept(FCItems.COOKED_BRACHIOSAURUS.get());
        output.accept(FCItems.DILOPHOSAURUS_DNA.get());
        output.accept(FCItems.DILOPHOSAURUS_EGG.get());
        output.accept(FCItems.RAW_DILOPHOSAURUS.get());
        output.accept(FCItems.COOKED_DILOPHOSAURUS.get());
        output.accept(FCItems.FUTABASAURUS_DNA.get());
        output.accept(FCItems.FUTABASAURUS_EGG.get());
        output.accept(FCItems.RAW_FUTABASAURUS.get());
        output.accept(FCItems.COOKED_FUTABASAURUS.get());
        output.accept(FCItems.MOSASAURUS_DNA.get());
        output.accept(FCItems.MOSASAURUS_EGG.get());
        output.accept(FCItems.RAW_MOSASAURUS.get());
        output.accept(FCItems.COOKED_MOSASAURUS.get());
        output.accept(FCItems.PTERANODON_DNA.get());
        output.accept(FCItems.PTERANODON_EGG.get());
        output.accept(FCItems.RAW_PTERANODON.get());
        output.accept(FCItems.COOKED_PTERANODON.get());
        output.accept(FCItems.STEGOSAURUS_DNA.get());
        output.accept(FCItems.STEGOSAURUS_EGG.get());
        output.accept(FCItems.RAW_STEGOSAURUS.get());
        output.accept(FCItems.COOKED_STEGOSAURUS.get());
        output.accept(FCItems.TRICERATOPS_DNA.get());
        output.accept(FCItems.TRICERATOPS_EGG.get());
        output.accept(FCItems.RAW_TRICERATOPS.get());
        output.accept(FCItems.COOKED_TRICERATOPS.get());
        output.accept(FCItems.TYRANNOSAURUS_DNA.get());
        output.accept(FCItems.TYRANNOSAURUS_EGG.get());
        output.accept(FCItems.RAW_TYRANNOSAURUS.get());
        output.accept(FCItems.COOKED_TYRANNOSAURUS.get());
        output.accept(FCItems.TYRANNOSAURUS_TOOTH.get());
        output.accept(FCItems.VELOCIRAPTOR_DNA.get());
        output.accept(FCItems.VELOCIRAPTOR_EGG.get());
        output.accept(FCItems.RAW_VELOCIRAPTOR.get());
        output.accept(FCItems.COOKED_VELOCIRAPTOR.get());
        output.accept(FCItems.TOOTH_DAGGER.get());
        output.accept(FCItems.SKULL_STICK.get());
        output.accept(FCItems.MAGIC_CONCH.get());
        output.accept(FCItems.DINOPEDIA.get());
        output.accept(FCItems.RAW_CHICKEN_SOUP_BUCKET.get());
        output.accept(FCItems.COOKED_CHICKEN_SOUP_BUCKET.get());
        output.accept(FCItems.CHICKEN_ESSENCE_BOTTLE.get());
        output.accept(FCItems.JURASSIC_FERN_SPORES.get());
    });
    public static final SimpleHolder<CreativeModeTab> FOSSILS_CLASSIC_ARCHAEOLOGY_ITEMS = CREATIVE_MODE_TABS.register("fossils_classic_archaeology_items", () -> new ItemStack(FCItems.SCARAB_GEM.get()), (itemDisplayParameters, output) -> {
        output.accept(FCItems.RELIC_SCRAP.get());
        output.accept(FCItems.STONE_TABLET.get());
        itemDisplayParameters.holders().lookup(FCRegistries.STONE_TABLET_VARIANT).ifPresent(stoneTabletVariants -> FCCreativeModeTabs.generatePresetStoneTablets(output, stoneTabletVariants, stoneTabletVariantHolder -> stoneTabletVariantHolder.is(FCStoneTabletVariantTags.PLACEABLE)));
        output.accept(FCItems.BROKEN_ANCIENT_SWORD.get());
        output.accept(FCItems.BROKEN_ANCIENT_HELMET.get());
        output.accept(FCItems.SCARAB_GEM.get());
        output.accept(FCItems.ANCIENT_SWORD.get());
        output.accept(FCItems.ANCIENT_HELMET.get());
        output.accept(FCItems.SCARAB_GEM_SWORD.get());
        output.accept(FCItems.SCARAB_GEM_SHOVEL.get());
        output.accept(FCItems.SCARAB_GEM_PICKAXE.get());
        output.accept(FCItems.SCARAB_GEM_AXE.get());
        output.accept(FCItems.SCARAB_GEM_HOE.get());
        output.accept(FCItems.SCARAB_GEM_SPEAR.get());
        output.accept(FCItems.SCARAB_GEM_UPGRADE_SMITHING_TEMPLATE.get());
        output.accept(FCItems.WOODEN_JAVELIN.get());
        output.accept(FCItems.STONE_JAVELIN.get());
        output.accept(FCItems.COPPER_JAVELIN.get());
        output.accept(FCItems.IRON_JAVELIN.get());
        output.accept(FCItems.GOLDEN_JAVELIN.get());
        output.accept(FCItems.DIAMOND_JAVELIN.get());
        output.accept(FCItems.NETHERITE_JAVELIN.get());
        output.accept(FCItems.SCARAB_GEM_JAVELIN.get());
    });
    public static final SimpleHolder<CreativeModeTab> FOSSILS_CLASSIC_SPAWN_EGGS = CREATIVE_MODE_TABS.register("fossils_classic_spawn_eggs", () -> new ItemStack(FCItems.TRICERATOPS_SPAWN_EGG.get()), (itemDisplayParameters, output) -> {
        output.accept(FCItems.BONES_SPAWN_EGG.get());
        output.accept(FCItems.FAILURESAURUS_SPAWN_EGG.get());
        output.accept(FCItems.SMILODON_SPAWN_EGG.get());
        output.accept(FCItems.STEGOSAURUS_SPAWN_EGG.get());
        output.accept(FCItems.TRICERATOPS_SPAWN_EGG.get());
    });


    public static final SimpleHolder<CreativeModeTab> FOSSILS_CLASSIC_CUSTOM_CONTENT = CREATIVE_MODE_TABS.register("fossils_classic_custom_content", () -> new ItemStack(FCItems.FOSSIL.get()), (itemDisplayParameters, output) -> {
        if (itemDisplayParameters.enabledFeatures().contains(FCFeatureFlags.CUSTOM_DINOSAURS)) {
            HolderLookup.RegistryLookup<Chromosome> chromosomes = itemDisplayParameters.holders().lookupOrThrow(FCRegistries.CHROMOSOME);
            chromosomes.listElements().forEach(chromosome -> output.accept(CustomItemUtils.getSpawnEgg(chromosome)));
        }
    });

    private static void generatePresetFossils(CreativeModeTab.Output output, HolderLookup.RegistryLookup<FossilVariant> fossilVariants, Predicate<Holder<FossilVariant>> predicate) {
        fossilVariants.listElements().filter(predicate).forEach(fossilVariant -> {
            ItemStack itemStack = new ItemStack(FCItems.FOSSIL.get());
            itemStack.set(FCDataComponents.FOSSIL_VARIANT.get(), fossilVariant);
            output.accept(itemStack);
        });
    }

    private static void generatePresetStoneTablets(CreativeModeTab.Output output, HolderLookup.RegistryLookup<StoneTabletVariant> stoneTabletVariants, Predicate<Holder<StoneTabletVariant>> predicate) {
        stoneTabletVariants.listElements().filter(predicate).forEach(fossilVariant -> {
            ItemStack itemStack = new ItemStack(FCItems.STONE_TABLET.get());
            itemStack.set(FCDataComponents.STONE_TABLET_VARIANT.get(), fossilVariant);
            output.accept(itemStack);
        });
    }
}
