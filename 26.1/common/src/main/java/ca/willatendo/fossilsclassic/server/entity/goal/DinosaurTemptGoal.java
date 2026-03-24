package ca.willatendo.fossilsclassic.server.entity.goal;

import ca.willatendo.fossilsclassic.server.entity.command_type.FCCommandTypes;
import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class DinosaurTemptGoal extends TemptGoal {
    private final Dinosaur dinosaur;

    public DinosaurTemptGoal(Dinosaur dinosaur, double speedModifier, Predicate<ItemStack> items, boolean canScare) {
        super(dinosaur, speedModifier, items, canScare);
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
