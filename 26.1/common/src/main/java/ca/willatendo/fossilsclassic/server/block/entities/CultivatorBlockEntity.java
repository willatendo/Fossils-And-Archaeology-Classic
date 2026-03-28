package ca.willatendo.fossilsclassic.server.block.entities;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.ValueMaps;
import ca.willatendo.fossilsclassic.server.block.FCBlockEntityTypes;
import ca.willatendo.fossilsclassic.server.block.blocks.CultivatorBlock;
import ca.willatendo.fossilsclassic.server.menu.menus.CultivatorMenu;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeTypes;
import ca.willatendo.fossilsclassic.server.recipe.recipes.CultivationRecipe;
import ca.willatendo.simplelibrary.core.utils.SimpleCoreUtils;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;

public class CultivatorBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    public int onTimeRemaining;
    public int onTimeTotalTime;
    public int cultivationProgress;
    public int cultivationTotalTime;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int slot) {
            return switch (slot) {
                case 0 -> CultivatorBlockEntity.this.onTimeRemaining;
                case 1 -> CultivatorBlockEntity.this.onTimeTotalTime;
                case 2 -> CultivatorBlockEntity.this.cultivationProgress;
                case 3 -> CultivatorBlockEntity.this.cultivationTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(int slot, int set) {
            switch (slot) {
                case 0 -> CultivatorBlockEntity.this.onTimeRemaining = set;
                case 1 -> CultivatorBlockEntity.this.onTimeTotalTime = set;
                case 2 -> CultivatorBlockEntity.this.cultivationProgress = set;
                case 3 -> CultivatorBlockEntity.this.cultivationTotalTime = set;
            }

        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInput, CultivationRecipe> recipeCheck = RecipeManager.createCheck(FCRecipeTypes.CULTIVATION.get());


    public CultivatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FCBlockEntityTypes.CULTIVATOR.get(), blockPos, blockState);
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
        this.onTimeTotalTime = valueInput.getIntOr("on_time_total_time", 0);
        this.cultivationProgress = valueInput.getIntOr("cultivation_time", 0);
        this.cultivationTotalTime = valueInput.getIntOr("cultivation_time_total", 0);
        this.recipesUsed.clear();
        this.recipesUsed.putAll(valueInput.read("recipes_used", RECIPES_USED_CODEC).orElse(Map.of()));
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("on_time_remaining", this.onTimeRemaining);
        valueOutput.putInt("on_time_total_time", this.onTimeTotalTime);
        valueOutput.putInt("cultivation_time", this.cultivationProgress);
        valueOutput.putInt("cultivation_time_total", this.cultivationTotalTime);
        ContainerHelper.saveAllItems(valueOutput, this.items);
        valueOutput.store("recipes_used", RECIPES_USED_CODEC, this.recipesUsed);
    }

    public static void serverTick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, CultivatorBlockEntity cultivatorBlockEntity) {
        boolean isOn = cultivatorBlockEntity.isOn();
        boolean dirty = false;
        if (cultivatorBlockEntity.isOn()) {
            --cultivatorBlockEntity.onTimeRemaining;
        }

        ItemStack fuelItemStack = cultivatorBlockEntity.items.get(1);
        ItemStack inputItemStack = cultivatorBlockEntity.items.get(0);
        boolean hasInput = !inputItemStack.isEmpty();
        boolean hasFuel = !fuelItemStack.isEmpty();
        if (!cultivatorBlockEntity.isOn() && (!hasFuel || !hasInput)) {
            if (!cultivatorBlockEntity.isOn() && cultivatorBlockEntity.cultivationProgress > 0) {
                cultivatorBlockEntity.cultivationProgress = Mth.clamp(cultivatorBlockEntity.cultivationProgress - 2, 0, cultivatorBlockEntity.cultivationTotalTime);
            }
        } else {
            SingleRecipeInput singleRecipeInput = new SingleRecipeInput(inputItemStack);
            RecipeHolder<CultivationRecipe> recipe;
            if (hasInput) {
                recipe = cultivatorBlockEntity.recipeCheck.getRecipeFor(singleRecipeInput, serverLevel).orElse(null);
            } else {
                recipe = null;
            }

            int maxStackSize = cultivatorBlockEntity.getMaxStackSize();
            if (!cultivatorBlockEntity.isOn() && CultivatorBlockEntity.canCultivate(serverLevel.registryAccess(), recipe, cultivatorBlockEntity.items, maxStackSize)) {
                cultivatorBlockEntity.onTimeRemaining = cultivatorBlockEntity.getBiomassDuration(fuelItemStack);
                cultivatorBlockEntity.onTimeTotalTime = cultivatorBlockEntity.onTimeRemaining;
                if (cultivatorBlockEntity.isOn()) {
                    dirty = true;
                    Item item = fuelItemStack.getItem();
                    fuelItemStack.shrink(1);
                    if (fuelItemStack.isEmpty()) {
                        ItemStackTemplate craftingRemainder = item.getCraftingRemainder();
                        cultivatorBlockEntity.items.set(1, craftingRemainder != null ? craftingRemainder.create() : ItemStack.EMPTY);
                    }
                }
            }

            if (cultivatorBlockEntity.isOn() && CultivatorBlockEntity.canCultivate(serverLevel.registryAccess(), recipe, cultivatorBlockEntity.items, maxStackSize)) {
                ++cultivatorBlockEntity.cultivationProgress;
                if (cultivatorBlockEntity.cultivationProgress == cultivatorBlockEntity.cultivationTotalTime) {
                    SimpleCoreUtils.LOGGER.info("OT: {} | OTT: {} | CP: {} | CTT: {}", cultivatorBlockEntity.onTimeRemaining, cultivatorBlockEntity.onTimeTotalTime, cultivatorBlockEntity.cultivationProgress, cultivatorBlockEntity.cultivationTotalTime);
                    cultivatorBlockEntity.cultivationProgress = 0;
                    cultivatorBlockEntity.cultivationTotalTime = CultivatorBlockEntity.getTotalCultivationTime(serverLevel, cultivatorBlockEntity);
                    if (CultivatorBlockEntity.cultivate(serverLevel.registryAccess(), recipe, cultivatorBlockEntity.items, maxStackSize)) {
                        cultivatorBlockEntity.setRecipeUsed(recipe);
                    }

                    dirty = true;
                }
            } else {
                cultivatorBlockEntity.cultivationProgress = 0;
            }
        }

        if (cultivatorBlockEntity.onTimeRemaining == (CultivatorBlockEntity.getTotalCultivationTime(serverLevel, cultivatorBlockEntity) / 2) + 1) {
            if (cultivatorBlockEntity.level.getRandom().nextInt(100) <= 30) {
                CultivatorBlock.shatter(serverLevel, blockPos, cultivatorBlockEntity);
            }
        }

        if (isOn != cultivatorBlockEntity.isOn()) {
            dirty = true;
            blockState = blockState.setValue(CultivatorBlock.ACTIVE, cultivatorBlockEntity.isOn());
            serverLevel.setBlock(blockPos, blockState, 3);
        }

        if (dirty) {
            BlockEntity.setChanged(serverLevel, blockPos, blockState);
        }
    }

    private static boolean canCultivate(RegistryAccess registryAccess, RecipeHolder<?> recipeHolder, NonNullList<ItemStack> items, int maxStackSize) {
        ItemStack inputItemStack = items.get(0);
        if (!inputItemStack.isEmpty() && recipeHolder != null) {
            ItemStack output = ((CultivationRecipe) recipeHolder.value()).assemble(new SingleRecipeInput(inputItemStack));
            if (output.isEmpty()) {
                return false;
            } else {
                ItemStack outputSlot = items.get(2);
                if (outputSlot.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(outputSlot, output)) {
                    return false;
                } else if (outputSlot.getCount() + output.getCount() <= maxStackSize && outputSlot.getCount() + output.getCount() <= outputSlot.getMaxStackSize()) {
                    return true;
                } else {
                    return outputSlot.getCount() + output.getCount() <= output.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean cultivate(RegistryAccess registryAccess, RecipeHolder<?> recipeHolder, NonNullList<ItemStack> items, int maxStackSize) {
        if (recipeHolder != null && CultivatorBlockEntity.canCultivate(registryAccess, recipeHolder, items, maxStackSize)) {
            ItemStack input = items.get(0);
            ItemStack output = ((CultivationRecipe) recipeHolder.value()).assemble(new SingleRecipeInput(items.get(0)));
            ItemStack outputSlot = items.get(2);
            if (outputSlot.isEmpty()) {
                items.set(2, output.copy());
            } else if (outputSlot.is(output.getItem())) {
                outputSlot.grow(output.getCount());
            }

            input.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    public int getBiomassDuration(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 0;
        }
        return ValueMaps.getBiomassValue(itemStack.typeHolder());
    }

    private static int getTotalCultivationTime(ServerLevel serverLevel, CultivatorBlockEntity cultivatorBlockEntity) {
        return cultivatorBlockEntity.recipeCheck.getRecipeFor(new SingleRecipeInput(cultivatorBlockEntity.getItem(0)), serverLevel).map(recipeHolder -> recipeHolder.value().getTime()).orElse(6000);
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
        if (slotIndex == 0 && !flag) {
            if (this.getLevel() instanceof ServerLevel serverLevel) {
                this.cultivationTotalTime = CultivatorBlockEntity.getTotalCultivationTime(serverLevel, this);
                this.cultivationProgress = 0;
                this.setChanged();
            }
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (slot == 0) {
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
                CultivatorBlockEntity.createExperience(level, popVec, entry.getIntValue(), ((CultivationRecipe) recipeHolder.value()).getExperience());
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
        return FCCoreUtils.translation("container", "cultivator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
        return new CultivatorMenu(windowId, inventory, this);
    }
}
