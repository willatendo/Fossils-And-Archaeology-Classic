package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.utils.DinopediaInteractable;
import ca.willatendo.fossilsclassic.server.entity.utils.TicksToBirth;
import ca.willatendo.fossilsclassic.server.item.dinopedia.DinopediaInformation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class Egg extends Animal implements TicksToBirth, DinopediaInteractable {
    private static final EntityDataAccessor<Integer> REMAINING_TICKS = SynchedEntityData.defineId(Egg.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> WARM = SynchedEntityData.defineId(Egg.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(Egg.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);

    private Egg(EntityType<Egg> entityType, Level level) {
        super(entityType, level);
    }

    public static <I extends Item, E extends Mob> Egg createLand(EntityType<Egg> entityType, Level level, Supplier<I> item, Supplier<EntityType<E>> offspring) {
        return new Egg(entityType, level) {
            @Override
            public Component cannotIncubateMessage() {
                return FCCoreUtils.translation("dinopedia", "status.cold");
            }

            @Override
            public Component canIncubateMessage() {
                return FCCoreUtils.translation("dinopedia", "status.warm");
            }

            @Override
            public Component killMessage() {
                return FCCoreUtils.translation("entity", "egg.die.cold");
            }

            @Override
            public boolean canIncubate() {
                return this.level().getBrightness(LightLayer.BLOCK, this.blockPosition()) > 10.0F;
            }

            @Override
            public ItemStack getPickResult() {
                return new ItemStack(item.get());
            }

            @Override
            public EntityType<E> getOffspring() {
                return offspring.get();
            }
        };
    }

    public static <I extends Item, E extends Mob> Egg createWater(EntityType<Egg> entityType, Level level, Supplier<I> item, Supplier<EntityType<E>> offspring) {
        return new Egg(entityType, level) {
            @Override
            public Component cannotIncubateMessage() {
                return FCCoreUtils.translation("dinopedia", "status.dry");
            }

            @Override
            public Component canIncubateMessage() {
                return FCCoreUtils.translation("dinopedia", "status.wet");
            }

            @Override
            public Component killMessage() {
                return FCCoreUtils.translation("entity", "egg.die.dry");
            }

            @Override
            public boolean canIncubate() {
                return this.isInWater();
            }

            @Override
            public ItemStack getPickResult() {
                return new ItemStack(item.get());
            }

            @Override
            public EntityType<E> getOffspring() {
                return offspring.get();
            }
        };
    }

    public abstract Component cannotIncubateMessage();

    public abstract Component canIncubateMessage();

    public static AttributeSupplier eggAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0F).build();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void kill() {
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();

        this.setWarm(this.canIncubate());

        if (this.level() instanceof ServerLevel serverLevel) {
            this.birthTick(this, serverLevel, this.getOwner());
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(REMAINING_TICKS, 0);
        builder.define(WARM, false);
        builder.define(OWNER, Optional.empty());
    }

    @Override
    public void saveWithoutId(ValueOutput valueOutput) {
        super.saveWithoutId(valueOutput);
        valueOutput.putInt("remaining_ticks", this.getRemainingTicks());
        valueOutput.putBoolean("warm", this.isWarm());
        EntityReference.store(this.getOwnerReference(), valueOutput, "owner");
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putInt("remaining_ticks", this.getRemainingTicks());
        valueOutput.putBoolean("warm", this.isWarm());
        EntityReference.store(this.getOwnerReference(), valueOutput, "owner");
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.setRemainingTicks(valueInput.getIntOr("remaining_ticks", 0));
        this.setWarm(valueInput.getBooleanOr("warm", false));
        EntityReference<LivingEntity> entityEntityReference = EntityReference.readWithOldOwnerConversion(valueInput, "owner", this.level());
        if (entityEntityReference != null) {
            this.entityData.set(OWNER, Optional.of(entityEntityReference));
        } else {
            this.entityData.set(OWNER, Optional.empty());
        }
    }

    @Override
    public void setRemainingTicks(int remainingTicks) {
        this.entityData.set(REMAINING_TICKS, remainingTicks);
    }

    @Override
    public int getRemainingTicks() {
        return this.entityData.get(REMAINING_TICKS);
    }

    public void setWarm(boolean warm) {
        this.entityData.set(WARM, warm);
    }

    public boolean isWarm() {
        return this.entityData.get(WARM);
    }

    public void setOwner(LivingEntity owner) {
        this.entityData.set(OWNER, Optional.ofNullable(owner).map(EntityReference::of));
    }

    public void setOwnerReference(EntityReference<LivingEntity> owner) {
        this.entityData.set(OWNER, Optional.ofNullable(owner));
    }

    public Optional<EntityReference<LivingEntity>> getOwner() {
        return this.entityData.get(OWNER);
    }

    public EntityReference<LivingEntity> getOwnerReference() {
        return this.getOwner().orElse(null);
    }

    @Override
    public void onEntityTicksComplete(Mob parentMob, Mob offspringMob, ServerLevel serverLevel) {
        if (parentMob.hasCustomName()) {
            offspringMob.setCustomName(parentMob.getCustomName());
        }
        TicksToBirth.super.onEntityTicksComplete(parentMob, offspringMob, serverLevel);
    }

    @Override
    protected void dropExperience(ServerLevel serverLevel, Entity entity) {
    }

    @Override
    public void knockback(double strength, double x, double z) {
    }

    @Override
    public void die(DamageSource damageSource) {
        Level level = this.level();
        if (level instanceof ServerLevel serverLevel) {
            this.dropAllDeathLoot(serverLevel, damageSource);
        }
        this.discard();
    }

    @Override
    public int maxTicks() {
        return 3000;
    }

    @Override
    public DinopediaInformation getDinopediaInformation(Player player) {
        Component status = this.canIncubate() ? this.canIncubateMessage() : this.cannotIncubateMessage();
        Component ticksRemaining = Component.literal((int) Math.floor(((float) this.getRemainingTicks() / (float) this.maxTicks()) * 100) + "%").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY);
        return DinopediaInformation.builder().addEntityName(this).addLine(FCCoreUtils.translationWithArguments("dinopedia", "status", status.copy().withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY), FCCoreUtils.translationWithArguments("dinopedia", "remaining_ticks", ticksRemaining).copy().withStyle(ChatFormatting.GRAY)).build();
    }
}
