package ca.willatendo.fossilsclassic.server.mob_food;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public record MobFoodValue(Holder<EntityType<?>> food, int value) {
    public static final Codec<MobFoodValue> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(BuiltInRegistries.ENTITY_TYPE.holderByNameCodec().fieldOf("food").forGetter(MobFoodValue::food), Codec.INT.fieldOf("value").forGetter(MobFoodValue::value)).apply(instance, MobFoodValue::new));

    public MobFoodValue(EntityType<?> entityType, int value) {
        this(entityType.builtInRegistryHolder(), value);
    }
}
