package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.chromosome.FCChromosomes;
import ca.willatendo.fossilsclassic.server.entity.FCAttributes;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.goal.DinosaurFollowOwnerGoal;
import ca.willatendo.fossilsclassic.server.entity.goal.DinosaurOwnerHurtByTargetGoal;
import ca.willatendo.fossilsclassic.server.entity.goal.DinosaurOwnerHurtTargetGoal;
import ca.willatendo.fossilsclassic.server.entity.goal.DinosaurWaterAvoidingRandomStrollGoal;
import ca.willatendo.fossilsclassic.server.entity.utils.CommandingInformation;
import ca.willatendo.fossilsclassic.server.entity.utils.Diet;
import ca.willatendo.fossilsclassic.server.entity.utils.DinopediaInteractable;
import ca.willatendo.fossilsclassic.server.gene.FCGenes;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.item.dinopedia.DinopediaInformation;
import ca.willatendo.fossilsclassic.server.sound_event.FCSoundEvents;
import ca.willatendo.fossilsclassic.server.tags.FCItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Triceratops extends Dinosaur implements DinopediaInteractable {
    public Triceratops(EntityType<? extends Triceratops> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier triceratopsAttributes() {
        return Dinosaur.createDinosaurAttributes().add(Attributes.MAX_HEALTH, 8.0F).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(FCAttributes.MAX_HUNGER, 500.0D).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25F));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0F));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2F, itemStack -> this.getDiet().isFood(itemStack), false));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0F, true));
        this.goalSelector.addGoal(6, new DinosaurFollowOwnerGoal(this, 1.0F, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(8, new DinosaurWaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new DinosaurOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new DinosaurOwnerHurtTargetGoal(this));
    }

    @Override
    protected ResourceKey<Chromosome> getDefaultChromosome() {
        return FCChromosomes.TRICERATOPS;
    }

    @Override
    protected ResourceKey<Gene> getDefaultCosmeticGene() {
        return FCGenes.COSMETICS_GREEN_TRICERATOPS;
    }

    @Override
    public int getMaxGrowthStage() {
        return 12;
    }

    @Override
    protected float getBoundingBoxWidthGrowth() {
        return 0.2F;
    }

    @Override
    protected float getBoundingBoxHeightGrowth() {
        return 0.2F;
    }

    @Override
    protected float getEyeHeight(float boundingBoxHeight) {
        return boundingBoxHeight / 2.0F;
    }

    @Override
    protected void updateAttributesWithGrowthStage(int growthStage, boolean updateHealth) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D + (2.0D * growthStage));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D + (0.005D * growthStage));

        if (updateHealth) {
            this.setHealth(this.getMaxHealth());
        }
    }

    @Override
    protected Diet getDiet() {
        return Diet.herbivore();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FCSoundEvents.TRICERATOPS_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return FCSoundEvents.TRICERATOPS_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FCSoundEvents.TRICERATOPS_DEATH.get();
    }

    @Override
    public boolean tamesAtBirth() {
        return true;
    }

    @Override
    public CommandingInformation commandingInformation() {
        return CommandingInformation.tag(FCItemTags.TRICERATOPS_COMMANDABLES);
    }

    @Override
    public DinopediaInformation getDinopediaInformation(Player player) {
        return DinopediaInformation.basicLogic(this, player);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return FCEntityTypes.TRICERATOPS.get().create(serverLevel, EntitySpawnReason.BREEDING);
    }
}
