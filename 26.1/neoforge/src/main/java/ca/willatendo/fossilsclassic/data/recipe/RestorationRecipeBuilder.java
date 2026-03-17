package ca.willatendo.fossilsclassic.data.recipe;

import ca.willatendo.fossilsclassic.server.recipe.categories.RestorationBookCategory;
import ca.willatendo.fossilsclassic.server.recipe.recipes.RestorationRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class RestorationRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final RestorationBookCategory restorationBookCategory;
    private final Ingredient ingredient;
    private final ItemStack result;
    private final int time;
    private final float experience;
    private String group;

    private RestorationRecipeBuilder(RestorationBookCategory restorationBookCategory, Ingredient ingredient, ItemStack result, int time, float experience) {
        this.restorationBookCategory = restorationBookCategory;
        this.ingredient = ingredient;
        this.time = time;
        this.result = result;
        this.experience = experience;
    }

    public static RestorationRecipeBuilder recipe(RestorationBookCategory restorationBookCategory, Ingredient ingredient, ItemStack result, int time, float experience) {
        return new RestorationRecipeBuilder(restorationBookCategory, ingredient, result, time, experience);
    }

    @Override
    public RestorationRecipeBuilder unlockedBy(String name, Criterion criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RestorationRecipeBuilder group(String group) {
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
        recipeOutput.accept(recipeId, new RestorationRecipe(Objects.requireNonNullElse(this.group, ""), this.restorationBookCategory, this.ingredient, this.result, this.time, this.experience), builder.build(recipeId.identifier().withPrefix("recipes/misc/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId.identifier());
        }
    }
}