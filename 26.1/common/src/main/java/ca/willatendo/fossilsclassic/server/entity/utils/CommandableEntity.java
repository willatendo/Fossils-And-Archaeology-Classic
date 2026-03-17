package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public interface CommandableEntity {
    Holder<CommandType> getCommand();

    void setCommand(Holder<CommandType> dinosaurOrder);

    CommandingInformation commandingInformation();

    default void cycleCommand() {
        this.setCommand(CommandTypeUtils.getNext(this.getCommand()));
    }

    default boolean willListenToDrum(Player player, InteractionHand interactionHand) {
        return this.commandingInformation().canCommandWithItem(player.getItemInHand(interactionHand));
    }
}
