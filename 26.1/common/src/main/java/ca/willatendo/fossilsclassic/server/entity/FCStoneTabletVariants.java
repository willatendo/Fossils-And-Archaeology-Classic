package ca.willatendo.fossilsclassic.server.entity;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public final class FCStoneTabletVariants {
    public static final ResourceKey<StoneTabletVariant> LIGHTING = FCStoneTabletVariants.create("lighting");
    public static final ResourceKey<StoneTabletVariant> SOCIAL = FCStoneTabletVariants.create("social");
    public static final ResourceKey<StoneTabletVariant> GREAT_WAR = FCStoneTabletVariants.create("great_war");
    public static final ResourceKey<StoneTabletVariant> ANU_DEATH = FCStoneTabletVariants.create("anu_death");
    public static final ResourceKey<StoneTabletVariant> PORTAL = FCStoneTabletVariants.create("portal");
    public static final ResourceKey<StoneTabletVariant> HEROBRINE = FCStoneTabletVariants.create("herobrine");
    public static final ResourceKey<StoneTabletVariant> SKELETON_AND_CREEPER = FCStoneTabletVariants.create("skeleton_and_creeper");
    public static final ResourceKey<StoneTabletVariant> ZOMBIE_AND_SPIDER = FCStoneTabletVariants.create("zombie_and_spider");
    public static final ResourceKey<StoneTabletVariant> TYRANNOSAURUS_IN_ICEBERG = FCStoneTabletVariants.create("tyrannosaurus_in_iceberg");
    public static final ResourceKey<StoneTabletVariant> TYRANNOSAURUS_TRANSPORT = FCStoneTabletVariants.create("tyrannosaurus_transport");
    public static final ResourceKey<StoneTabletVariant> TYRANNOSAURUS_MELT = FCStoneTabletVariants.create("tyrannosaurus_melt");
    public static final ResourceKey<StoneTabletVariant> TYRANNOSAURUS_ATTACK = FCStoneTabletVariants.create("tyrannosaurus_attack");
    public static final ResourceKey<StoneTabletVariant> TYRANNOSAURUS_DEATH = FCStoneTabletVariants.create("tyrannosaurus_death");
    public static final ResourceKey<StoneTabletVariant> TYRANNOSAURUS_CORPSE = FCStoneTabletVariants.create("tyrannosaurus_corpse");
    public static final ResourceKey<StoneTabletVariant> PRINCESS = FCStoneTabletVariants.create("princess");
    public static final ResourceKey<StoneTabletVariant> MOSASAURUS = FCStoneTabletVariants.create("mosasaurus");
    public static final ResourceKey<StoneTabletVariant> HOLY_MOSASAURUS = FCStoneTabletVariants.create("holy_mosasaurus");
    public static final ResourceKey<StoneTabletVariant> PAST = FCStoneTabletVariants.create("past");
    public static final ResourceKey<StoneTabletVariant> TIME_MACHINE = FCStoneTabletVariants.create("time_machine");
    public static final ResourceKey<StoneTabletVariant> FUTURE = FCStoneTabletVariants.create("future");

    private static ResourceKey<StoneTabletVariant> create(String name) {
        return ResourceKey.create(FCRegistries.STONE_TABLET_VARIANT, FCCoreUtils.resource(name));
    }

    private static void register(BootstrapContext<StoneTabletVariant> bootstrapContext, ResourceKey<StoneTabletVariant> resourceKey, int width, int height) {
        bootstrapContext.register(resourceKey, new StoneTabletVariant(width, height, resourceKey.identifier(), Optional.of(Component.translatable(resourceKey.identifier().toLanguageKey("stone_tablet", "title")).withStyle(ChatFormatting.YELLOW)), Optional.of(Component.translatable(resourceKey.identifier().toLanguageKey("stone_tablet", "author")).withStyle(ChatFormatting.GRAY))));
    }

    public static void bootstrap(BootstrapContext<StoneTabletVariant> bootstrapContext) {
        FCStoneTabletVariants.register(bootstrapContext, LIGHTING, 2, 1);
        FCStoneTabletVariants.register(bootstrapContext, SOCIAL, 1, 1);
        FCStoneTabletVariants.register(bootstrapContext, GREAT_WAR, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, ANU_DEATH, 2, 1);
        FCStoneTabletVariants.register(bootstrapContext, PORTAL, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, HEROBRINE, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, SKELETON_AND_CREEPER, 1, 1);
        FCStoneTabletVariants.register(bootstrapContext, ZOMBIE_AND_SPIDER, 1, 1);
        FCStoneTabletVariants.register(bootstrapContext, TYRANNOSAURUS_IN_ICEBERG, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, TYRANNOSAURUS_TRANSPORT, 2, 1);
        FCStoneTabletVariants.register(bootstrapContext, TYRANNOSAURUS_MELT, 2, 1);
        FCStoneTabletVariants.register(bootstrapContext, TYRANNOSAURUS_ATTACK, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, TYRANNOSAURUS_DEATH, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, TYRANNOSAURUS_CORPSE, 4, 2);
        FCStoneTabletVariants.register(bootstrapContext, PRINCESS, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, MOSASAURUS, 2, 1);
        FCStoneTabletVariants.register(bootstrapContext, HOLY_MOSASAURUS, 4, 2);
        FCStoneTabletVariants.register(bootstrapContext, PAST, 2, 2);
        FCStoneTabletVariants.register(bootstrapContext, TIME_MACHINE, 1, 2);
        FCStoneTabletVariants.register(bootstrapContext, FUTURE, 2, 2);
    }
}
