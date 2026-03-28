package ca.willatendo.fossilsclassic.server.loot;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.loot.entries.LootRandomItem;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public final class FCLootPoolEntries {
    public static final SimpleRegistry<MapCodec<? extends LootPoolEntryContainer>> LOOT_POOL_ENTRY_TYPES = new SimpleRegistry<>(Registries.LOOT_POOL_ENTRY_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<MapCodec<LootRandomItem>> LOOT_RANDOM_ITEM = LOOT_POOL_ENTRY_TYPES.register("loot_random_item", () -> LootRandomItem.MAP_CODEC);
}
