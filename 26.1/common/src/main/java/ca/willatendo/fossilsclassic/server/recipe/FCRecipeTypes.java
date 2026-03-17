package ca.willatendo.fossilsclassic.server.recipe;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.recipe.recipes.AnalyzationRecipe;
import ca.willatendo.fossilsclassic.server.recipe.recipes.CultivationRecipe;
import ca.willatendo.fossilsclassic.server.recipe.recipes.RestorationRecipe;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import ca.willatendo.simplelibrary.server.recipe.SimpleRecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;

public final class FCRecipeTypes {
    public static final SimpleRegistry<RecipeType<?>> RECIPE_TYPE = new SimpleRegistry<>(Registries.RECIPE_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<RecipeType<AnalyzationRecipe>> ANALYZATION = RECIPE_TYPE.register("analyzation", SimpleRecipeType::new);
    public static final SimpleHolder<RecipeType<CultivationRecipe>> CULTIVATION = RECIPE_TYPE.register("cultivation", SimpleRecipeType::new);
    public static final SimpleHolder<RecipeType<RestorationRecipe>> RESTORATION = RECIPE_TYPE.register("restoration", SimpleRecipeType::new);
}
