package ca.willatendo.fossilsclassic.server.entity.goal;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import net.minecraft.world.entity.ai.goal.BreedGoal;

public class DinosaurBreedGoal extends BreedGoal {
    private final Dinosaur dinosaur;
    private final double speedModifier;

    public DinosaurBreedGoal(Dinosaur dinosaur, double speedModifier) {
        super(dinosaur, speedModifier);
        this.dinosaur = dinosaur;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        return !this.dinosaur.isHungry() && super.canUse();
    }

    @Override
    public void tick() {
        this.animal.getLookControl().setLookAt(this.partner, 10.0F, (float) this.animal.getMaxHeadXRot());
        this.animal.getNavigation().moveTo(this.partner, this.speedModifier);
        ++this.loveTime;
        if (this.loveTime >= this.adjustedTickDelay(60) && this.animal.distanceToSqr(this.partner) < (9.0F + (0.5F * this.dinosaur.getGrowthStage()))) {
            this.breed();
        }
    }
}
