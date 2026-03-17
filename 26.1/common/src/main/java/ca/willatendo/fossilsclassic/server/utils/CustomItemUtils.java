package ca.willatendo.fossilsclassic.server.utils;

import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.item.FCDataComponents;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.simplelibrary.core.utils.CoreUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class CustomItemUtils {
    private CustomItemUtils() {
    }

    public static ItemStack getSpawnEgg(Holder<Chromosome> chromosome) {
        String[] data = chromosome.getRegisteredName().split(":");
        String modId = data[0];
        String itemName = data[1] + "_spawn_egg";
        Identifier identifier = CoreUtils.resource(modId, itemName);
        ItemStack spawnEggItemStack = new ItemStack(FCItems.CUSTOM_SPAWN_EGG.get());
        //spawnEggItemStack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(FCEntityTypes.CUSTOM.get(), new CompoundTag()));
        spawnEggItemStack.set(FCDataComponents.CHROMOSOME.get(), chromosome);
        spawnEggItemStack.set(DataComponents.ITEM_MODEL, identifier);
        spawnEggItemStack.set(DataComponents.ITEM_NAME, Component.translatable("item." + modId + "." + itemName));
        return spawnEggItemStack;
    }
}
