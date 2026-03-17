package ca.willatendo.fossilsclassic.server.entity.fossil_variant;

import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;

import java.util.Optional;

public record FossilVariant(Optional<Component> name, int maxGrowthStage, float boundingBoxBaseWidth, float boundingBoxBaseHeight, float boundingBoxWidthGrowth, float boundingBoxHeightGrowth) {
    public static final Codec<FossilVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(FossilVariant::name), Codec.INT.fieldOf("max_growth_stage").forGetter(FossilVariant::maxGrowthStage), Codec.FLOAT.fieldOf("bounding_box_base_width").forGetter(FossilVariant::boundingBoxBaseWidth), Codec.FLOAT.fieldOf("bounding_box_base_height").forGetter(FossilVariant::boundingBoxBaseHeight), Codec.FLOAT.fieldOf("bounding_box_width_growth").forGetter(FossilVariant::boundingBoxWidthGrowth), Codec.FLOAT.fieldOf("bounding_box_height_growth").forGetter(FossilVariant::boundingBoxHeightGrowth)).apply(instance, FossilVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FossilVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_OPTIONAL_STREAM_CODEC, FossilVariant::name, ByteBufCodecs.INT, FossilVariant::maxGrowthStage, ByteBufCodecs.FLOAT, FossilVariant::boundingBoxBaseWidth, ByteBufCodecs.FLOAT, FossilVariant::boundingBoxBaseHeight, ByteBufCodecs.FLOAT, FossilVariant::boundingBoxWidthGrowth, ByteBufCodecs.FLOAT, FossilVariant::boundingBoxHeightGrowth, FossilVariant::new);
    public static final Codec<Holder<FossilVariant>> CODEC = RegistryFileCodec.create(FCRegistries.FOSSIL_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FossilVariant>> STREAM_CODEC = ByteBufCodecs.holder(FCRegistries.FOSSIL_VARIANT, DIRECT_STREAM_CODEC);
}
