package ca.willatendo.fossilsclassic.server.chromosome;

import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public interface Chromosome {
    Codec<Chromosome> DIRECT_CODEC = Chromosome.Type.CODEC.dispatch(Chromosome::getType, Chromosome.Type::getCodec);
    StreamCodec<RegistryFriendlyByteBuf, Chromosome> DIRECT_STREAM_CODEC = Type.STREAM_CODEC.dispatch(Chromosome::getType, Chromosome.Type::getStreamCodec);
    Codec<Holder<Chromosome>> CODEC = RegistryFileCodec.create(FCRegistries.CHROMOSOME, DIRECT_CODEC);
    StreamCodec<RegistryFriendlyByteBuf, Holder<Chromosome>> STREAM_CODEC = ByteBufCodecs.holder(FCRegistries.CHROMOSOME, DIRECT_STREAM_CODEC);

    CosmeticChooser getCosmeticChooser();

    HolderSet<Gene> getCosmeticGenes();

    Chromosome.Type getType();

    enum Type implements StringRepresentable {
        BUILT_IN(0, "built_in", BuiltInChromosome.CODEC, BuiltInChromosome.STREAM_CODEC),
        CUSTOM(1, "custom", CustomChromosome.CODEC, CustomChromosome.STREAM_CODEC);

        public static final Codec<Chromosome.Type> CODEC = StringRepresentable.fromValues(Chromosome.Type::values);
        private static final IntFunction<Chromosome.Type> BY_ID = ByIdMap.continuous(Chromosome.Type::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<RegistryFriendlyByteBuf, Chromosome.Type> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, Chromosome.Type::getId, BY_ID::apply);

        private final int id;
        private final String name;
        private final MapCodec<? extends Chromosome> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, ? extends Chromosome> streamCodec;

        Type(int id, String name, MapCodec<? extends Chromosome> codec, StreamCodec<RegistryFriendlyByteBuf, ? extends Chromosome> streamCodec) {
            this.id = id;
            this.name = name;
            this.codec = codec;
            this.streamCodec = streamCodec;
        }

        public int getId() {
            return this.id;
        }

        public MapCodec<? extends Chromosome> getCodec() {
            return this.codec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, ? extends Chromosome> getStreamCodec() {
            return this.streamCodec;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
