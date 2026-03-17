package ca.willatendo.fossilsclassic.client.fossil_render;

import com.google.common.collect.Maps;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public final class FossilRenderLoader extends SimpleJsonResourceReloadListener<FossilRender> {
    public static final FossilRenderLoader INSTANCE = new FossilRenderLoader();
    private static final FileToIdConverter ASSET_LISTER = FileToIdConverter.json("fossilsclassic/fossil_renders");
    private static final Map<Identifier, FossilRender> JSON_MODELS = Maps.newHashMap();

    private FossilRenderLoader() {
        super(FossilRender.CODEC, ASSET_LISTER);
    }

    public static FossilRender getFossilRender(Identifier identifier) {
        return JSON_MODELS.get(identifier);
    }

    @Override
    protected Map<Identifier, FossilRender> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<Identifier, FossilRender> map = Maps.newHashMap();
        SimpleJsonResourceReloadListener.scanDirectory(resourceManager, ASSET_LISTER, JsonOps.INSTANCE, FossilRender.CODEC, map);
        return map;
    }

    @Override
    protected void apply(Map<Identifier, FossilRender> identifierFossilRenderMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        JSON_MODELS.clear();
        JSON_MODELS.putAll(identifierFossilRenderMap);
    }
}
