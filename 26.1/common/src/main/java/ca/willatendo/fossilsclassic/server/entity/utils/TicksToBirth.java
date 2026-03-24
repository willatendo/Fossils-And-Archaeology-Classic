package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.game_rules.FCGameRules;
import ca.willatendo.fossilsclassic.server.utils.BirthUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;

import java.util.Optional;

public interface TicksToBirth {
    Component killMessage();

    EntityType<? extends Mob> getOffspring();

    int getRemainingTicks();

    void setRemainingTicks(int remainingTicks);

    int maxTicks();

    boolean canIncubate();

    void kill();

    default void onEntityTicksComplete(Mob parentMob, Mob offspringMob, ServerLevel serverLevel) {
        offspringMob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(parentMob.blockPosition()), EntitySpawnReason.BREEDING, null);
        parentMob.remove(Entity.RemovalReason.DISCARDED);
    }

    default void birthTick(Mob parentMob, ServerLevel serverLevel, Optional<EntityReference<LivingEntity>> owner) {
        if (this.canIncubate()) {
            BirthUtils.birthTick(this.getRemainingTicks(), this.maxTicks(), serverLevel, this.getOffspring(), parentMob, owner, (parentMobIn, offspringMobIn) -> this.onEntityTicksComplete(parentMobIn, offspringMobIn, serverLevel), remainingTicks -> {
            }, this::setRemainingTicks);
        } else {
            if (this.getRemainingTicks() <= -((this.maxTicks() / 25) * 5)) {
                serverLevel.getPlayers(serverPlayer -> true, serverLevel.getGameRules().get(FCGameRules.NOTIFICATION_DISTANCE.get())).forEach(serverPlayer -> serverPlayer.sendSystemMessage(this.killMessage()));
                this.kill();
            } else {
                this.setRemainingTicks(this.getRemainingTicks() - 1);
            }
        }
    }
}
