package ca.willatendo.fossilsclassic.server.recipe;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import net.minecraft.world.inventory.RecipeBookType;

public final class FCRecipeBookTypes {
    public static final RecipeBookType ANALYZATION = ServerUtils.getRecipeBookType(FCCoreUtils.ID, "analyzation");
    public static final RecipeBookType CULTIVATION = ServerUtils.getRecipeBookType(FCCoreUtils.ID, "cultivation");
    public static final RecipeBookType RESTORATION = ServerUtils.getRecipeBookType(FCCoreUtils.ID, "restoration");
}
