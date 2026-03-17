package ca.willatendo.fossilsclassic.server.recipe;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.recipe.display.AnalyzationRecipeDisplay;
import ca.willatendo.fossilsclassic.server.recipe.display.CultivationRecipeDisplay;
import ca.willatendo.fossilsclassic.server.recipe.display.RestorationRecipeDisplay;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

public final class FCRecipeDisplays {
    public static final SimpleRegistry<RecipeDisplay.Type<?>> RECIPE_DISPLAY_TYPES = new SimpleRegistry<>(Registries.RECIPE_DISPLAY, FCCoreUtils.ID);

    public static final SimpleHolder<RecipeDisplay.Type<AnalyzationRecipeDisplay>> ANALYZATION = RECIPE_DISPLAY_TYPES.register("analyzation", () -> AnalyzationRecipeDisplay.TYPE);
    public static final SimpleHolder<RecipeDisplay.Type<CultivationRecipeDisplay>> CULTIVATION = RECIPE_DISPLAY_TYPES.register("cultivation", () -> CultivationRecipeDisplay.TYPE);
    public static final SimpleHolder<RecipeDisplay.Type<RestorationRecipeDisplay>> RESTORATION = RECIPE_DISPLAY_TYPES.register("restoration", () -> RestorationRecipeDisplay.TYPE);
}
