package ca.willatendo.fossilsclassic.server.loot;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public final class FCChestLootTables {
    public static final ResourceKey<LootTable> ACADEMY_DISC = FCChestLootTables.create("academy_disc");
    public static final ResourceKey<LootTable> ACADEMY_LOOT = FCChestLootTables.create("academy_loot");
    public static final ResourceKey<LootTable> WEAPON_SHOP_DECOY = FCChestLootTables.create("weapon_shop_decoy");
    public static final ResourceKey<LootTable> WEAPON_SHOP_LOOT = FCChestLootTables.create("weapon_shop_loot");

    private static ResourceKey<LootTable> create(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, FCCoreUtils.resource("chests/" + name));
    }
}
