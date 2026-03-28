package ca.willatendo.fossilsclassic.client.screen;

import ca.willatendo.fossilsclassic.client.screen.recipe_book.AnalyzerRecipeBookComponent;
import ca.willatendo.fossilsclassic.client.screen.recipe_book.FCSearchRecipeBookCategory;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.menu.menus.AnalyzerMenu;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookCategories;
import ca.willatendo.simplelibrary.client.utils.RecipeBookUtils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class AnalyzerScreen extends AbstractRecipeBookScreen<AnalyzerMenu> {
    private static final Identifier TEXTURE = FCCoreUtils.resource("textures/gui/container/analyzer.png");
    private static final Identifier ANALYZATION_PROGRESS_SPRITE = FCCoreUtils.resource("container/analyzer/analyzation_progress");
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.analyzable");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(RecipeBookUtils.createSearch(FCSearchRecipeBookCategory.ANALYZATION), new RecipeBookComponent.TabInfo(FCItems.FOSSIL.get(), FCRecipeBookCategories.ANALYZATION_PALAEONTOLOGY.get()), new RecipeBookComponent.TabInfo(FCItems.JURASSIC_FERN_SPORES.get(), FCRecipeBookCategories.ANALYZATION_PALAEOBOTANY.get()), new RecipeBookComponent.TabInfo(FCItems.RELIC_SCRAP.get(), FCRecipeBookCategories.ANALYZATION_ARCHAEOLOGY.get()), new RecipeBookComponent.TabInfo(FCItems.VELOCIRAPTOR_DNA.get(), FCRecipeBookCategories.ANALYZATION_MISC.get()));

    public AnalyzerScreen(AnalyzerMenu analyzerMenu, Inventory inventory, Component title) {
        super(analyzerMenu, new AnalyzerRecipeBookComponent(analyzerMenu, FILTER_NAME, TABS), inventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 129, this.topPos + 22);
    }


    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphicsExtractor, int xMouse, int yMouse, float a) {
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        guiGraphicsExtractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        int analyzerProgess = this.menu.getAnalyzationProgress();
        guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, ANALYZATION_PROGRESS_SPRITE, 21, 9, 0, 0, leftPos + 68, topPos + 39, analyzerProgess + 1, 9);
    }
}
