package ca.willatendo.fossilsclassic.server.menu.menus;

import ca.willatendo.fossilsclassic.server.ValueMaps;
import ca.willatendo.fossilsclassic.server.block.blocks.FeederBlock;
import ca.willatendo.fossilsclassic.server.block.entities.FeederBlockEntity;
import ca.willatendo.fossilsclassic.server.menu.FCMenuTypes;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FeederMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess containerLevelAccess;
    public final FeederBlockEntity feederBlockEntity;

    public FeederMenu(int windowId, Inventory inventory, FeederBlockEntity feederBlockEntity) {
        super(FCMenuTypes.FEEDER.get(), windowId);
        Level level = feederBlockEntity.getLevel();
        this.containerLevelAccess = ContainerLevelAccess.create(level, feederBlockEntity.getBlockPos());
        this.feederBlockEntity = feederBlockEntity;

        this.addSlot(new Slot(feederBlockEntity, 0, 60, 62) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return ValueMaps.getFeederFoodValue(itemStack.getItemHolder(), true) > 0;
            }
        });
        this.addSlot(new Slot(feederBlockEntity, 1, 104, 62) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return ValueMaps.getFeederFoodValue(itemStack.getItemHolder(), false) > 0;
            }
        });

        this.addStandardInventorySlots(inventory, 8, 84);

        this.addDataSlots(feederBlockEntity.containerData);
    }

    public FeederMenu(int windowId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(windowId, inventory, friendlyByteBuf.readBlockPos());
    }

    public FeederMenu(int windowId, Inventory inventory, BlockPos blockPos) {
        this(windowId, inventory, (FeederBlockEntity) inventory.player.level().getBlockEntity(blockPos));
    }

    @Override
    public boolean stillValid(Player player) {
        return this.containerLevelAccess.evaluate((level, blockPos) -> level.getBlockState(blockPos).getBlock() instanceof FeederBlock && player.distanceToSqr((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ServerUtils.quickMoveItemStack(this, player, slotIndex, 2);
    }

    public int getMeatScaled(int scale) {
        if (this.feederBlockEntity.containerData.get(0) == 0) {
            return 0;
        }
        return this.feederBlockEntity.containerData.get(0) * scale / 10000;
    }

    public int getPlantsScaled(int scale) {
        if (this.feederBlockEntity.containerData.get(1) == 0) {
            return 0;
        }
        return this.feederBlockEntity.containerData.get(1) * scale / 10000;
    }
}