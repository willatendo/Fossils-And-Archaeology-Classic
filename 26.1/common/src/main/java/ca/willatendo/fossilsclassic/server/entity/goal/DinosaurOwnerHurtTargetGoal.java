package ca.willatendo.fossilsclassic.server.entity.goal;

import ca.willatendo.fossilsclassic.server.entity.command_type.FCCommandTypes;
import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class DinosaurOwnerHurtTargetGoal extends TargetGoal {
    private final Dinosaur dinosaur;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public DinosaurOwnerHurtTargetGoal(Dinosaur dinosaur) {
        super(dinosaur, false);
        this.dinosaur = dinosaur;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.dinosaur.isTame() && !this.dinosaur.getCommand().is(FCCommandTypes.STAY)) {
            LivingEntity owner = this.dinosaur.getOwner();
            if (owner == null) {
                return false;
            } else {
                this.ownerLastHurt = owner.getLastHurtMob();
                int i = owner.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity owner = this.dinosaur.getOwner();
        if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
