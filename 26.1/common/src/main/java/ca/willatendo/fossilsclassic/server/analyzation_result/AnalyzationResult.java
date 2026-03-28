package ca.willatendo.fossilsclassic.server.analyzation_result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStackTemplate;

public record AnalyzationResult(int weight, ItemStackTemplate result) {
    public static final Codec<AnalyzationResult> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("weight").forGetter(AnalyzationResult::weight), ItemStackTemplate.CODEC.fieldOf("result").forGetter(AnalyzationResult::result)).apply(instance, AnalyzationResult::new));
}
