package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.attachment_type.FCAttachmentTypes;
import ca.willatendo.fossilsclassic.server.attachment_type.attachment_types.Pregnancy;
import ca.willatendo.fossilsclassic.server.entity.utils.DinopediaInteractable;
import ca.willatendo.simplelibrary.core.utils.AttachmentTypeUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

public class DinopediaItem extends Item {
    private static final Function<Pregnancy, Integer> REMAINING = pregnancy -> (int) Math.floor(((float) pregnancy.getRemainingTicks() / (float) pregnancy.getMaxTicks()) * 100);
    private static final Function<LivingEntity, List<Component>> PREGNANCY_LINES = livingEntity -> List.of(FCCoreUtils.translationWithArguments("dinopedia", "name", livingEntity.getDisplayName().copy().withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD)), FCCoreUtils.translationWithArguments("dinopedia", "health", Component.literal((int) livingEntity.getHealth() + "").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY), Component.literal((int) livingEntity.getMaxHealth() + "").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY), FCCoreUtils.translationWithArguments("dinopedia", "remaining_ticks", Component.literal(REMAINING.apply(AttachmentTypeUtils.getData(livingEntity, FCAttachmentTypes.PREGNANCY)) + "%").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY), FCCoreUtils.translationWithArguments("dinopedia", "embryo_type", ((Pregnancy) AttachmentTypeUtils.getData(livingEntity, FCAttachmentTypes.PREGNANCY)).getDisplayName().copy().withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY));

    public DinopediaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity instanceof DinopediaInteractable dinopediaInteractable) {
            return this.use(player, itemStack, dinopediaInteractable.getDinopediaInformation(player).getLines());
        }
        if (AttachmentTypeUtils.hasData(livingEntity, FCAttachmentTypes.PREGNANCY)) {
            return this.use(player, itemStack, PREGNANCY_LINES.apply(livingEntity));
        }

        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }

    private InteractionResult use(Player player, ItemStack itemStack, List<Component> components) {
        if (player.level() instanceof ServerLevel) {
            return InteractionResult.SUCCESS_SERVER;
        }

        player.getCooldowns().addCooldown(itemStack, 10);
        for (Component component : components) {
            player.displayClientMessage(component, false);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }
}
