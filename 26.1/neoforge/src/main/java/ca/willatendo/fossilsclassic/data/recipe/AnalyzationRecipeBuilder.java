package ca.willatendo.fossilsclassic.data.recipe;

import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.recipe.categories.AnalyzationBookCategory;
import ca.willatendo.fossilsclassic.server.recipe.recipes.AnalyzationRecipe;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class AnalyzationRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final AnalyzationBookCategory analyzationBookCategory;
    private final Ingredient ingredient;
    private final Either<TagKey<AnalyzationResult>, ItemStack> results;
    private final int time;
    private final float experience;
    private String group;

    private AnalyzationRecipeBuilder(AnalyzationBookCategory analyzationBookCategory, Ingredient ingredient, Either<TagKey<AnalyzationResult>, ItemStack> results, int time, float experience) {
        this.analyzationBookCategory = analyzationBookCategory;
        this.ingredient = ingredient;
        this.time = time;
        this.results = results;
        this.experience = experience;
    }

    public static AnalyzationRecipeBuilder recipe(AnalyzationBookCategory analyzationBookCategory, Ingredient ingredient, TagKey<AnalyzationResult> results, int time, float experience) {
        return new AnalyzationRecipeBuilder(analyzationBookCategory, ingredient, Either.left(results), time, experience);
    }

    public static AnalyzationRecipeBuilder recipe(AnalyzationBookCategory analyzationBookCategory, Ingredient ingredient, ItemStack result, int time, float experience) {
        return new AnalyzationRecipeBuilder(analyzationBookCategory, ingredient, Either.right(result), time, experience);
    }

    @Override
    public AnalyzationRecipeBuilder unlockedBy(String name, Criterion criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public AnalyzationRecipeBuilder group(String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.ingredient.items().toList().get(0).value();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> recipeId) {
        this.ensureValid(recipeId);
        Advancement.Builder builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        recipeOutput.accept(recipeId, new AnalyzationRecipe(Objects.requireNonNullElse(this.group, ""), this.analyzationBookCategory, this.ingredient, this.results, this.time, this.experience), builder.build(recipeId.identifier().withPrefix("recipes/misc/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId.identifier());
        }
    }
}