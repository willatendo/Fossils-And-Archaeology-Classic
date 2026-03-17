package ca.willatendo.fossilsclassic.server.item.items;

import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.function.Supplier;

public class FrozenMeatItem extends Item {
    private final Supplier<Item> breaksToItemStack;

    public FrozenMeatItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Supplier<Item> breaksToItemStack, Item.Properties properties) {
        super(properties.repairable(toolMaterial.repairItems()).enchantable(toolMaterial.enchantmentValue()).component(DataComponents.TOOL, new Tool(List.of(Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F), Tool.Rule.overrideSpeed(BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK).getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE), Tool.Rule.overrideSpeed(BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK).getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false)).attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double) (attackDamage + toolMaterial.attackDamageBonus()), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double) attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).component(DataComponents.WEAPON, new Weapon(1)));
        this.breaksToItemStack = breaksToItemStack;
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity targetLivingEntity, LivingEntity attackerLivingEntity) {
        if (attackerLivingEntity instanceof Player player && !player.isCreative()) {
            ItemStack breaksToItemStack = new ItemStack(this.breaksToItemStack.get());
            if (itemStack.getCount() > 1) {
                breaksToItemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                player.addItem(breaksToItemStack);
                itemStack.shrink(1);
            } else {
                breaksToItemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                player.setItemInHand(player.getUsedItemHand(), breaksToItemStack);
            }
        }
    }
}
