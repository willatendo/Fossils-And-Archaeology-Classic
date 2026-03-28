package ca.willatendo.fossilsclassic.server.recipe.recipes;

import ca.willatendo.fossilsclassic.server.analyzation_result.AnalyzationResult;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.item.crafting.SingleRecipeInputWithRegistries;
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
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class AnalyzationRecipe implements Recipe<SingleRecipeInputWithRegistries> {
    public static final MapCodec<AnalyzationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Recipe.CommonInfo.MAP_CODEC.forGetter(analyzationRecipe -> analyzationRecipe.commonInfo), AnalyzationBookInfo.MAP_CODEC.forGetter(analyzationRecipe -> analyzationRecipe.analyzationBookInfo), Ingredient.CODEC.fieldOf("ingredient").forGetter(analyzationRecipe -> analyzationRecipe.ingredient), Codec.either(TagKey.codec(FCRegistries.ANALYZATION_RESULT), ItemStackTemplate.CODEC).fieldOf("results").forGetter(analyzationRecipe -> analyzationRecipe.results), Codec.INT.fieldOf("time").orElse(100).forGetter(analyzationRecipe -> analyzationRecipe.time), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(analyzationRecipe -> analyzationRecipe.experience)).apply(instance, AnalyzationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AnalyzationRecipe> STREAM_CODEC = StreamCodec.composite(Recipe.CommonInfo.STREAM_CODEC, analyzationRecipe -> analyzationRecipe.commonInfo, AnalyzationBookInfo.STREAM_CODEC, analyzationRecipe -> analyzationRecipe.analyzationBookInfo, Ingredient.CONTENTS_STREAM_CODEC, analyzationRecipe -> analyzationRecipe.ingredient, ByteBufCodecs.either(TagKey.streamCodec(FCRegistries.ANALYZATION_RESULT), ItemStackTemplate.STREAM_CODEC), analyzationRecipe -> analyzationRecipe.results, ByteBufCodecs.INT, analyzationRecipe -> analyzationRecipe.time, ByteBufCodecs.FLOAT, analyzationRecipe -> analyzationRecipe.experience, AnalyzationRecipe::new);
    private final Recipe.CommonInfo commonInfo;
    private final AnalyzationRecipe.AnalyzationBookInfo analyzationBookInfo;
    private final Ingredient ingredient;
    private final Either<TagKey<AnalyzationResult>, ItemStackTemplate> results;
    private final int time;
    private final float experience;
    private WeightedList<ItemStackTemplate> resultsList;
    private PlacementInfo placementInfo;

    public AnalyzationRecipe(Recipe.CommonInfo commonInfo, AnalyzationRecipe.AnalyzationBookInfo analyzationBookInfo, Ingredient ingredient, Either<TagKey<AnalyzationResult>, ItemStackTemplate> results, int time, float experience) {
        this.commonInfo = commonInfo;
        this.analyzationBookInfo = analyzationBookInfo;
        this.ingredient = ingredient;
        this.results = results;
        this.time = time;
        this.experience = experience;
    }

    @Override
    public String group() {
        return this.analyzationBookInfo.group();
    }

    public int getTime() {
        return this.time;
    }

    public float getExperience() {
        return this.experience;
    }

    @Override
    public boolean matches(SingleRecipeInputWithRegistries singleRecipeInputWithRegistries, Level level) {
        return this.ingredient.test(singleRecipeInputWithRegistries.itemStack());
    }

    @Override
    public ItemStack assemble(SingleRecipeInputWithRegistries singleRecipeInputWithRegistries) {
        if (this.results.left().isPresent()) {
            if (this.resultsList == null) {
                RegistryAccess registryAccess = singleRecipeInputWithRegistries.registryAccess();
                WeightedList.Builder<ItemStackTemplate> builder = new WeightedList.Builder<>();
                HolderLookup.RegistryLookup<AnalyzationResult> analyzationResultRegistry = registryAccess.lookupOrThrow(FCRegistries.ANALYZATION_RESULT);
                analyzationResultRegistry.getOrThrow(this.results.left().get()).stream().map(Holder::value).forEach(analyzationResult -> builder.add(analyzationResult.result(), analyzationResult.weight()));
                this.resultsList = builder.build();
            }

            return this.resultsList.getRandomOrThrow(RandomSource.create()).create();
        } else {
            return this.results.right().get().create();
        }
    }

    @Override
    public boolean showNotification() {
        return this.commonInfo.showNotification();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInputWithRegistries>> getSerializer() {
        return FCRecipeSerializers.ANALYZATION_RECIPE.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInputWithRegistries>> getType() {
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
        return switch (this.analyzationBookInfo.category()) {
            case PALAEONTOLOGY -> FCRecipeBookCategories.ANALYZATION_PALAEONTOLOGY.get();
            case PALAEOBOTANY -> FCRecipeBookCategories.ANALYZATION_PALAEOBOTANY.get();
            case ARCHAEOLOGY -> FCRecipeBookCategories.ANALYZATION_ARCHAEOLOGY.get();
            default -> FCRecipeBookCategories.ANALYZATION_MISC.get();
        };
    }

    public record AnalyzationBookInfo(AnalyzationBookCategory category, String group) implements Recipe.BookInfo<AnalyzationBookCategory> {
        public static final MapCodec<AnalyzationBookInfo> MAP_CODEC = BookInfo.mapCodec(AnalyzationBookCategory.CODEC, AnalyzationBookCategory.MISC, AnalyzationBookInfo::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, AnalyzationBookInfo> STREAM_CODEC = BookInfo.streamCodec(AnalyzationBookCategory.STREAM_CODEC, AnalyzationBookInfo::new);
    }
}
