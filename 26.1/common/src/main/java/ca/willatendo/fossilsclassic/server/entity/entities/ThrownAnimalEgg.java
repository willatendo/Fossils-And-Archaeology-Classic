package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public abstract class ThrownAnimalEgg<T extends AgeableMob> extends ThrowableItemProjectile {
    public ThrownAnimalEgg(EntityType<? extends ThrownAnimalEgg<?>> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownAnimalEgg(EntityType<? extends ThrownAnimalEgg<?>> entityType, Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(entityType, livingEntity, level, itemStack);
    }

    public ThrownAnimalEgg(EntityType<? extends ThrownAnimalEgg<?>> entityType, Level level, double x, double y, double z, ItemStack itemStack) {
        super(entityType, x, y, z, level, itemStack);
    }

    public abstract EntityType<T> getOffspring();

    public abstract boolean isIncubated();

    public abstract void onThrown(T ageableMob);

    @Override
    public void handleEntityEvent(byte event) {
        if (event == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide()) {
            if (this.isIncubated() || this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int animals = 0; animals < i; ++animals) {
                    T t = this.getOffspring().create(this.level(), EntitySpawnReason.TRIGGERED);
                    t.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                    this.onThrown(t);
                    this.level().addFreshEntity(t);
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    public static final class ThrownIncubatedChickenEgg extends ThrownAnimalEgg<Chicken> {
        public ThrownIncubatedChickenEgg(EntityType<? extends ThrownIncubatedChickenEgg> entityType, Level level) {
            super(entityType, level);
        }

        public ThrownIncubatedChickenEgg(Level level, LivingEntity livingEntity, ItemStack itemStack) {
            super(FCEntityTypes.INCUBATED_CHICKEN_EGG.get(), level, livingEntity, itemStack);
        }

        public ThrownIncubatedChickenEgg(Level level, double x, double y, double z, ItemStack itemStack) {
            super(FCEntityTypes.INCUBATED_CHICKEN_EGG.get(), level, x, y, z, itemStack);
        }

        @Override
        public EntityType<Chicken> getOffspring() {
            return EntityType.CHICKEN;
        }

        @Override
        public boolean isIncubated() {
            return true;
        }

        @Override
        public void onThrown(Chicken chicken) {
            if (this.hasCustomName()) {
                chicken.setCustomName(this.getCustomName());
            }
            chicken.setBaby(true);
            chicken.setPersistenceRequired();
        }

        @Override
        protected Item getDefaultItem() {
            return FCItems.INCUBATED_CHICKEN_EGG.get();
        }
    }

    public static final class ThrownIncubatedParrotEgg extends ThrownAnimalEgg<Parrot> {
        public ThrownIncubatedParrotEgg(EntityType<? extends ThrownIncubatedParrotEgg> entityType, Level level) {
            super(entityType, level);
        }

        public ThrownIncubatedParrotEgg(Level level, LivingEntity livingEntity, ItemStack itemStack) {
            super(FCEntityTypes.INCUBATED_PARROT_EGG.get(), level, livingEntity, itemStack);
        }

        public ThrownIncubatedParrotEgg(Level level, double x, double y, double z, ItemStack itemStack) {
            super(FCEntityTypes.INCUBATED_PARROT_EGG.get(), level, x, y, z, itemStack);
        }

        @Override
        public EntityType<Parrot> getOffspring() {
            return EntityType.PARROT;
        }

        @Override
        public boolean isIncubated() {
            return true;
        }

        @Override
        public void onThrown(Parrot parrot) {
            if (this.hasCustomName()) {
                parrot.setCustomName(this.getCustomName());
            }
            parrot.setPersistenceRequired();
        }

        @Override
        protected Item getDefaultItem() {
            return FCItems.INCUBATED_PARROT_EGG.get();
        }
    }
}
