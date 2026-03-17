package ca.willatendo.fossilsclassic.server.utils;

import ca.willatendo.fossilsclassic.server.entity.utils.GrowingEntity;
import ca.willatendo.fossilsclassic.server.entity.utils.TameAccessor;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class BirthUtils {
    private BirthUtils() {
    }

    public static void birthTick(int remainingTicks, int maxTicks, ServerLevel serverLevel, EntityType<? extends Mob> offspringMobEntityType, Mob parentMob, Optional<EntityReference<LivingEntity>> owner, BiConsumer<Mob, Mob> onTicksCompleted, Consumer<Integer> onTick, Consumer<Integer> setRemainingTicks) {
        if (remainingTicks >= maxTicks) {
            int count = 1;
            if (serverLevel.getRandom().nextInt(100) == 1) {
                count = 2;
            } else if (serverLevel.getRandom().nextInt(1000) == 1) {
                count = 3;
            } else if (serverLevel.getRandom().nextInt(10000) == 1) {
                count = 4;
            }
            for (int i = 0; i < count; i++) {
                offspringMobEntityType.spawn(serverLevel, offspringMob -> {
                    if (offspringMob instanceof GrowingEntity growingEntity) {
                        growingEntity.setGrowthStage(0, true);
                    }
                    if (offspringMob instanceof Animal animal) {
                        animal.setBaby(true);
                    }
                    if (offspringMob instanceof TameAccessor tameAccessor) {
                        if (owner.isEmpty()) {
                            if (tameAccessor.tamesAtBirth()) {
                                Player player = serverLevel.getNearestPlayer(offspringMob, 25.0D);
                                if (player != null) {
                                    tameAccessor.setOwner(player);
                                }
                                if (player instanceof ServerPlayer serverPlayer && tameAccessor instanceof Animal animal) {
                                    CriteriaTriggers.TAME_ANIMAL.trigger(serverPlayer, animal);
                                }
                            }
                        } else {
                            tameAccessor.setOwnerReference(owner.get());
                        }
                    }
                    onTicksCompleted.accept(parentMob, offspringMob);
                }, parentMob.blockPosition(), EntitySpawnReason.BREEDING, false, false);
            }
        } else {
            setRemainingTicks.accept(remainingTicks + 1);
            onTick.accept(remainingTicks + 1);
        }
    }
}
