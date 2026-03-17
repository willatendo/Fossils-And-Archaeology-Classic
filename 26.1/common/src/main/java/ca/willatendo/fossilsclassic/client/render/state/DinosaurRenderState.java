package ca.willatendo.fossilsclassic.client.render.state;

import ca.willatendo.fossilsclassic.client.gene_cosmetic.GeneCosmetic;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class DinosaurRenderState extends LivingEntityRenderState {
    public int size = 0;
    public GeneCosmetic geneCosmetic;

    public final float getScaleWidth() {
        return this.geneCosmetic.baseWidthScale() + (this.geneCosmetic.sizeWidthScale() * (float) this.size);
    }

    public final float getScaleHeight() {
        return this.geneCosmetic.baseHeightScale() + (this.geneCosmetic.sizeHeightScale() * (float) this.size);
    }

    public final float getScaleShadow() {
        return this.geneCosmetic.baseShadowSize() + (this.geneCosmetic.sizeShadowScale() * (float) this.size);
    }
}
