package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChickenEssanceBottleItem extends Item {
    public ChickenEssanceBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity interactionTargetLivingEntity, InteractionHand interactionHand) {
        if (interactionTargetLivingEntity instanceof Dinosaur dinosaur) {
            if (dinosaur.getGrowthStage() < dinosaur.getMaxGrowthStage()) {
                dinosaur.grow();
                dinosaur.setHunger(dinosaur.getHunger() - (dinosaur.getMaxHunger() / 10));
                itemStack.shrink(1);
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return super.interactLivingEntity(itemStack, player, interactionTargetLivingEntity, interactionHand);
    }
}
