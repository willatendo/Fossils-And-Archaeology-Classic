package ca.willatendo.fossilsclassic.server.loot.predicates;

import ca.willatendo.fossilsclassic.server.entity.utils.GrowingEntity;
import ca.willatendo.fossilsclassic.server.loot.FCEntitySubPredicates;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record DinosaurPredicate(MinMaxBounds.Ints growthStage) implements EntitySubPredicate {
    public static final MapCodec<DinosaurPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.optionalFieldOf("growth_stage", MinMaxBounds.Ints.ANY).forGetter(DinosaurPredicate::growthStage)).apply(instance, DinosaurPredicate::new));

    public static DinosaurPredicate growthStage(MinMaxBounds.Ints growthStage) {
        return new DinosaurPredicate(growthStage);
    }

    @Override
    public boolean matches(Entity entity, ServerLevel serverLevel, Vec3 vec3) {
        if (entity instanceof GrowingEntity growingEntity) {
            return this.growthStage.matches(growingEntity.getGrowthStage());
        }
        return false;
    }

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return FCEntitySubPredicates.DINOSAUR.get();
    }
}