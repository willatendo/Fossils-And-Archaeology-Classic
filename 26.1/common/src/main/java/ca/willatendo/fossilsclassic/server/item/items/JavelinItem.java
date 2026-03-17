package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.entities.ThrownJavelin;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class JavelinItem extends Item {
    private final ToolMaterial toolMaterial;
    protected Supplier<BrokenJavelinItem> brokenItem;
    private final Supplier<EntityType<ThrownJavelin>> thrownJavelin;
    private final boolean generateLightning;

    public JavelinItem(ToolMaterial toolMaterial, Supplier<BrokenJavelinItem> brokenItem, Supplier<EntityType<ThrownJavelin>> thrownJavelin, boolean generateLightning, Item.Properties properties) {
        super(properties.enchantable(toolMaterial.enchantmentValue()));
        this.toolMaterial = toolMaterial;
        this.brokenItem = brokenItem;
        this.thrownJavelin = thrownJavelin;
        this.generateLightning = generateLightning;
    }

    public JavelinItem(ToolMaterial toolMaterial, Supplier<BrokenJavelinItem> brokenItem, Supplier<EntityType<ThrownJavelin>> thrownJavelin, Item.Properties properties) {
        this(toolMaterial, brokenItem, thrownJavelin, false, properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 72000;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int time) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(itemStack, livingEntity) - time;
            if (i >= 0) {
                player.awardStat(Stats.ITEM_USED.get(this));

                if (level instanceof ServerLevel serverLevel) {
                    ItemStack damagedItemStack = itemStack;
                    if (!player.isCreative() && !(itemStack.getItem() instanceof BrokenJavelinItem)) {
                        damagedItemStack = new ItemStack(this.brokenItem.get());
                    }
                    damagedItemStack.hurtWithoutBreaking(1, player);
                    ThrownJavelin thrownJavelin = Projectile.spawnProjectileFromRotation((serverLevelIn, livingEntityIn, itemStackIn) -> new ThrownJavelin(this.thrownJavelin.get(), serverLevelIn, livingEntityIn, itemStackIn, 6.0F + this.toolMaterial.attackDamageBonus(), this.generateLightning), serverLevel, damagedItemStack, player, 0.0F, 2.5F, 1.0F);

                    if (player.getAbilities().instabuild) {
                        thrownJavelin.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    level.playSound(null, thrownJavelin, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);

                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.nextDamageWillBreak()) {
            return InteractionResult.FAIL;
        } else {
            player.startUsingItem(interactionHand);
            return InteractionResult.CONSUME;
        }
    }
}
