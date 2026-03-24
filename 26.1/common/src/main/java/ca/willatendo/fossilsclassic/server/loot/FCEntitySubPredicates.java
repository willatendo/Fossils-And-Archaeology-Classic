package ca.willatendo.fossilsclassic.server.loot;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.loot.predicates.DinosaurPredicate;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.core.registries.Registries;

public final class FCEntitySubPredicates {
    public static final SimpleRegistry<MapCodec<? extends EntitySubPredicate>> ENTITY_SUB_PREDICATES = new SimpleRegistry(Registries.ENTITY_SUB_PREDICATE_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<MapCodec<DinosaurPredicate>> DINOSAUR = ENTITY_SUB_PREDICATES.register("dinosaur", () -> DinosaurPredicate.CODEC);
}