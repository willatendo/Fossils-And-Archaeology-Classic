package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.server.entity.entities.ThrownJavelin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class BrokenJavelinItem extends JavelinItem {
    public BrokenJavelinItem(ToolMaterial toolMaterial, Supplier<EntityType<ThrownJavelin>> thrownJavelin, boolean generateLightning, Properties properties) {
        super(toolMaterial, null, thrownJavelin, generateLightning, properties.durability(toolMaterial.durability()).repairable(toolMaterial.repairItems()));
        this.brokenItem = () -> this;
    }

    public BrokenJavelinItem(ToolMaterial toolMaterial, Supplier<EntityType<ThrownJavelin>> thrownJavelin, Properties properties) {
        this(toolMaterial, thrownJavelin, false, properties);
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int time) {
        return !itemStack.nextDamageWillBreak() && super.releaseUsing(itemStack, level, livingEntity, time);
    }
}
