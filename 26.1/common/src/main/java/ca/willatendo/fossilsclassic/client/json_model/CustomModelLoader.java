package ca.willatendo.fossilsclassic.client.json_model;

import ca.willatendo.fossilsclassic.client.json_animation.CustomAnimation;
import ca.willatendo.fossilsclassic.client.json_animation.CustomAnimationLoader;
import ca.willatendo.fossilsclassic.client.model.CustomTypeModel;
import com.google.common.collect.Maps;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public final class CustomModelLoader extends SimpleJsonResourceReloadListener<CustomModel> {
    public static final CustomModelLoader INSTANCE = new CustomModelLoader();
    private static final FileToIdConverter ASSET_LISTER = FileToIdConverter.json("fossilsclassic/custom_models");
    private static final Map<Identifier, CustomModel> MODELS = Maps.newHashMap();

    private CustomModelLoader() {
        super(CustomModel.CODEC, ASSET_LISTER);
    }

    public static <T extends LivingEntityRenderState> Optional<CustomTypeModel<T>> getCustomModel(Identifier identifier, String layer) {
        if (MODELS.containsKey(identifier)) {
            List<CustomAnimation> customAnimations = Lists.newArrayList();
            MODELS.get(identifier).animations().forEach(animation -> customAnimations.add(CustomAnimationLoader.get(animation)));
            return Optional.of(new CustomTypeModel<>(customAnimations, CustomModelLayerDefinitionResourceManager.bakeLayer(new ModelLayerLocation(identifier, layer))));
        }
        return Optional.empty();
    }

    public static boolean hasModel(Identifier identifier) {
        return MODELS.containsKey(identifier);
    }

    public static CustomModel getModel(Identifier identifier) {
        return MODELS.get(identifier);
    }

    public static void forJsonModels(BiConsumer<Identifier, CustomModel> consumer) {
        MODELS.forEach(consumer);
    }

    @Override
    protected Map<Identifier, CustomModel> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<Identifier, CustomModel> map = Maps.newHashMap();
        SimpleJsonResourceReloadListener.scanDirectory(resourceManager, ASSET_LISTER, JsonOps.INSTANCE, CustomModel.CODEC, map);
        return map;
    }

    @Override
    protected void apply(Map<Identifier, CustomModel> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        MODELS.clear();

        MODELS.putAll(map);
    }
}
