package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.render.state.DinosaurRenderState;
import ca.willatendo.fossilsclassic.server.entity.entities.Smilodon;
import ca.willatendo.fossilsclassic.server.entity.entities.Stegosaurus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SmilodonRenderer extends DinosaurRenderer<Smilodon, DinosaurRenderState> {
    public SmilodonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public DinosaurRenderState createRenderState() {
        return new DinosaurRenderState();
    }
}
