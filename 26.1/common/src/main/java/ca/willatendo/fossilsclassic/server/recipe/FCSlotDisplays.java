package ca.willatendo.fossilsclassic.server.recipe;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.recipe.display.ItemStacksSlotDisplay;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public final class FCSlotDisplays {
    public static final SimpleRegistry<SlotDisplay.Type<?>> SLOT_DISPLAY_TYPES = new SimpleRegistry<>(Registries.SLOT_DISPLAY, FCCoreUtils.ID);

    public static final SimpleHolder<SlotDisplay.Type<ItemStacksSlotDisplay>> ITEM_STACKS = SLOT_DISPLAY_TYPES.register("item_stacks", () -> ItemStacksSlotDisplay.TYPE);
}
