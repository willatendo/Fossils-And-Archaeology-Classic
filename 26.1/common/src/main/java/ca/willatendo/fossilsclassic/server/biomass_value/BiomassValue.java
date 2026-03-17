package ca.willatendo.fossilsclassic.server.biomass_value;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public record BiomassValue(Holder<Item> biomass, int value) {
    public static final Codec<BiomassValue> CODEC = RecordCodecBuilder.create(instance -> instance.group(Item.CODEC.fieldOf("biomass").forGetter(BiomassValue::biomass), Codec.INT.fieldOf("value").forGetter(BiomassValue::value)).apply(instance, BiomassValue::new));

    public BiomassValue(Item biomass, int value) {
        this(biomass.builtInRegistryHolder(), value);
    }
}
