package ca.willatendo.fossilsclassic.client.screen;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.menu.menus.FeederMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class FeederScreen extends AbstractContainerScreen<FeederMenu> {
    private static final Identifier TEXTURE = FCCoreUtils.resource("textures/gui/container/feeder.png");
    private static final Identifier FEEDER_CAPACITY_SPRITE = FCCoreUtils.resource("container/feeder/feeder_capacity");

    public FeederScreen(FeederMenu feederMenu, Inventory inventory, Component title) {
        super(feederMenu, inventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        super.render(guiGraphics, x, y, partialTicks);
        this.renderTooltip(guiGraphics, x, y);
        guiGraphics.drawString(this.font, this.menu.feederBlockEntity.containerData.get(0) + "", this.leftPos + 25, this.topPos + 32, 0xFF0000);
        guiGraphics.drawString(this.font, this.menu.feederBlockEntity.containerData.get(1) + "", this.leftPos + 121, this.topPos + 32, 0x3B703);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        int meat = this.menu.getMeatScaled(46);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FEEDER_CAPACITY_SPRITE, 3, 46, 0, 46 - meat, leftPos + 67, topPos + 55 - meat, 3, meat);
        int plants = this.menu.getPlantsScaled(46);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FEEDER_CAPACITY_SPRITE, 3, 46, 0, 46 - plants, leftPos + 111, topPos + 55 - plants, 3, plants);
    }
}
