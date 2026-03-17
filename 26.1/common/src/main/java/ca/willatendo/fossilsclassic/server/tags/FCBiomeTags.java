package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class FCBiomeTags {
    public static final TagKey<Biome> HAS_ACADEMY = FCBiomeTags.create("has_structure/academy");
    public static final TagKey<Biome> HAS_CHINESE_WARSHIP = FCBiomeTags.create("has_structure/chinese_warship");
    public static final TagKey<Biome> HAS_PIRATE_SHIPWRECK = FCBiomeTags.create("has_structure/pirate_shipwreck");
    public static final TagKey<Biome> HAS_VIKING_SHIP = FCBiomeTags.create("has_structure/viking_ship");
    public static final TagKey<Biome> HAS_WEAPON_SHOP = FCBiomeTags.create("has_structure/weapon_shop");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, FCCoreUtils.resource(name));
    }
}
