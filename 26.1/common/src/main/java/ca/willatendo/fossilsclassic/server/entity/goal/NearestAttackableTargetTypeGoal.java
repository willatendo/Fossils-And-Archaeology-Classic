package ca.willatendo.fossilsclassic.server.entity.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;

public class NearestAttackableTargetTypeGoal<T extends LivingEntity> extends TargetGoal {
    protected final EntityType<T> targetType;
    protected final int randomInterval;
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public NearestAttackableTargetTypeGoal(Mob mob, EntityType<T> targetType, boolean mustSee) {
        this(mob, targetType, 10, mustSee, false, null);
    }

    public NearestAttackableTargetTypeGoal(Mob mob, EntityType<T> targetType, boolean mustSee, TargetingConditions.Selector selector) {
        this(mob, targetType, 10, mustSee, false, selector);
    }

    public NearestAttackableTargetTypeGoal(Mob mob, EntityType<T> targetType, boolean mustSee, boolean mustReach) {
        this(mob, targetType, 10, mustSee, mustReach, null);
    }

    public NearestAttackableTargetTypeGoal(Mob mob, EntityType<T> targetType, int interval, boolean mustSee, boolean mustReach, TargetingConditions.Selector selector) {
        super(mob, mustSee, mustReach);
        this.targetType = targetType;
        this.randomInterval = reducedTickDelay(interval);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(selector);
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.findTarget();
            return this.target != null;
        }
    }

    protected AABB getTargetSearchArea(double targetDistance) {
        return this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
    }

    protected void findTarget() {
        ServerLevel serverLevel = Goal.getServerLevel(this.mob);
        if (this.targetType != EntityType.PLAYER) {
            this.target = serverLevel.getNearestEntity(this.mob.level().getEntities(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), entity -> true), this.getTargetConditions(), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        } else {
            this.target = serverLevel.getNearestPlayer(this.getTargetConditions(), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    private TargetingConditions getTargetConditions() {
        return this.targetConditions.range(this.getFollowDistance());
    }
}
