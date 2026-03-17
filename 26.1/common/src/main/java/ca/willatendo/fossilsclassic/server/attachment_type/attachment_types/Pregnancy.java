package ca.willatendo.fossilsclassic.server.attachment_type.attachment_types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public final class Pregnancy {
    public static final Codec<Pregnancy> CODEC = RecordCodecBuilder.create(instance -> instance.group(ComponentSerialization.CODEC.fieldOf("display_name").forGetter(Pregnancy::getDisplayName), Codec.INT.fieldOf("max_ticks").forGetter(Pregnancy::getMaxTicks), EntityType.CODEC.fieldOf("entity_type").forGetter(Pregnancy::getOffspringEntityType), Codec.INT.fieldOf("remaining_ticks").forGetter(Pregnancy::getRemainingTicks)).apply(instance, Pregnancy::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Pregnancy> STREAM_CODEC = StreamCodec.composite(ComponentSerialization.STREAM_CODEC, Pregnancy::getDisplayName, ByteBufCodecs.VAR_INT, Pregnancy::getMaxTicks, EntityType.STREAM_CODEC, Pregnancy::getOffspringEntityType, ByteBufCodecs.VAR_INT, Pregnancy::getRemainingTicks, Pregnancy::new);
    private final Component displayName;
    private int maxTicks;
    private EntityType<? extends Mob> entityType;
    private int remainingTicks;

    public Pregnancy(Component displayName) {
        this.displayName = displayName;
    }

    public Pregnancy(Component displayName, int maxTicks, EntityType<? extends Mob> entityType) {
        this.displayName = displayName;
        this.maxTicks = maxTicks;
        this.entityType = entityType;
    }

    private Pregnancy(Component displayName, int maxTicks, EntityType<?> entityType, int remainingTicks) {
        this.displayName = displayName;
        this.maxTicks = maxTicks;
        this.entityType = (EntityType) entityType;
        this.remainingTicks = remainingTicks;
    }

    public Component getDisplayName() {
        return this.displayName;
    }

    public int getMaxTicks() {
        return this.maxTicks;
    }

    public EntityType<? extends Mob> getOffspringEntityType() {
        return this.entityType;
    }

    public void setRemainingTicks(int remainingTicks) {
        this.remainingTicks = remainingTicks;
    }

    public int getRemainingTicks() {
        return this.remainingTicks;
    }
}
