package ca.willatendo.fossilsclassic.server.feeder_food;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class FCFeederFoodValues {
    private static ResourceKey<FeederFoodValue> create(String name) {
        return ResourceKey.create(FCRegistries.FEEDER_FOOD_VALUE, FCCoreUtils.resource(name));
    }

    private static void register(BootstrapContext<FeederFoodValue> bootstrapContext, Item item, int value, boolean meat) {
        bootstrapContext.register(FCFeederFoodValues.create(BuiltInRegistries.ITEM.getKey(item).getPath()), new FeederFoodValue(item, value, meat));
    }

    public static void bootstrap(BootstrapContext<FeederFoodValue> bootstrapContext) {
        FCFeederFoodValues.register(bootstrapContext, Items.APPLE, 100, false);
    }
}
