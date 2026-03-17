package ca.willatendo.fossilsclassic.server.recipe.recipes;

import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookCategories;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeSerializers;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeTypes;
import ca.willatendo.fossilsclassic.server.recipe.categories.CultivationBookCategory;
import ca.willatendo.fossilsclassic.server.recipe.display.CultivationRecipeDisplay;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class CultivationRecipe implements Recipe<SingleRecipeInput> {
    private final String group;
    private final CultivationBookCategory cultivationBookCategory;
    private final Ingredient ingredient;
    private final ItemStack result;
    private final int time;
    private final float experience;
    private PlacementInfo placementInfo;

    public CultivationRecipe(String group, CultivationBookCategory cultivationBookCategory, Ingredient ingredient, ItemStack result, int time, float experience) {
        this.group = group;
        this.cultivationBookCategory = cultivationBookCategory;
        this.ingredient = ingredient;
        this.result = result;
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
        ItemStack itemStack = singleRecipeInput.item();
        return this.ingredient.test(itemStack);
    }

    @Override
    public ItemStack assemble(SingleRecipeInput analyzerRecipeInput, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return FCRecipeSerializers.CULTIVATION_RECIPE.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return FCRecipeTypes.CULTIVATION.get();
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
        return List.of(new CultivationRecipeDisplay(this.ingredient.display(), new SlotDisplay.ItemStackSlotDisplay(this.result), new SlotDisplay.ItemSlotDisplay(FCItems.CULTIVATOR.get()), this.time));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.cultivationBookCategory) {
            case EGG -> FCRecipeBookCategories.CULTIVATION_EGGS.get();
            case EMBRYO -> FCRecipeBookCategories.CULTIVATION_EMBRYOS.get();
            case PLANT -> FCRecipeBookCategories.CULTIVATION_PLANTS.get();
            default -> FCRecipeBookCategories.CULTIVATION_MISC.get();
        };
    }

    public static final class Serializer implements RecipeSerializer<CultivationRecipe> {
        public static final MapCodec<CultivationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(cultivationRecipe -> cultivationRecipe.group), CultivationBookCategory.CODEC.fieldOf("category").orElse(CultivationBookCategory.MISC).forGetter(cultivationRecipe -> cultivationRecipe.cultivationBookCategory), Ingredient.CODEC.fieldOf("ingredient").forGetter(cultivationRecipe -> cultivationRecipe.ingredient), ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter(cultivationRecipe -> cultivationRecipe.result), Codec.INT.fieldOf("time").orElse(6000).forGetter(cultivationRecipe -> cultivationRecipe.time), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(cultivationRecipe -> cultivationRecipe.experience)).apply(instance, CultivationRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, CultivationRecipe> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, cultivationRecipe -> cultivationRecipe.group, CultivationBookCategory.STREAM_CODEC, cultivationRecipe -> cultivationRecipe.cultivationBookCategory, Ingredient.CONTENTS_STREAM_CODEC, cultivationRecipe -> cultivationRecipe.ingredient, ItemStack.STREAM_CODEC, cultivationRecipe -> cultivationRecipe.result, ByteBufCodecs.INT, cultivationRecipe -> cultivationRecipe.time, ByteBufCodecs.FLOAT, cultivationRecipe -> cultivationRecipe.experience, CultivationRecipe::new);
        public static final CultivationRecipe.Serializer INSTANCE = new CultivationRecipe.Serializer();

        private Serializer() {
        }

        @Override
        public MapCodec<CultivationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CultivationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
