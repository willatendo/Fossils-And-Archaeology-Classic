package ca.willatendo.fossilsclassic.server.recipe.recipes;

import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookCategories;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeSerializers;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeTypes;
import ca.willatendo.fossilsclassic.server.recipe.categories.AnalyzationBookCategory;
import ca.willatendo.fossilsclassic.server.recipe.display.AnalyzationRecipeDisplay;
import ca.willatendo.fossilsclassic.server.recipe.display.ItemStacksSlotDisplay;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class AnalyzationRecipe implements Recipe<SingleRecipeInput> {
    private final String group;
    private final AnalyzationBookCategory analyzationBookCategory;
    private final Ingredient ingredient;
    private final Either<TagKey<AnalyzationResult>, ItemStack> results;
    private final int time;
    private final float experience;
    private WeightedList<ItemStack> resultsList;
    private PlacementInfo placementInfo;

    public AnalyzationRecipe(String group, AnalyzationBookCategory analyzationBookCategory, Ingredient ingredient, Either<TagKey<AnalyzationResult>, ItemStack> results, int time, float experience) {
        this.group = group;
        this.analyzationBookCategory = analyzationBookCategory;
        this.ingredient = ingredient;
        this.results = results;
        this.time = time;
        this.experience = experience;
    }

    @Override
    public String group() {
        return this.group;
    }

    public int getTime() {
        return this.time;
    }

    public float getExperience() {
        return this.experience;
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return this.ingredient.test(singleRecipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput analyzerRecipeInput, HolderLookup.Provider registries) {
        if (this.results.left().isPresent()) {
            if (this.resultsList == null) {
                WeightedList.Builder<ItemStack> builder = new WeightedList.Builder<>();
                HolderLookup.RegistryLookup<AnalyzationResult> analyzationResultRegistry = registries.lookupOrThrow(FCRegistries.ANALYZATION_RESULT);
                analyzationResultRegistry.getOrThrow(this.results.left().get()).stream().map(Holder::value).forEach(analyzationResult -> builder.add(analyzationResult.result(), analyzationResult.weight()));
                this.resultsList = builder.build();
            }

            return this.resultsList.getRandomOrThrow(RandomSource.create()).copy();
        } else {
            return this.results.right().get();
        }
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return FCRecipeSerializers.ANALYZATION_RECIPE.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return FCRecipeTypes.ANALYZATION.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredient);
        }

        return this.placementInfo;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new AnalyzationRecipeDisplay(this.ingredient.display(), this.results.left().isPresent() ? new ItemStacksSlotDisplay(this.results.left().get()) : this.results.right().isPresent() ? new SlotDisplay.ItemStackSlotDisplay(this.results.right().get()) : SlotDisplay.Empty.INSTANCE, new SlotDisplay.ItemSlotDisplay(FCItems.ANALYZER.get()), this.time));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.analyzationBookCategory) {
            case PALAEONTOLOGY -> FCRecipeBookCategories.ANALYZATION_PALAEONTOLOGY.get();
            case PALAEOBOTANY -> FCRecipeBookCategories.ANALYZATION_PALAEOBOTANY.get();
            case ARCHAEOLOGY -> FCRecipeBookCategories.ANALYZATION_ARCHAEOLOGY.get();
            default -> FCRecipeBookCategories.ANALYZATION_MISC.get();
        };
    }

    public static final class Serializer implements RecipeSerializer<AnalyzationRecipe> {
        public static final MapCodec<AnalyzationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(analyzationRecipe -> analyzationRecipe.group), AnalyzationBookCategory.CODEC.fieldOf("category").orElse(AnalyzationBookCategory.MISC).forGetter(analyzationRecipe -> analyzationRecipe.analyzationBookCategory), Ingredient.CODEC.fieldOf("ingredient").forGetter(analyzationRecipe -> analyzationRecipe.ingredient), Codec.either(TagKey.codec(FCRegistries.ANALYZATION_RESULT), ItemStack.STRICT_SINGLE_ITEM_CODEC).fieldOf("results").forGetter(analyzationRecipe -> analyzationRecipe.results), Codec.INT.fieldOf("time").orElse(100).forGetter(analyzationRecipe -> analyzationRecipe.time), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(analyzationRecipe -> analyzationRecipe.experience)).apply(instance, AnalyzationRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, AnalyzationRecipe> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, analyzationRecipe -> analyzationRecipe.group, AnalyzationBookCategory.STREAM_CODEC, analyzationRecipe -> analyzationRecipe.analyzationBookCategory, Ingredient.CONTENTS_STREAM_CODEC, analyzationRecipe -> analyzationRecipe.ingredient, ByteBufCodecs.either(TagKey.streamCodec(FCRegistries.ANALYZATION_RESULT), ItemStack.STREAM_CODEC), analyzationRecipe -> analyzationRecipe.results, ByteBufCodecs.INT, analyzationRecipe -> analyzationRecipe.time, ByteBufCodecs.FLOAT, analyzationRecipe -> analyzationRecipe.experience, AnalyzationRecipe::new);
        public static final AnalyzationRecipe.Serializer INSTANCE = new AnalyzationRecipe.Serializer();

        private Serializer() {
        }

        @Override
        public MapCodec<AnalyzationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AnalyzationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
