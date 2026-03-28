package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.data.recipe.AnalyzationRecipeBuilder;
import ca.willatendo.fossilsclassic.data.recipe.CultivationRecipeBuilder;
import ca.willatendo.fossilsclassic.data.recipe.RestorationRecipeBuilder;
import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.recipe.categories.AnalyzationBookCategory;
import ca.willatendo.fossilsclassic.server.recipe.categories.CultivationBookCategory;
import ca.willatendo.fossilsclassic.server.recipe.categories.RestorationBookCategory;
import ca.willatendo.fossilsclassic.server.tags.FCAnalyzationResultTags;
import ca.willatendo.simplelibrary.data.providers.SimpleRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class FCRecipeProvider extends SimpleRecipeProvider {
    private FCRecipeProvider(HolderLookup.Provider registries, String modId, RecipeOutput recipeOutput) {
        super(registries, recipeOutput, modId);
    }

    @Override
    public void buildRecipes() {
        this.shaped(RecipeCategory.TOOLS, FCBlocks.ANALYZER.get()).pattern("#$#").pattern("#@#").define('#', Items.IRON_INGOT).define('$', FCItems.RELIC_SCRAP.get()).define('@', FCItems.FOSSIL.get()).unlockedBy(RecipeProvider.getHasName(Items.IRON_INGOT), this.has(Items.IRON_INGOT)).save(this.output);
        this.shaped(RecipeCategory.TOOLS, FCBlocks.CULTIVATOR.get()).pattern("#$#").pattern("#@#").pattern("%%%").define('#', Tags.Items.GLASS_BLOCKS).define('$', Items.GREEN_DYE).define('@', Items.WATER_BUCKET).define('%', Items.IRON_INGOT).unlockedBy(RecipeProvider.getHasName(Items.IRON_INGOT), this.has(Items.IRON_INGOT)).save(this.output);
        this.shapeless(RecipeCategory.TOOLS, FCBlocks.ARCHAEOLOGY_WORKBENCH.get()).requires(Items.PAPER).requires(Blocks.CRAFTING_TABLE).unlockedBy(RecipeProvider.getHasName(Items.PAPER), this.has(Items.PAPER)).save(this.output);
        this.shaped(RecipeCategory.TOOLS, FCBlocks.DRUM.get()).pattern("###").pattern("$%$").pattern("$$$").define('#', Items.LEATHER).define('%', Items.REDSTONE).define('$', ItemTags.PLANKS).unlockedBy(RecipeProvider.getHasName(Items.REDSTONE), this.has(Items.REDSTONE)).save(this.output);
        this.shaped(RecipeCategory.TOOLS, FCBlocks.FEEDER.get()).pattern("$#$").pattern("@!%").pattern("%%%").define('$', Items.IRON_INGOT).define('#', Tags.Items.GLASS_BLOCKS).define('@', Blocks.STONE_BUTTON).define('!', Items.BUCKET).define('%', Blocks.STONE).unlockedBy(RecipeProvider.getHasName(Blocks.STONE), this.has(Blocks.STONE)).save(this.output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, FCBlocks.SKULL_LANTERN_BLOCK.get()).requires(FCBlocks.SKULL_BLOCK.get()).requires(Blocks.TORCH).unlockedBy(RecipeProvider.getHasName(FCBlocks.SKULL_BLOCK.get()), this.has(FCBlocks.SKULL_BLOCK.get())).save(this.output);
        this.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 5).requires(FCBlocks.SKULL_BLOCK.get()).unlockedBy(RecipeProvider.getHasName(FCBlocks.SKULL_BLOCK.get()), this.has(FCBlocks.SKULL_BLOCK.get())).save(this.output, FCCoreUtils.ID + ":bone_meal_from_skull_block");

        this.shapeless(RecipeCategory.FOOD, FCItems.RAW_CHICKEN_SOUP_BUCKET.get()).requires(Items.CHICKEN).requires(Items.BUCKET).unlockedBy(RecipeProvider.getHasName(Items.CHICKEN), this.has(Items.CHICKEN)).save(this.output);
        this.cookFood(FCItems.RAW_CHICKEN_SOUP_BUCKET.get(), FCItems.COOKED_CHICKEN_SOUP_BUCKET.get());
        this.shaped(RecipeCategory.FOOD, FCItems.CHICKEN_ESSENCE_BOTTLE.get(), 8).pattern("###").pattern("#$#").pattern("###").define('#', Items.GLASS_BOTTLE).define('$', FCItems.COOKED_CHICKEN_SOUP_BUCKET.get()).unlockedBy(RecipeProvider.getHasName(FCItems.COOKED_CHICKEN_SOUP_BUCKET.get()), this.has(FCItems.COOKED_CHICKEN_SOUP_BUCKET.get())).save(this.output);
        this.cookFood(FCItems.RAW_TRICERATOPS.get(), FCItems.COOKED_TRICERATOPS.get());
        this.cookFood(FCItems.RAW_VELOCIRAPTOR.get(), FCItems.COOKED_VELOCIRAPTOR.get());
        this.cookFood(FCItems.RAW_TYRANNOSAURUS.get(), FCItems.COOKED_TYRANNOSAURUS.get());
        this.cookFood(FCItems.RAW_PTERANODON.get(), FCItems.COOKED_PTERANODON.get());
        this.cookFood(FCItems.LIVING_BABY_NAUTILUS.get(), FCItems.SIO_CHIU_LE.get());
        this.cookFood(FCItems.RAW_FUTABASAURUS.get(), FCItems.COOKED_FUTABASAURUS.get());
        this.cookFood(FCItems.RAW_MOSASAURUS.get(), FCItems.COOKED_MOSASAURUS.get());
        this.cookFood(FCItems.RAW_STEGOSAURUS.get(), FCItems.COOKED_STEGOSAURUS.get());
        this.cookFood(FCItems.RAW_DILOPHOSAURUS.get(), FCItems.COOKED_DILOPHOSAURUS.get());
        this.cookFood(FCItems.RAW_BRACHIOSAURUS.get(), FCItems.COOKED_BRACHIOSAURUS.get());
        this.cookFood(FCItems.RAW_SMILODON.get(), FCItems.COOKED_SMILODON.get());
        this.cookFood(FCItems.RAW_MAMMOTH.get(), FCItems.COOKED_MAMMOTH.get());

        this.shaped(RecipeCategory.TOOLS, FCItems.TOOTH_DAGGER.get()).pattern("#").pattern("$").define('#', FCItems.TOOTH_DAGGER.get()).define('$', Items.STICK).unlockedBy(RecipeProvider.getHasName(FCItems.TOOTH_DAGGER.get()), this.has(FCItems.TOOTH_DAGGER.get())).save(this.output);
        this.shaped(RecipeCategory.TOOLS, FCItems.SKULL_STICK.get()).pattern("#").pattern("$").define('#', FCBlocks.SKULL_BLOCK.get()).define('$', Items.STICK).unlockedBy(RecipeProvider.getHasName(FCBlocks.SKULL_BLOCK.get()), this.has(FCBlocks.SKULL_BLOCK.get())).save(this.output);

        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.FOSSIL.get(), FCAnalyzationResultTags.BIO_FOSSIL_RESULTS, 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.FROZEN_MEAT.get(), FCAnalyzationResultTags.FROZEN_MEAT_RESULTS, 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.BEEF, FCItems.COW_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.PORKCHOP, FCItems.PIG_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.CHICKEN, FCItems.CHICKEN_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("chicken_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.FEATHER, FCItems.CHICKEN_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("chicken_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.BROWN_EGG, FCItems.CHICKEN_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("chicken_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.BLUE_EGG, FCItems.CHICKEN_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("chicken_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.EGG, FCItems.CHICKEN_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("chicken_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Items.MUTTON, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.WHITE_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.ORANGE_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.MAGENTA_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.LIGHT_BLUE_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.YELLOW_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.LIME_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.PINK_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.GRAY_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.LIGHT_GRAY_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.CYAN_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.PURPLE_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.BLUE_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.BROWN_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.GREEN_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.RED_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, Blocks.BLACK_WOOL, FCItems.SHEEP_DNA.get(), 100, 0.35F, analyzationRecipeBuilder -> analyzationRecipeBuilder.group("sheep_dna"));
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_TRICERATOPS.get(), FCItems.TRICERATOPS_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_VELOCIRAPTOR.get(), FCItems.VELOCIRAPTOR_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_TYRANNOSAURUS.get(), FCItems.TYRANNOSAURUS_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_PTERANODON.get(), FCItems.PTERANODON_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_FUTABASAURUS.get(), FCItems.FUTABASAURUS_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_MOSASAURUS.get(), FCItems.MOSASAURUS_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_STEGOSAURUS.get(), FCItems.STEGOSAURUS_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_DILOPHOSAURUS.get(), FCItems.DILOPHOSAURUS_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_BRACHIOSAURUS.get(), FCItems.BRACHIOSAURUS_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_SMILODON.get(), FCItems.SMILODON_DNA.get(), 100, 0.35F);
        this.analyzation(AnalyzationBookCategory.PALAEONTOLOGY, FCItems.RAW_MAMMOTH.get(), FCItems.MAMMOTH_DNA.get(), 100, 0.35F);

        this.analyzation(AnalyzationBookCategory.ARCHAEOLOGY, FCItems.RELIC_SCRAP.get(), FCAnalyzationResultTags.RELIC_SCRAP_RESULTS, 100, 0.35F);

        this.cultivation(CultivationBookCategory.EGG, FCItems.TRICERATOPS_DNA.get(), new ItemStackTemplate(FCItems.TRICERATOPS_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.VELOCIRAPTOR_DNA.get(), new ItemStackTemplate(FCItems.VELOCIRAPTOR_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.TYRANNOSAURUS_DNA.get(), new ItemStackTemplate(FCItems.TYRANNOSAURUS_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.PTERANODON_DNA.get(), new ItemStackTemplate(FCItems.PTERANODON_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.NAUTILUS_DNA.get(), new ItemStackTemplate(FCItems.NAUTILUS_EGGS.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.FUTABASAURUS_DNA.get(), new ItemStackTemplate(FCItems.FUTABASAURUS_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.MOSASAURUS_DNA.get(), new ItemStackTemplate(FCItems.MOSASAURUS_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.STEGOSAURUS_DNA.get(), new ItemStackTemplate(FCItems.STEGOSAURUS_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.DILOPHOSAURUS_DNA.get(), new ItemStackTemplate(FCItems.DILOPHOSAURUS_EGG.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EGG, FCItems.BRACHIOSAURUS_DNA.get(), new ItemStackTemplate(FCItems.BRACHIOSAURUS_EGG.get()), 6000, 0.35F);

        this.cultivation(CultivationBookCategory.EMBRYO, FCItems.SMILODON_DNA.get(), new ItemStackTemplate(FCItems.SMILODON_EMBRYO_SYRINGE.get()), 6000, 0.35F);
        this.cultivation(CultivationBookCategory.EMBRYO, FCItems.MAMMOTH_DNA.get(), new ItemStackTemplate(FCItems.MAMMOTH_EMBRYO_SYRINGE.get()), 6000, 0.35F);

        this.restoration(RestorationBookCategory.RESTORE, FCItems.BROKEN_ANCIENT_HELMET.get(), FCItems.ANCIENT_HELMET.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.RESTORE, FCItems.BROKEN_ANCIENT_SWORD.get(), FCItems.ANCIENT_SWORD.get(), 6000, 0.35F);

        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_WOODEN_JAVELIN.get(), FCItems.WOODEN_JAVELIN.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_STONE_JAVELIN.get(), FCItems.STONE_JAVELIN.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_COPPER_JAVELIN.get(), FCItems.COPPER_JAVELIN.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_IRON_JAVELIN.get(), FCItems.IRON_JAVELIN.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_GOLDEN_JAVELIN.get(), FCItems.GOLDEN_JAVELIN.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_DIAMOND_JAVELIN.get(), FCItems.DIAMOND_JAVELIN.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_NETHERITE_JAVELIN.get(), FCItems.NETHERITE_JAVELIN.get(), 6000, 0.35F);
        this.restoration(RestorationBookCategory.REPAIR, FCItems.BROKEN_SCARAB_GEM_JAVELIN.get(), FCItems.SCARAB_GEM_JAVELIN.get(), 6000, 0.35F);

        this.scarabGemUpgrade(Items.NETHERITE_AXE, FCItems.SCARAB_GEM_AXE.get());
        this.scarabGemUpgrade(Items.NETHERITE_HOE, FCItems.SCARAB_GEM_HOE.get());
        this.scarabGemUpgrade(Items.NETHERITE_PICKAXE, FCItems.SCARAB_GEM_PICKAXE.get());
        this.scarabGemUpgrade(Items.NETHERITE_SHOVEL, FCItems.SCARAB_GEM_SHOVEL.get());
        this.scarabGemUpgrade(Items.NETHERITE_SPEAR, FCItems.SCARAB_GEM_SPEAR.get());
        this.scarabGemUpgrade(Items.NETHERITE_SWORD, FCItems.SCARAB_GEM_SWORD.get());
    }

    private void cookFood(Item inputItem, Item outputItem) {
        String outputItemName = BuiltInRegistries.ITEM.getKey(outputItem).getPath();
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(inputItem), RecipeCategory.FOOD, CookingBookCategory.FOOD, outputItem, 0.35F, 200).unlockedBy(RecipeProvider.getHasName(inputItem), this.has(inputItem)).save(this.output, FCCoreUtils.ID + ":" + outputItemName + "_from_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(inputItem), RecipeCategory.FOOD, outputItem, 0.35F, 100).unlockedBy(RecipeProvider.getHasName(inputItem), this.has(inputItem)).save(this.output, FCCoreUtils.ID + ":" + outputItemName + "_from_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(inputItem), RecipeCategory.FOOD, outputItem, 0.35F, 400).unlockedBy(RecipeProvider.getHasName(inputItem), this.has(inputItem)).save(this.output, FCCoreUtils.ID + ":" + outputItemName + "_from_campfire_cooking");
    }

    private void analyzation(AnalyzationBookCategory analyzationBookCategory, ItemLike inputItemLike, TagKey<AnalyzationResult> analyzationResultsTagKey, int cookingTime, float experience) {
        String inputName = BuiltInRegistries.ITEM.getKey(inputItemLike.asItem()).getPath();
        AnalyzationRecipeBuilder.recipe(analyzationBookCategory, Ingredient.of(inputItemLike), analyzationResultsTagKey, cookingTime, experience, FCCoreUtils.resource(inputName)).unlockedBy(RecipeProvider.getHasName(inputItemLike), this.has(inputItemLike)).save(this.output, FCCoreUtils.ID + ":" + inputName + "_outputs");
    }

    private void analyzation(AnalyzationBookCategory analyzationBookCategory, ItemLike inputItemLike, ItemLike outputItemLike, int cookingTime, float experience) {
        this.analyzation(analyzationBookCategory, inputItemLike, outputItemLike, cookingTime, experience, analyzationRecipeBuilder -> {
        });
    }

    private void analyzation(AnalyzationBookCategory analyzationBookCategory, ItemLike inputItemLike, ItemLike outputItemLike, int cookingTime, float experience, Consumer<AnalyzationRecipeBuilder> consumer) {
        String inputName = BuiltInRegistries.ITEM.getKey(inputItemLike.asItem()).getPath();
        String outputName = BuiltInRegistries.ITEM.getKey(outputItemLike.asItem()).getPath();
        Identifier defaultId = FCCoreUtils.resource(outputName + "_from_" + inputName);
        AnalyzationRecipeBuilder analyzationRecipeBuilder = AnalyzationRecipeBuilder.recipe(analyzationBookCategory, Ingredient.of(inputItemLike), new ItemStackTemplate(outputItemLike.asItem()), cookingTime, experience, defaultId).unlockedBy(RecipeProvider.getHasName(inputItemLike), this.has(inputItemLike));
        consumer.accept(analyzationRecipeBuilder);
        analyzationRecipeBuilder.save(this.output, defaultId.toString());
    }

    private void cultivation(CultivationBookCategory cultivationBookCategory, ItemLike inputItemLike, ItemStackTemplate outputItemStack, int cookingTime, float experience) {
        this.cultivation(cultivationBookCategory, inputItemLike, outputItemStack, cookingTime, experience, cultivationRecipeBuilder -> {
        });
    }

    private void cultivation(CultivationBookCategory cultivationBookCategory, ItemLike inputItemLike, ItemStackTemplate outputItemStack, int cookingTime, float experience, Consumer<CultivationRecipeBuilder> consumer) {
        String outputName = BuiltInRegistries.ITEM.getKey(outputItemStack.item().value()).getPath();
        CultivationRecipeBuilder cultivationRecipeBuilder = CultivationRecipeBuilder.recipe(cultivationBookCategory, Ingredient.of(inputItemLike), outputItemStack, cookingTime, experience).unlockedBy(RecipeProvider.getHasName(inputItemLike), this.has(inputItemLike));
        consumer.accept(cultivationRecipeBuilder);
        cultivationRecipeBuilder.save(this.output, FCCoreUtils.ID + ":" + outputName);
    }

    private void restoration(RestorationBookCategory restorationBookCategory, ItemLike inputItemLike, ItemLike outputItemLike, int cookingTime, float experience) {
        this.restoration(restorationBookCategory, inputItemLike, outputItemLike, cookingTime, experience, cultivationRecipeBuilder -> {
        });
    }

    private void restoration(RestorationBookCategory restorationBookCategory, ItemLike inputItemLike, ItemLike outputItemLike, int cookingTime, float experience, Consumer<RestorationRecipeBuilder> consumer) {
        String outputName = BuiltInRegistries.ITEM.getKey(outputItemLike.asItem()).getPath();
        RestorationRecipeBuilder restorationRecipeBuilder = RestorationRecipeBuilder.recipe(restorationBookCategory, Ingredient.of(inputItemLike), new ItemStackTemplate(outputItemLike.asItem()), cookingTime, experience).unlockedBy(RecipeProvider.getHasName(inputItemLike), this.has(inputItemLike));
        consumer.accept(restorationRecipeBuilder);
        restorationRecipeBuilder.save(this.output, FCCoreUtils.ID + ":" + outputName);
    }

    private void scarabGemUpgrade(ItemLike baseItemLike, ItemLike result) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(FCItems.SCARAB_GEM_UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(baseItemLike), Ingredient.of(FCItems.SCARAB_GEM.get()), RecipeCategory.BUILDING_BLOCKS, result.asItem()).unlocks(RecipeProvider.getHasName(baseItemLike), this.has(baseItemLike)).save(this.output, RecipeProvider.getItemName(result) + "_smithing");

    }

    public static final class Runner extends SimpleRecipeProvider.Runner {
        private final String modId;

        public Runner(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, modId, registries);
            this.modId = modId;
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
            return new FCRecipeProvider(registries, this.modId, recipeOutput);
        }

        @Override
        public String getName() {
            return "Fossils and Archaeology: Classic - Recipes";
        }
    }
}
