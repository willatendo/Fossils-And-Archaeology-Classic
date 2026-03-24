package ca.willatendo.fossilsclassic.server.biomass_value;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;

public final class FCBiomassValues {
    public static final ResourceKey<BiomassValue> FOSSIL = FCBiomassValues.create("fossil");
    public static final ResourceKey<BiomassValue> RAW_CHICKEN_SOUP_BUCKET = FCBiomassValues.create("raw_chicken_soup_bucket");
    public static final ResourceKey<BiomassValue> TRICERATOPS_EGG = FCBiomassValues.create("triceratops_egg");
    public static final ResourceKey<BiomassValue> VELOCIRAPTOR_EGG = FCBiomassValues.create("velociraptor_egg");
    public static final ResourceKey<BiomassValue> TYRANNOSAURUS_EGG = FCBiomassValues.create("tyrannosaurus_egg");
    public static final ResourceKey<BiomassValue> PTERANODON_EGG = FCBiomassValues.create("pteranodon_egg");
    public static final ResourceKey<BiomassValue> NAUTILUS_EGGS = FCBiomassValues.create("nautilus_eggs");
    public static final ResourceKey<BiomassValue> FUTABASAURUS_EGG = FCBiomassValues.create("futabasaurus_egg");
    public static final ResourceKey<BiomassValue> MOSASAURUS_EGG = FCBiomassValues.create("mosasaurus_egg");
    public static final ResourceKey<BiomassValue> STEGOSAURUS_EGG = FCBiomassValues.create("stegosaurus_egg");
    public static final ResourceKey<BiomassValue> DILOPHOSAURUS_EGG = FCBiomassValues.create("dilophosaurus_egg");
    public static final ResourceKey<BiomassValue> BRACHIOSAURUS_EGG = FCBiomassValues.create("brachiosaurus_egg");
    public static final ResourceKey<BiomassValue> PORKCHOP = FCBiomassValues.create("porkchop");
    public static final ResourceKey<BiomassValue> MUTTON = FCBiomassValues.create("mutton");
    public static final ResourceKey<BiomassValue> COD = FCBiomassValues.create("cod");
    public static final ResourceKey<BiomassValue> SALMON = FCBiomassValues.create("salmon");
    public static final ResourceKey<BiomassValue> TROPICAL_FISH = FCBiomassValues.create("tropical_fish");
    public static final ResourceKey<BiomassValue> BEEF = FCBiomassValues.create("beef");
    public static final ResourceKey<BiomassValue> CHICKEN = FCBiomassValues.create("chicken");
    public static final ResourceKey<BiomassValue> RABBIT = FCBiomassValues.create("rabbit");
    public static final ResourceKey<BiomassValue> RAW_TRICERATOPS = FCBiomassValues.create("raw_triceratops");
    public static final ResourceKey<BiomassValue> RAW_VELOCIRAPTOR = FCBiomassValues.create("raw_velociraptor");
    public static final ResourceKey<BiomassValue> RAW_TYRANNOSAURUS = FCBiomassValues.create("raw_tyrannosaurus");
    public static final ResourceKey<BiomassValue> RAW_PTERANODON = FCBiomassValues.create("raw_pteranodon");
    public static final ResourceKey<BiomassValue> RAW_FUTABASAURUS = FCBiomassValues.create("raw_futabasaurus");
    public static final ResourceKey<BiomassValue> RAW_MOSASAURUS = FCBiomassValues.create("raw_mosasaurus");
    public static final ResourceKey<BiomassValue> RAW_STEGOSAURUS = FCBiomassValues.create("raw_stegosaurus");
    public static final ResourceKey<BiomassValue> RAW_DILOPHOSAURUS = FCBiomassValues.create("raw_dilophosaurus");
    public static final ResourceKey<BiomassValue> RAW_BRACHIOSAURUS = FCBiomassValues.create("raw_brachiosaurus");
    public static final ResourceKey<BiomassValue> RAW_SMILODON = FCBiomassValues.create("raw_smilodon");
    public static final ResourceKey<BiomassValue> EGG = FCBiomassValues.create("egg");
    public static final ResourceKey<BiomassValue> SLIME_BALL = FCBiomassValues.create("slime_ball");
    public static final ResourceKey<BiomassValue> MILK_BUCKET = FCBiomassValues.create("milk_bucket");

