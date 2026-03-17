package ca.willatendo.fossilsclassic.server.item.items;

import net.minecraft.world.item.Item;

public class IncubatedEggItem extends Item {
    private final int eggType;

    public IncubatedEggItem(int eggType, Item.Properties properties) {
        super(properties);
        this.eggType = eggType;
    }
}
