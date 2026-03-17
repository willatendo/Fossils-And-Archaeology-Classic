package ca.willatendo.fossilsclassic.client.screen.recipe_book;

import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookCategories;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;

import java.util.List;

public enum FCSearchRecipeBookCategory implements ExtendedRecipeBookCategory {
    ANALYZATION(FCRecipeBookCategories.ANALYZATION_PALAEONTOLOGY.get(), FCRecipeBookCategories.ANALYZATION_PALAEOBOTANY.get(), FCRecipeBookCategories.ANALYZATION_ARCHAEOLOGY.get(), FCRecipeBookCategories.ANALYZATION_MISC.get()),
    RESTORATION(FCRecipeBookCategories.RESTORATION_RESTORE.get(), FCRecipeBookCategories.RESTORATION_REPAIR.get(), FCRecipeBookCategories.RESTORATION_MISC.get()),
    CULTIVATION(FCRecipeBookCategories.CULTIVATION_EGGS.get(), FCRecipeBookCategories.CULTIVATION_EMBRYOS.get(), FCRecipeBookCategories.CULTIVATION_PLANTS.get(), FCRecipeBookCategories.CULTIVATION_MISC.get());
    private final List<RecipeBookCategory> includedCategories;

    FCSearchRecipeBookCategory(RecipeBookCategory... includedCategories) {
        this.includedCategories = List.of(includedCategories);
    }

    public List<RecipeBookCategory> includedCategories() {
        return this.includedCategories;
    }
}