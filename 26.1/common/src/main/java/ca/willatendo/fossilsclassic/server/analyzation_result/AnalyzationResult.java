package ca.willatendo.fossilsclassic.server.analyzation_result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

public record AnalyzationResult(int weight, ItemStack result) {
    public static final Codec<AnalyzationResult> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("weight").forGetter(AnalyzationResult::weight), ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter(AnalyzationResult::result)).apply(instance, AnalyzationResult::new));
}
