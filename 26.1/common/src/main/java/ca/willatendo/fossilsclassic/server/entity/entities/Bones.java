package ca.willatendo.fossilsclassic.server.entity.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SpecialDates;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Bones extends Monster {
    boolean searchingForLand;

    public Bones(EntityType<? extends Bones> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new Bones.BonesMoveControl(this);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25F).build();
    }

    public static boolean checkBonesSpawnRules(EntityType<Bones> entityType, ServerLevelAccessor serverLevelAccessor, EntitySpawnReason entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
        if (!serverLevelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && !EntitySpawnReason.isSpawner(entitySpawnReason)) {
            return false;
        } else {
            boolean flag = serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && (EntitySpawnReason.isSpawner(entitySpawnReason) || serverLevelAccessor.getFluidState(blockPos).is(FluidTags.WATER));
            if (!flag || !EntitySpawnReason.isSpawner(entitySpawnReason) && entitySpawnReason != EntitySpawnReason.REINFORCEMENT) {
                return flag;
            } else {
                return true;
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new Bones.BonesGoToWaterGoal(this, 1.0F));
        this.goalSelector.addGoal(2, new Bones.BonesAttackGoal(this, 1.0F, false));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0F));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0F, 1.2));
        this.goalSelector.addGoal(5, new Bones.BonesGoToBeachGoal(this, 1.0F));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(6, new Bones.BonesSwimUpGoal(this, 1.0F, this.level().getSeaLevel()));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }



    @Override
    public void rideTick() {
        super.rideTick();
        Entity entity = this.getControlledVehicle();
        if (entity instanceof PathfinderMob pathfinderMob) {
            this.yBodyRot = pathfinderMob.yBodyRot;
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason spawnReason, SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, spawnReason, spawnGroupData);
        RandomSource randomSource = serverLevelAccessor.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        this.populateDefaultEquipmentEnchantments(serverLevelAccessor, randomSource, difficultyInstance);
        this.setCanPickUpLoot(randomSource.nextFloat() < 0.55F * difficultyInstance.getSpecialMultiplier());
        if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && SpecialDates.isHalloween() && randomSource.nextFloat() < 0.25F) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(randomSource.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
            this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        }

        return spawnGroupData;
    }

    protected boolean closeToNextPos() {
        Path path = this.getNavigation().getPath();
        if (path != null) {
            BlockPos targetBlockPos = path.getTarget();
            if (targetBlockPos != null) {
                double distance = this.distanceToSqr(targetBlockPos.getX(), targetBlockPos.getY(), targetBlockPos.getZ());
                if (distance < (double) 4.0F) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean okTarget(LivingEntity livingEntity) {
        return livingEntity != null && (!this.level().isBrightOutside() || livingEntity.isInWater());
    }

    boolean wantsToSwim() {
        if (this.searchingForLand) {
            return true;
        } else {
            LivingEntity targetLivingEntity = this.getTarget();
            return targetLivingEntity != null && targetLivingEntity.isInWater();
        }
    }

    public void setSearchingForLand(boolean searchingForLand) {
        this.searchingForLand = searchingForLand;
    }

    static class BonesAttackGoal extends MeleeAttackGoal {
        private final Bones bones;
        private int raiseArmTicks;

        public BonesAttackGoal(Bones bones, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(bones, speedModifier, followingTargetEvenIfNotSeen);
            this.bones = bones;
        }

        @Override
        public void start() {
            super.start();
            this.raiseArmTicks = 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.bones.setAggressive(false);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.bones.okTarget(this.bones.getTarget());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.bones.okTarget(this.bones.getTarget());
        }

        @Override
        public void tick() {
            super.tick();
            ++this.raiseArmTicks;
            this.bones.setAggressive(this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2);
        }
    }

    static class BonesGoToBeachGoal extends MoveToBlockGoal {
        private final Bones bones;

        public BonesGoToBeachGoal(Bones bones, double speedModifier) {
            super(bones, speedModifier, 8, 2);
            this.bones = bones;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.bones.level().isBrightOutside() && this.bones.isInWater() && this.bones.getY() >= (double) (this.bones.level().getSeaLevel() - 3);
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            BlockPos aboveBlockPos = blockPos.above();
            return levelReader.isEmptyBlock(aboveBlockPos) && levelReader.isEmptyBlock(aboveBlockPos.above()) && levelReader.getBlockState(blockPos).entityCanStandOn(levelReader, blockPos, this.bones);
        }

        @Override
        public void start() {
            this.bones.setSearchingForLand(false);
            super.start();
        }
    }

    static class BonesGoToWaterGoal extends Goal {
        private final Bones bones;
        private double wantedX;
        private double wantedY;
        private double wantedZ;
        private final double speedModifier;
        private final Level level;

        public BonesGoToWaterGoal(Bones bones, double speedModifier) {
            this.bones = bones;
            this.speedModifier = speedModifier;
            this.level = bones.level();
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!this.level.isBrightOutside()) {
                return false;
            } else if (this.bones.isInWater()) {
                return false;
            } else {
                Vec3 waterPos = this.getWaterPos();
                if (waterPos == null) {
                    return false;
                } else {
                    this.wantedX = waterPos.x;
                    this.wantedY = waterPos.y;
                    this.wantedZ = waterPos.z;
                    return true;
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return !this.bones.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.bones.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }

        private Vec3 getWaterPos() {
            RandomSource randomSource = this.bones.getRandom();
            BlockPos blockPos = this.bones.blockPosition();

            for (int i = 0; i < 10; ++i) {
                BlockPos offsetBlockPos = blockPos.offset(randomSource.nextInt(20) - 10, 2 - randomSource.nextInt(8), randomSource.nextInt(20) - 10);
                if (this.level.getBlockState(offsetBlockPos).is(Blocks.WATER)) {
                    return Vec3.atBottomCenterOf(offsetBlockPos);
                }
            }

            return null;
        }
    }

    static class BonesMoveControl extends MoveControl {
        private final Bones bones;

        public BonesMoveControl(Bones bones) {
            super(bones);
            this.bones = bones;
        }

        @Override
        public void tick() {
            LivingEntity targetLivingEntity = this.bones.getTarget();
            if (this.bones.wantsToSwim() && this.bones.isInWater()) {
                if (targetLivingEntity != null && targetLivingEntity.getY() > this.bones.getY() || this.bones.searchingForLand) {
                    this.bones.setDeltaMovement(this.bones.getDeltaMovement().add(0.0F, 0.002F, 0.0F));
                }

                if (this.operation != Operation.MOVE_TO || this.bones.getNavigation().isDone()) {
                    this.bones.setSpeed(0.0F);
                    return;
                }

                double x = this.wantedX - this.bones.getX();
                double y = this.wantedY - this.bones.getY();
                double z = this.wantedZ - this.bones.getZ();
                double sqrt = Math.sqrt(x * x + y * y + z * z);
                y /= sqrt;
                float targetAngle = (float) (Mth.atan2(z, x) * (double) 180.0F / (double) (float) Math.PI) - 90.0F;
                this.bones.setYRot(this.rotlerp(this.bones.getYRot(), targetAngle, 90.0F));
                this.bones.yBodyRot = this.bones.getYRot();
                float end = (float) (this.speedModifier * this.bones.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float speed = Mth.lerp(0.125F, this.bones.getSpeed(), end);
                this.bones.setSpeed(speed);
                this.bones.setDeltaMovement(this.bones.getDeltaMovement().add((double) speed * x * 0.005, (double) speed * y * 0.1, (double) speed * z * 0.005));
            } else {
                if (!this.bones.onGround()) {
                    this.bones.setDeltaMovement(this.bones.getDeltaMovement().add((double) 0.0F, -0.008, (double) 0.0F));
                }

                super.tick();
            }
        }
    }

    static class BonesSwimUpGoal extends Goal {
        private final Bones bones;
        private final double speedModifier;
        private final int seaLevel;
        private boolean stuck;

        public BonesSwimUpGoal(Bones bones, double speedModifier, int seaLevel) {
            this.bones = bones;
            this.speedModifier = speedModifier;
            this.seaLevel = seaLevel;
        }

        @Override
        public boolean canUse() {
            return !this.bones.level().isBrightOutside() && this.bones.isInWater() && this.bones.getY() < (double) (this.seaLevel - 2);
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse() && !this.stuck;
        }

        @Override
        public void tick() {
            if (this.bones.getY() < (double) (this.seaLevel - 1) && (this.bones.getNavigation().isDone() || this.bones.closeToNextPos())) {
                Vec3 vec3 = DefaultRandomPos.getPosTowards(this.bones, 4, 8, new Vec3(this.bones.getX(), (double) (this.seaLevel - 1), this.bones.getZ()), (double) ((float) Math.PI / 2F));
                if (vec3 == null) {
                    this.stuck = true;
                    return;
                }

                this.bones.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, this.speedModifier);
            }
        }

        @Override
        public void start() {
            this.bones.setSearchingForLand(true);
            this.stuck = false;
        }

        public void stop() {
            this.bones.setSearchingForLand(false);
        }
    }
}
