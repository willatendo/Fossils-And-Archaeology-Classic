package ca.willatendo.fossilsclassic.client.json_animation;

import com.google.common.collect.Maps;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public final class CustomAnimationLoader extends SimpleJsonResourceReloadListener<CustomAnimation> {
    public static final CustomAnimationLoader INSTANCE = new CustomAnimationLoader();
    private static final FileToIdConverter ASSET_LISTER = FileToIdConverter.json("fossilsclassic/custom_animations");
    private static final Map<Identifier, CustomAnimation> JSON_MODELS = Maps.newHashMap();

    private CustomAnimationLoader() {
        super(CustomAnimation.CODEC, ASSET_LISTER);
    }

    public static CustomAnimation get(Identifier identifier) {
        return JSON_MODELS.get(identifier);
    }

    @Override
    protected Map<Identifier, CustomAnimation> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<Identifier, CustomAnimation> map = Maps.newHashMap();
        SimpleJsonResourceReloadListener.scanDirectory(resourceManager, ASSET_LISTER, JsonOps.INSTANCE, CustomAnimation.CODEC, map);
        return map;
    }

    @Override
    protected void apply(Map<Identifier, CustomAnimation> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        JSON_MODELS.clear();

        JSON_MODELS.putAll(map);
    }
}
