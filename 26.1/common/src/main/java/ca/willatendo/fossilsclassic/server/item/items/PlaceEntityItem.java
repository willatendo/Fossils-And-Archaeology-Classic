package ca.willatendo.fossilsclassic.server.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.function.Supplier;

public class PlaceEntityItem<T extends Entity> extends Item {
    private final Supplier<EntityType<T>> entityType;

    public PlaceEntityItem(Supplier<EntityType<T>> entityType, Item.Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    public Supplier<EntityType<T>> getEntityType() {
        return this.entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            ItemStack itemStack = useOnContext.getItemInHand();
            BlockPos blockPos = useOnContext.getClickedPos();
            Direction direction = useOnContext.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);
            BlockPos placeBlockPos;
            if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                placeBlockPos = blockPos;
            } else {
                placeBlockPos = blockPos.relative(direction);
            }

            if (this.entityType.get() != null) {
                Player player = useOnContext.getPlayer();
                this.entityType.get().spawn(serverLevel, entity -> this.entityPlaceModification(itemStack, entity), placeBlockPos, EntitySpawnReason.SPAWN_ITEM_USE, false, false);
                itemStack.shrink(1);
                player.awardStat(Stats.ITEM_USED.get(this));
                level.gameEvent(player, GameEvent.ENTITY_PLACE, placeBlockPos);
            }

            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    public void entityPlaceModification(ItemStack itemStack, T entity) {
    }
}
