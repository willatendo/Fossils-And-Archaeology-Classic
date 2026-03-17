package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.server.entity.FCDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownJavelin extends AbstractArrow {
    private ItemStack javelinItemStack;
    private float damage = 1.0F;
    private boolean generateLightning = false;

    public ThrownJavelin(EntityType<ThrownJavelin> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownJavelin(EntityType<ThrownJavelin> entityType, Level level, LivingEntity livingEntity, ItemStack itemStack, float damage, boolean generateLightning) {
        super(entityType, livingEntity, level, itemStack, null);
        this.javelinItemStack = itemStack.copy();
        this.damage = damage;
        this.generateLightning = generateLightning;
    }

    @Override
    public void saveWithoutId(ValueOutput valueOutput) {
        super.saveWithoutId(valueOutput);
        valueOutput.putFloat("damage", this.damage);
        valueOutput.putBoolean("generate_lightning", this.generateLightning);
        valueOutput.store("javelin_item_stack", ItemStack.CODEC, this.javelinItemStack);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putFloat("damage", this.damage);
        valueOutput.putBoolean("generate_lightning", this.generateLightning);
        valueOutput.store("javelin_item_stack", ItemStack.CODEC, this.javelinItemStack);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.damage = valueInput.getFloatOr("damage", 1.0F);
        this.generateLightning = valueInput.getBooleanOr("generate_lightning", false);
        this.javelinItemStack = valueInput.read("javelin_item_stack", ItemStack.CODEC).orElse(null);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Entity owner = this.getOwner();
        DamageSource damageSource = new DamageSource(this.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(FCDamageTypes.JAVELIN));
        SoundEvent soundevent = SoundEvents.ARROW_HIT;
        if (entity.hurtOrSimulate(damageSource, this.damage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity) {
                this.doPostHurtEffects(livingEntity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float soundLevel = 1.0F;
        if (this.level() instanceof ServerLevel serverLevel) {
            BlockPos blockPos = entity.blockPosition();
            if (serverLevel.canSeeSky(blockPos) && this.generateLightning) {
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level(), EntitySpawnReason.EVENT);
                if (lightningbolt != null) {
                    lightningbolt.snapTo(Vec3.atBottomCenterOf(blockPos));
                    lightningbolt.setCause(owner instanceof ServerPlayer serverPlayer ? serverPlayer : null);
                    serverLevel.addFreshEntity(lightningbolt);
                    soundevent = SoundEvents.LIGHTNING_BOLT_THUNDER;
                    soundLevel = 5.0F;
                }
            }
        }
        this.playSound(soundevent, soundLevel, 1.0F);
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.javelinItemStack;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return this.javelinItemStack;
    }
}
