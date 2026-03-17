package ca.willatendo.fossilsclassic;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.server.NeoforgeModInit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FCCoreUtils.ID)
public final class FossilsClassicNeoforgeMod {
    public FossilsClassicNeoforgeMod(IEventBus iEventBus) {
        NeoforgeModInit neoforgeModInit = new NeoforgeModInit(iEventBus);
        FossilsClassicMod.modInit(neoforgeModInit);
    }
}
