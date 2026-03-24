package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class FCItemTags {
    public static final TagKey<Item> ANCIENT_TOOL_MATERIALS = FCItemTags.create("ancient_tool_materials");
    public static final TagKey<Item> DAGGER_TOOL_MATERIALS = FCItemTags.create("dagger_tool_materials");
    public static final TagKey<Item> DRUM_INSTRUMENT = FCItemTags.create("drum_instrument");
    public static final TagKey<Item> HERBIVORE_FOOD = FCItemTags.create("herbivore_food");
    public static final TagKey<Item> ICED_MEAT_TOOL_MATERIALS = FCItemTags.create("iced_meat_tool_materials");
    public static final TagKey<Item> PIGLIN_TAMING_ARMOR = FCItemTags.create("piglin_taming_armor");
    public static final TagKey<Item> REPAIRS_ANCIENT_ARMOR = FCItemTags.create("repairs_ancient_armor");
    public static final TagKey<Item> SCARAB_GEM_TOOL_MATERIALS = FCItemTags.create("scarab_gem_tool_materials");
    public static final TagKey<Item> STEGOSAURUS_COMMANDABLES = FCItemTags.create("commandables/stegosaurus");
    public static final TagKey<Item> TRICERATOPS_COMMANDABLES = FCItemTags.create("commandables/triceratops");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, FCCoreUtils.resource(name));
    }
}
