package ca.willatendo.fossilsclassic.server.entity.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface SpeechBubble {
    String getTranslationKey();

    Component getMessage(Player player, LivingEntity livingEntity);
}
