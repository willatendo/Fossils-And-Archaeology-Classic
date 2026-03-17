package ca.willatendo.fossilsclassic.server.gene;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.*;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;

public final class FCJsonBehaviorTypes {
    public static final SimpleRegistry<JsonBehaviorType<?>> JSON_BEHAVIOR_TYPES = new SimpleRegistry<>(FCRegistries.JSON_BEHAVIOR_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<JsonBehaviorType<BreedJsonBehavior>> BREED = JSON_BEHAVIOR_TYPES.register("breed", () -> new JsonBehaviorType<>(BreedJsonBehavior.CODEC, BreedJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<FloatJsonBehavior>> FLOAT = JSON_BEHAVIOR_TYPES.register("float", () -> new JsonBehaviorType<>(FloatJsonBehavior.CODEC, FloatJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<FollowParentJsonBehavior>> FOLLOW_PARENT = JSON_BEHAVIOR_TYPES.register("follow_parent", () -> new JsonBehaviorType<>(FollowParentJsonBehavior.CODEC, FollowParentJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<LookAtPlayerJsonBehavior>> LOOK_AT_PLAYER = JSON_BEHAVIOR_TYPES.register("look_at_player", () -> new JsonBehaviorType<>(LookAtPlayerJsonBehavior.CODEC, LookAtPlayerJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<MeleeAttackJsonBehavior>> MELEE_ATTACK = JSON_BEHAVIOR_TYPES.register("melee_attack", () -> new JsonBehaviorType<>(MeleeAttackJsonBehavior.CODEC, MeleeAttackJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<NearestAttackableTargetJsonBehavior>> NEAREST_ATTACKABLE_TARGET = JSON_BEHAVIOR_TYPES.register("nearest_attackable_target", () -> new JsonBehaviorType<>(NearestAttackableTargetJsonBehavior.CODEC, NearestAttackableTargetJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<PanicJsonBehavior>> PANIC = JSON_BEHAVIOR_TYPES.register("panic", () -> new JsonBehaviorType<>(PanicJsonBehavior.CODEC, PanicJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<RandomLookAroundJsonBehavior>> RANDOM_LOOK_AROUND = JSON_BEHAVIOR_TYPES.register("random_look_around", () -> new JsonBehaviorType<>(RandomLookAroundJsonBehavior.CODEC, RandomLookAroundJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<TemptJsonBehavior>> TEMPT = JSON_BEHAVIOR_TYPES.register("tempt", () -> new JsonBehaviorType<>(TemptJsonBehavior.CODEC, TemptJsonBehavior.STREAM_CODEC));
    public static final SimpleHolder<JsonBehaviorType<WaterAvoidingRandomStrollJsonBehavior>> WATER_AVOIDING_RANDOM_STROLL = JSON_BEHAVIOR_TYPES.register("water_avoiding_random_stroll", () -> new JsonBehaviorType<>(WaterAvoidingRandomStrollJsonBehavior.CODEC, WaterAvoidingRandomStrollJsonBehavior.STREAM_CODEC));
}
