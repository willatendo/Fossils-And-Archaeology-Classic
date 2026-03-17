package ca.willatendo.fossilsclassic.server.entity.entities;

import ca.willatendo.fossilsclassic.server.entity.FCEntityDataSerializers;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.item.FCDataComponents;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.fossilsclassic.server.tags.FCStoneTabletVariantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StoneTablet extends HangingEntity {
    private static final EntityDataAccessor<Holder<StoneTabletVariant>> STONE_TABLET_VARIANT = SynchedEntityData.defineId(StoneTablet.class, FCEntityDataSerializers.STONE_TABLET_VARIANT.get());

    public static Optional<StoneTablet> create(Level level, BlockPos blockPos, Direction direction) {
        StoneTablet stoneTablet = new StoneTablet(level, blockPos);
        List<Holder<StoneTabletVariant>> stoneTabletVariants = new ArrayList<>();
        Iterable<Holder<StoneTabletVariant>> iterable = level.registryAccess().lookupOrThrow(FCRegistries.STONE_TABLET_VARIANT).getTagOrEmpty(FCStoneTabletVariantTags.PLACEABLE);
        Objects.requireNonNull(stoneTabletVariants);
        iterable.forEach(stoneTabletVariants::add);
        if (stoneTabletVariants.isEmpty()) {
            return Optional.empty();
        } else {
            stoneTablet.setDirection(direction);
            stoneTabletVariants.removeIf(stoneTabletVariantHolder -> {
                stoneTablet.setVariant(stoneTabletVariantHolder);
                return !stoneTablet.survives();
            });
            if (stoneTabletVariants.isEmpty()) {
                return Optional.empty();
            } else {
                int area = stoneTabletVariants.stream().mapToInt(StoneTablet::variantArea).max().orElse(0);
                stoneTabletVariants.removeIf(stoneTabletVariantHolder -> StoneTablet.variantArea(stoneTabletVariantHolder) < area);
                Optional<Holder<StoneTabletVariant>> optionalStoneTabletVariant = Util.getRandomSafe(stoneTabletVariants, stoneTablet.random);
                if (optionalStoneTabletVariant.isEmpty()) {
                    return Optional.empty();
                } else {
                    stoneTablet.setVariant(optionalStoneTabletVariant.get());
                    stoneTablet.setDirection(direction);
                    return Optional.of(stoneTablet);
                }
            }
        }
    }

    private static int variantArea(Holder<StoneTabletVariant> stoneTabletVariantHolder) {
        return stoneTabletVariantHolder.value().area();
    }

    public StoneTablet(EntityType<? extends StoneTablet> entityType, Level level) {
        super(entityType, level);
    }

    private StoneTablet(Level level, BlockPos blockPos) {
        super(FCEntityTypes.STONE_TABLET.get(), level, blockPos);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STONE_TABLET_VARIANT, VariantUtils.getAny(this.registryAccess(), FCRegistries.STONE_TABLET_VARIANT));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        super.onSyncedDataUpdated(entityDataAccessor);
        if (STONE_TABLET_VARIANT.equals(entityDataAccessor)) {
            this.recalculateBoundingBox();
        }
    }

    private void setVariant(Holder<StoneTabletVariant> stoneTabletVariant) {
        this.entityData.set(STONE_TABLET_VARIANT, stoneTabletVariant);
    }

    public Holder<StoneTabletVariant> getVariant() {
        return this.entityData.get(STONE_TABLET_VARIANT);
    }

    @Override
    public <T> T get(DataComponentType<? extends T> dataComponentType) {
        return (dataComponentType == FCDataComponents.STONE_TABLET_VARIANT.get() ? StoneTablet.castComponentValue(dataComponentType, this.getVariant()) : super.get(dataComponentType));
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        this.applyImplicitComponentIfPresent(dataComponentGetter, FCDataComponents.STONE_TABLET_VARIANT.get());
        super.applyImplicitComponents(dataComponentGetter);
    }

    @Override
    protected <T> boolean applyImplicitComponent(DataComponentType<T> dataComponentType, T value) {
        if (dataComponentType == FCDataComponents.STONE_TABLET_VARIANT.get()) {
            this.setVariant(StoneTablet.castComponentValue(FCDataComponents.STONE_TABLET_VARIANT.get(), value));
            return true;
        } else {
            return super.applyImplicitComponent(dataComponentType, value);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.store("facing", Direction.CODEC, this.getDirection());
        super.addAdditionalSaveData(valueOutput);
        VariantUtils.writeVariant(valueOutput, this.getVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        Direction direction = valueInput.read("facing", Direction.CODEC).orElse(Direction.SOUTH);
        super.readAdditionalSaveData(valueInput);
        this.setDirection(direction);
        VariantUtils.readVariant(valueInput, FCRegistries.STONE_TABLET_VARIANT).ifPresent(this::setVariant);
    }

    @Override
    protected AABB calculateBoundingBox(BlockPos blockPos, Direction direction) {
        Vec3 blockCenter = Vec3.atCenterOf(blockPos).relative(direction, -0.46875F);
        StoneTabletVariant stoneTabletVariant = this.getVariant().value();
        double widthOffset = this.offsetForPaintingSize(stoneTabletVariant.width());
        double heightOffset = this.offsetForPaintingSize(stoneTabletVariant.height());
        Direction directionCounterClockWise = direction.getCounterClockWise();
        Vec3 center = blockCenter.relative(directionCounterClockWise, widthOffset).relative(Direction.UP, heightOffset);
        Direction.Axis axis = direction.getAxis();
        double xSize = axis == Direction.Axis.X ? (double) 0.0625F : (double) stoneTabletVariant.width();
        double ySize = stoneTabletVariant.height();
        double zSize = axis == Direction.Axis.Z ? (double) 0.0625F : (double) stoneTabletVariant.width();
        return AABB.ofSize(center, xSize, ySize, zSize);
    }

    private double offsetForPaintingSize(int p_480598_) {
        return p_480598_ % 2 == 0 ? (double) 0.5F : (double) 0.0F;
    }

    @Override
    public void dropItem(ServerLevel serverLevel, Entity entity) {
        if (serverLevel.getGameRules().get(GameRules.ENTITY_DROPS)) {
            this.playSound(SoundEvents.STONE_BREAK, 1.0F, 1.0F);
            if (entity instanceof Player player) {
                if (player.hasInfiniteMaterials()) {
                    return;
                }
            }

            this.spawnAtLocation(serverLevel, FCItems.STONE_TABLET.get());
        }

    }

    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.STONE_PLACE, 1.0F, 1.0F);
    }

    @Override
    public void snapTo(double x, double y, double z, float yRot, float xRot) {
        this.setPos(x, y, z);
    }

    @Override
    public Vec3 trackingPosition() {
        return Vec3.atLowerCornerOf(this.pos);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, this.getDirection().get3DDataValue(), this.getPos());
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        this.setDirection(Direction.from3DDataValue(clientboundAddEntityPacket.getData()));
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(FCItems.STONE_TABLET.get());
    }
}
