package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.FCEntityDataSerializers;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.FCFossilVariants;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.item.FCDataComponents;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.fossilsclassic.server.tags.FCFossilVariantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Fossil extends Mob {
    private static final EntityDataAccessor<Holder<FossilVariant>> FOSSIL_VARIANT = SynchedEntityData.defineId(Fossil.class, FCEntityDataSerializers.FOSSIL_VARIANT.get());
    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Fossil.class, EntityDataSerializers.INT);

    public static Optional<List<Holder<FossilVariant>>> checkHasPlaceable(Level level, BlockPos blockPos, ItemStack itemStack) {
        Fossil fossil = new Fossil(level, blockPos);
        List<Holder<FossilVariant>> fossilVariants = new ArrayList<>();
        if (!itemStack.has(FCDataComponents.FOSSIL_VARIANT.get())) {
            Iterable<Holder<FossilVariant>> iterable = level.registryAccess().lookupOrThrow(FCRegistries.FOSSIL_VARIANT).getTagOrEmpty(FCFossilVariantTags.PLACEABLE);
            Objects.requireNonNull(fossilVariants);
            iterable.forEach(fossilVariants::add);
        } else {
            fossilVariants.add(itemStack.get(FCDataComponents.FOSSIL_VARIANT.get()));
        }
        if (fossilVariants.isEmpty()) {
            return Optional.empty();
        } else {
            fossilVariants.removeIf(fossilVariantHolder -> {
                fossil.setFossilVariant(fossilVariantHolder);
                fossil.setSize(0);
                return !fossil.hasPlaceSpace();
            });
            if (fossilVariants.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(fossilVariants);
            }
        }
    }

    public Fossil(EntityType<? extends Fossil> entityType, Level level) {
        super(entityType, level);
    }

    private Fossil(Level level, BlockPos blockPos) {
        super(FCEntityTypes.FOSSIL.get(), level);
        this.setPos(blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F);
    }

    public static AttributeSupplier fossilAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D).build();
    }

    public void playPlacementSound() {
        this.playSound(SoundEvents.BONE_BLOCK_PLACE, 1.0F, 1.0F);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        FossilVariant fossilVariant = this.getFossilVariant().value();
        return this.dimensions = EntityDimensions.scalable(fossilVariant.boundingBoxBaseWidth() + (fossilVariant.boundingBoxWidthGrowth() * this.getSize()), fossilVariant.boundingBoxBaseHeight() + (fossilVariant.boundingBoxHeightGrowth() * this.getSize()));
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BONE_BLOCK_BREAK;
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    public EntityDimensions getEntityDimensions(int size) {
        FossilVariant fossilVariant = this.getFossilVariant().value();
        return EntityDimensions.scalable(fossilVariant.boundingBoxBaseWidth() + (fossilVariant.boundingBoxWidthGrowth() * size), fossilVariant.boundingBoxBaseHeight() + (fossilVariant.boundingBoxHeightGrowth() * size));
    }

    @Override
    public boolean isBaby() {
        return this.getSize() < this.getFossilVariant().value().maxGrowthStage() / 2;
    }

    @Override
    public void tick() {
        if (!this.isNoAi()) {
            if (this.dimensions.width() != this.getEntityDimensions(this.getSize()).width() || this.dimensions.height() != this.getEntityDimensions(this.getSize()).height()) {
                this.refreshDimensions();
            }
        }
        super.tick();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (SIZE.equals(entityDataAccessor) || FOSSIL_VARIANT.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FCItems.FOSSIL.get());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FOSSIL_VARIANT, VariantUtils.getDefaultOrAny(this.registryAccess(), FCFossilVariants.TRICERATOPS));
        builder.define(SIZE, 0);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        VariantUtils.writeVariant(valueOutput, this.getFossilVariant());
        valueOutput.putInt("size", this.getSize());
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        VariantUtils.readVariant(valueInput, FCRegistries.FOSSIL_VARIANT).ifPresent(this::setFossilVariant);
        this.setSize(valueInput.getIntOr("size", 0));
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.is(Items.BONE)) {
            if (this.getSize() < this.getFossilVariant().value().maxGrowthStage()) {
                if (this.grow(1)) {
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    player.displayClientMessage(FCCoreUtils.translation("entity", "fossil.no_space"), true);
                }
            } else {
                player.displayClientMessage(FCCoreUtils.translation("entity", "fossil.full_size"), true);
            }
        } else if (itemStack.isEmpty()) {
            if (this.getSize() >= 1) {
                this.setSize(this.getSize() - 1);
                if (!player.isCreative()) {
                    player.addItem(new ItemStack(Items.BONE));
                }
                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(FCCoreUtils.translation("entity", "fossil.minimum_size"), true);
            }
        }
        return InteractionResult.PASS;
    }

    public void setFossilVariant(Holder<FossilVariant> fossilVariant) {
        this.entityData.set(FOSSIL_VARIANT, fossilVariant);
    }

    public Holder<FossilVariant> getFossilVariant() {
        return this.entityData.get(FOSSIL_VARIANT);
    }

    public void setSize(int size) {
        this.entityData.set(SIZE, size);
        this.refreshDimensions();
    }

    public int getSize() {
        return this.entityData.get(SIZE);
    }

    private boolean grow(int count) {
        if (this.hasSpaceWhenGrows(count)) {
            this.setSize(this.getSize() + count);
            return true;
        }
        return false;
    }

    public static boolean hasSpace(Level level, FossilVariant fossilVariant, Entity entity, Vec3 pos, int size) {
        EntityDimensions entityDimensions = EntityDimensions.scalable(fossilVariant.boundingBoxBaseWidth() + (fossilVariant.boundingBoxWidthGrowth() * size), fossilVariant.boundingBoxBaseHeight() + (fossilVariant.boundingBoxHeightGrowth() * size));
        return !level.getBlockCollisions(entity, entityDimensions.makeBoundingBox(pos)).iterator().hasNext();
    }

    public boolean hasSpaceWhenGrows(int count) {
        FossilVariant fossilVariant = this.getFossilVariant().value();
        return Fossil.hasSpace(this.level(), fossilVariant, this, this.position(), this.getSize() + count);
    }

    public boolean hasPlaceSpace() {
        return this.hasSpaceWhenGrows(0);
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float damage) {
        if (!damageSource.isCreativePlayer()) {
            if (serverLevel.getGameRules().get(GameRules.ENTITY_DROPS)) {
                if (this.getSize() > 0) {
                    for (int i = 0; i < this.getSize(); i++) {
                        Block.popResource(this.level(), this.blockPosition(), new ItemStack(Items.BONE));
                    }
                }
                Block.popResource(this.level(), this.blockPosition(), new ItemStack(FCItems.FOSSIL.get()));
            }
        }
        this.playHurtSound(damageSource);
        this.remove(RemovalReason.KILLED);
        return true;
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        this.applyImplicitComponentIfPresent(dataComponentGetter, FCDataComponents.FOSSIL_VARIANT.get());
        super.applyImplicitComponents(dataComponentGetter);
    }

    @Override
    protected <T> boolean applyImplicitComponent(DataComponentType<T> dataComponentType, T value) {
        if (dataComponentType == FCDataComponents.FOSSIL_VARIANT.get()) {
            this.setFossilVariant(Fossil.castComponentValue(FCDataComponents.FOSSIL_VARIANT.get(), value));
            return true;
        } else {
            return super.applyImplicitComponent(dataComponentType, value);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason entitySpawnReason, SpawnGroupData spawnGroupData) {
        this.refreshDimensions();
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, entitySpawnReason, spawnGroupData);
    }
}
