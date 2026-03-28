package ca.willatendo.fossilsclassic.client.screen;

import ca.willatendo.fossilsclassic.client.screen.recipe_book.ArchaeologyWorkbenchRecipeBookComponent;
import ca.willatendo.fossilsclassic.client.screen.recipe_book.FCSearchRecipeBookCategory;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.menu.menus.ArchaeologyWorkbenchMenu;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookCategories;
import ca.willatendo.simplelibrary.client.utils.RecipeBookUtils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class ArchaeologyWorkbenchScreen extends AbstractRecipeBookScreen<ArchaeologyWorkbenchMenu> {
    private static final Identifier TEXTURE = FCCoreUtils.resource("textures/gui/container/archaeology_workbench.png");
    private static final Identifier RESTORATION_PROGRESS_SPRITE = FCCoreUtils.resource("container/archaeology_workbench/restoration_progress");
    private static final Identifier ARCHAEOLOGY_SPRITE = FCCoreUtils.resource("container/archaeology_workbench/archaeology_progress");
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.restorable");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(RecipeBookUtils.createSearch(FCSearchRecipeBookCategory.RESTORATION), new RecipeBookComponent.TabInfo(FCItems.BROKEN_ANCIENT_SWORD.get(), FCRecipeBookCategories.RESTORATION_RESTORE.get()), new RecipeBookComponent.TabInfo(FCItems.DIAMOND_JAVELIN.get(), FCRecipeBookCategories.RESTORATION_REPAIR.get()), new RecipeBookComponent.TabInfo(FCItems.SCARAB_GEM.get(), FCRecipeBookCategories.RESTORATION_MISC.get()));

    public ArchaeologyWorkbenchScreen(ArchaeologyWorkbenchMenu archaeologyWorkbenchMenu, Inventory inventory, Component title) {
        super(archaeologyWorkbenchMenu, new ArchaeologyWorkbenchRecipeBookComponent(archaeologyWorkbenchMenu, FILTER_NAME, TABS), inventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 20, this.height / 2 - 49);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphicsExtractor, int xMouse, int yMouse, float a) {
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        guiGraphicsExtractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isOn()) {
            int onProgess = Mth.ceil(this.menu.getOnProgress() * 13.0F) + 1;
            guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, ARCHAEOLOGY_SPRITE, 14, 14, 0, 14 - onProgess, leftPos + 81, topPos + 36 + 14 - onProgess, 14, onProgess);
        }

        int cultivationProgess = this.menu.getCultivationProgress();
        guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, RESTORATION_PROGRESS_SPRITE, 24, 14, 0, 0, leftPos + 76, topPos + 21, cultivationProgess + 1, 14);
    }
}
