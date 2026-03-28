package ca.willatendo.fossilsclassic.server.mob_food;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public final class FCMobFoodValues {
    private static ResourceKey<MobFoodValue> create(String name) {
        return ResourceKey.create(FCRegistries.MOB_FOOD_VALUE, FCCoreUtils.resource(name));
    }

    private static void register(BootstrapContext<MobFoodValue> bootstrapContext, EntityType<?> entityType, int value) {
        bootstrapContext.register(FCMobFoodValues.create(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath()), new MobFoodValue(entityType, value));
    }

    public static void bootstrap(BootstrapContext<MobFoodValue> bootstrapContext) {
        FCMobFoodValues.register(bootstrapContext, EntityType.PIG, 30);
        FCMobFoodValues.register(bootstrapContext, EntityType.SHEEP, 35);
        FCMobFoodValues.register(bootstrapContext, EntityType.COW, 50);
        FCMobFoodValues.register(bootstrapContext, EntityType.CHICKEN, 20);
    }
}
