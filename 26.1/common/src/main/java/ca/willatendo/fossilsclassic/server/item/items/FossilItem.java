package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.entities.Fossil;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.item.FCDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class FossilItem extends Item {
    private static final Component TOOLTIP_RANDOM_VARIANT = Component.translatable("fossil.random").withStyle(ChatFormatting.GRAY);

    public FossilItem(Properties properties) {
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
            Optional<List<Holder<FossilVariant>>> fossilVariants = Fossil.checkHasPlaceable(level, relativeBlockPos, itemStack);
            if (fossilVariants.isEmpty()) {
                player.displayClientMessage(FCCoreUtils.translation("item", "fossil.no_space"), true);
                return InteractionResult.CONSUME;
            }
            if (level instanceof ServerLevel serverLevel) {
                FCEntityTypes.FOSSIL.get().spawn(serverLevel, fossilIn -> {
                    RandomSource randomSource = level.getRandom();
                    Util.getRandomSafe(fossilVariants.get(), randomSource).ifPresent(fossilIn::setFossilVariant);
                    fossilIn.setSize(0);
                    fossilIn.playPlacementSound();
                    itemStack.shrink(1);
                }, relativeBlockPos, EntitySpawnReason.SPAWN_ITEM_USE, false, false);
            }
            return InteractionResult.SUCCESS;
        }
    }

    protected boolean mayPlace(Player player, Direction direction, ItemStack hangingEntityStack, BlockPos blockPos) {
        return direction == Direction.UP && player.mayUseItemAt(blockPos, direction, hangingEntityStack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> tooltips, TooltipFlag tooltipFlag) {
        if (tooltipDisplay.shows(FCDataComponents.FOSSIL_VARIANT.get())) {
            Holder<FossilVariant> fossilVariantHolder = itemStack.get(FCDataComponents.FOSSIL_VARIANT.get());
            if (fossilVariantHolder != null) {
                FossilVariant fossilVariant = fossilVariantHolder.value();
                fossilVariant.name().ifPresent(tooltips);
            } else if (tooltipFlag.isCreative()) {
                tooltips.accept(TOOLTIP_RANDOM_VARIANT);
            }
        }
    }
}
