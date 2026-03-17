package ca.willatendo.fossilsclassic.client.screen;

import ca.willatendo.fossilsclassic.client.screen.recipe_book.CultivatorRecipeBookComponent;
import ca.willatendo.fossilsclassic.client.screen.recipe_book.FCSearchRecipeBookCategory;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.menu.menus.CultivatorMenu;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookCategories;
import ca.willatendo.simplelibrary.client.utils.RecipeBookUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class CultivatorScreen extends AbstractRecipeBookScreen<CultivatorMenu> {
    private static final Identifier TEXTURE = FCCoreUtils.resource("textures/gui/container/cultivator.png");
    private static final Identifier CULTIVATION_PROGRESS_SPRITE = FCCoreUtils.resource("container/cultivator/cultivation_progress");
    private static final Identifier BIOMASS_PROGRESS_SPRITE = FCCoreUtils.resource("container/cultivator/biomass_progress");
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.cultivatable");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(RecipeBookUtils.createSearch(FCSearchRecipeBookCategory.CULTIVATION), new RecipeBookComponent.TabInfo(FCItems.TRICERATOPS_EGG.get(), FCRecipeBookCategories.CULTIVATION_EGGS.get()), new RecipeBookComponent.TabInfo(FCItems.SMILODON_EMBRYO_SYRINGE.get(), FCRecipeBookCategories.CULTIVATION_EMBRYOS.get()), new RecipeBookComponent.TabInfo(FCItems.JURASSIC_FERN_SPORES.get(), FCRecipeBookCategories.CULTIVATION_PLANTS.get()), new RecipeBookComponent.TabInfo(FCItems.TRICERATOPS_DNA.get(), FCRecipeBookCategories.CULTIVATION_MISC.get()));

    public CultivatorScreen(CultivatorMenu cultivatorMenu, Inventory inventory, Component title) {
        super(cultivatorMenu, new CultivatorRecipeBookComponent(cultivatorMenu, FILTER_NAME, TABS), inventory, title);
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
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isOn()) {
            int onProgess = Mth.ceil(this.menu.getOnProgress() * 13.0F) + 1;
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BIOMASS_PROGRESS_SPRITE, 14, 14, 0, 14 - onProgess, leftPos + 81, topPos + 36 + 14 - onProgess, 14, onProgess);
        }

        int cultivationProgess = this.menu.getCultivationProgress();
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CULTIVATION_PROGRESS_SPRITE, 22, 9, 0, 0, leftPos + 77, topPos + 23, cultivationProgess + 1, 9);
    }
}
