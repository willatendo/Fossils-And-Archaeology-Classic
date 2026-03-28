package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.attachment_type.FCAttachmentTypes;
import ca.willatendo.fossilsclassic.server.attachment_type.attachment_types.Pregnancy;
import ca.willatendo.fossilsclassic.server.tags.FCEntityTypeTags;
import ca.willatendo.simplelibrary.core.utils.AttachmentTypeUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;

public class SyringeItem extends Item {
    public SyringeItem(Properties properties) {
        super(properties);
    }

    public EntityType<?> getType(ItemStack itemStack) {
        TypedEntityData<EntityType<?>> typedEntityData = itemStack.get(DataComponents.ENTITY_DATA);
        return typedEntityData != null ? typedEntityData.type() : null;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity.getType().builtInRegistryHolder().is(FCEntityTypeTags.MAMMALS)) {
            if (livingEntity instanceof AgeableMob ageableMob) {
                if (!ageableMob.isBaby()) {
                    return this.applyPregnancy(itemStack, player, livingEntity);
                }
            } else {
                return this.applyPregnancy(itemStack, player, livingEntity);
            }
        }
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }

    private InteractionResult applyPregnancy(ItemStack itemStack, Player player, LivingEntity livingEntity) {
        if (!AttachmentTypeUtils.hasData(livingEntity, FCAttachmentTypes.PREGNANCY)) {
            Pregnancy pregnancy = new Pregnancy(Component.translatable(this.descriptionId + ".embryo_name"), 3000, (EntityType<? extends Mob>) this.getType(itemStack));
            AttachmentTypeUtils.setData(livingEntity, FCAttachmentTypes.PREGNANCY, pregnancy);
            itemStack.shrink(1);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }
}
