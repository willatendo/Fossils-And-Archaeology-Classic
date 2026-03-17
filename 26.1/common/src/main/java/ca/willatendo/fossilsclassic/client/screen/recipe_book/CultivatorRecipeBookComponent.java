package ca.willatendo.fossilsclassic.client.screen.recipe_book;

import ca.willatendo.fossilsclassic.client.FCRecipeBookOverlays;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.menu.menus.CultivatorMenu;
import ca.willatendo.fossilsclassic.server.recipe.display.CultivationRecipeDisplay;
import ca.willatendo.simplelibrary.client.screen.recipe_book.IdentifiableRecipeBookComponent;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.util.List;

public class CultivatorRecipeBookComponent extends RecipeBookComponent<CultivatorMenu> implements IdentifiableRecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(FCCoreUtils.resource("recipe_book/cultivation_filter_enabled"), FCCoreUtils.resource("recipe_book/cultivation_filter_disabled"), FCCoreUtils.resource("recipe_book/cultivation_filter_enabled_highlighted"), FCCoreUtils.resource("recipe_book/cultivation_filter_disabled_highlighted"));
    private final Component recipeFilterName;

    public CultivatorRecipeBookComponent(CultivatorMenu cultivatorMenu, Component recipeFilterName, List<TabInfo> tabInfos) {
        super(cultivatorMenu, tabInfos);
        this.recipeFilterName = recipeFilterName;
    }

    @Override
    protected WidgetSprites getFilterButtonTextures() {
        return FILTER_SPRITES;
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        boolean craftingSlot;
        switch (slot.index) {
            case 0, 1, 2 -> craftingSlot = true;
            default -> craftingSlot = false;
        }

        return craftingSlot;
    }

    @Override
    protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipeDisplay, ContextMap contextMap) {
        ghostSlots.setResult(this.menu.slots.get(2), contextMap, recipeDisplay.result());
        if (recipeDisplay instanceof CultivationRecipeDisplay cultivationRecipeDisplay) {
            ghostSlots.setInput(this.menu.slots.get(0), contextMap, cultivationRecipeDisplay.ingredient());
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return this.recipeFilterName;
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection recipeCollection, StackedItemContents stackedItemContents) {
        recipeCollection.selectRecipes(stackedItemContents, recipeDisplay -> recipeDisplay instanceof CultivationRecipeDisplay);
    }

    @Override
    public Identifier getIdentifier() {
        return FCRecipeBookOverlays.CULTIVATOR;
    }
}
