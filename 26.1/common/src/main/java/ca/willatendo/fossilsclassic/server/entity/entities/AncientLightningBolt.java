package ca.willatendo.fossilsclassic.server.entity.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class AncientLightningBolt extends LightningBolt implements OwnableEntity {
    protected static final EntityDataAccessor<Byte> FLAGS = SynchedEntityData.defineId(AncientLightningBolt.class, EntityDataSerializers.BYTE);
    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(AncientLightningBolt.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);

    public AncientLightningBolt(EntityType<? extends AncientLightningBolt> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FLAGS, (byte) 0);
        builder.define(OWNER, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        EntityReference<LivingEntity> ownerReference = this.getOwnerReference();
        EntityReference.store(ownerReference, valueOutput, "Owner");
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        EntityReference<LivingEntity> ownerReference = EntityReference.readWithOldOwnerConversion(valueInput, "Owner", this.level());
        if (ownerReference != null) {
            this.entityData.set(OWNER, Optional.of(ownerReference));
        } else {
            this.entityData.set(OWNER, Optional.empty());
        }
    }

    @Override
    public EntityReference<LivingEntity> getOwnerReference() {
        return this.entityData.get(OWNER).orElse(null);
    }

    public void setOwner(LivingEntity owner) {
        this.entityData.set(OWNER, Optional.ofNullable(owner).map(EntityReference::of));
    }

    public boolean hasOwner() {
        return this.getOwnerReference() != null;
    }
}
