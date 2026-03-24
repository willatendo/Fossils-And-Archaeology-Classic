package ca.willatendo.fossilsclassic.server.game_rules;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;

import java.util.function.ToIntFunction;

public final class FCGameRules {
    public static final SimpleRegistry<GameRule<?>> GAME_RULES = new SimpleRegistry<>(Registries.GAME_RULE, FCCoreUtils.ID);

    public static final SimpleHolder<GameRule<Integer>> NOTIFICATION_DISTANCE = FCGameRules.registerInteger("notification_distance", GameRuleCategory.CHAT, 30, 0, 200);
    public static final SimpleHolder<GameRule<Integer>> LOW_FEEDER_MESSAGE_QUORUM = FCGameRules.registerInteger("low_feeder_message_quorum", GameRuleCategory.CHAT, 100, 0, 200);
    public static final SimpleHolder<GameRule<Boolean>> DO_LIMIT_NOTIFICATION_DISTANCE = FCGameRules.registerBoolean("do_limit_notification_distance", GameRuleCategory.CHAT, true);
    public static final SimpleHolder<GameRule<Boolean>> DO_UNTAME_ANIMAL_MESSAGES = FCGameRules.registerBoolean("do_untame_animal_messages", GameRuleCategory.CHAT, true);
    public static final SimpleHolder<GameRule<Boolean>> DO_ANIMAL_GROWTH = FCGameRules.registerBoolean("do_animal_growth", GameRuleCategory.MOBS, true);
    public static final SimpleHolder<GameRule<Boolean>> DO_ANIMAL_HUNGER = FCGameRules.registerBoolean("do_animal_hunger", GameRuleCategory.MOBS, true);

    public static SimpleHolder<GameRule<Integer>> registerInteger(String name, GameRuleCategory gameRuleCategory, int defaultValue, int min) {
        return FCGameRules.registerInteger(name, gameRuleCategory, defaultValue, min, Integer.MAX_VALUE, FeatureFlagSet.of());
    }

    public static SimpleHolder<GameRule<Integer>> registerInteger(String name, GameRuleCategory gameRuleCategory, int defaultValue, int min, int max) {
        return FCGameRules.registerInteger(name, gameRuleCategory, defaultValue, min, max, FeatureFlagSet.of());
    }

    public static SimpleHolder<GameRule<Integer>> registerInteger(String name, GameRuleCategory gameRuleCategory, int defaultValue, int min, int max, FeatureFlagSet featureFlagSet) {
        return FCGameRules.register(name, gameRuleCategory, GameRuleType.INT, IntegerArgumentType.integer(min, max), Codec.intRange(min, max), defaultValue, featureFlagSet, GameRuleTypeVisitor::visitInteger, i -> i);
    }

    private static SimpleHolder<GameRule<Boolean>> registerBoolean(String name, GameRuleCategory gameRuleCategory, boolean defaultValue) {
        return FCGameRules.register(name, gameRuleCategory, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, defaultValue, FeatureFlagSet.of(), GameRuleTypeVisitor::visitBoolean, p_460985_ -> p_460985_ ? 1 : 0);
    }

    public static <T> SimpleHolder<GameRule<T>> register(String name, GameRuleCategory gameRuleCategory, GameRuleType gameRuleType, ArgumentType<T> argumentType, Codec<T> codec, T defaultValue, FeatureFlagSet featureFlagSet, GameRules.VisitorCaller<T> visitorCaller, ToIntFunction<T> commandResultFunction) {
        return GAME_RULES.register(name, () -> new GameRule<>(gameRuleCategory, gameRuleType, argumentType, visitorCaller, codec, commandResultFunction, defaultValue, featureFlagSet));
    }
}
