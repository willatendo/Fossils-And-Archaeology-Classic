package ca.willatendo.fossilsclassic.server.event;

import ca.willatendo.fossilsclassic.client.screen.recipe_book.FCSearchRecipeBookCategory;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.ValueMaps;
import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.archaeology_value.ArchaeologyValue;
import ca.willatendo.fossilsclassic.server.attachment_type.FCAttachmentTypes;
import ca.willatendo.fossilsclassic.server.attachment_type.attachment_types.Pregnancy;
import ca.willatendo.fossilsclassic.server.biomass_value.BiomassValue;
import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.entities.*;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.feeder_food.FeederFoodValue;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.registry.FCBuiltInRegistries;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.fossilsclassic.server.stats.FCStats;
import ca.willatendo.fossilsclassic.server.utils.BirthUtils;
import ca.willatendo.simplelibrary.core.utils.AttachmentTypeUtils;
import ca.willatendo.simplelibrary.server.EventListener;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.nautilus.Nautilus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Optional;

public final class FossilsClassicEventListener implements EventListener {
    public static final FossilsClassicEventListener INSTANCE = new FossilsClassicEventListener();

    private FossilsClassicEventListener() {
    }

    @Override
    public void commonSetup() {
        FCStats.CUSTOM_STATS.registerStatFormatters();
    }

    @Override
    public void registerRecipeBookSearchCategory(RecipeBookSearchCategoryRegister recipeBookSearchCategoryRegister) {
        for (FCSearchRecipeBookCategory category : FCSearchRecipeBookCategory.values()) {
            recipeBookSearchCategoryRegister.register(category, category.includedCategories().toArray(RecipeBookCategory[]::new));
        }
    }

    @Override
    public void registerNewRegistries(NewRegistryRegister newRegistryRegister) {
        newRegistryRegister.apply(FCBuiltInRegistries.COMMAND_TYPES);
        newRegistryRegister.apply(FCBuiltInRegistries.GENE_TYPES);
        newRegistryRegister.apply(FCBuiltInRegistries.JSON_BEHAVIOR_TYPES);
    }

    @Override
    public void registerDynamicRegistries(DynamicRegistryRegister dynamicRegistryRegister) {
        dynamicRegistryRegister.apply(FCRegistries.ANALYZATION_RESULT, AnalyzationResult.CODEC, AnalyzationResult.CODEC);
        dynamicRegistryRegister.apply(FCRegistries.ARCHAEOLOGY_VALUE, ArchaeologyValue.CODEC, ArchaeologyValue.CODEC);
        dynamicRegistryRegister.apply(FCRegistries.BIOMASS_VALUE, BiomassValue.CODEC, BiomassValue.CODEC);
        dynamicRegistryRegister.apply(FCRegistries.CHROMOSOME, Chromosome.DIRECT_CODEC, Chromosome.DIRECT_CODEC);
        dynamicRegistryRegister.apply(FCRegistries.FEEDER_FOOD_VALUE, FeederFoodValue.DIRECT_CODEC, FeederFoodValue.DIRECT_CODEC);
        dynamicRegistryRegister.apply(FCRegistries.FOSSIL_VARIANT, FossilVariant.DIRECT_CODEC, FossilVariant.DIRECT_CODEC);
        dynamicRegistryRegister.apply(FCRegistries.GENE, Gene.DIRECT_CODEC, Gene.DIRECT_CODEC);
        dynamicRegistryRegister.apply(FCRegistries.STONE_TABLET_VARIANT, StoneTabletVariant.DIRECT_CODEC, StoneTabletVariant.DIRECT_CODEC);
    }

    @Override
    public void registerAttributes(AttributeRegister attributeRegister) {
        attributeRegister.apply(FCEntityTypes.BONES.get(), Bones.createAttributes());
        attributeRegister.apply(FCEntityTypes.FAILURESAURUS.get(), Failuresaurs.createAttributes());

        attributeRegister.apply(FCEntityTypes.STEGOSAURUS.get(), Stegosaurus.stegosaurusAttributes());
        attributeRegister.apply(FCEntityTypes.TRICERATOPS.get(), Triceratops.triceratopsAttributes());
        // attributeRegister.apply(FCEntityTypes.CUSTOM.get(), Custom.baseAttributes());

        attributeRegister.apply(FCEntityTypes.FOSSIL.get(), Fossil.fossilAttributes());

        attributeRegister.apply(FCEntityTypes.TRICERATOPS_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.VELOCIRAPTOR_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.TYRANNOSAURUS_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.PTERANODON_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.FUTABASAURUS_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.MOSASAURUS_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.STEGOSAURUS_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.DILOPHOSAURUS_EGG.get(), Egg.eggAttributes());
        attributeRegister.apply(FCEntityTypes.BRACHIOSAURUS_EGG.get(), Egg.eggAttributes());
    }

    @Override
    public void registerBuiltInResourcePacks(BuiltInResourcePackRegister builtInResourcePackRegister) {
        builtInResourcePackRegister.featurePack(FCCoreUtils.ID, "palaeocraft_example", Pack.Position.TOP);
    }

    @Override
    public void registerSpawnPlacements(SpawnPlacementRegister spawnPlacementRegister) {
        spawnPlacementRegister.apply(FCEntityTypes.BONES.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bones::checkBonesSpawnRules);
    }

    @Override
    public InteractionResult playerEntityInteractEvent(Entity entity, Player player) {
        if (entity instanceof Nautilus nautilus && nautilus.isBaby() && !nautilus.level().isClientSide() && player.getMainHandItem().isEmpty()) {
            ItemStack livingBabyNautilusItemStack = new ItemStack(FCItems.LIVING_BABY_NAUTILUS.get());
            if (nautilus.hasCustomName()) {
                livingBabyNautilusItemStack.set(DataComponents.CUSTOM_NAME, nautilus.getCustomName());
            }
            player.setItemInHand(InteractionHand.MAIN_HAND, livingBabyNautilusItemStack);
            nautilus.discard();
            return InteractionResult.SUCCESS.heldItemTransformedTo(livingBabyNautilusItemStack);
        }
        return EventListener.super.playerEntityInteractEvent(entity, player);
    }

    @Override
    public void postEntityTickEvent(Entity entity) {
        if (AttachmentTypeUtils.hasData(entity, FCAttachmentTypes.PREGNANCY)) {
            Pregnancy pregnancy = AttachmentTypeUtils.getData(entity, FCAttachmentTypes.PREGNANCY);
            if (entity.level() instanceof ServerLevel serverLevel && entity instanceof Mob mob) {
                BirthUtils.birthTick(pregnancy.getRemainingTicks(), pregnancy.getMaxTicks(), serverLevel, pregnancy.getOffspringEntityType(), mob, Optional.empty(), (parentMobIn, offspringMobIn) -> AttachmentTypeUtils.removeData(parentMobIn, FCAttachmentTypes.PREGNANCY), remainingTicks -> AttachmentTypeUtils.setData(entity, FCAttachmentTypes.PREGNANCY, pregnancy), pregnancy::setRemainingTicks);
            }
        }
    }

    @Override
    public void dataReloadEvent(MinecraftServer minecraftServer) {
        ValueMaps.fillMaps(minecraftServer.registryAccess());
    }
}
