package ca.willatendo.fossilsclassic.server;

import ca.willatendo.fossilsclassic.server.archaeology_value.ArchaeologyValue;
import ca.willatendo.fossilsclassic.server.biomass_value.BiomassValue;
import ca.willatendo.fossilsclassic.server.feeder_food.FeederFoodValue;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.simplelibrary.core.utils.CoreUtils;
import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.function.Function;

public final class ValueMaps {
    public static final Map<Holder<Item>, Integer> ARCHAEOLOGY_VALUES = Maps.newHashMap();
    public static final Map<Holder<Item>, Integer> BIOMASS_VALUES = Maps.newHashMap();
    public static final Map<Holder<Item>, Integer> PLANT_FEEDER_FOOD_VALUES = Maps.newHashMap();
    public static final Map<Holder<Item>, Integer> MEAT_FEEDER_FOOD_VALUES = Maps.newHashMap();

    private ValueMaps() {
    }

    public static void fillMaps(RegistryAccess registryAccess) {
        ValueMaps.fillMap(ARCHAEOLOGY_VALUES, registryAccess.lookupOrThrow(FCRegistries.ARCHAEOLOGY_VALUE), ArchaeologyValue::archaeology, ArchaeologyValue::value);
        ValueMaps.fillMap(BIOMASS_VALUES, registryAccess.lookupOrThrow(FCRegistries.BIOMASS_VALUE), BiomassValue::biomass, BiomassValue::value);
        ValueMaps.fillMaps(MEAT_FEEDER_FOOD_VALUES, PLANT_FEEDER_FOOD_VALUES, registryAccess.lookupOrThrow(FCRegistries.FEEDER_FOOD_VALUE), FeederFoodValue::meat, FeederFoodValue::feederFood, FeederFoodValue::value);
    }

    private static <A, B, C> void fillMaps(Map<A, B> map1, Map<A, B> map2, Registry<C> registry, Function<C, Boolean> condition, Function<C, A> consumerA, Function<C, B> consumerB) {
        map1.clear();
        map2.clear();
        registry.forEach(c -> {
            if (condition.apply(c)) {
                map1.put(consumerA.apply(c), consumerB.apply(c));
            } else {
                map2.put(consumerA.apply(c), consumerB.apply(c));
            }
        });
        CoreUtils.LOGGER.info("{}", map1);
        CoreUtils.LOGGER.info("{}", map2);
    }

    private static <A, B, C> void fillMap(Map<A, B> map, Registry<C> registry, Function<C, A> consumerA, Function<C, B> consumerB) {
        map.clear();
        registry.forEach(c -> map.put(consumerA.apply(c), consumerB.apply(c)));
        CoreUtils.LOGGER.info("{}", map);
    }

    public static int getArchaeologyValue(Holder<Item> itemHolder) {
        return ARCHAEOLOGY_VALUES.getOrDefault(itemHolder, 0);
    }

    public static int getBiomassValue(Holder<Item> itemHolder) {
        return ARCHAEOLOGY_VALUES.getOrDefault(itemHolder, 0);
    }

    public static boolean isFeederFood(Holder<Item> itemHolder, boolean meat) {
        if (meat) {
            return MEAT_FEEDER_FOOD_VALUES.containsKey(itemHolder);
        }
        return PLANT_FEEDER_FOOD_VALUES.containsKey(itemHolder);
    }

    public static int getFeederFoodValue(Holder<Item> itemHolder, boolean meat) {
        if (meat) {
            return MEAT_FEEDER_FOOD_VALUES.getOrDefault(itemHolder, 0);
        }
        return PLANT_FEEDER_FOOD_VALUES.getOrDefault(itemHolder, 0);
    }
}
