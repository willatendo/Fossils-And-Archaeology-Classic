package ca.willatendo.fossilsclassic.server.recipe.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record RestorationRecipeDisplay(SlotDisplay ingredient, SlotDisplay result, SlotDisplay craftingStation, int duration) implements RecipeDisplay {
    public static final MapCodec<RestorationRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(SlotDisplay.CODEC.fieldOf("ingredient").forGetter(RestorationRecipeDisplay::ingredient), SlotDisplay.CODEC.fieldOf("result").forGetter(RestorationRecipeDisplay::result), SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(RestorationRecipeDisplay::craftingStation), Codec.INT.fieldOf("duration").forGetter(RestorationRecipeDisplay::duration)).apply(instance, RestorationRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RestorationRecipeDisplay> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, RestorationRecipeDisplay::ingredient, SlotDisplay.STREAM_CODEC, RestorationRecipeDisplay::result, SlotDisplay.STREAM_CODEC, RestorationRecipeDisplay::craftingStation, ByteBufCodecs.VAR_INT, RestorationRecipeDisplay::duration, RestorationRecipeDisplay::new);
    public static final Type<RestorationRecipeDisplay> TYPE = new Type<>(RestorationRecipeDisplay.MAP_CODEC, RestorationRecipeDisplay.STREAM_CODEC);

    @Override
    public Type<? extends RecipeDisplay> type() {
        return RestorationRecipeDisplay.TYPE;
    }
}