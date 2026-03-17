package ca.willatendo.fossilsclassic.data.generic;

import ca.willatendo.fossilsclassic.client.json_model.CustomModel;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.core.utils.CoreUtils;
import com.google.common.collect.Maps;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class CustomModelProvider implements DataProvider {
    private final Map<Identifier, CustomModel> jsonModels = Maps.newHashMap();
    private final PackOutput.PathProvider pathProvider;
    private final String modId;

    public CustomModelProvider(PackOutput packOutput, String modId) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, FCCoreUtils.ID + "/custom_models");
        this.modId = modId;
    }

    protected abstract void addAll();

    protected void add(String identifier, LayerDefinition layerDefinition, String layer) {
        this.add(identifier, layerDefinition, layer, List.of());
    }

    protected void add(String identifier, LayerDefinition layerDefinition, String layer, List<Identifier> animations) {
        this.add(CoreUtils.resource(this.modId, identifier), layerDefinition, layer, animations);
    }

    protected void add(Identifier identifier, LayerDefinition layerDefinition, String layer, List<Identifier> animations) {
        MaterialDefinition materialDefinition = layerDefinition.material;
        List<CustomModel.JsonPartDefinition> jsonPartDefinitions = Lists.newArrayList();
        this.add("root", layerDefinition.mesh.getRoot(), jsonPartDefinitions);
        this.add(identifier, new CustomModel(layer, materialDefinition.xTexSize, materialDefinition.yTexSize, jsonPartDefinitions, animations, Optional.empty()));
    }

    private void add(String name, PartDefinition partDefinition, List<CustomModel.JsonPartDefinition> jsonPartDefinitions) {
        List<CustomModel.JsonCube> jsonCubes = Lists.newArrayList();
        partDefinition.cubes.forEach(cubeDefinition -> jsonCubes.add(new CustomModel.JsonCube(cubeDefinition)));
        List<CustomModel.JsonPartDefinition> children = Lists.newArrayList();
        partDefinition.getChildren().forEach(entry -> {
            this.add(entry.getKey(), entry.getValue(), children);
        });
        jsonPartDefinitions.add(new CustomModel.JsonPartDefinition(name, jsonCubes, new CustomModel.JsonPartPose(partDefinition.partPose), children));
    }

    protected void add(String identifier, CustomModel customModel) {
        this.add(CoreUtils.resource(this.modId, identifier), customModel);
    }

    protected void add(Identifier identifier, CustomModel customModel) {
        this.jsonModels.put(identifier, customModel);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.jsonModels.clear();
        this.addAll();
        return DataProvider.saveAll(cachedOutput, CustomModel.CODEC, this.pathProvider, this.jsonModels);
    }

    @Override
    public String getName() {
        return "Custom Models: " + this.modId;
    }
}
