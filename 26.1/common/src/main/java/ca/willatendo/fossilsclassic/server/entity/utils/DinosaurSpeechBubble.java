package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public enum DinosaurSpeechBubble implements SpeechBubble {
    HUNGRY("hungry"),
    STARVE("starve"),
    LEARNED_CHESTS("learned_chests"),
    TAME_WITH_TREAT("tame_with_treat"),
    NO_SPACE("no_space"),
    STARVE_ESCAPE("starve_escape"),
    HURT_ESCAPE("hurt_escape"),
    FULL("full"),
    TAME_TYRANNOSAURUS_ERROR_TOO_YOUNG("tame_tyrannosaurus_error_too_young"),
    TAME_TYRANNOSAURUS_ERROR_HEALTH("tame_tyrannosaurus_error_health");

    private final Function<LivingEntity, Component> message;
    private final String translationKey;

    DinosaurSpeechBubble(Function<LivingEntity, Component> message, String translationKey) {
        this.message = message;
        this.translationKey = translationKey;
    }

    DinosaurSpeechBubble(String id) {
        this(DinosaurSpeechBubble.basicSpeach(id), "speech_bubble." + FCCoreUtils.ID + "." + id);
    }

    private static Function<LivingEntity, Component> basicSpeach(String id) {
        return livingEntity -> FCCoreUtils.translationWithArguments("speech_bubble", id, livingEntity.getDisplayName());
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public Component getMessage(Player player, LivingEntity livingEntity) {
        return this.message.apply(livingEntity);
    }
}
