package ca.willatendo.fossilsclassic.server.menu.slot;

import ca.willatendo.fossilsclassic.server.block.entities.AnalyzerBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AnalyzerResultSlot extends Slot {
    private final Player player;
    private int removeCount;

    public AnalyzerResultSlot(Player player, Container container, int slot, int xPosition, int yPosition) {
        super(container, slot, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }

        return super.remove(amount);
    }

    @Override
    public void onTake(Player player, ItemStack itemStack) {
        this.checkTakeAchievements(itemStack);
        super.onTake(player, itemStack);
    }

    @Override
    protected void onQuickCraft(ItemStack itemStack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(itemStack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack itemStack) {
        itemStack.onCraftedBy(this.player, this.removeCount);
        if (this.player instanceof ServerPlayer serverPlayer) {
            if (this.container instanceof AnalyzerBlockEntity analyzerBlockEntity) {
                analyzerBlockEntity.awardUsedRecipesAndPopExperience(serverPlayer);
            }
        }

        this.removeCount = 0;
    }
}
