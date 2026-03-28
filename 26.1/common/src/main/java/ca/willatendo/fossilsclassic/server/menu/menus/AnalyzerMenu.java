package ca.willatendo.fossilsclassic.server.menu.menus;

import ca.willatendo.fossilsclassic.server.block.blocks.AnalyzerBlock;
import ca.willatendo.fossilsclassic.server.block.entities.AnalyzerBlockEntity;
import ca.willatendo.fossilsclassic.server.item.crafting.SingleRecipeInputWithRegistries;
import ca.willatendo.fossilsclassic.server.menu.FCMenuTypes;
import ca.willatendo.fossilsclassic.server.menu.slot.AnalyzerResultSlot;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookTypes;
import ca.willatendo.fossilsclassic.server.recipe.recipes.AnalyzationRecipe;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;

import java.util.List;

public class AnalyzerMenu extends RecipeBookMenu {
    private final ContainerLevelAccess containerLevelAccess;
    public final AnalyzerBlockEntity analyzerBlockEntity;

    public AnalyzerMenu(int windowId, Inventory inventory, AnalyzerBlockEntity analyzerBlockEntity) {
        super(FCMenuTypes.ANALYZER.get(), windowId);

        this.containerLevelAccess = ContainerLevelAccess.create(analyzerBlockEntity.getLevel(), analyzerBlockEntity.getBlockPos());
        this.analyzerBlockEntity = analyzerBlockEntity;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                this.addSlot(new Slot(analyzerBlockEntity, column + row * 3, 8 + column * 18, 18 + row * 18));
            }
        }

        this.addSlot(new AnalyzerResultSlot(inventory.player, analyzerBlockEntity, 9, 102, 22));
        for (int column = 0; column < 3; column++) {
            this.addSlot(new AnalyzerResultSlot(inventory.player, analyzerBlockEntity, column + 10, 98 + column * 18, 54));
        }

        this.addStandardInventorySlots(inventory, 8, 84);

        this.addDataSlots(analyzerBlockEntity.containerData);
    }

    public AnalyzerMenu(int windowId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(windowId, inventory, friendlyByteBuf.readBlockPos());
    }

    public AnalyzerMenu(int windowId, Inventory inventory, BlockPos blockPos) {
        this(windowId, inventory, (AnalyzerBlockEntity) inventory.player.level().getBlockEntity(blockPos));
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
        if (this.analyzerBlockEntity instanceof StackedContentsCompatible stackedContentsCompatible) {
            stackedContentsCompatible.fillStackedContents(stackedItemContents);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.containerLevelAccess.evaluate((level, blockPos) -> level.getBlockState(blockPos).getBlock() instanceof AnalyzerBlock && player.distanceToSqr((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ServerUtils.quickMoveItemStack(this, player, slotIndex, 13);
    }

    public int getAnalyzationProgress() {
        int analyzationProgress = this.analyzerBlockEntity.containerData.get(1);
        int analyzationTotalTime = this.analyzerBlockEntity.containerData.get(2);
        return analyzationTotalTime != 0 && analyzationProgress != 0 ? analyzationProgress * 21 / analyzationTotalTime : 0;
    }

    public boolean isOn() {
        return this.analyzerBlockEntity.containerData.get(0) > 0;
    }

    @Override
    public RecipeBookMenu.PostPlaceAction handlePlacement(boolean useMaxItems, boolean isCreative, RecipeHolder<?> recipeHolder, ServerLevel serverLevel, Inventory inventory) {
        List<Slot> list = List.of(this.getSlot(0), this.getSlot(9));
        RecipeHolder<AnalyzationRecipe> analyzationRecipeRecipeHolder = (RecipeHolder<AnalyzationRecipe>) recipeHolder;
        return ServerPlaceRecipe.placeRecipe(new ServerPlaceRecipe.CraftingMenuAccess<>() {
            @Override
            public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
                AnalyzerMenu.this.fillCraftSlotsStackedContents(stackedItemContents);
            }

            @Override
            public void clearCraftingContent() {
                list.forEach(slot -> slot.set(ItemStack.EMPTY));
            }

            @Override
            public boolean recipeMatches(RecipeHolder<AnalyzationRecipe> recipeHolder) {
                return analyzationRecipeRecipeHolder.value().matches(new SingleRecipeInputWithRegistries(AnalyzerMenu.this.analyzerBlockEntity.getItem(0), serverLevel.registryAccess()), serverLevel);
            }
        }, 1, 1, List.of(this.getSlot(0)), list, inventory, analyzationRecipeRecipeHolder, useMaxItems, isCreative);
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return FCRecipeBookTypes.ANALYZATION;
    }
}
