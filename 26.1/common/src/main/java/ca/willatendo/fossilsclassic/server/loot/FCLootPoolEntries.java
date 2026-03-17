package ca.willatendo.fossilsclassic.server.loot;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.loot.entries.LootRandomItem;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;

public final class FCLootPoolEntries {
    public static final SimpleRegistry<LootPoolEntryType> LOOT_POOL_ENTRY_TYPES = new SimpleRegistry<>(Registries.LOOT_POOL_ENTRY_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<LootPoolEntryType> LOOT_RANDOM_ITEM = LOOT_POOL_ENTRY_TYPES.register("loot_random_item", () -> new LootPoolEntryType(LootRandomItem.CODEC));
}
