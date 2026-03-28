package ca.willatendo.fossilsclassic.server.recipe.display;

import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;

import java.util.stream.Stream;

public record ItemStacksSlotDisplay(TagKey<AnalyzationResult> resultsTag) implements SlotDisplay {
    public static final MapCodec<ItemStacksSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(TagKey.codec(FCRegistries.ANALYZATION_RESULT).fieldOf("results").forGetter(ItemStacksSlotDisplay::resultsTag)).apply(instance, ItemStacksSlotDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemStacksSlotDisplay> STREAM_CODEC = StreamCodec.composite(TagKey.streamCodec(FCRegistries.ANALYZATION_RESULT), ItemStacksSlotDisplay::resultsTag, ItemStacksSlotDisplay::new);
    public static final Type<ItemStacksSlotDisplay> TYPE = new Type<>(ItemStacksSlotDisplay.MAP_CODEC, ItemStacksSlotDisplay.STREAM_CODEC);

    @Override
    public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> displayContentsFactory) {
        if (displayContentsFactory instanceof DisplayContentsFactory.ForStacks<T> forStacks) {
            HolderLookup.Provider provider = contextMap.getOptional(SlotDisplayContext.REGISTRIES);
            if (provider != null) {
                return provider.lookupOrThrow(FCRegistries.ANALYZATION_RESULT).get(this.resultsTag).map(analyzerResult -> analyzerResult.stream().map(Holder::value).map(AnalyzationResult::result)).orElseGet(Stream::of).map(itemStackTemplate -> forStacks.forStack(itemStackTemplate.item()));
            }
        }
        return Stream.empty();
    }

    @Override
    public Type<? extends SlotDisplay> type() {
        return ItemStacksSlotDisplay.TYPE;
    }
}
