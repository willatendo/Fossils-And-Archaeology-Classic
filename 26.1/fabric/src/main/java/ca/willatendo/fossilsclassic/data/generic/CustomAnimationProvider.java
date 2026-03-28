package ca.willatendo.fossilsclassic.data.generic;

import ca.willatendo.fossilsclassic.client.json_animation.CustomAnimation;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import com.google.common.collect.Maps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class CustomAnimationProvider implements DataProvider {
    private final Map<Identifier, CustomAnimation> jsonModels = Maps.newHashMap();
    private final PackOutput.PathProvider pathProvider;
    private final String modId;

    public CustomAnimationProvider(PackOutput packOutput, String modId) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, FCCoreUtils.ID + "/custom_animations");
        this.modId = modId;
    }

    protected abstract void addAll();

    public <T extends CustomAnimation> void add(String identifier, T customAnimation) {
        this.jsonModels.put(FCCoreUtils.resource(identifier), customAnimation);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.jsonModels.clear();
        this.addAll();
        return DataProvider.saveAll(cachedOutput, CustomAnimation.CODEC, this.pathProvider, this.jsonModels);
    }

    @Override
    public String getName() {
        return "Custom Animations: " + this.modId;
    }
}
