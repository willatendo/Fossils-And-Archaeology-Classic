package ca.willatendo.fossilsclassic.server.menu.menus;

import ca.willatendo.fossilsclassic.server.block.blocks.CultivatorBlock;
import ca.willatendo.fossilsclassic.server.block.entities.CultivatorBlockEntity;
import ca.willatendo.fossilsclassic.server.menu.FCMenuTypes;
import ca.willatendo.fossilsclassic.server.menu.slot.CultivatorResultSlot;
import ca.willatendo.fossilsclassic.server.menu.slot.FuelSlot;
import ca.willatendo.fossilsclassic.server.recipe.FCRecipeBookTypes;
import ca.willatendo.fossilsclassic.server.recipe.recipes.CultivationRecipe;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;

import java.util.List;

public class CultivatorMenu extends RecipeBookMenu {
    private final ContainerLevelAccess containerLevelAccess;
    public final CultivatorBlockEntity cultivatorBlockEntity;

    public CultivatorMenu(int windowId, Inventory inventory, CultivatorBlockEntity cultivatorBlockEntity) {
        super(FCMenuTypes.CULTIVATOR.get(), windowId);

        this.containerLevelAccess = ContainerLevelAccess.create(cultivatorBlockEntity.getLevel(), cultivatorBlockEntity.getBlockPos());
        this.cultivatorBlockEntity = cultivatorBlockEntity;

        this.addSlot(new Slot(cultivatorBlockEntity, 0, 49, 20));
        this.addSlot(new FuelSlot(cultivatorBlockEntity, 1, 80, 54, itemStack -> cultivatorBlockEntity.getBiomassDuration(itemStack) > 0));
        this.addSlot(new CultivatorResultSlot(inventory.player, cultivatorBlockEntity, 2, 111, 20));
        this.addStandardInventorySlots(inventory, 8, 84);

        this.addDataSlots(cultivatorBlockEntity.containerData);
    }

    public CultivatorMenu(int windowId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(windowId, inventory, friendlyByteBuf.readBlockPos());
    }

    public CultivatorMenu(int windowId, Inventory inventory, BlockPos blockPos) {
        this(windowId, inventory, (CultivatorBlockEntity) inventory.player.level().getBlockEntity(blockPos));
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
        if (this.cultivatorBlockEntity instanceof StackedContentsCompatible stackedContentsCompatible) {
            stackedContentsCompatible.fillStackedContents(stackedItemContents);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.containerLevelAccess.evaluate((level, blockPos) -> level.getBlockState(blockPos).getBlock() instanceof CultivatorBlock && player.distanceToSqr((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ServerUtils.quickMoveItemStack(this, player, slotIndex, 3);
    }

    public float getOnProgress() {
        int onDuration = this.cultivatorBlockEntity.containerData.get(1);
        if (onDuration == 0) {
            onDuration = 6000;
        }

        return Mth.clamp(this.cultivatorBlockEntity.containerData.get(0) / (float) onDuration, 0.0F, 1.0F);
    }

    public int getCultivationProgress() {
        int cultivationProgress = this.cultivatorBlockEntity.containerData.get(2);
        int cultivationTotalTime = this.cultivatorBlockEntity.containerData.get(3);
        return cultivationTotalTime != 0 && cultivationProgress != 0 ? cultivationProgress * 21 / cultivationTotalTime : 0;
    }

    public boolean isOn() {
        return this.cultivatorBlockEntity.containerData.get(0) > 0;
    }

    @Override
    public PostPlaceAction handlePlacement(boolean useMaxItems, boolean isCreative, RecipeHolder<?> recipeHolder, ServerLevel serverLevel, Inventory inventory) {
        List<Slot> list = List.of(this.getSlot(0), this.getSlot(9));
        RecipeHolder<CultivationRecipe> cultivationRecipeRecipeHolder = (RecipeHolder<CultivationRecipe>) recipeHolder;
        return ServerPlaceRecipe.placeRecipe(new ServerPlaceRecipe.CraftingMenuAccess<>() {
            @Override
            public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
                CultivatorMenu.this.fillCraftSlotsStackedContents(stackedItemContents);
            }

            @Override
            public void clearCraftingContent() {
                list.forEach(slot -> slot.set(ItemStack.EMPTY));
            }

            @Override
            public boolean recipeMatches(RecipeHolder<CultivationRecipe> recipeHolder) {
                return cultivationRecipeRecipeHolder.value().matches(new SingleRecipeInput(CultivatorMenu.this.cultivatorBlockEntity.getItem(0)), serverLevel);
            }
        }, 1, 1, List.of(this.getSlot(0)), list, inventory, cultivationRecipeRecipeHolder, useMaxItems, isCreative);
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return FCRecipeBookTypes.CULTIVATION;
    }
}
