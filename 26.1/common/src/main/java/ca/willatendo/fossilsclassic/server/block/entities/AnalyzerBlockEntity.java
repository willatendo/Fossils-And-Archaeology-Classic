package ca.willatendo.fossilsclassic.server.block.entities;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.FCBlockEntityTypes;
import ca.willatendo.fossilsclassic.server.block.blocks.AnalyzerBlock;
import ca.willatendo.fossilsclassic.server.item.crafting.SingleRecipeInputWithRegistries;
import ca.willatendo.fossilsclassic.server.menu.menus.AnalyzerMenu;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeTypes;
import ca.willatendo.fossilsclassic.server.recipe.recipes.AnalyzationRecipe;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;

public class AnalyzerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);
    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] SLOTS_FOR_DOWN = new int[]{9, 10, 11, 12};
    private static final int[] SLOTS_FOR_SIDES = SLOTS_FOR_UP;
    protected NonNullList<ItemStack> items = NonNullList.withSize(13, ItemStack.EMPTY);
    public int onTimeRemaining;
    public int analyzationProgress;
    public int analyzationTotalTime;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int slot) {
            switch (slot) {
                case 0:
                    return AnalyzerBlockEntity.this.onTimeRemaining;
                case 1:
                    return AnalyzerBlockEntity.this.analyzationProgress;
                case 2:
                    return AnalyzerBlockEntity.this.analyzationTotalTime;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int slot, int set) {
            switch (slot) {
                case 0 -> AnalyzerBlockEntity.this.onTimeRemaining = set;
                case 1 -> AnalyzerBlockEntity.this.analyzationProgress = set;
                case 2 -> AnalyzerBlockEntity.this.analyzationTotalTime = set;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInputWithRegistries, AnalyzationRecipe> recipeCheck = RecipeManager.createCheck(FCRecipeTypes.ANALYZATION.get());


    public AnalyzerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FCBlockEntityTypes.ANALYZER.get(), blockPos, blockState);
    }

    private boolean isOn() {
        return this.onTimeRemaining > 0;
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(valueInput, this.items);
        this.onTimeRemaining = valueInput.getIntOr("on_time_remaining", 0);
        this.analyzationProgress = valueInput.getIntOr("analyzation_time", 0);
        this.analyzationTotalTime = valueInput.getIntOr("analyzation_time_total", 0);
        this.recipesUsed.clear();
        this.recipesUsed.putAll(valueInput.read("recipes_used", RECIPES_USED_CODEC).orElse(Map.of()));
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("on_time_remaining", this.onTimeRemaining);
        valueOutput.putInt("analyzation_time", this.analyzationProgress);
        valueOutput.putInt("analyzation_time_total", this.analyzationTotalTime);
        ContainerHelper.saveAllItems(valueOutput, this.items);
        valueOutput.store("recipes_used", RECIPES_USED_CODEC, this.recipesUsed);
    }

    public static void serverTick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, AnalyzerBlockEntity analyzerBlockEntity) {
        boolean isOn = analyzerBlockEntity.isOn();
        boolean dirty = false;
        if (isOn) {
            --analyzerBlockEntity.onTimeRemaining;
        }

        if (analyzerBlockEntity.items.get(9).isEmpty() || analyzerBlockEntity.items.get(10).isEmpty() || analyzerBlockEntity.items.get(11).isEmpty() || analyzerBlockEntity.items.get(12).isEmpty()) {
            for (int inputSlot = 0; inputSlot < 9; inputSlot++) {
                boolean hasInput = !analyzerBlockEntity.items.get(inputSlot).isEmpty();
                if (analyzerBlockEntity.isOn() || hasInput) {
                    SingleRecipeInputWithRegistries singleRecipeInput = new SingleRecipeInputWithRegistries(analyzerBlockEntity.items.get(inputSlot), serverLevel.registryAccess());
                    RecipeHolder<AnalyzationRecipe> recipe;
                    if (hasInput) {
                        recipe = analyzerBlockEntity.recipeCheck.getRecipeFor(singleRecipeInput, serverLevel).orElse(null);
                    } else {
                        recipe = null;
                    }

                    if (recipe != null) {
                        int maxStackSize = analyzerBlockEntity.getMaxStackSize();
                        ItemStack outputStack = recipe.value().assemble(singleRecipeInput);
                        for (int outputSlot = 9; outputSlot < 13; outputSlot++) {
                            if (AnalyzerBlockEntity.canAnalyze(outputSlot, inputSlot, outputStack, analyzerBlockEntity.items, maxStackSize)) {
                                if (!analyzerBlockEntity.isOn()) {
                                    analyzerBlockEntity.onTimeRemaining = 100;
                                    if (analyzerBlockEntity.isOn()) {
                                        dirty = true;
                                    }
                                }

                                if (analyzerBlockEntity.isOn()) {
                                    analyzerBlockEntity.analyzationProgress++;
                                    if (analyzerBlockEntity.analyzationProgress == analyzerBlockEntity.analyzationTotalTime) {
                                        analyzerBlockEntity.analyzationProgress = 0;
                                        analyzerBlockEntity.analyzationTotalTime = getTotalAnalyzationTime(inputSlot, serverLevel, analyzerBlockEntity);
                                        if (AnalyzerBlockEntity.analyze(outputSlot, inputSlot, outputStack, analyzerBlockEntity.items, maxStackSize)) {
                                            analyzerBlockEntity.setRecipeUsed(recipe);
                                        }

                                        dirty = true;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                } else if (inputSlot == 8 && !analyzerBlockEntity.isOn() && analyzerBlockEntity.analyzationProgress > 0) {
                    analyzerBlockEntity.analyzationProgress = Mth.clamp(analyzerBlockEntity.analyzationProgress - 2, 0, analyzerBlockEntity.analyzationTotalTime);
                }
            }
        }

        if (isOn != analyzerBlockEntity.isOn()) {
            dirty = true;
            blockState = blockState.setValue(AnalyzerBlock.ACTIVE, analyzerBlockEntity.isOn());
            serverLevel.setBlock(blockPos, blockState, 3);
        }

        if (dirty) {
            BlockEntity.setChanged(serverLevel, blockPos, blockState);
        }
    }

    private static boolean canAnalyze(int outputSlot, int inputSlot, ItemStack output, NonNullList<ItemStack> items, int maxStackSize) {
        if (!items.get(inputSlot).isEmpty() && output != null) {
            if (output.isEmpty()) {
                return false;
            } else {
                ItemStack outputItemStack = items.get(outputSlot);
                if (outputItemStack.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(outputItemStack, output)) {
                    return false;
                } else if (outputItemStack.getCount() + output.getCount() <= maxStackSize && outputItemStack.getCount() + output.getCount() <= outputItemStack.getMaxStackSize()) {
                    return true;
                } else {
                    return outputItemStack.getCount() + output.getCount() <= output.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean analyze(int outputSlot, int inputSlot, ItemStack output, NonNullList<ItemStack> items, int maxStackSize) {
        if (AnalyzerBlockEntity.canAnalyze(outputSlot, inputSlot, output, items, maxStackSize)) {
            ItemStack inputItemStack = items.get(inputSlot);
            ItemStack outputItemStack = items.get(outputSlot);
            if (outputItemStack.isEmpty()) {
                items.set(outputSlot, output.copy());
            } else if (outputItemStack.is(output.getItem())) {
                outputItemStack.grow(output.getCount());
            }

            inputItemStack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    private static int getTotalAnalyzationTime(int inputSlot, ServerLevel serverLevel, AnalyzerBlockEntity analyzerBlockEntity) {
        return analyzerBlockEntity.recipeCheck.getRecipeFor(new SingleRecipeInputWithRegistries(analyzerBlockEntity.items.get(inputSlot), serverLevel.registryAccess()), serverLevel).map(recipeHolder -> recipeHolder.value().getTime()).orElse(100);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return this.canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setItem(int slotIndex, ItemStack itemStack) {
        ItemStack itemStackInSlot = this.items.get(slotIndex);
        boolean flag = !itemStack.isEmpty() && ItemStack.isSameItemSameComponents(itemStackInSlot, itemStack);
        this.items.set(slotIndex, itemStack);
        itemStack.limitSize(this.getMaxStackSize(itemStack));

        if (slotIndex <= 8 && !flag) {
            if (this.getLevel() instanceof ServerLevel serverLevel) {
                this.analyzationTotalTime = AnalyzerBlockEntity.getTotalAnalyzationTime(slotIndex, serverLevel, this);
                this.analyzationProgress = 0;
                this.setChanged();
            }
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (slot <= 8) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setRecipeUsed(RecipeHolder<?> recipeHolder) {
        if (recipeHolder != null) {
            ResourceKey<Recipe<?>> recipeId = recipeHolder.id();
            this.recipesUsed.addTo(recipeId, 1);
        }
    }

    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer serverPlayer) {
        List<RecipeHolder<?>> recipeHolders = this.getRecipesToAwardAndPopExperience(serverPlayer.level(), serverPlayer.position());
        serverPlayer.awardRecipes(recipeHolders);

        for (RecipeHolder<?> recipeholder : recipeHolders) {
            serverPlayer.triggerRecipeCrafted(recipeholder, this.items);
        }

        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<RecipeHolder<?>> recipeHolders = Lists.newArrayList();

        for (Reference2IntMap.Entry<ResourceKey<Recipe<?>>> entry : this.recipesUsed.reference2IntEntrySet()) {
            level.recipeAccess().byKey(entry.getKey()).ifPresent(recipeHolder -> {
                recipeHolders.add(recipeHolder);
                AnalyzerBlockEntity.createExperience(level, popVec, entry.getIntValue(), ((AnalyzationRecipe) recipeHolder.value()).getExperience());
            });
        }

        return recipeHolders;
    }

    private static void createExperience(ServerLevel serverLevel, Vec3 popVec, int recipeIndex, float experience) {
        int amount = Mth.floor((float) recipeIndex * experience);
        float f = Mth.frac((float) recipeIndex * experience);
        if (f != 0.0F && serverLevel.getRandom().nextFloat() < f) {
            ++amount;
        }

        ExperienceOrb.award(serverLevel, popVec, amount);
    }

    @Override
    public void fillStackedContents(StackedItemContents stackedItemContents) {
        for (ItemStack itemStack : this.items) {
            stackedItemContents.accountStack(itemStack);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos blockPos, BlockState blockState) {
        super.preRemoveSideEffects(blockPos, blockState);
        if (this.level instanceof ServerLevel serverLevel) {
            this.getRecipesToAwardAndPopExperience(serverLevel, Vec3.atCenterOf(blockPos));
        }
    }

    @Override
    protected Component getDefaultName() {
        return FCCoreUtils.translation("container", "analyzer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
        return new AnalyzerMenu(windowId, inventory, this);
    }
}
