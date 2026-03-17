package ca.willatendo.fossilsclassic.server.entity.utils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface CommandingInformation {
    boolean canCommand(Player player, InteractionHand interactionHand);

    boolean canCommandWithItem(ItemStack itemStack);

    static CommandingInformation none() {
        return new CommandingInformation() {
            @Override
            public boolean canCommand(Player player, InteractionHand interactionHand) {
                return false;
            }

            @Override
            public boolean canCommandWithItem(ItemStack itemStack) {
                return false;
            }
        };
    }

    static CommandingInformation hand() {
        return new CommandingInformation() {
            @Override
            public boolean canCommand(Player player, InteractionHand interactionHand) {
                return player.getItemInHand(interactionHand).isEmpty();
            }

            @Override
            public boolean canCommandWithItem(ItemStack itemStack) {
                return false;
            }
        };
    }

    static CommandingInformation tag(TagKey<Item> tag) {
        return new CommandingInformation() {
            @Override
            public boolean canCommand(Player player, InteractionHand interactionHand) {
                return player.getItemInHand(interactionHand).is(tag);
            }

            @Override
            public boolean canCommandWithItem(ItemStack itemStack) {
                return itemStack.is(tag);
            }
        };
    }
}
