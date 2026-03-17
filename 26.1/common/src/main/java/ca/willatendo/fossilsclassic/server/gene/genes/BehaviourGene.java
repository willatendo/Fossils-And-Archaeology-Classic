package ca.willatendo.fossilsclassic.server.gene.genes;

import ca.willatendo.fossilsclassic.server.gene.FCGeneTypes;
import ca.willatendo.fossilsclassic.server.gene.genes.behavior.JsonBehavior;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.goal.Goal;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public record BehaviourGene(Component name, List<GoalSelector<?>> goalSelectors, List<GoalSelector<?>> targetSelectors) implements Gene {
    public static final MapCodec<BehaviourGene> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ComponentSerialization.CODEC.fieldOf("name").forGetter(BehaviourGene::getName), Codec.list(BehaviourGene.GoalSelector.CODEC).fieldOf("goal_selector").forGetter(BehaviourGene::goalSelectors), Codec.list(BehaviourGene.GoalSelector.CODEC).fieldOf("target_selector").forGetter(BehaviourGene::targetSelectors)).apply(instance, BehaviourGene::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BehaviourGene> STREAM_CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_STREAM_CODEC, BehaviourGene::getName, BehaviourGene.GoalSelector.STREAM_CODEC.apply(ByteBufCodecs.list()), BehaviourGene::goalSelectors, BehaviourGene.GoalSelector.STREAM_CODEC.apply(ByteBufCodecs.list()), BehaviourGene::targetSelectors, BehaviourGene::new);

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public GeneType<?> getType() {
        return FCGeneTypes.BEHAVIOUR.get();
    }

    public static BehaviourGene.GoalSelectorBuilder selectorBuilder() {
        return new BehaviourGene.GoalSelectorBuilder();
    }

    public static final class GoalSelectorBuilder {
        private final List<GoalSelector<?>> goalSelectors = Lists.newArrayList();

        private GoalSelectorBuilder() {
        }

        public <T extends Goal> GoalSelectorBuilder add(int priority, JsonBehavior<T> jsonBehavior) {
            this.goalSelectors.add(new GoalSelector<>(priority, jsonBehavior));
            return this;
        }

        public List<GoalSelector<?>> build() {
            return this.goalSelectors;
        }
    }

    public record GoalSelector<T extends Goal>(int priority, JsonBehavior<T> jsonBehavior) {
        public static final Codec<BehaviourGene.GoalSelector<?>> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("priority").forGetter(BehaviourGene.GoalSelector::priority), JsonBehavior.CODEC.fieldOf("goal").forGetter(BehaviourGene.GoalSelector::jsonBehavior)).apply(instance, BehaviourGene.GoalSelector::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BehaviourGene.GoalSelector<?>> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, BehaviourGene.GoalSelector::priority, JsonBehavior.STREAM_CODEC, BehaviourGene.GoalSelector::jsonBehavior, BehaviourGene.GoalSelector::new);
    }
}
