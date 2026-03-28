package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.ValueMaps;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public interface Diet {
    default int foodFromKill(EntityType<?> entityType) {
        return 0;
    }

    boolean isFood(ItemStack itemStack);

    int getFoodValue(ItemStack itemStack);

    static Diet herbivore() {
        return new Diet() {
            @Override
            public boolean isFood(ItemStack itemStack) {
                return ValueMaps.isFeederFood(itemStack.typeHolder(), false);
            }

            @Override
            public int getFoodValue(ItemStack itemStack) {
                return ValueMaps.getFeederFoodValue(itemStack.typeHolder(), false);
            }
        };
    }

    static Diet carnivore() {
        return new Diet() {
            @Override
            public int foodFromKill(EntityType<?> entityType) {
                return ValueMaps.getMobFoodValue(entityType.builtInRegistryHolder());
            }

            @Override
            public boolean isFood(ItemStack itemStack) {
                return ValueMaps.isFeederFood(itemStack.typeHolder(), true);
            }

            @Override
            public int getFoodValue(ItemStack itemStack) {
                return ValueMaps.getFeederFoodValue(itemStack.typeHolder(), true);
            }
        };
    }
}
