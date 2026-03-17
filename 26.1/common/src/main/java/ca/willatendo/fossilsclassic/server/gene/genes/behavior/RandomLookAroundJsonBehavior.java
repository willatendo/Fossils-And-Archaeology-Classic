package ca.willatendo.fossilsclassic.server.gene.genes.behavior;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.gene.FCJsonBehaviorTypes;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public final class RandomLookAroundJsonBehavior implements JsonBehavior<RandomLookAroundGoal> {
    public static final RandomLookAroundJsonBehavior INSTANCE = new RandomLookAroundJsonBehavior();
    public static final MapCodec<RandomLookAroundJsonBehavior> CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, RandomLookAroundJsonBehavior> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private RandomLookAroundJsonBehavior() {
    }

    @Override
    public <D extends Dinosaur> RandomLookAroundGoal create(D dinosaur) {
        return new RandomLookAroundGoal(dinosaur);
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.RANDOM_LOOK_AROUND.get();
    }
}
