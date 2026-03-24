package ca.willatendo.fossilsclassic.server.block.entities;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.ValueMaps;
import ca.willatendo.fossilsclassic.server.block.FCBlockEntityTypes;
import ca.willatendo.fossilsclassic.server.block.blocks.FeederBlock;
import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.menu.menus.FeederMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class FeederBlockEntity extends BaseContainerBlockEntity {
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
    public int meatLevel = 0;
    public int plantsLevel = 0;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int data) {
            return switch (data) {
                case 0 -> FeederBlockEntity.this.meatLevel;
                case 1 -> FeederBlockEntity.this.plantsLevel;
                default -> 0;
            };
        }

        @Override
        public void set(int data, int set) {
            switch (data) {
                case 0:
                    FeederBlockEntity.this.meatLevel = set;
                    break;
                case 1:
                    FeederBlockEntity.this.plantsLevel = set;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public FeederBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FCBlockEntityTypes.FEEDER.get(), blockPos, blockState);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.meatLevel = valueInput.getIntOr("meat_level", 0);
        this.plantsLevel = valueInput.getIntOr("plant_level", 0);
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(valueInput, this.itemStacks);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("meat_level", this.meatLevel);
        valueOutput.putInt("plant_level", this.plantsLevel);
        ContainerHelper.saveAllItems(valueOutput, this.itemStacks);
    }

    public static void serverTick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, FeederBlockEntity feederBlockEntity) {
        boolean hasFood = (feederBlockEntity.meatLevel > 0 || feederBlockEntity.plantsLevel > 0);
        boolean changed = false;
        ItemStack meatItemStack = feederBlockEntity.itemStacks.get(0);
        ItemStack plantItemStack = feederBlockEntity.itemStacks.get(1);

        if (!meatItemStack.isEmpty()) {
            int fillAmount = ValueMaps.getFeederFoodValue(meatItemStack.getItemHolder(), true);
            if (fillAmount > 0) {
                if (!(fillAmount + feederBlockEntity.meatLevel > 10000)) {
                    feederBlockEntity.meatLevel += fillAmount;
                    meatItemStack.shrink(1);
                    changed = true;
                }
            }
        }
        if (!plantItemStack.isEmpty()) {
            int fillAmount = ValueMaps.getFeederFoodValue(plantItemStack.getItemHolder(), false);
            if (fillAmount > 0) {
                if (!(fillAmount + feederBlockEntity.plantsLevel > 10000)) {
                    feederBlockEntity.plantsLevel += fillAmount;
                    plantItemStack.shrink(1);
                    changed = true;
                }
            }
        }

        if (hasFood) {
            serverLevel.setBlock(blockPos, blockState.setValue(FeederBlock.HAS_FOOD, true), 3);
        } else {
            serverLevel.setBlock(blockPos, blockState.setValue(FeederBlock.HAS_FOOD, false), 3);
        }

        if (changed) {
            BlockEntity.setChanged(serverLevel, blockPos, blockState);
        }
    }

    public boolean hasFood(boolean meat) {
        return meat ? this.meatLevel > 0 : this.plantsLevel > 0;
    }

    public void feed(Dinosaur dinosaur, boolean meat) {
        while (dinosaur.feed() && this.hasFood(meat)) {
            if (meat) {
                this.meatLevel--;
            } else {
                this.plantsLevel--;
            }
        }
    }

    @Override
    public int getContainerSize() {
        return this.itemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.itemStacks) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.itemStacks.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.itemStacks, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.itemStacks, slot);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        ItemStack itemStackInSlot = this.itemStacks.get(slot);
        boolean flag = !itemStack.isEmpty() && ItemStack.isSameItem(itemStackInSlot, itemStack) && ItemStack.isSameItemSameComponents(itemStack, itemStackInSlot);
        this.itemStacks.set(slot, itemStack);
        if (itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }

        if ((slot == 0 || slot == 1) && !flag) {
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.itemStacks.clear();
    }

    @Override
    protected Component getDefaultName() {
        return FCCoreUtils.translation("container", "feeder");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
        return new FeederMenu(windowId, inventory, this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("meat_level", this.meatLevel);
        compoundTag.putInt("plant_level", this.plantsLevel);
        return compoundTag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
