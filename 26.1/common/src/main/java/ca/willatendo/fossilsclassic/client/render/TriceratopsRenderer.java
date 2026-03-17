package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.render.state.DinosaurRenderState;
import ca.willatendo.fossilsclassic.server.entity.entities.Triceratops;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class TriceratopsRenderer extends DinosaurRenderer<Triceratops, DinosaurRenderState> {
    public TriceratopsRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public DinosaurRenderState createRenderState() {
        return new DinosaurRenderState();
    }
}
