package ca.willatendo.fossilsclassic.server.recipe;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.recipe.recipes.AnalyzationRecipe;
import ca.willatendo.fossilsclassic.server.recipe.recipes.CultivationRecipe;
import ca.willatendo.fossilsclassic.server.recipe.recipes.RestorationRecipe;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class FCRecipeSerializers {
    public static final SimpleRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = new SimpleRegistry<>(Registries.RECIPE_SERIALIZER, FCCoreUtils.ID);

    public static final SimpleHolder<RecipeSerializer<AnalyzationRecipe>> ANALYZATION_RECIPE = RECIPE_SERIALIZERS.register("analyzation_recipe", () -> new RecipeSerializer<>(AnalyzationRecipe.CODEC, AnalyzationRecipe.STREAM_CODEC));
    public static final SimpleHolder<RecipeSerializer<CultivationRecipe>> CULTIVATION_RECIPE = RECIPE_SERIALIZERS.register("cultivation_recipe", () -> new RecipeSerializer<>(CultivationRecipe.CODEC, CultivationRecipe.STREAM_CODEC));
    public static final SimpleHolder<RecipeSerializer<RestorationRecipe>> RESTORATION_RECIPE = RECIPE_SERIALIZERS.register("restoration_recipe", () -> new RecipeSerializer<>(RestorationRecipe.CODEC, RestorationRecipe.STREAM_CODEC));
}
