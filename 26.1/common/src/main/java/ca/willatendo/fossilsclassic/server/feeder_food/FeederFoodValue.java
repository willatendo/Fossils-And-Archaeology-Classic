package ca.willatendo.fossilsclassic.server.feeder_food;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public record FeederFoodValue(Holder<Item> feederFood, int value, boolean meat) {
    public static final Codec<FeederFoodValue> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(Item.CODEC.fieldOf("feeder_food").forGetter(FeederFoodValue::feederFood), Codec.INT.fieldOf("value").forGetter(FeederFoodValue::value), Codec.BOOL.fieldOf("meat").forGetter(FeederFoodValue::meat)).apply(instance, FeederFoodValue::new));

    public FeederFoodValue(Item feederFood, int value, boolean meat) {
        this(feederFood.builtInRegistryHolder(), value, meat);
    }
}
