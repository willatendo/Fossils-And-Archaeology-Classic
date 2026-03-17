package ca.willatendo.fossilsclassic.server.item;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public final class FCEquipmentAssets {
    public static final ResourceKey<EquipmentAsset> ANCIENT = FCEquipmentAssets.register("ancient");
    public static final ResourceKey<EquipmentAsset> SCARAB_GEM = FCEquipmentAssets.register("scarab_gem");

    private static ResourceKey<EquipmentAsset> register(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, FCCoreUtils.resource(name));
    }
}
