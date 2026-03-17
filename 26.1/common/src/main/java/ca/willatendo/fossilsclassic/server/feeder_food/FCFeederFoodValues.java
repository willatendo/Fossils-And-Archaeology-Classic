package ca.willatendo.fossilsclassic.server.feeder_food;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;

public class FCFeederFoodValues {
    public static final ResourceKey<FeederFoodValue> APPLE = FCFeederFoodValues.create("apple");

    private static ResourceKey<FeederFoodValue> create(String name) {
        return ResourceKey.create(FCRegistries.FEEDER_FOOD_VALUE, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<FeederFoodValue> bootstrapContext) {
        bootstrapContext.register(APPLE, new FeederFoodValue(Items.APPLE, 100, false));
    }
}
