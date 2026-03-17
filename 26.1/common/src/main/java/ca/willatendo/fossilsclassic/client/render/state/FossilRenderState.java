package ca.willatendo.fossilsclassic.client.render.state;

import ca.willatendo.fossilsclassic.client.fossil_render.FossilRender;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class FossilRenderState extends LivingEntityRenderState {
    public int size;
    public FossilRender fossilRender;

    public final float getScaleWidth() {
        return this.fossilRender.baseWidthScale() + (this.fossilRender.sizeWidthScale() * (float) this.size);
    }

    public final float getScaleHeight() {
        return this.fossilRender.baseHeightScale() + (this.fossilRender.sizeHeightScale() * (float) this.size);
    }

    public final float getScaleShadow() {
        return this.fossilRender.baseShadowSize() + (this.fossilRender.sizeShadowScale() * (float) this.size);
    }
}
