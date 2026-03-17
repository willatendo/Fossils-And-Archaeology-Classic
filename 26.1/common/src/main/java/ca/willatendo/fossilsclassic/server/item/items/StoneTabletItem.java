package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.entities.StoneTablet;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.fossilsclassic.server.item.FCDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.function.Consumer;

public class StoneTabletItem extends Item {
    private static final Component TOOLTIP_RANDOM_VARIANT = Component.translatable("stone_tablet.random").withStyle(ChatFormatting.GRAY);

    public StoneTabletItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        BlockPos blockPos = useOnContext.getClickedPos();
        Direction direction = useOnContext.getClickedFace();
        BlockPos relativeBlockPos = blockPos.relative(direction);
        Player player = useOnContext.getPlayer();
        ItemStack itemStack = useOnContext.getItemInHand();
        if (player != null && !this.mayPlace(player, direction, itemStack, relativeBlockPos)) {
            return InteractionResult.FAIL;
        } else {
            Level level = useOnContext.getLevel();
            Optional<StoneTablet> optionalStoneTablet = StoneTablet.create(level, relativeBlockPos, direction);
            if (optionalStoneTablet.isEmpty()) {
                return InteractionResult.CONSUME;
            }
            StoneTablet stoneTablet = optionalStoneTablet.get();
            EntityType.createDefaultStackConfig(level, itemStack, player).accept(stoneTablet);
            if (stoneTablet.survives()) {
                if (!level.isClientSide()) {
                    stoneTablet.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, stoneTablet.position());
                    level.addFreshEntity(stoneTablet);
                }

                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected boolean mayPlace(Player player, Direction direction, ItemStack hangingEntityStack, BlockPos blockPos) {
        return !direction.getAxis().isVertical() && player.mayUseItemAt(blockPos, direction, hangingEntityStack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> tooltips, TooltipFlag tooltipFlag) {
        if (tooltipDisplay.shows(FCDataComponents.STONE_TABLET_VARIANT.get())) {
            Holder<StoneTabletVariant> stoneTabletVariantHolder = itemStack.get(FCDataComponents.STONE_TABLET_VARIANT.get());
            if (stoneTabletVariantHolder != null) {
                StoneTabletVariant stoneTabletVariant = stoneTabletVariantHolder.value();
                stoneTabletVariant.title().ifPresent(tooltips);
                stoneTabletVariant.author().ifPresent(tooltips);
                tooltips.accept(Component.translatable("stone_tablet.dimensions", stoneTabletVariant.width(), stoneTabletVariant.height()));
            } else if (tooltipFlag.isCreative()) {
                tooltips.accept(TOOLTIP_RANDOM_VARIANT);
            }
        }
    }
}
