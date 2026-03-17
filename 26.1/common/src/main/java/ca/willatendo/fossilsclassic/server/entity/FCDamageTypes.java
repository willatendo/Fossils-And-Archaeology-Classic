package ca.willatendo.fossilsclassic.server.entity;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public final class FCDamageTypes {
    public static final ResourceKey<DamageType> ANIMAL_STARVE = FCDamageTypes.create("animal_starve");
    public static final ResourceKey<DamageType> JAVELIN = FCDamageTypes.create("javelin");

    private static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<DamageType> bootstrapContext) {
        bootstrapContext.register(ANIMAL_STARVE, new DamageType("animal_starve", 0.1F));
        bootstrapContext.register(JAVELIN, new DamageType("javelin", 0.1F));
    }
}
