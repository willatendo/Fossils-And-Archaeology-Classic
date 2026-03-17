package ca.willatendo.fossilsclassic.server.archaeology_value;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class FCArchaeologyValues {
    public static final ResourceKey<ArchaeologyValue> RELIC_SCRAP = FCArchaeologyValues.create("relic_scrap");

    private static ResourceKey<ArchaeologyValue> create(String name) {
        return ResourceKey.create(FCRegistries.ARCHAEOLOGY_VALUE, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<ArchaeologyValue> bootstrapContext) {
        bootstrapContext.register(RELIC_SCRAP, new ArchaeologyValue(FCItems.RELIC_SCRAP.get(), 300));
    }
}