    private static ResourceKey<BiomassValue> create(String name) {
        return ResourceKey.create(FCRegistries.BIOMASS_VALUE, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<BiomassValue> bootstrapContext) {
        bootstrapContext.register(FOSSIL, new BiomassValue(FCItems.FOSSIL.get(), 300));
        bootstrapContext.register(RAW_CHICKEN_SOUP_BUCKET, new BiomassValue(FCItems.RAW_CHICKEN_SOUP_BUCKET.get(), 1000));
        bootstrapContext.register(TRICERATOPS_EGG, new BiomassValue(FCItems.TRICERATOPS_EGG.get(), 12000));
        bootstrapContext.register(VELOCIRAPTOR_EGG, new BiomassValue(FCItems.VELOCIRAPTOR_EGG.get(), 12000));
        bootstrapContext.register(TYRANNOSAURUS_EGG, new BiomassValue(FCItems.TYRANNOSAURUS_EGG.get(), 12000));
        bootstrapContext.register(PTERANODON_EGG, new BiomassValue(FCItems.PTERANODON_EGG.get(), 12000));
        bootstrapContext.register(NAUTILUS_EGGS, new BiomassValue(FCItems.NAUTILUS_EGGS.get(), 12000));
        bootstrapContext.register(FUTABASAURUS_EGG, new BiomassValue(FCItems.FUTABASAURUS_EGG.get(), 12000));
        bootstrapContext.register(MOSASAURUS_EGG, new BiomassValue(FCItems.MOSASAURUS_EGG.get(), 12000));
        bootstrapContext.register(STEGOSAURUS_EGG, new BiomassValue(FCItems.STEGOSAURUS_EGG.get(), 12000));
        bootstrapContext.register(DILOPHOSAURUS_EGG, new BiomassValue(FCItems.DILOPHOSAURUS_EGG.get(), 12000));
        bootstrapContext.register(BRACHIOSAURUS_EGG, new BiomassValue(FCItems.BRACHIOSAURUS_EGG.get(), 12000));
        bootstrapContext.register(PORKCHOP, new BiomassValue(Items.PORKCHOP, 3000));
        bootstrapContext.register(MUTTON, new BiomassValue(Items.MUTTON, 2000));
        bootstrapContext.register(COD, new BiomassValue(Items.COD, 3000));
        bootstrapContext.register(SALMON, new BiomassValue(Items.SALMON, 3000));
        bootstrapContext.register(TROPICAL_FISH, new BiomassValue(Items.TROPICAL_FISH, 3000));
        bootstrapContext.register(BEEF, new BiomassValue(Items.BEEF, 4000));
        bootstrapContext.register(CHICKEN, new BiomassValue(Items.CHICKEN, 1500));
        bootstrapContext.register(RABBIT, new BiomassValue(Items.RABBIT, 1500));
        bootstrapContext.register(RAW_TRICERATOPS, new BiomassValue(FCItems.RAW_TRICERATOPS.get(), 4000));
        bootstrapContext.register(RAW_VELOCIRAPTOR, new BiomassValue(FCItems.RAW_VELOCIRAPTOR.get(), 4000));
        bootstrapContext.register(RAW_TYRANNOSAURUS, new BiomassValue(FCItems.RAW_TYRANNOSAURUS.get(), 4000));
        bootstrapContext.register(RAW_PTERANODON, new BiomassValue(FCItems.RAW_PTERANODON.get(), 4000));
        bootstrapContext.register(RAW_FUTABASAURUS, new BiomassValue(FCItems.RAW_FUTABASAURUS.get(), 4000));
        bootstrapContext.register(RAW_MOSASAURUS, new BiomassValue(FCItems.RAW_MOSASAURUS.get(), 4000));
        bootstrapContext.register(RAW_STEGOSAURUS, new BiomassValue(FCItems.RAW_STEGOSAURUS.get(), 4000));
        bootstrapContext.register(RAW_DILOPHOSAURUS, new BiomassValue(FCItems.RAW_DILOPHOSAURUS.get(), 4000));
        bootstrapContext.register(RAW_BRACHIOSAURUS, new BiomassValue(FCItems.RAW_BRACHIOSAURUS.get(), 4000));
        bootstrapContext.register(RAW_SMILODON, new BiomassValue(FCItems.RAW_SMILODON.get(), 4000));
        bootstrapContext.register(EGG, new BiomassValue(Items.EGG, 1000));
        bootstrapContext.register(SLIME_BALL, new BiomassValue(Items.SLIME_BALL, 800));
        bootstrapContext.register(MILK_BUCKET, new BiomassValue(Items.MILK_BUCKET, 6000));
    }
}
