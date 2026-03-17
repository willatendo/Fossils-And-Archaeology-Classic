package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.FCLayers;
import ca.willatendo.fossilsclassic.client.gene_cosmetic.GeneCosmeticLoader;
import ca.willatendo.fossilsclassic.client.json_model.CustomModelLoader;
import ca.willatendo.fossilsclassic.client.model.CustomTypeModel;
import ca.willatendo.fossilsclassic.client.model.TriceratopsModel;
import ca.willatendo.fossilsclassic.client.render.state.DinosaurRenderState;
import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;

public abstract class DinosaurRenderer<D extends Dinosaur, S extends DinosaurRenderState> extends MobRenderer<D, S, CustomTypeModel<S>> {
    private final TriceratopsModel<S> triceratopsModel;
    private Identifier modelId;

    public DinosaurRenderer(EntityRendererProvider.Context context) {
        super(context, null, 0.0F);
        this.triceratopsModel = new TriceratopsModel<>(context.bakeLayer(FCLayers.TRICERATOPS));
    }

    protected final CustomTypeModel<S> getModel(Identifier modelId) {
        return CustomModelLoader.<S>getCustomModel(modelId, "main").orElse(this.triceratopsModel);
    }

    private void setModel(Identifier modelId) {
        if (this.modelId != modelId) {
            this.modelId = modelId;
            this.model = this.getModel(modelId);
        }
    }

    @Override
    public void extractRenderState(D dinosaur, S dinosaurRenderState, float partialTick) {
        super.extractRenderState(dinosaur, dinosaurRenderState, partialTick);
        dinosaurRenderState.size = dinosaur.getGrowthStage();
        RegistryAccess registryAccess = dinosaur.registryAccess();
        Registry<Gene> genes = registryAccess.lookupOrThrow(FCRegistries.GENE);
        dinosaurRenderState.geneCosmetic = GeneCosmeticLoader.getGeneCosmetic(genes.getKey(dinosaur.getCosmeticGene().value()));
    }

    @Override
    public void submit(S dinosaurRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        this.setModel(dinosaurRenderState.geneCosmetic.model());

        super.submit(dinosaurRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @Override
    protected float getShadowRadius(S dinosaurRenderState) {
        return dinosaurRenderState.getScaleShadow();
    }

    @Override
    protected void scale(S dinosaurRenderState, PoseStack poseStack) {
        poseStack.scale(dinosaurRenderState.getScaleWidth(), dinosaurRenderState.getScaleHeight(), dinosaurRenderState.getScaleWidth());
    }

    @Override
    public Identifier getTextureLocation(S dinosaurRenderState) {
        return dinosaurRenderState.geneCosmetic.getTexture(dinosaurRenderState);
    }
}
