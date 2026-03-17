package ca.willatendo.fossilsclassic.server.gene.genes.behavior;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.gene.FCJsonBehaviorTypes;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public final class FloatJsonBehavior implements JsonBehavior<FloatGoal> {
    public static final FloatJsonBehavior INSTANCE = new FloatJsonBehavior();
    public static final MapCodec<FloatJsonBehavior> CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, FloatJsonBehavior> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private FloatJsonBehavior() {
    }

    @Override
    public <D extends Dinosaur> FloatGoal create(D dinosaur) {
        return new FloatGoal(dinosaur);
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.FLOAT.get();
    }
}
