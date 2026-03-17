package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public final class FCEntityTypeTags {
    public static final TagKey<EntityType<?>> EGGS = FCEntityTypeTags.create("eggs");
    public static final TagKey<EntityType<?>> JAVELINS = FCEntityTypeTags.create("javelins");
    public static final TagKey<EntityType<?>> MAMMALS = FCEntityTypeTags.create("mammals");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, FCCoreUtils.resource(name));
    }
}
