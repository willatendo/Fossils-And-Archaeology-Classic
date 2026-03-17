package ca.willatendo.fossilsclassic.server.archaeology_value;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public record ArchaeologyValue(Holder<Item> archaeology, int value) {
    public static final Codec<ArchaeologyValue> CODEC = RecordCodecBuilder.create(instance -> instance.group(Item.CODEC.fieldOf("archaeology").forGetter(ArchaeologyValue::archaeology), Codec.INT.fieldOf("value").forGetter(ArchaeologyValue::value)).apply(instance, ArchaeologyValue::new));

    public ArchaeologyValue(Item archaeology, int value) {
        this(archaeology.builtInRegistryHolder(), value);
    }
}
