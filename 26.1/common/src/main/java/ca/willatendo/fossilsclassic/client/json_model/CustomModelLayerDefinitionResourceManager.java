package ca.willatendo.fossilsclassic.client.json_model;

import com.google.common.collect.Maps;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.util.Map;

public final class CustomModelLayerDefinitionResourceManager implements ResourceManagerReloadListener {
    public static final CustomModelLayerDefinitionResourceManager INSTANCE = new CustomModelLayerDefinitionResourceManager();
    private static final Map<ModelLayerLocation, LayerDefinition> MODELS = Maps.newHashMap();

    private CustomModelLayerDefinitionResourceManager() {
    }

    public static ModelPart bakeLayer(ModelLayerLocation modelLayerLocation) {
        LayerDefinition layerDefinition = MODELS.get(modelLayerLocation);
        if (layerDefinition == null) {
            throw new IllegalArgumentException("No model for layer " + modelLayerLocation);
        } else {
            return layerDefinition.bakeRoot();
        }
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        MODELS.clear();

        CustomModelLoader.forJsonModels((identifier, customModel) -> MODELS.put(new ModelLayerLocation(identifier, customModel.layer()), customModel.createModelLayer()));
    }
}
