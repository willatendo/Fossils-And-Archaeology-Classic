package ca.willatendo.fossilsclassic.data.generic;

import ca.willatendo.fossilsclassic.client.fossil_render.FossilRender;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import com.google.common.collect.Maps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class FossilRenderProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final String modId;

    public FossilRenderProvider(PackOutput packOutput, String modId) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, FCCoreUtils.ID + "/fossil_renders");
        this.modId = modId;
    }

    protected abstract void add(BiConsumer<Identifier, FossilRender> biConsumer);

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<Identifier, FossilRender> map = Maps.newHashMap();
        this.add((identifier, fossilRender) -> {
            if (map.putIfAbsent(identifier, fossilRender) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + identifier);
            }
        });
        return DataProvider.saveAll(cachedOutput, FossilRender.CODEC, this.pathProvider::json, map);
    }

    @Override
    public String getName() {
        return "Fossil Render: " + this.modId;
    }
}
