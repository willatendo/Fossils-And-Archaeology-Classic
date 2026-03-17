package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface TameAccessor extends OwnableEntity {
    Level level();

    void setOwner(LivingEntity owner);

    void setOwnerReference(EntityReference<LivingEntity> owner);

    boolean isTame();

    boolean tamesAtBirth();

    default void setOwner(LivingEntity owner, boolean treat) {
        if (treat) {
            if (this instanceof Dinosaur dinosaur) {
                if (this.level().getPlayerByUUID(owner.getUUID()) instanceof ServerPlayer serverPlayer) {
                    dinosaur.sendMessageToPlayer(DinosaurSpeechBubble.TAME_WITH_TREAT, serverPlayer);
                }
            } else {
                Player player = this.level().getPlayerByUUID(owner.getUUID());
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.sendSystemMessage(DinosaurSpeechBubble.TAME_WITH_TREAT.getMessage(player, (LivingEntity) this));
                }
            }
        }
        this.setOwner(owner);
    }
}
