package ca.willatendo.fossilsclassic.client;

import ca.willatendo.fossilsclassic.client.event.FossilsClassicClientEventListener;
import ca.willatendo.simplelibrary.client.ClientModInit;

public final class FossilsClassicClient {
    public static void clientModInit(ClientModInit clientModInit) {
        clientModInit.clientEventListener(FossilsClassicClientEventListener.INSTANCE);
    }
}
