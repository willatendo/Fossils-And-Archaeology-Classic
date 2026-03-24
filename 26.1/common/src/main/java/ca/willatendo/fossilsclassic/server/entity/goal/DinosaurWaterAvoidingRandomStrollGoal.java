package ca.willatendo.fossilsclassic.server.entity.goal;

import ca.willatendo.fossilsclassic.server.entity.command_type.FCCommandTypes;
import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class DinosaurWaterAvoidingRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
    private final Dinosaur dinosaur;

    public DinosaurWaterAvoidingRandomStrollGoal(Dinosaur dinosaur, double speedModifier) {
        super(dinosaur, speedModifier);
        this.dinosaur = dinosaur;
    }

    @Override
    public boolean canUse() {
        return this.dinosaur.getCommand().is(FCCommandTypes.FREE_MOVE) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.dinosaur.getCommand().is(FCCommandTypes.FREE_MOVE) && super.canContinueToUse();
    }
}
