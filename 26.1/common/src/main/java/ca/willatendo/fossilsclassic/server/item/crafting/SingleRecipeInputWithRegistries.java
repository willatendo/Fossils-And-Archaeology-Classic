package ca.willatendo.fossilsclassic.server.item.crafting;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record SingleRecipeInputWithRegistries(ItemStack itemStack, RegistryAccess registryAccess) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No item for index " + index);
        } else {
            return this.itemStack();
        }
    }

    @Override
    public int size() {
        return 0;
    }
}
