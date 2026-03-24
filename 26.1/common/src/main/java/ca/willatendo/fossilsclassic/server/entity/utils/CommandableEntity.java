package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
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
        boolean tameFlag = true;
        if (this instanceof Dinosaur dinosaur) {
            tameFlag = dinosaur.isOwnedBy(player);
        }
        return tameFlag && this.commandingInformation().canCommandWithItem(player.getItemInHand(interactionHand));
    }
}
