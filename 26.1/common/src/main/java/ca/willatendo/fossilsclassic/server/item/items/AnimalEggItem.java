package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.entities.ThrownAnimalEgg;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class AnimalEggItem<T extends AgeableMob> extends Item implements ProjectileItem {
    private final AnimalEggItem.FromLivingEntity<T> fromLivingEntity;
    private final AnimalEggItem.FromPosition<T> fromPosition;

    public AnimalEggItem(AnimalEggItem.FromLivingEntity<T> fromLivingEntity, AnimalEggItem.FromPosition<T> fromPosition, Item.Properties properties) {
        super(properties);
        this.fromLivingEntity = fromLivingEntity;
        this.fromPosition = fromPosition;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide()) {
            ThrownAnimalEgg<T> thrownAnimalEgg = this.fromLivingEntity.create(level, player, player.getItemInHand(interactionHand));
            if (itemStack.has(DataComponents.CUSTOM_NAME)) {
                thrownAnimalEgg.setCustomName(itemStack.getCustomName());
            }
            thrownAnimalEgg.setItem(itemStack);
            thrownAnimalEgg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownAnimalEgg);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);

        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return this.fromPosition.create(level, position.x(), position.y(), position.z(), itemStack);
    }

    public interface FromLivingEntity<T extends AgeableMob> {
        ThrownAnimalEgg<T> create(Level level, LivingEntity livingEntity, ItemStack itemStack);
    }

    public interface FromPosition<T extends AgeableMob> {
        ThrownAnimalEgg<T> create(Level level, double x, double y, double z, ItemStack itemStack);
    }
}
