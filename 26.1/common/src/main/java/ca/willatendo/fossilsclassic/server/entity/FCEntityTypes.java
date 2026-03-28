package ca.willatendo.fossilsclassic.server.entity;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.entities.*;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.sub.EntityTypeSubRegistry;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public final class FCEntityTypes {
    public static final EntityTypeSubRegistry ENTITY_TYPES = new EntityTypeSubRegistry(FCCoreUtils.ID);

    public static final SimpleHolder<EntityType<Bones>> BONES = ENTITY_TYPES.register("bones", ServerUtils.simpleEntityType(Bones::new, MobCategory.MONSTER, 0.6F, 1.99F).eyeHeight(1.74F).ridingOffset(-0.7F).clientTrackingRange(8).notInPeaceful());
    public static final SimpleHolder<EntityType<Failuresaurs>> FAILURESAURUS = ENTITY_TYPES.register("failuresaurus", ServerUtils.simpleEntityType(Failuresaurs::new, MobCategory.MONSTER, 1.0F, 0.8F).clientTrackingRange(8).notInPeaceful());

    public static final SimpleHolder<EntityType<AncientLightningBolt>> ANCIENT_LIGHTNING_BOLT = ENTITY_TYPES.register("ancient_lightning_bolt", ServerUtils.simpleEntityType(AncientLightningBolt::new, MobCategory.MISC, 0.0F, 0.0F).noSave().noLootTable());

    public static final SimpleHolder<EntityType<Smilodon>> SMILODON = ENTITY_TYPES.register("smilodon", ServerUtils.simpleEntityType(Smilodon::new, MobCategory.CREATURE, 0.75F, 0.75F));
    public static final SimpleHolder<EntityType<Stegosaurus>> STEGOSAURUS = ENTITY_TYPES.register("stegosaurus", ServerUtils.simpleEntityType(Stegosaurus::new, MobCategory.CREATURE, 1.2F, 1.2F).eyeHeight(0.3F));
    public static final SimpleHolder<EntityType<Triceratops>> TRICERATOPS = ENTITY_TYPES.register("triceratops", ServerUtils.simpleEntityType(Triceratops::new, MobCategory.CREATURE, 0.75F, 0.75F).eyeHeight(0.5F));
    //public static final SimpleHolder<EntityType<Custom>> CUSTOM = ENTITY_TYPES.register("custom", ServerUtils.simpleEntityType(Custom::new, MobCategory.CREATURE, 0.0F, 0.0F).requiredFeatures(FCFeatureFlags.CUSTOM_DINOSAURS).noLootTable());

    public static final SimpleHolder<EntityType<Fossil>> FOSSIL = ENTITY_TYPES.register("fossil", ServerUtils.simpleEntityType(Fossil::new, MobCategory.MISC, 1.0F, 1.0F).noLootTable());
    public static final SimpleHolder<EntityType<StoneTablet>> STONE_TABLET = ENTITY_TYPES.register("stone_tablet", ServerUtils.<StoneTablet>simpleEntityType(StoneTablet::new, MobCategory.MISC, 0.5F, 0.5F).noLootTable().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE));

    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_WOODEN_JAVELIN = FCEntityTypes.registerJavelin("thrown_wooden_javelin");
    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_STONE_JAVELIN = FCEntityTypes.registerJavelin("thrown_stone_javelin");
    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_COPPER_JAVELIN = FCEntityTypes.registerJavelin("thrown_copper_javelin");
    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_IRON_JAVELIN = FCEntityTypes.registerJavelin("thrown_iron_javelin");
    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_GOLDEN_JAVELIN = FCEntityTypes.registerJavelin("thrown_golden_javelin");
    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_DIAMOND_JAVELIN = FCEntityTypes.registerJavelin("thrown_diamond_javelin");
    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_NETHERITE_JAVELIN = FCEntityTypes.registerJavelin("thrown_netherite_javelin");
    public static final SimpleHolder<EntityType<ThrownJavelin>> THROWN_SCARAB_GEM_JAVELIN = FCEntityTypes.registerJavelin("thrown_scarab_gem_javelin");

    public static final SimpleHolder<EntityType<Egg>> TRICERATOPS_EGG = FCEntityTypes.registerLandEgg("triceratops_egg", FCItems.TRICERATOPS_EGG::get, FCEntityTypes.TRICERATOPS::get);
    public static final SimpleHolder<EntityType<Egg>> VELOCIRAPTOR_EGG = FCEntityTypes.registerLandEgg("velociraptor_egg", FCItems.VELOCIRAPTOR_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<Egg>> TYRANNOSAURUS_EGG = FCEntityTypes.registerLandEgg("tyrannosaurus_egg", FCItems.TYRANNOSAURUS_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<Egg>> PTERANODON_EGG = FCEntityTypes.registerLandEgg("pteranodon_egg", FCItems.PTERANODON_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<Egg>> FUTABASAURUS_EGG = FCEntityTypes.registerLandEgg("futabasaurus_egg", FCItems.FUTABASAURUS_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<Egg>> MOSASAURUS_EGG = FCEntityTypes.registerWaterEgg("mosasaurus_egg", FCItems.MOSASAURUS_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<Egg>> STEGOSAURUS_EGG = FCEntityTypes.registerLandEgg("stegosaurus_egg", FCItems.STEGOSAURUS_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<Egg>> DILOPHOSAURUS_EGG = FCEntityTypes.registerLandEgg("dilophosaurus_egg", FCItems.DILOPHOSAURUS_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<Egg>> BRACHIOSAURUS_EGG = FCEntityTypes.registerLandEgg("brachiosaurus_egg", FCItems.BRACHIOSAURUS_EGG::get, () -> EntityType.SHEEP);
    public static final SimpleHolder<EntityType<ThrownAnimalEgg.ThrownIncubatedChickenEgg>> INCUBATED_CHICKEN_EGG = FCEntityTypes.registerThrownEgg("incubated_chicken_egg", ThrownAnimalEgg.ThrownIncubatedChickenEgg::new);
    public static final SimpleHolder<EntityType<ThrownAnimalEgg.ThrownIncubatedParrotEgg>> INCUBATED_PARROT_EGG = FCEntityTypes.registerThrownEgg("incubated_parrot_egg", ThrownAnimalEgg.ThrownIncubatedParrotEgg::new);

    private static SimpleHolder<EntityType<ThrownJavelin>> registerJavelin(String name) {
        return ENTITY_TYPES.register(name, ServerUtils.<ThrownJavelin>simpleEntityType(ThrownJavelin::new, MobCategory.MISC, 0.5F, 0.5F).noLootTable());
    }

    private static <I extends Item, E extends Mob> SimpleHolder<EntityType<Egg>> registerLandEgg(String name, Supplier<I> item, Supplier<EntityType<E>> offspring) {
        return ENTITY_TYPES.register(name, ServerUtils.simpleEntityType((entityType, level) -> Egg.createLand(entityType, level, item, offspring), MobCategory.MISC, 0.5F, 0.5F));
    }

    private static <I extends Item, E extends Mob> SimpleHolder<EntityType<Egg>> registerWaterEgg(String name, Supplier<I> item, Supplier<EntityType<E>> offspring) {
        return ENTITY_TYPES.register(name, ServerUtils.simpleEntityType((entityType, level) -> Egg.createWater(entityType, level, item, offspring), MobCategory.MISC, 0.5F, 0.5F));
    }

    private static <E extends AgeableMob, T extends ThrownAnimalEgg<E>> SimpleHolder<EntityType<T>> registerThrownEgg(String name, EntityType.EntityFactory<T> entityFactory) {
        return ENTITY_TYPES.register(name, ServerUtils.simpleEntityType(entityFactory, MobCategory.MISC, 0.25F, 0.25F).noLootTable().clientTrackingRange(4).updateInterval(10));
    }
}
