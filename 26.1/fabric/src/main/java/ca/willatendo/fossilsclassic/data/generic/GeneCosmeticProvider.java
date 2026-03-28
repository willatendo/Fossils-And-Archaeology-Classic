package ca.willatendo.fossilsclassic.data.generic;

import ca.willatendo.fossilsclassic.client.gene_cosmetic.GeneCosmetic;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import com.google.common.collect.Maps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class GeneCosmeticProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final String modId;

    public GeneCosmeticProvider(PackOutput packOutput, String modId) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, FCCoreUtils.ID + "/gene_cosmetics");
        this.modId = modId;
    }

    protected abstract void add(BiConsumer<Identifier, GeneCosmetic> biConsumer);

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<Identifier, GeneCosmetic> map = Maps.newHashMap();
        this.add((identifier, geneCosmetic) -> {
            if (map.putIfAbsent(identifier, geneCosmetic) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + identifier);
            }
        });
        return DataProvider.saveAll(cachedOutput, GeneCosmetic.CODEC, this.pathProvider::json, map);
    }

    @Override
    public String getName() {
        return "Gene Cosmetic: " + this.modId;
    }
}
