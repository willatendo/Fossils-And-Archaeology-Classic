package ca.willatendo.fossilsclassic;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.client.RecipeBookManager;
import ca.willatendo.simplelibrary.server.FabricModInit;
import ca.willatendo.simplelibrary.server.event.FeatureFlagRegistryEvent;
import net.fabricmc.api.ModInitializer;

public final class FossilsClassicFabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricModInit fabricModInit = new FabricModInit(FCCoreUtils.ID);
        FossilsClassicMod.modInit(fabricModInit);
        RecipeBookManager.init();

        FeatureFlagRegistryEvent.EVENT.register(builder -> builder.create(FCCoreUtils.resource("custom_dinosaurus")));
    }
}
