package ca.willatendo.fossilsclassic.client.render;

import ca.willatendo.fossilsclassic.client.FCLayers;
import ca.willatendo.fossilsclassic.client.fossil_render.FossilRenderLoader;
import ca.willatendo.fossilsclassic.client.json_model.CustomModelLoader;
import ca.willatendo.fossilsclassic.client.model.BrachiosaurusModel;
import ca.willatendo.fossilsclassic.client.model.FutabasaurusModel;
import ca.willatendo.fossilsclassic.client.model.TriceratopsModel;
import ca.willatendo.fossilsclassic.client.render.state.FossilRenderState;
import ca.willatendo.fossilsclassic.server.entity.entities.Fossil;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class FossilRenderer extends MobRenderer<Fossil, FossilRenderState, EntityModel<FossilRenderState>> {
    private final EntityModel<FossilRenderState> brachiosaurusModel;
    private final EntityModel<FossilRenderState> futabasaurusModel;
    private final EntityModel<FossilRenderState> pteranodonModel;
    private final EntityModel<FossilRenderState> triceratopsModel;

    public FossilRenderer(EntityRendererProvider.Context context) {
        super(context, null, 0.0F);
        this.brachiosaurusModel = new BrachiosaurusModel(context.bakeLayer(FCLayers.BRACHIOSAURUS));
        this.futabasaurusModel = new FutabasaurusModel(context.bakeLayer(FCLayers.FUTABASAURUS));
        this.pteranodonModel = new FutabasaurusModel(context.bakeLayer(FCLayers.PTERANODON));
        this.triceratopsModel = new TriceratopsModel<>(context.bakeLayer(FCLayers.TRICERATOPS));
    }

    @Override
    public void extractRenderState(Fossil fossil, FossilRenderState fossilRenderState, float partialTick) {
        super.extractRenderState(fossil, fossilRenderState, partialTick);
        fossilRenderState.size = fossil.getSize();
        fossilRenderState.fossilRender = FossilRenderLoader.getFossilRender(fossil.registryAccess().lookupOrThrow(FCRegistries.FOSSIL_VARIANT).getKey(fossil.getFossilVariant().value()));
    }

    @Override
    public void submit(FossilRenderState fossilRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        this.model = CustomModelLoader.<FossilRenderState>getCustomModel(fossilRenderState.fossilRender.model(), "main").orElse(null);
        if (this.model == null) {
            this.model = switch (fossilRenderState.fossilRender.model().toString()) {
                case "fossilsclassic:brachiosaurus" -> this.brachiosaurusModel;
                case "fossilsclassic:futabasaurus" -> this.futabasaurusModel;
                case "fossilsclassic:pteranodon" -> this.pteranodonModel;
                default -> this.triceratopsModel;
            };
        }

        super.submit(fossilRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @Override
    protected float getShadowRadius(FossilRenderState fossilRenderState) {
        return fossilRenderState.getScaleShadow();
    }

    @Override
    protected void scale(FossilRenderState fossilRenderState, PoseStack poseStack) {
        poseStack.scale(fossilRenderState.getScaleWidth(), fossilRenderState.getScaleHeight(), fossilRenderState.getScaleWidth());
    }

    @Override
    public Identifier getTextureLocation(FossilRenderState fossilRenderState) {
        return fossilRenderState.fossilRender.texture();
    }

    @Override
    public FossilRenderState createRenderState() {
        return new FossilRenderState();
    }
}
