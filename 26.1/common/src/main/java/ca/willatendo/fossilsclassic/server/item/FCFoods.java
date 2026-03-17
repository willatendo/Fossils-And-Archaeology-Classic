package ca.willatendo.fossilsclassic.server.item;

import net.minecraft.world.food.FoodProperties;

public final class FCFoods {
    public static final FoodProperties RAW_DINOSAUR_MEAT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build();
    public static final FoodProperties COOKED_DINOSAUR_MEAT = new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build();
    public static final FoodProperties SIO_CHIU_LE = new FoodProperties.Builder().nutrition(8).saturationModifier(2.0F).build();
    public static final FoodProperties RAW_CHICKEN_SOUP = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build(); // .effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F)
    public static final FoodProperties COOKED_CHICKEN_SOUP = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    public static final FoodProperties CHICKEN_ESSENCE = new FoodProperties.Builder().nutrition(10).saturationModifier(0.0F).build();
}
