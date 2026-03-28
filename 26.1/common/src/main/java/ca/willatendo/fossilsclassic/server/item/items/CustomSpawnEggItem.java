package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.stats.Stats;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class CustomSpawnEggItem extends Item {
    public CustomSpawnEggItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = useOnContext.getItemInHand();
            BlockPos blockPos = useOnContext.getClickedPos();
            Direction direction = useOnContext.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof Spawner spawner) {
                EntityType<?> entityType = this.getType(itemStack);
                if (entityType == null) {
                    return InteractionResult.FAIL;
                } else if (!serverLevel.isSpawnerBlockEnabled()) {
                    Player player = useOnContext.getPlayer();
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.sendSystemMessage(Component.translatable("advMode.notEnabled.spawner"));
                    }

                    return InteractionResult.FAIL;
                } else {
                    spawner.setEntityId(entityType, level.getRandom());
                    level.sendBlockUpdated(blockPos, blockState, blockState, 3);
                    level.gameEvent(useOnContext.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                    itemStack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            } else {
                BlockPos placeBlockPos;
                if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                    placeBlockPos = blockPos;
                } else {
                    placeBlockPos = blockPos.relative(direction);
                }

                useOnContext.getPlayer().sendSystemMessage(Component.literal("IS: " + useOnContext.getItemInHand().get(FCDataComponents.CHROMOSOME.get())));
                return this.spawnMob(useOnContext.getPlayer(), itemStack, level, placeBlockPos, true, !Objects.equals(blockPos, placeBlockPos) && direction == Direction.UP);
            }
        }
    }

    private InteractionResult spawnMob(LivingEntity source, ItemStack itemStack, Level level, BlockPos pos, boolean shouldOffsetY, boolean shouldOffsetYMore) {
        EntityType<?> entityType = this.getType(itemStack);
        if (entityType == null) {
            if (source instanceof Player player) {
                player.sendOverlayMessage(FCCoreUtils.translation("item", "custom_spawn_egg.no_entity").copy().withStyle(ChatFormatting.RED));
            }
            return InteractionResult.FAIL;
        } else if (!entityType.isAllowedInPeaceful() && level.getDifficulty() == Difficulty.PEACEFUL) {
            return InteractionResult.FAIL;
        } else {
            if (entityType.spawn((ServerLevel) level, itemStack, source, pos, EntitySpawnReason.SPAWN_ITEM_USE, shouldOffsetY, shouldOffsetYMore) != null) {
                itemStack.consume(1, source);
                level.gameEvent(source, GameEvent.ENTITY_PLACE, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public InteractionResult use(Level p_43225_, Player p_43226_, InteractionHand p_43227_) {
        ItemStack itemstack = p_43226_.getItemInHand(p_43227_);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(p_43225_, p_43226_, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        } else if (p_43225_ instanceof ServerLevel) {
            BlockPos $$7 = blockhitresult.getBlockPos();
            if (!(p_43225_.getBlockState($$7).getBlock() instanceof LiquidBlock)) {
                return InteractionResult.PASS;
            } else if (p_43225_.mayInteract(p_43226_, $$7) && p_43226_.mayUseItemAt($$7, blockhitresult.getDirection(), itemstack)) {
                InteractionResult interactionresult = this.spawnMob(p_43226_, itemstack, p_43225_, $$7, false, false);
                if (interactionresult == InteractionResult.SUCCESS) {
                    p_43226_.awardStat(Stats.ITEM_USED.get(this));
                }

                return interactionresult;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    public boolean spawnsEntity(ItemStack stack, EntityType<?> entityType) {
        return Objects.equals(this.getType(stack), entityType);
    }

    public @Nullable EntityType<?> getType(ItemStack stack) {
        TypedEntityData<EntityType<?>> typedentitydata = stack.get(DataComponents.ENTITY_DATA);
        return typedentitydata != null ? typedentitydata.type() : null;
    }

    public Optional<Mob> spawnOffspringFromSpawnEgg(Player player, Mob mob, EntityType<? extends Mob> entityType, ServerLevel serverLevel, Vec3 pos, ItemStack itemStack) {
        if (!this.spawnsEntity(itemStack, entityType)) {
            return Optional.empty();
        } else {
            Mob offspringMob;
            if (mob instanceof AgeableMob ageableMob) {
                offspringMob = ageableMob.getBreedOffspring(serverLevel, ageableMob);
            } else {
                offspringMob = entityType.create(serverLevel, EntitySpawnReason.SPAWN_ITEM_USE);
            }

            if (offspringMob == null) {
                return Optional.empty();
            } else {
                offspringMob.setBaby(true);
                if (!offspringMob.isBaby()) {
                    return Optional.empty();
                } else {
                    offspringMob.snapTo(pos.x(), pos.y(), pos.z(), 0.0F, 0.0F);
                    offspringMob.applyComponentsFromItemStack(itemStack);
                    serverLevel.addFreshEntityWithPassengers(offspringMob);
                    itemStack.consume(1, player);
                    return Optional.of(offspringMob);
                }
            }
        }
    }

    public boolean shouldPrintOpWarning(ItemStack itemStack, Player player) {
        if (player != null && player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
            TypedEntityData<EntityType<?>> typeTypedEntityData = itemStack.get(DataComponents.ENTITY_DATA);
            if (typeTypedEntityData != null) {
                return typeTypedEntityData.type().onlyOpCanSetNbt();
            }
        }

        return false;
    }
}
