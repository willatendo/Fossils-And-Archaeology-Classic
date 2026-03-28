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
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class RestorationRecipe implements Recipe<SingleRecipeInput> {
    public static final MapCodec<RestorationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Recipe.CommonInfo.MAP_CODEC.forGetter(restorationRecipe -> restorationRecipe.commonInfo), RestorationRecipe.RestorationBookInfo.MAP_CODEC.forGetter(restorationRecipe -> restorationRecipe.restorationBookInfo), Ingredient.CODEC.fieldOf("ingredient").forGetter(restorationRecipe -> restorationRecipe.ingredient), ItemStackTemplate.CODEC.fieldOf("result").forGetter(restorationRecipe -> restorationRecipe.result), Codec.INT.fieldOf("time").orElse(6000).forGetter(restorationRecipe -> restorationRecipe.time), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(restorationRecipe -> restorationRecipe.experience)).apply(instance, RestorationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RestorationRecipe> STREAM_CODEC = StreamCodec.composite(Recipe.CommonInfo.STREAM_CODEC, restorationRecipe -> restorationRecipe.commonInfo, RestorationRecipe.RestorationBookInfo.STREAM_CODEC, restorationRecipe -> restorationRecipe.restorationBookInfo, Ingredient.CONTENTS_STREAM_CODEC, restorationRecipe -> restorationRecipe.ingredient, ItemStackTemplate.STREAM_CODEC, restorationRecipe -> restorationRecipe.result, ByteBufCodecs.INT, restorationRecipe -> restorationRecipe.time, ByteBufCodecs.FLOAT, restorationRecipe -> restorationRecipe.experience, RestorationRecipe::new);
    private final Recipe.CommonInfo commonInfo;
    private final RestorationRecipe.RestorationBookInfo restorationBookInfo;
    private final Ingredient ingredient;
    private final ItemStackTemplate result;
    private final int time;
    private final float experience;
    private PlacementInfo placementInfo;

    public RestorationRecipe(Recipe.CommonInfo commonInfo, RestorationRecipe.RestorationBookInfo restorationBookInfo, Ingredient ingredient, ItemStackTemplate result, int time, float experience) {
        this.commonInfo = commonInfo;
        this.restorationBookInfo = restorationBookInfo;
        this.ingredient = ingredient;
        this.result = result;
        this.time = time;
        this.experience = experience;
    }

    @Override
    public String group() {
        return this.restorationBookInfo.group();
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
    public ItemStack assemble(SingleRecipeInput singleRecipeInput) {
        return this.result.create();
    }

    @Override
    public boolean showNotification() {
        return this.commonInfo.showNotification();
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
        return switch (this.restorationBookInfo.category()) {
            case RESTORE -> FCRecipeBookCategories.RESTORATION_RESTORE.get();
            case REPAIR -> FCRecipeBookCategories.RESTORATION_REPAIR.get();
            default -> FCRecipeBookCategories.RESTORATION_MISC.get();
        };
    }

    public record RestorationBookInfo(RestorationBookCategory category, String group) implements Recipe.BookInfo<RestorationBookCategory> {
        public static final MapCodec<RestorationBookInfo> MAP_CODEC = BookInfo.mapCodec(RestorationBookCategory.CODEC, RestorationBookCategory.MISC, RestorationBookInfo::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, RestorationBookInfo> STREAM_CODEC = BookInfo.streamCodec(RestorationBookCategory.STREAM_CODEC, RestorationBookInfo::new);
    }
}
