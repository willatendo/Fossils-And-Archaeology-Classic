package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.chromosome.FCChromosomes;
import ca.willatendo.fossilsclassic.server.entity.FCAttributes;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.goal.*;
import ca.willatendo.fossilsclassic.server.entity.utils.CommandingInformation;
import ca.willatendo.fossilsclassic.server.entity.utils.Diet;
import ca.willatendo.fossilsclassic.server.entity.utils.DinopediaInteractable;
import ca.willatendo.fossilsclassic.server.gene.FCGenes;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.item.dinopedia.DinopediaInformation;
import ca.willatendo.fossilsclassic.server.sound_event.FCSoundEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Smilodon extends Dinosaur implements DinopediaInteractable {
    public Smilodon(EntityType<? extends Smilodon> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier smilodonAttributes() {
        return Dinosaur.createDinosaurAttributes().add(Attributes.MAX_HEALTH, 8.0F).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(FCAttributes.MAX_HUNGER, 100.0D).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25F));
        this.goalSelector.addGoal(2, new DinosaurEatFromFeederGoal(this, 1.0D, 24, true));
        this.goalSelector.addGoal(3, new DinosaurBreedGoal(this, 1.0F));
        this.goalSelector.addGoal(4, new DinosaurTemptGoal(this, 1.2F, itemStack -> this.getDiet().isFood(itemStack), false));
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
        return FCChromosomes.SMILODON;
    }

    @Override
    protected ResourceKey<Gene> getDefaultCosmeticGene() {
        return FCGenes.COSMETICS_SMILODON;
    }

    @Override
    public int getMaxGrowthStage() {
        return 1;
    }

    @Override
    protected float getBoundingBoxWidthGrowth() {
        return 0.5F;
    }

    @Override
    protected float getBoundingBoxHeightGrowth() {
        return 0.75F;
    }

    @Override
    protected float getEyeHeight(float boundingBoxHeight) {
        return boundingBoxHeight * 0.8F;
    }

    @Override
    protected float getAttachmentY(float boundingBoxHeight) {
        return boundingBoxHeight;
    }

    @Override
    protected void updateAttributesWithGrowthStage(int growthStage, boolean updateHealth) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D + (20.0D * growthStage));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D + (0.005D * growthStage));

        if (updateHealth) {
            this.setHealth(this.getMaxHealth());
        }
    }

    @Override
    protected Diet getDiet() {
        return Diet.carnivore();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FCSoundEvents.SMILODON_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return FCSoundEvents.SMILODON_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FCSoundEvents.SMILODON_DEATH.get();
    }

    @Override
    public boolean tamesAtBirth() {
        return true;
    }

    @Override
    public CommandingInformation commandingInformation() {
        return CommandingInformation.hand();
    }

    @Override
    public DinopediaInformation getDinopediaInformation(Player player) {
        return DinopediaInformation.basicLogic(this, player);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return FCEntityTypes.SMILODON.get().create(serverLevel, EntitySpawnReason.BREEDING);
    }

    @Override
    protected Dinosaur getSpawnEggOffspring(ServerLevel serverLevel) {
        return FCEntityTypes.SMILODON.get().create(serverLevel, EntitySpawnReason.SPAWN_ITEM_USE);
    }
}
