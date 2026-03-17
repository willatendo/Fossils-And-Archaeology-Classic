package ca.willatendo.fossilsclassic.server.entity;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class FCAttributes {
    public static final SimpleRegistry<Attribute> ATTRIBUTES = new SimpleRegistry<>(Registries.ATTRIBUTE, FCCoreUtils.ID);

    public static final SimpleHolder<Attribute> MAX_HUNGER = ATTRIBUTES.register("max_hunger", () -> new RangedAttribute("attribute.name.max_hunger", 20.0F, 0.0F, 1024.0F).setSyncable(true));
}
