package ca.willatendo.fossilsclassic.server.recipe.recipes;

import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookCategories;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeSerializers;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeTypes;
import ca.willatendo.fossilsclassic.server.recipe.categories.RestorationBookCategory;
import ca.willatendo.fossilsclassic.server.recipe.display.RestorationRecipeDisplay;
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

public class RestorationRecipe implements Recipe<SingleRecipeInput> {
    private final String group;
    private final RestorationBookCategory restorationBookCategory;
    private final Ingredient ingredient;
    private final ItemStack result;
    private final int time;
    private final float experience;
    private PlacementInfo placementInfo;

    public RestorationRecipe(String group, RestorationBookCategory restorationBookCategory, Ingredient ingredient, ItemStack result, int time, float experience) {
        this.group = group;
        this.restorationBookCategory = restorationBookCategory;
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
        return this.ingredient.test(singleRecipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput analyzerRecipeInput, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return FCRecipeSerializers.RESTORATION_RECIPE.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return FCRecipeTypes.RESTORATION.get();
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
        return List.of(new RestorationRecipeDisplay(this.ingredient.display(), new SlotDisplay.ItemStackSlotDisplay(this.result), new SlotDisplay.ItemSlotDisplay(FCItems.ARCHAEOLOGY_WORKBENCH.get()), this.time));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.restorationBookCategory) {
            case RESTORE -> FCRecipeBookCategories.RESTORATION_RESTORE.get();
            case REPAIR -> FCRecipeBookCategories.RESTORATION_REPAIR.get();
            default -> FCRecipeBookCategories.RESTORATION_MISC.get();
        };
    }

    public static final class Serializer implements RecipeSerializer<RestorationRecipe> {
        public static final MapCodec<RestorationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(restorationRecipe -> restorationRecipe.group), RestorationBookCategory.CODEC.fieldOf("category").orElse(RestorationBookCategory.MISC).forGetter(restorationRecipe -> restorationRecipe.restorationBookCategory), Ingredient.CODEC.fieldOf("ingredient").forGetter(restorationRecipe -> restorationRecipe.ingredient), ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter(restorationRecipe -> restorationRecipe.result), Codec.INT.fieldOf("time").orElse(6000).forGetter(restorationRecipe -> restorationRecipe.time), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(restorationRecipe -> restorationRecipe.experience)).apply(instance, RestorationRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, RestorationRecipe> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, restorationRecipe -> restorationRecipe.group, RestorationBookCategory.STREAM_CODEC, restorationRecipe -> restorationRecipe.restorationBookCategory, Ingredient.CONTENTS_STREAM_CODEC, restorationRecipe -> restorationRecipe.ingredient, ItemStack.STREAM_CODEC, restorationRecipe -> restorationRecipe.result, ByteBufCodecs.INT, restorationRecipe -> restorationRecipe.time, ByteBufCodecs.FLOAT, restorationRecipe -> restorationRecipe.experience, RestorationRecipe::new);
        public static final RestorationRecipe.Serializer INSTANCE = new RestorationRecipe.Serializer();

        private Serializer() {
        }

        @Override
        public MapCodec<RestorationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RestorationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
