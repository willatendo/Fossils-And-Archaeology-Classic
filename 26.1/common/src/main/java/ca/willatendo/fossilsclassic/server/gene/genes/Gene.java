package ca.willatendo.fossilsclassic.server.gene.genes;

import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import ca.willatendo.fossilsclassic.server.registry.FCBuiltInRegistries;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;

public interface Gene {
    Codec<Gene> DIRECT_CODEC = FCBuiltInRegistries.GENE_TYPES.byNameCodec().dispatch(Gene::getType, GeneType::codec);
    StreamCodec<RegistryFriendlyByteBuf, Gene> DIRECT_STREAM_CODEC = ByteBufCodecs.registry(FCRegistries.GENE_TYPE).dispatch(Gene::getType, GeneType::streamCodec);
    Codec<Holder<Gene>> CODEC = RegistryFileCodec.create(FCRegistries.GENE, DIRECT_CODEC);
    Codec<HolderSet<Gene>> LIST_CODEC = RegistryCodecs.homogeneousList(FCRegistries.GENE, DIRECT_CODEC);
    StreamCodec<RegistryFriendlyByteBuf, Holder<Gene>> STREAM_CODEC = ByteBufCodecs.holder(FCRegistries.GENE, DIRECT_STREAM_CODEC);
    StreamCodec<RegistryFriendlyByteBuf, HolderSet<Gene>> LIST_STREAM_CODEC = ByteBufCodecs.holderSet(FCRegistries.GENE);

    Component getName();

    GeneType<?> getType();
}
