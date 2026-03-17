package ca.willatendo.fossilsclassic.server.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class FuelSlot extends Slot {
    private final Function<ItemStack, Boolean> mayPlace;

    public FuelSlot(Container container, int slot, int x, int y, Function<ItemStack, Boolean> mayPlace) {
        super(container, slot, x, y);
        this.mayPlace = mayPlace;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return this.mayPlace.apply(itemStack);
    }
}
