package ca.willatendo.fossilsclassic.client;

import ca.willatendo.simplelibrary.client.FabricClientModInit;
import net.fabricmc.api.ClientModInitializer;

public final class FossilsClassicFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FossilsClassicClient.clientModInit(new FabricClientModInit());
    }
}
