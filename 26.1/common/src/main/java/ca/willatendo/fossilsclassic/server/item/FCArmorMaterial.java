package ca.willatendo.fossilsclassic.server.item;

import ca.willatendo.fossilsclassic.server.tags.FCItemTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

public final class FCArmorMaterial {
    public static final ArmorMaterial ANCIENT = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
        map.put(ArmorType.BODY, 5);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, FCItemTags.REPAIRS_ANCIENT_ARMOR, FCEquipmentAssets.ANCIENT);
}
