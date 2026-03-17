package ca.willatendo.fossilsclassic.server.gene.genes.behavior;

import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.entity.goal.NearestAttackableTargetTypeGoal;
import ca.willatendo.fossilsclassic.server.gene.FCJsonBehaviorTypes;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.type.JsonBehaviorType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public record NearestAttackableTargetJsonBehavior(EntityType<?> targetType, int interval, boolean mustSee, boolean mustReach) implements JsonBehavior<NearestAttackableTargetTypeGoal<?>> {
    public static final MapCodec<NearestAttackableTargetJsonBehavior> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EntityType.CODEC.fieldOf("target_type").forGetter(NearestAttackableTargetJsonBehavior::targetType), Codec.INT.fieldOf("interval").forGetter(NearestAttackableTargetJsonBehavior::interval), Codec.BOOL.fieldOf("must_see").forGetter(NearestAttackableTargetJsonBehavior::mustSee), Codec.BOOL.fieldOf("must_reach").forGetter(NearestAttackableTargetJsonBehavior::mustReach)).apply(instance, NearestAttackableTargetJsonBehavior::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, NearestAttackableTargetJsonBehavior> STREAM_CODEC = StreamCodec.composite(EntityType.STREAM_CODEC, NearestAttackableTargetJsonBehavior::targetType, ByteBufCodecs.INT, NearestAttackableTargetJsonBehavior::interval, ByteBufCodecs.BOOL, NearestAttackableTargetJsonBehavior::mustSee, ByteBufCodecs.BOOL, NearestAttackableTargetJsonBehavior::mustReach, NearestAttackableTargetJsonBehavior::new);

    public NearestAttackableTargetJsonBehavior(EntityType<?> targetType, boolean mustSee, boolean mustReach) {
        this(targetType, 10, mustSee, mustReach);
    }

    public NearestAttackableTargetJsonBehavior(EntityType<?> targetType, boolean mustSee) {
        this(targetType, mustSee, false);
    }

    @Override
    public <D extends Dinosaur> NearestAttackableTargetTypeGoal<?> create(D dinosaur) {
        return new NearestAttackableTargetTypeGoal<>(dinosaur, (EntityType<? extends LivingEntity>) this.targetType(), this.interval(), this.mustSee(), this.mustReach(), null);
    }

    @Override
    public JsonBehaviorType<?> getType() {
        return FCJsonBehaviorTypes.NEAREST_ATTACKABLE_TARGET.get();
    }
}
