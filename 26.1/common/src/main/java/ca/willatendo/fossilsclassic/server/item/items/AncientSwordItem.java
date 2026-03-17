package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.entities.AncientLightningBolt;
import ca.willatendo.fossilsclassic.server.tags.FCItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AncientSwordItem extends Item {
    public AncientSwordItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity targetLivingEntity, LivingEntity attackerLivingEntity) {
        Level level = targetLivingEntity.level();
        LightningBolt lightningBolt;
        if (attackerLivingEntity instanceof Player player && attackerLivingEntity.getItemBySlot(EquipmentSlot.HEAD).is(FCItemTags.PIGLIN_TAMING_ARMOR)) {
            lightningBolt = FCEntityTypes.ANCIENT_LIGHTNING_BOLT.get().create(level, EntitySpawnReason.TRIGGERED);
            ((AncientLightningBolt) lightningBolt).setOwner(player);
        } else {
            lightningBolt = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);
        }
        BlockPos blockPos = targetLivingEntity.blockPosition();
        lightningBolt.snapTo(Vec3.atBottomCenterOf(blockPos));
        level.addFreshEntity(lightningBolt);
        if (level.isClientSide()) {
            level.playSound(targetLivingEntity, blockPos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
