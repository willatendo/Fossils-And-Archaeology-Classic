package ca.willatendo.fossilsclassic.client.gene_cosmetic;

import com.google.common.collect.Maps;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public final class GeneCosmeticLoader extends SimpleJsonResourceReloadListener<GeneCosmetic> {
    public static final GeneCosmeticLoader INSTANCE = new GeneCosmeticLoader();
    private static final FileToIdConverter ASSET_LISTER = FileToIdConverter.json("fossilsclassic/gene_cosmetics");
    private static final Map<Identifier, GeneCosmetic> JSON_MODELS = Maps.newHashMap();

    private GeneCosmeticLoader() {
        super(GeneCosmetic.CODEC, ASSET_LISTER);
    }

    public static GeneCosmetic getGeneCosmetic(Identifier identifier) {
        return JSON_MODELS.get(identifier);
    }

    @Override
    protected Map<Identifier, GeneCosmetic> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<Identifier, GeneCosmetic> map = Maps.newHashMap();
        SimpleJsonResourceReloadListener.scanDirectory(resourceManager, ASSET_LISTER, JsonOps.INSTANCE, GeneCosmetic.CODEC, map);
        return map;
    }

    @Override
    protected void apply(Map<Identifier, GeneCosmetic> identifierGeneCosmeticMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        JSON_MODELS.clear();
        JSON_MODELS.putAll(identifierGeneCosmeticMap);
    }
}
