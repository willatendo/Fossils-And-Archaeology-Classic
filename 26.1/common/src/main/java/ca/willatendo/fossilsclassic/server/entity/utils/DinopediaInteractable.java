package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.item.dinopedia.DinopediaInformation;
import net.minecraft.world.entity.player.Player;

public interface DinopediaInteractable {
    DinopediaInformation getDinopediaInformation(Player player);
}
