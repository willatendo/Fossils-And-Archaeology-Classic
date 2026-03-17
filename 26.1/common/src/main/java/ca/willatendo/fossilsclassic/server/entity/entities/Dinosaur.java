package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.entity.FCAttributes;
import ca.willatendo.fossilsclassic.server.entity.FCDamageTypes;
import ca.willatendo.fossilsclassic.server.entity.FCEntityDataSerializers;
import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import ca.willatendo.fossilsclassic.server.entity.command_type.FCCommandTypes;
import ca.willatendo.fossilsclassic.server.entity.utils.*;
import ca.willatendo.fossilsclassic.server.game_rules.FCGameRules;
import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public abstract class Dinosaur extends Animal implements CommandableEntity, TameAccessor {
    protected static final EntityDataAccessor<Holder<Chromosome>> CHROMOSOME = SynchedEntityData.defineId(Dinosaur.class, FCEntityDataSerializers.CHROMOSOME.get());
    protected static final EntityDataAccessor<Holder<CommandType>> COMMAND_TYPE = SynchedEntityData.defineId(Dinosaur.class, FCEntityDataSerializers.COMMAND_TYPE.get());
    protected static final EntityDataAccessor<Holder<Gene>> COSMETIC_GENE = SynchedEntityData.defineId(Dinosaur.class, FCEntityDataSerializers.GENE.get());
    protected static final EntityDataAccessor<Integer> GROWTH_STAGE = SynchedEntityData.defineId(Dinosaur.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> HUNGER = SynchedEntityData.defineId(Dinosaur.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(Dinosaur.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private int daysAlive;
    protected int internalClock = 0;
    private boolean isNatural;

    public Dinosaur(EntityType<? extends Dinosaur> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createDinosaurAttributes() {
        return Animal.createAnimalAttributes().add(FCAttributes.MAX_HUNGER);
    }

    public static boolean checkDinosaurSpawnRules(EntityType<? extends Dinosaur> dinosaur, LevelAccessor levelAccessor, EntitySpawnReason entitySpawnReason, BlockPos blockPos, RandomSource randomSource, TagKey<Block> spawnableBlocks) {
        boolean flag = EntitySpawnReason.ignoresLightRequirements(entitySpawnReason) || Animal.isBrightEnoughToSpawn(levelAccessor, blockPos);
        return levelAccessor.getBlockState(blockPos.below()).is(spawnableBlocks) && flag;
    }

    protected abstract ResourceKey<Chromosome> getDefaultChromosome();

    protected abstract ResourceKey<Gene> getDefaultCosmeticGene();

    public abstract int getMaxGrowthStage();

    protected abstract float getBoundingBoxWidthGrowth();

    protected abstract float getBoundingBoxHeightGrowth();

    protected abstract float getEyeHeight(float boundingBoxHeight);

    protected abstract void updateAttributesWithGrowthStage(int growthStage, boolean updateHealth);

    protected abstract Diet getDiet();

    public final float getMaxHunger() {
        return (float) this.getAttributeValue(FCAttributes.MAX_HUNGER);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHROMOSOME, VariantUtils.getDefaultOrAny(this.registryAccess(), this.getDefaultChromosome()));
        builder.define(COMMAND_TYPE, FCCommandTypes.STAY);
        builder.define(COSMETIC_GENE, VariantUtils.getDefaultOrAny(this.registryAccess(), this.getDefaultCosmeticGene()));
        builder.define(GROWTH_STAGE, 0);
        builder.define(HUNGER, 0.0F);
        builder.define(OWNER, Optional.empty());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (GROWTH_STAGE.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);

        HolderUtils.writeVariant(valueOutput, "chromosome", this.getChromosome());
        HolderUtils.writeVariant(valueOutput, "command_type", this.getCommand());
        HolderUtils.writeVariant(valueOutput, "cosmetic_gene", this.getCosmeticGene());
        valueOutput.putInt("growth_stage", this.getGrowthStage());
        valueOutput.putFloat("hunger", this.getHunger());
        EntityReference.store(this.getOwnerReference(), valueOutput, "owner");

        valueOutput.putInt("days_alive", this.daysAlive);
        valueOutput.putBoolean("is_natural", this.isNatural);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);

        HolderUtils.readVariant(valueInput, "chromosome", FCRegistries.CHROMOSOME).ifPresent(this::setChromosome);
        HolderUtils.readVariant(valueInput, "command_type", FCRegistries.COMMAND_TYPE).ifPresent(this::setCommand);
        HolderUtils.readVariant(valueInput, "cosmetic_gene", FCRegistries.GENE).ifPresent(this::setCosmeticGene);
        this.setGrowthStage(valueInput.getIntOr("growth_stage", 0), false);
        this.setHunger(valueInput.getFloatOr("hunger", 0));
        EntityReference<LivingEntity> entityreference = EntityReference.readWithOldOwnerConversion(valueInput, "owner", this.level());
        if (entityreference != null) {
            this.entityData.set(OWNER, Optional.of(entityreference));
        } else {
            this.entityData.set(OWNER, Optional.empty());
        }

        this.daysAlive = valueInput.getIntOr("days_alive", 0);
        this.isNatural = valueInput.getBooleanOr("is_natural", false);
    }

    public Holder<Chromosome> getChromosome() {
        return this.entityData.get(CHROMOSOME);
    }

    public void setChromosome(Holder<Chromosome> chromosome) {
        this.entityData.set(CHROMOSOME, chromosome);
    }

    @Override
    public Holder<CommandType> getCommand() {
        return this.entityData.get(COMMAND_TYPE);
    }

    @Override
    public void setCommand(Holder<CommandType> dinosaurOrder) {
        this.entityData.set(COMMAND_TYPE, dinosaurOrder);
    }

    public Holder<Gene> getCosmeticGene() {
        return this.entityData.get(COSMETIC_GENE);
    }

    public void setCosmeticGene(Holder<Gene> cosmeticGene) {
        this.entityData.set(COSMETIC_GENE, cosmeticGene);
    }

    public int getGrowthStage() {
        return this.entityData.get(GROWTH_STAGE);
    }

    public void setGrowthStage(int growthStage, boolean updateHealth) {
        this.entityData.set(GROWTH_STAGE, growthStage);
        this.updateAttributesWithGrowthStage(growthStage, updateHealth);
        this.refreshDimensions();
    }

    public void grow() {
        this.setGrowthStage(this.getGrowthStage() + 1, true);
    }

    public EntityReference<LivingEntity> getOwnerReference() {
        return this.entityData.get(OWNER).orElse(null);
    }

    public void setOwner(LivingEntity ownerLivingEntity) {
        this.entityData.set(OWNER, Optional.ofNullable(ownerLivingEntity).map(EntityReference::of));
    }

    public void setOwnerReference(EntityReference<LivingEntity> ownerReference) {
        this.entityData.set(OWNER, Optional.ofNullable(ownerReference));
    }

    public boolean isTame() {
        return this.getOwnerReference() != null;
    }

    public float getHunger() {
        return this.entityData.get(HUNGER);
    }

    public void setHunger(float hunger) {
        this.entityData.set(HUNGER, hunger);
    }

    public int getDaysAlive() {
        return this.daysAlive;
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    private EntityDimensions getDimensionsForGrowthStage(int growthStage) {
        EntityDimensions defaultDimensions = this.getType().getDimensions();
        float height = defaultDimensions.height() + (this.getBoundingBoxHeightGrowth() * growthStage);
        return EntityDimensions.scalable(defaultDimensions.width() + (this.getBoundingBoxWidthGrowth() * growthStage), height).withEyeHeight(this.getEyeHeight(height));
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return this.getDimensionsForGrowthStage(this.getGrowthStage());
    }

    public boolean isOwnedBy(LivingEntity entity) {
        return entity == this.getOwner();
    }

    public final boolean unableToMoveToOwner() {
        return this.getCommand().is(FCCommandTypes.STAY) || this.isPassenger() || this.mayBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    public void tryToTeleportToOwner() {
        LivingEntity owner = this.getOwner();
        if (owner != null) {
            this.teleportToAroundBlockPos(owner.blockPosition());
        }
    }

    public boolean shouldTryTeleportToOwner() {
        LivingEntity owner = this.getOwner();
        return owner != null && this.distanceToSqr(this.getOwner()) >= 144.0F;
    }

    private void teleportToAroundBlockPos(BlockPos blockPos) {
        for (int i = 0; i < 10; ++i) {
            int x = this.random.nextIntBetweenInclusive(-3, 3);
            int z = this.random.nextIntBetweenInclusive(-3, 3);
            if (Math.abs(x) >= 2 || Math.abs(z) >= 2) {
                int y = this.random.nextIntBetweenInclusive(-1, 1);
                if (this.maybeTeleportTo(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z)) {
                    return;
                }
            }
        }
    }

    private boolean maybeTeleportTo(int x, int y, int z) {
        if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.snapTo(x + 0.5F, y, z + 0.5F, this.getYRot(), this.getXRot());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos blockPos) {
        PathType pathType = WalkNodeEvaluator.getPathTypeStatic(this, blockPos);
        if (pathType != PathType.WALKABLE) {
            return false;
        } else {
            BlockState blockState = this.level().getBlockState(blockPos.below());
            if (blockState.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos differenceBlockPos = blockPos.subtract(this.blockPosition());
                return this.level().noCollision(this, this.getBoundingBox().move(differenceBlockPos));
            }
        }
    }

    public boolean hasSpace() {
        EntityDimensions updatedDimensions = this.getDimensionsForGrowthStage(this.getGrowthStage() + 1);
        return !this.level().getBlockCollisions(this, updatedDimensions.makeBoundingBox(this.position())).iterator().hasNext();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isNoAi()) {
            if (this.internalClock == 24000) {
                this.internalClock = 0;
                this.daysAlive++;
                if (this.getGrowthStage() < this.getMaxGrowthStage()) {
                    if (this.hasSpace()) {
                        this.setGrowthStage(this.getGrowthStage() + 1, true);
                    } else {
                        this.sendMessageToOwnerOrElseAll(DinosaurSpeechBubble.NO_SPACE);
                    }
                }
            }

            this.internalClock++;
        }

        if (!this.isNoAi()) {
            if (this.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(FCGameRules.DO_ANIMAL_HUNGER.get()) && !this.isNatural) {
                if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
                    if (this.internalClock % 300 == 0) {
                        this.decreaseHunger();

                        if (this.getHunger() == (this.getMaxHunger() / 2)) {
                            this.sendMessageToOwnerOrElseAll(DinosaurSpeechBubble.HUNGRY);
                        }
                    }

                    if (this.getHunger() < 0) {
                        if (this.isTame()) {
                            this.sendMessageToOwnerOrElseAll(DinosaurSpeechBubble.STARVE_ESCAPE);
                            this.setCommand(FCCommandTypes.FREE_MOVE);
                            this.setOwner(null);
                        }
                        if (this.internalClock % 100 == 0) {
                            this.sendMessageToOwnerOrElseAll(DinosaurSpeechBubble.STARVE);
                        }
                        this.hurt(new DamageSource(this.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(FCDamageTypes.ANIMAL_STARVE)), 20.0F);
                    }
                }
            }

            if (!this.isDeadOrDying()) {
                if (this.internalClock % 10 == 0) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        if (this.getHunger() > this.getMaxHunger() / 2) {
                            this.setHunger(this.getHunger() - 5);
                            this.setHealth(this.getHealth() + 1.0F);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        if (this.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(this.getCombatTracker().getDeathMessage());
        }
        super.die(damageSource);
    }

    @Override
    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        } else if (!(animal instanceof Dinosaur)) {
            return false;
        } else {
            Dinosaur dinosaur = (Dinosaur) animal;
            return animal.getClass() == this.getClass() && this.isInLove() && dinosaur.isInLove();
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (interactionHand == InteractionHand.MAIN_HAND && this.commandingInformation().canCommandWithItem(itemStack) && this.isOwnedBy(player)) {
            this.cycleCommand();
            player.displayClientMessage(FCCoreUtils.translationWithArguments("entity", "dinosaur.set_command", this.getCommand().value().getName().copy().withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY), true);
            return InteractionResult.SUCCESS_SERVER;
        }

        int foodValue = this.getDiet().getFoodValue(itemStack);
        if (foodValue > 0) {
            if (this.getHunger() < this.getMaxHunger()) {
                float addition = this.getHunger() + foodValue;
                if (addition < this.getMaxHunger()) {
                    this.setHunger(addition);
                    itemStack.shrink(1);
                    this.playEatingSound();
                } else {
                    this.setHunger(this.getMaxHunger());
                    itemStack.shrink(1);
                    this.playEatingSound();

                    if (player instanceof ServerPlayer serverPlayer) {
                        this.sendMessageToPlayer(DinosaurSpeechBubble.FULL, serverPlayer);
                    }
                }
                if (this.isBaby()) {
                    this.tryToTame(player);
                } else {
                    this.setInLove(player);
                }
            } else {
                if (player instanceof ServerPlayer serverPlayer) {
                    this.sendMessageToPlayer(DinosaurSpeechBubble.FULL, serverPlayer);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.interact(player, interactionHand);
    }

    private void tryToTame(Player player) {
        int choice = this.random.nextInt(3);
        if (choice == 0) {
            this.setOwner(player, true);
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.TAME_ANIMAL.trigger(serverPlayer, this);
            }
            this.navigation.stop();
            this.setTarget(null);
            this.setCommand(FCCommandTypes.STAY);
            this.level().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 6);
        }
    }

    public boolean feed() {
        if (this.getHunger() < this.getMaxHunger()) {
            this.setHunger(this.getHunger() + 1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        if (this.isOwnedBy(livingEntity)) {
            return false;
        } else if (this.getOwner() != null && livingEntity instanceof TamableAnimal tamableAnimal) {
            return tamableAnimal.isOwnedBy(this.getOwner());
        }
        return super.canAttack(livingEntity);
    }

    @Override
    public void ate() {
        this.setHunger(this.getMaxHunger());
        super.ate();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return this.getDiet().isFood(itemStack);
    }

    @Override
    public boolean isBaby() {
        return this.getGrowthStage() < (this.getMaxGrowthStage() / 2);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 360;
    }

    @Override
    public boolean shouldDropExperience() {
        return true;
    }

    // Collide Damage


    public void decreaseHunger() {
        this.decreaseHunger(1);
    }

    public void decreaseHunger(int value) {
        float newHunger = this.getHunger() - value;
        if (!(newHunger < 0)) {
            this.setHunger(newHunger);
        } else {
            this.setHunger(0);
        }
    }

    public void increaseHunger() {
        this.increaseHunger(1);
    }

    public void increaseHunger(int value) {
        this.setHunger(Math.min(this.getHunger() + value, this.getMaxHunger()));
    }

    @Override
    public boolean killedEntity(ServerLevel serverLevel, LivingEntity livingEntity, DamageSource damageSource) {
        if (this.getDiet().gainsFoodFromKill()) {
            this.increaseHunger(this.getDiet().foodFromKill(livingEntity.getType()));
        }
        return super.killedEntity(serverLevel, livingEntity, damageSource);
    }

    @Override
    protected int getBaseExperienceReward(ServerLevel serverLevel) {
        return super.getBaseExperienceReward(serverLevel) * this.getGrowthStage();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason entitySpawnReason, SpawnGroupData spawnGroupData) {
        Chromosome chromosome = this.getChromosome().value();
        chromosome.getCosmeticChooser().getGene(chromosome.getCosmeticGenes(), this.getRandom(), this.level().getBiome(this.blockPosition())).ifPresent(this::setCosmeticGene);

        if (entitySpawnReason != EntitySpawnReason.BREEDING) {
            this.setGrowthStage(this.getMaxGrowthStage(), true);
        }
        this.setHunger(this.getMaxHunger());
        this.setCommand(FCCommandTypes.FREE_MOVE);
        if (entitySpawnReason == EntitySpawnReason.NATURAL) {
            this.isNatural = true;
        }

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, entitySpawnReason, spawnGroupData);
    }

    public void sendMessageToPlayer(DinosaurSpeechBubble dinosaurSpeechBubble, ServerPlayer serverPlayer) {
        if (!this.isNatural) {
            serverPlayer.sendSystemMessage(dinosaurSpeechBubble.getMessage(serverPlayer, this));
        }
    }

    public void sendMessageToOwnerOrElseAll(DinosaurSpeechBubble dinosaurSpeechBubble) {
        if (this.isTame() && this.getOwner() instanceof ServerPlayer serverPlayer) {
            this.sendMessageToPlayer(dinosaurSpeechBubble, serverPlayer);
        } else {
            if (this.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(FCGameRules.DO_UNTAME_ANIMAL_MESSAGES.get())) {
                for (Player player : this.level().players()) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        this.sendMessageToPlayer(dinosaurSpeechBubble, serverPlayer);
                    }
                }
            }
        }
    }
}
