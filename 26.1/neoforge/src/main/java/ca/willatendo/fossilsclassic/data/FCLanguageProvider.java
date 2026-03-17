package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.utils.DinosaurSpeechBubble;
import ca.willatendo.fossilsclassic.server.entity.utils.SpeechBubble;
import ca.willatendo.fossilsclassic.server.item.FCCreativeModeTabs;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.stats.FCStats;
import ca.willatendo.simplelibrary.data.SimpleLanguageProvider;
import net.minecraft.data.PackOutput;

public final class FCLanguageProvider extends SimpleLanguageProvider {
    public FCLanguageProvider(PackOutput packOutput, String modId, String local) {
        super(packOutput, modId, local);
    }

    @Override
    protected void addTranslations() {
        this.add("attribute.name.max_hunger", "Max Hunger");

        this.add(FCBlocks.FOSSIL_ORE.get());
        this.add(FCBlocks.DEEPSLATE_FOSSIL_ORE.get());
        this.add(FCBlocks.SKULL_BLOCK.get());
        this.add(FCBlocks.SKULL_LANTERN_BLOCK.get());
        this.add(FCBlocks.ANALYZER.get());
        this.add(FCBlocks.CULTIVATOR.get());
        this.add("block.fossilsclassic.cultivator.shatter", "Warning! Cultivation failure!");
        this.add(FCBlocks.ARCHAEOLOGY_WORKBENCH.get());
        this.add(FCBlocks.JURASSIC_FERN.get());
        this.add(FCBlocks.DRUM.get());
        this.add("block.fossilsclassic.drum.choose_command", "Drum order: %s.");
        this.add("block.fossilsclassic.drum.send_command", "Set all animals in 50 blocks commanded with %s to %s.");
        this.add(FCBlocks.FEEDER.get());
        this.add(FCBlocks.PERMAFROST.get());
        this.add(FCBlocks.ICED_STONE.get());
        this.add(FCBlocks.AXOLOTLSPAWN.get());

        this.add("command_type.fossilsclassic.follow", "follow");
        this.add("command_type.fossilsclassic.free_move", "free move");
        this.add("command_type.fossilsclassic.stay", "stay");

        this.add("container.fossilsclassic.analyzer", "Analyzer");
        this.add("container.fossilsclassic.archaeology_workbench", "Archaeology Workbench");
        this.add("container.fossilsclassic.cultivator", "Cultivator");

        this.add("dataPack.fossilsclassic.palaeocraft_example", "Palaeocraft Example");

        this.add("dinopedia.fossilsclassic.age", "Age: %s");
        this.add("dinopedia.fossilsclassic.embryo_type", "Embryo: %s");
        this.add("dinopedia.fossilsclassic.health", "Health: %s/%s");
        this.add("dinopedia.fossilsclassic.hunger", "Hunger: %s/%s");
        this.add("dinopedia.fossilsclassic.name", "%s");
        this.add("dinopedia.fossilsclassic.not_the_owner", "You do not own this animal!");
        this.add("dinopedia.fossilsclassic.owner", "Owner: %s");
        this.add("dinopedia.fossilsclassic.remaining_ticks", "Progress: %s");
        this.add("dinopedia.fossilsclassic.status", "Status: %s");
        this.add("dinopedia.fossilsclassic.status.cold", "Cold");
        this.add("dinopedia.fossilsclassic.status.dry", "Dry");
        this.add("dinopedia.fossilsclassic.status.warm", "Warm");
        this.add("dinopedia.fossilsclassic.status.wet", "Wet");
        this.add("dinopedia.fossilsclassic.wild", "This animal is wild!");

        this.add(FCEntityTypes.BONES.get());
        this.add(FCEntityTypes.FAILURESAURUS.get());

        this.add(FCEntityTypes.TRICERATOPS.get());
        this.add("entity.fossilsclassic.dinosaur.set_command", "Command: %s");

        this.add(FCEntityTypes.ANCIENT_LIGHTNING_BOLT.get(), "Lightning Bolt");

        this.add(FCEntityTypes.FOSSIL.get());
        this.add("entity.fossilsclassic.fossil.full_size", "This Fossil is full size!");
        this.add("entity.fossilsclassic.fossil.minimum_size", "This Fossil is its smallest size!");
        this.add("entity.fossilsclassic.fossil.no_space", "There is no space to grow this Fossil!");

        this.add(FCEntityTypes.THROWN_WOODEN_JAVELIN.get());
        this.add(FCEntityTypes.THROWN_STONE_JAVELIN.get());
        this.add(FCEntityTypes.THROWN_COPPER_JAVELIN.get());
        this.add(FCEntityTypes.THROWN_IRON_JAVELIN.get());
        this.add(FCEntityTypes.THROWN_GOLDEN_JAVELIN.get());
        this.add(FCEntityTypes.THROWN_DIAMOND_JAVELIN.get());
        this.add(FCEntityTypes.THROWN_NETHERITE_JAVELIN.get());
        this.add(FCEntityTypes.THROWN_SCARAB_GEM_JAVELIN.get());

        this.add("entity.fossilsclassic.egg.die.cold", "An egg was too cold and died!");
        this.add("entity.fossilsclassic.egg.die.dry", "An egg was too dry and died!");
        this.add(FCEntityTypes.TRICERATOPS_EGG.get());
        this.add(FCEntityTypes.VELOCIRAPTOR_EGG.get());
        this.add(FCEntityTypes.TYRANNOSAURUS_EGG.get());
        this.add(FCEntityTypes.PTERANODON_EGG.get());
        this.add(FCEntityTypes.FUTABASAURUS_EGG.get());
        this.add(FCEntityTypes.MOSASAURUS_EGG.get());
        this.add(FCEntityTypes.STEGOSAURUS_EGG.get());
        this.add(FCEntityTypes.DILOPHOSAURUS_EGG.get());
        this.add(FCEntityTypes.BRACHIOSAURUS_EGG.get());

        this.add("fossil.fossilsclassic.brachiosaurus.name", "Brachiosaurus");
        this.add("fossil.fossilsclassic.futabasaurus.name", "Futabasaurus");
        this.add("fossil.fossilsclassic.pteranodon.name", "Pteranodon");
        this.add("fossil.fossilsclassic.triceratops.name", "Triceratops");
        this.add("fossil.random", "Random variant");

        this.add("gui.recipebook.toggleRecipes.analyzable", "Showing Analyzable");
        this.add("gui.recipebook.toggleRecipes.cultivatable", "Showing Cultivatable");
        this.add("gui.recipebook.toggleRecipes.restorable", "Showing Restorable");

        this.add(FCItems.FOSSIL.get());
        this.add("item.fossilsclassic.fossil.no_space", "There is no space to place this Fossil!");
        this.add(FCItems.TRICERATOPS_DNA.get());
        this.add(FCItems.VELOCIRAPTOR_DNA.get());
        this.add(FCItems.TYRANNOSAURUS_DNA.get());
        this.add(FCItems.PTERANODON_DNA.get());
        this.add(FCItems.NAUTILUS_DNA.get());
        this.add(FCItems.FUTABASAURUS_DNA.get());
        this.add(FCItems.MOSASAURUS_DNA.get());
        this.add(FCItems.STEGOSAURUS_DNA.get());
        this.add(FCItems.DILOPHOSAURUS_DNA.get());
        this.add(FCItems.BRACHIOSAURUS_DNA.get());
        this.add(FCItems.TRICERATOPS_EGG.get());
        this.add(FCItems.VELOCIRAPTOR_EGG.get());
        this.add(FCItems.TYRANNOSAURUS_EGG.get());
        this.add(FCItems.PTERANODON_EGG.get());
        this.add(FCItems.FUTABASAURUS_EGG.get());
        this.add(FCItems.MOSASAURUS_EGG.get());
        this.add(FCItems.STEGOSAURUS_EGG.get());
        this.add(FCItems.DILOPHOSAURUS_EGG.get());
        this.add(FCItems.BRACHIOSAURUS_EGG.get());
        this.add(FCItems.RAW_TRICERATOPS.get());
        this.add(FCItems.RAW_VELOCIRAPTOR.get());
        this.add(FCItems.RAW_TYRANNOSAURUS.get());
        this.add(FCItems.RAW_PTERANODON.get());
        this.add(FCItems.RAW_FUTABASAURUS.get());
        this.add(FCItems.RAW_MOSASAURUS.get());
        this.add(FCItems.RAW_STEGOSAURUS.get());
        this.add(FCItems.RAW_DILOPHOSAURUS.get());
        this.add(FCItems.RAW_BRACHIOSAURUS.get());
        this.add(FCItems.RAW_SMILODON.get());
        this.add(FCItems.RAW_MAMMOTH.get());
        this.add(FCItems.COOKED_TRICERATOPS.get());
        this.add(FCItems.COOKED_VELOCIRAPTOR.get());
        this.add(FCItems.COOKED_TYRANNOSAURUS.get());
        this.add(FCItems.COOKED_PTERANODON.get());
        this.add(FCItems.COOKED_FUTABASAURUS.get());
        this.add(FCItems.COOKED_MOSASAURUS.get());
        this.add(FCItems.COOKED_STEGOSAURUS.get());
        this.add(FCItems.COOKED_DILOPHOSAURUS.get());
        this.add(FCItems.COOKED_BRACHIOSAURUS.get());
        this.add(FCItems.COOKED_SMILODON.get());
        this.add(FCItems.COOKED_MAMMOTH.get());
        this.add(FCItems.TYRANNOSAURUS_TOOTH.get());
        this.add(FCItems.TOOTH_DAGGER.get());
        this.add(FCItems.SKULL_STICK.get());
        this.add(FCItems.DINOPEDIA.get());
        this.add(FCItems.RAW_CHICKEN_SOUP_BUCKET.get());
        this.add(FCItems.COOKED_CHICKEN_SOUP_BUCKET.get());
        this.add(FCItems.CHICKEN_ESSENCE_BOTTLE.get());
        this.add(FCItems.MAGIC_CONCH.get());
        this.add(FCItems.FROZEN_MEAT.get());
        this.add(FCItems.BROKEN_FROZEN_MEAT.get(), "Frozen Meat");
        this.add(FCItems.AXOLOTL_DNA.get());
        this.add(FCItems.CAT_DNA.get());
        this.add(FCItems.CHICKEN_DNA.get());
        this.add(FCItems.COW_DNA.get());
        this.add(FCItems.DOLPHIN_DNA.get());
        this.add(FCItems.DONKEY_DNA.get());
        this.add(FCItems.FOX_DNA.get());
        this.add(FCItems.FROG_DNA.get());
        this.add(FCItems.GOAT_DNA.get());
        this.add(FCItems.HORSE_DNA.get());
        this.add(FCItems.LLAMA_DNA.get());
        this.add(FCItems.MULE_DNA.get());
        this.add(FCItems.OCELOT_DNA.get());
        this.add(FCItems.PANDA_DNA.get());
        this.add(FCItems.PARROT_DNA.get());
        this.add(FCItems.PIG_DNA.get());
        this.add(FCItems.POLAR_BEAR_DNA.get());
        this.add(FCItems.RABBIT_DNA.get());
        this.add(FCItems.SHEEP_DNA.get());
        this.add(FCItems.WOLF_DNA.get());
        this.add(FCItems.SMILODON_DNA.get());
        this.add(FCItems.MAMMOTH_DNA.get());
        this.add(FCItems.ARMADILLO_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.armadillo_embryo_syringe.embryo_name", "Armadillo");
        this.add(FCItems.BAT_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.bat_embryo_syringe.embryo_name", "Bat");
        this.add(FCItems.CAT_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.cat_embryo_syringe.embryo_name", "Cat");
        this.add(FCItems.CAMEL_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.camel_embryo_syringe.embryo_name", "Camel");
        this.add(FCItems.COW_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.cow_embryo_syringe.embryo_name", "Cow");
        this.add(FCItems.DOLPHIN_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.dolphin_embryo_syringe.embryo_name", "Dolphin");
        this.add(FCItems.DONKEY_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.donkey_embryo_syringe.embryo_name", "Donkey");
        this.add(FCItems.FOX_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.fox_embryo_syringe.embryo_name", "Fox");
        this.add(FCItems.GOAT_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.goat_embryo_syringe.embryo_name", "Goat");
        this.add(FCItems.HORSE_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.horse_embryo_syringe.embryo_name", "Horse");
        this.add(FCItems.LLAMA_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.llama_embryo_syringe.embryo_name", "Llama");
        this.add(FCItems.MULE_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.mule_embryo_syringe.embryo_name", "Mule");
        this.add(FCItems.OCELOT_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.ocelot_embryo_syringe.embryo_name", "Ocelot");
        this.add(FCItems.PANDA_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.panda_embryo_syringe.embryo_name", "Panda");
        this.add(FCItems.PIG_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.pig_embryo_syringe.embryo_name", "Pig");
        this.add(FCItems.POLAR_BEAR_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.polar_bear_embryo_syringe.embryo_name", "Polar Bear");
        this.add(FCItems.RABBIT_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.rabbit_embryo_syringe.embryo_name", "Rabbit");
        this.add(FCItems.SHEEP_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.sheep_embryo_syringe.embryo_name", "Sheep");
        this.add(FCItems.WOLF_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.wolf_embryo_syringe.embryo_name", "Wolf");
        this.add(FCItems.SMILODON_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.smilodon_embryo_syringe.embryo_name", "Smilodon");
        this.add(FCItems.MAMMOTH_EMBRYO_SYRINGE.get());
        this.add("item.fossilsclassic.mammoth_embryo_syringe.embryo_name", "Mammoth");
        this.add(FCItems.JURASSIC_FERN_SPORES.get());
        this.add(FCItems.RELIC_SCRAP.get());
        this.add(FCItems.STONE_TABLET.get());
        this.add(FCItems.BROKEN_ANCIENT_SWORD.get());
        this.add(FCItems.BROKEN_ANCIENT_HELMET.get());
        this.add(FCItems.SCARAB_GEM.get());
        this.add(FCItems.ANCIENT_SWORD.get());
        this.add(FCItems.ANCIENT_HELMET.get());
        this.add(FCItems.SCARAB_GEM_SWORD.get());
        this.add(FCItems.SCARAB_GEM_SHOVEL.get());
        this.add(FCItems.SCARAB_GEM_PICKAXE.get());
        this.add(FCItems.SCARAB_GEM_AXE.get());
        this.add(FCItems.SCARAB_GEM_HOE.get());
        this.add(FCItems.SCARAB_GEM_UPGRADE_SMITHING_TEMPLATE.get(), "Scarab Gem Upgrade");
        this.add("item.fossilsclassic.smithing_template.scarab_gem_upgrade.additions_slot_description", "Add Scarab Gem");
        this.add("item.fossilsclassic.smithing_template.scarab_gem_upgrade.applies_to", "Netherite Equipment");
        this.add("item.fossilsclassic.smithing_template.scarab_gem_upgrade.base_slot_description", "Add netherite weapon or tool");
        this.add("item.fossilsclassic.smithing_template.scarab_gem_upgrade.ingredient", "Scarab Gem");
        this.add(FCItems.WOODEN_JAVELIN.get());
        this.add(FCItems.BROKEN_WOODEN_JAVELIN.get(), "Wooden Javelin");
        this.add(FCItems.STONE_JAVELIN.get());
        this.add(FCItems.BROKEN_STONE_JAVELIN.get(), "Stone Javelin");
        this.add(FCItems.COPPER_JAVELIN.get());
        this.add(FCItems.BROKEN_COPPER_JAVELIN.get(), "Copper Javelin");
        this.add(FCItems.IRON_JAVELIN.get());
        this.add(FCItems.BROKEN_IRON_JAVELIN.get(), "Iron Javelin");
        this.add(FCItems.GOLDEN_JAVELIN.get());
        this.add(FCItems.BROKEN_GOLDEN_JAVELIN.get(), "Golden Javelin");
        this.add(FCItems.DIAMOND_JAVELIN.get());
        this.add(FCItems.BROKEN_DIAMOND_JAVELIN.get(), "Diamond Javelin");
        this.add(FCItems.NETHERITE_JAVELIN.get());
        this.add(FCItems.BROKEN_NETHERITE_JAVELIN.get(), "Netherite Javelin");
        this.add(FCItems.SCARAB_GEM_JAVELIN.get());
        this.add(FCItems.BROKEN_SCARAB_GEM_JAVELIN.get(), "Scarab Gem Javelin");

        this.add(FCItems.BONES_SPAWN_EGG.get());
        this.add(FCItems.FAILURESAURUS_SPAWN_EGG.get());

        this.add(FCItems.CUSTOM_SPAWN_EGG.get());
        this.add("item.fossilsclassic.custom_spawn_egg.no_entity", "No entity assigned to spawn!");

        this.add(FCCreativeModeTabs.FOSSILS_CLASSIC.get(), "F/A: Classic");
        this.add(FCCreativeModeTabs.FOSSILS_CLASSIC_SPAWN_EGGS.get(), "F/A: Classic Spawn Eggs");
        this.add(FCCreativeModeTabs.FOSSILS_CLASSIC_CUSTOM_CONTENT.get(), "F/A: Classic Custom Content");

        this.add(DinosaurSpeechBubble.FULL, "%s is hungry!");
        this.add(DinosaurSpeechBubble.HURT_ESCAPE, "%s will not trust humanity again!");
        this.add(DinosaurSpeechBubble.NO_SPACE, "%s has no space to grow!");
        this.add(DinosaurSpeechBubble.STARVE, "%s is starving!");
        this.add(DinosaurSpeechBubble.STARVE_ESCAPE, "%s has escaped from starvation!");
        this.add(DinosaurSpeechBubble.TAME_TYRANNOSAURUS_ERROR_TOO_YOUNG, "%s is too young to be tamed!");
        this.add(DinosaurSpeechBubble.TAME_TYRANNOSAURUS_ERROR_HEALTH, "%s must be knocked out to be tamed!");
        this.add(DinosaurSpeechBubble.TAME_WITH_TREAT, "%s is tamed!");

        this.addStat(FCStats.INTERACT_WITH_ANALYZER, "Interactions with Analyzer");
        this.addStat(FCStats.INTERACT_WITH_ARCHAEOLOGY_WORKBENCH, "Interactions with Archaeology Workbench");
        this.addStat(FCStats.INTERACT_WITH_CULTIVATOR, "Interactions with Cultivator");

        this.add("stone_tablet.dimensions", "%sx%s");
        this.add("stone_tablet.fossilsclassic.lighting.title", "Lightning");
        this.add("stone_tablet.fossilsclassic.lighting.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.social.title", "Social");
        this.add("stone_tablet.fossilsclassic.social.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.great_war.title", "Great War");
        this.add("stone_tablet.fossilsclassic.great_war.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.anu_death.title", "Anu's Death");
        this.add("stone_tablet.fossilsclassic.anu_death.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.portal.title", "Portal");
        this.add("stone_tablet.fossilsclassic.portal.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.herobrine.title", "Herobrine");
        this.add("stone_tablet.fossilsclassic.herobrine.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.skeleton_and_creeper.title", "Skeleton and Creeper");
        this.add("stone_tablet.fossilsclassic.skeleton_and_creeper.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.zombie_and_spider.title", "Zombie and Spider");
        this.add("stone_tablet.fossilsclassic.zombie_and_spider.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_in_iceberg.title", "Tyrannosaurus in Iceberg");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_in_iceberg.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_transport.title", "Tyrannosaurus Transport");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_transport.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_melt.title", "Tyrannosaurus Melt");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_melt.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_attack.title", "Tyrannosaurus Attack");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_attack.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_death.title", "Tyrannosaurus Death");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_death.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_corpse.title", "Tyrannosaurus Corpse");
        this.add("stone_tablet.fossilsclassic.tyrannosaurus_corpse.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.princess.title", "Princess");
        this.add("stone_tablet.fossilsclassic.princess.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.mosasaurus.title", "Mosasaurus");
        this.add("stone_tablet.fossilsclassic.mosasaurus.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.holy_mosasaurus.title", "Holy Mosasaurus");
        this.add("stone_tablet.fossilsclassic.holy_mosasaurus.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.past.title", "Past");
        this.add("stone_tablet.fossilsclassic.past.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.time_machine.title", "Time Machine");
        this.add("stone_tablet.fossilsclassic.time_machine.author", "Kajun");
        this.add("stone_tablet.fossilsclassic.future.title", "Future");
        this.add("stone_tablet.fossilsclassic.future.author", "Kajun");
        this.add("stone_tablet.random", "Random variant");

        this.add("subtitles.block.drum.hit", "Drum hit");
        this.add("subtitles.block.drum.triple_hit", "Drum triple hit");

        this.add("subtitles.entity.triceratops.ambientSound", "Triceratops grunts");
        this.add("subtitles.entity.triceratops.deathSound", "Triceratops dies");
        this.add("subtitles.entity.triceratops.hurtSound", "Triceratops hurts");
    }

    private void add(SpeechBubble speechBubble, String translation) {
        this.add(speechBubble.getTranslationKey(), translation);
    }
}
