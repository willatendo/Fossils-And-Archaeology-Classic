package ca.willatendo.fossilsclassic.server.recipe;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.sub.RecipeBookCategorySubRegistry;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public final class FCRecipeBookCategories {
    public static final RecipeBookCategorySubRegistry RECIPE_BOOK_CATEGORIES = new RecipeBookCategorySubRegistry(FCCoreUtils.ID);

    public static final SimpleHolder<RecipeBookCategory> ANALYZATION_PALAEONTOLOGY = RECIPE_BOOK_CATEGORIES.registerSimple("analyzation_palaeontology");
    public static final SimpleHolder<RecipeBookCategory> ANALYZATION_ARCHAEOLOGY = RECIPE_BOOK_CATEGORIES.registerSimple("analyzation_archaeology");
    public static final SimpleHolder<RecipeBookCategory> ANALYZATION_PALAEOBOTANY = RECIPE_BOOK_CATEGORIES.registerSimple("analyzation_palaeobotany");
    public static final SimpleHolder<RecipeBookCategory> ANALYZATION_MISC = RECIPE_BOOK_CATEGORIES.registerSimple("analyzation_misc");
    public static final SimpleHolder<RecipeBookCategory> CULTIVATION_EGGS = RECIPE_BOOK_CATEGORIES.registerSimple("cultivation_eggs");
    public static final SimpleHolder<RecipeBookCategory> CULTIVATION_EMBRYOS = RECIPE_BOOK_CATEGORIES.registerSimple("cultivation_embryos");
    public static final SimpleHolder<RecipeBookCategory> CULTIVATION_PLANTS = RECIPE_BOOK_CATEGORIES.registerSimple("cultivation_plants");
    public static final SimpleHolder<RecipeBookCategory> CULTIVATION_MISC = RECIPE_BOOK_CATEGORIES.registerSimple("cultivation_misc");
    public static final SimpleHolder<RecipeBookCategory> RESTORATION_RESTORE = RECIPE_BOOK_CATEGORIES.registerSimple("restoration_restore");
    public static final SimpleHolder<RecipeBookCategory> RESTORATION_REPAIR = RECIPE_BOOK_CATEGORIES.registerSimple("restoration_repair");
    public static final SimpleHolder<RecipeBookCategory> RESTORATION_MISC = RECIPE_BOOK_CATEGORIES.registerSimple("restoration_misc");
}
