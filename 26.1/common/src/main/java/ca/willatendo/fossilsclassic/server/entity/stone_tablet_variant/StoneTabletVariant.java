package ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant;

import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public record StoneTabletVariant(int width, int height, Identifier texture, Optional<Component> title, Optional<Component> author) {
    public static final Codec<StoneTabletVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(ExtraCodecs.intRange(1, 16).fieldOf("width").forGetter(StoneTabletVariant::width), ExtraCodecs.intRange(1, 16).fieldOf("height").forGetter(StoneTabletVariant::height), Identifier.CODEC.fieldOf("texture").forGetter(StoneTabletVariant::texture), ComponentSerialization.CODEC.optionalFieldOf("title").forGetter(StoneTabletVariant::title), ComponentSerialization.CODEC.optionalFieldOf("author").forGetter(StoneTabletVariant::author)).apply(instance, StoneTabletVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StoneTabletVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, StoneTabletVariant::width, ByteBufCodecs.VAR_INT, StoneTabletVariant::height, Identifier.STREAM_CODEC, StoneTabletVariant::texture, ComponentSerialization.TRUSTED_OPTIONAL_STREAM_CODEC, StoneTabletVariant::title, ComponentSerialization.TRUSTED_OPTIONAL_STREAM_CODEC, StoneTabletVariant::author, StoneTabletVariant::new);
    public static final Codec<Holder<StoneTabletVariant>> CODEC = RegistryFileCodec.create(FCRegistries.STONE_TABLET_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<StoneTabletVariant>> STREAM_CODEC = ByteBufCodecs.holder(FCRegistries.STONE_TABLET_VARIANT, DIRECT_STREAM_CODEC);

    public int area() {
        return this.width() * this.height();
    }
}
