package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.entities.Egg;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class EggItem extends PlaceEntityItem<Egg> {
    public EggItem(Supplier<EntityType<Egg>> entityType, Item.Properties properties) {
        super(entityType, properties);
    }
}
