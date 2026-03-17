package ca.willatendo.fossilsclassic.client;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.client.NeoforgeClientModInit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = FCCoreUtils.ID, dist = Dist.CLIENT)
public class FossilsClassicNeoforgeClient {
    public FossilsClassicNeoforgeClient(IEventBus iEventBus) {
        FossilsClassicClient.clientModInit(new NeoforgeClientModInit(iEventBus));
    }
}
