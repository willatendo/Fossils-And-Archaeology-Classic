package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;

public class DebugToolItem extends Item {
    private final BiConsumer<Player, Dinosaur> consumer;

    public DebugToolItem(BiConsumer<Player, Dinosaur> consumer, Properties properties) {
        super(properties);
        this.consumer = consumer;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity interactionTargetLivingEntity, InteractionHand interactionHand) {
        if (interactionTargetLivingEntity instanceof Dinosaur dinosaur) {
            this.consumer.accept(player, dinosaur);
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(itemStack, player, interactionTargetLivingEntity, interactionHand);
    }
}
