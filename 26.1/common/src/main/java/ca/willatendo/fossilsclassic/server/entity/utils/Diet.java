package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.ValueMaps;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public interface Diet {
    boolean gainsFoodFromKill();

    default int foodFromKill(EntityType<?> entityType) {
        return 0;
    }

    boolean isFood(ItemStack itemStack);

    int getFoodValue(ItemStack itemStack);

    static Diet herbivore() {
        return new Diet() {
            @Override
            public boolean gainsFoodFromKill() {
                return false;
            }

            @Override
            public boolean isFood(ItemStack itemStack) {
                return ValueMaps.isFeederFood(itemStack.getItemHolder(), false);
            }

            @Override
            public int getFoodValue(ItemStack itemStack) {
                return 0;
            }
        };
    }
}
