package ca.willatendo.fossilsclassic.server.chromosome;

import ca.willatendo.fossilsclassic.server.gene.genes.Gene;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

public interface CosmeticChooser {
    Codec<CosmeticChooser> CODEC = CosmeticChooser.Type.CODEC.dispatch(CosmeticChooser::getType, CosmeticChooser.Type::getCodec);
    StreamCodec<RegistryFriendlyByteBuf, CosmeticChooser> STREAM_CODEC = CosmeticChooser.Type.STREAM_CODEC.dispatch(CosmeticChooser::getType, type -> type.getStreamCodec());

    CosmeticChooser.Random RANDOM = new CosmeticChooser.Random();

    static CosmeticChooser.ByBiome byBiome(Holder<Gene> defaultGene, Map<ResourceKey<Biome>, Holder<Gene>> biomes) {
        return new CosmeticChooser.ByBiome(defaultGene, biomes.entrySet().stream().map(entry -> new CosmeticChooser.BiomeAndGene(entry.getKey(), entry.getValue())).toList());
    }

    Optional<Holder<Gene>> getGene(HolderSet<Gene> genes, RandomSource randomSource, Holder<Biome> biome);

    CosmeticChooser.Type getType();

    record ByBiome(Holder<Gene> defaultGene, List<CosmeticChooser.BiomeAndGene> biomeAndGene) implements CosmeticChooser {
        public static final MapCodec<CosmeticChooser.ByBiome> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Gene.CODEC.fieldOf("default_gene").forGetter(CosmeticChooser.ByBiome::defaultGene), Codec.list(CosmeticChooser.BiomeAndGene.CODEC).fieldOf("biomes").forGetter(CosmeticChooser.ByBiome::biomeAndGene)).apply(instance, CosmeticChooser.ByBiome::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, CosmeticChooser.ByBiome> STREAM_CODEC = StreamCodec.composite(Gene.STREAM_CODEC, CosmeticChooser.ByBiome::defaultGene, CosmeticChooser.BiomeAndGene.STREAM_CODEC.apply(ByteBufCodecs.list()), CosmeticChooser.ByBiome::biomeAndGene, CosmeticChooser.ByBiome::new);

        @Override
        public Optional<Holder<Gene>> getGene(HolderSet<Gene> genes, RandomSource randomSource, Holder<Biome> biome) {
            for (BiomeAndGene biomeAndGene : this.biomeAndGene()) {
                if (biome.is(biomeAndGene.biome())) {
                    return Optional.of(biomeAndGene.gene());
                }
            }
            return Optional.of(this.defaultGene());
        }

        @Override
        public CosmeticChooser.Type getType() {
            return CosmeticChooser.Type.BY_BIOME;
        }
    }

    record BiomeAndGene(ResourceKey<Biome> biome, Holder<Gene> gene) {
        public static final Codec<CosmeticChooser.BiomeAndGene> CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceKey.codec(Registries.BIOME).fieldOf("biome").forGetter(CosmeticChooser.BiomeAndGene::biome), Gene.CODEC.fieldOf("biome").forGetter(CosmeticChooser.BiomeAndGene::gene)).apply(instance, CosmeticChooser.BiomeAndGene::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, CosmeticChooser.BiomeAndGene> STREAM_CODEC = StreamCodec.composite(ResourceKey.streamCodec(Registries.BIOME), CosmeticChooser.BiomeAndGene::biome, Gene.STREAM_CODEC, CosmeticChooser.BiomeAndGene::gene, CosmeticChooser.BiomeAndGene::new);
    }

    class Random implements CosmeticChooser {
        public static final MapCodec<CosmeticChooser.Random> CODEC = MapCodec.unit(() -> CosmeticChooser.RANDOM);
        public static final StreamCodec<RegistryFriendlyByteBuf, CosmeticChooser.Random> STREAM_CODEC = StreamCodec.unit(CosmeticChooser.RANDOM);

        private Random() {
        }

        @Override
        public Optional<Holder<Gene>> getGene(HolderSet<Gene> genes, RandomSource randomSource, Holder<Biome> biome) {
            return genes.getRandomElement(randomSource);
        }

        @Override
        public CosmeticChooser.Type getType() {
            return CosmeticChooser.Type.RANDOM;
        }
    }

    enum Single implements CosmeticChooser {
        INSTANCE;

        public static final MapCodec<CosmeticChooser.Single> CODEC = MapCodec.unit(() -> CosmeticChooser.Single.INSTANCE);
        public static final StreamCodec<RegistryFriendlyByteBuf, CosmeticChooser.Single> STREAM_CODEC = StreamCodec.unit(CosmeticChooser.Single.INSTANCE);

        @Override
        public Optional<Holder<Gene>> getGene(HolderSet<Gene> genes, RandomSource randomSource, Holder<Biome> biome) {
            return Optional.of(genes.get(0));
        }

        @Override
        public Type getType() {
            return CosmeticChooser.Type.SINGLE;
        }
    }

    enum Type implements StringRepresentable {
        BY_BIOME(0, "by_biome", CosmeticChooser.ByBiome.CODEC, CosmeticChooser.ByBiome.STREAM_CODEC),
        RANDOM(1, "random", CosmeticChooser.Random.CODEC, CosmeticChooser.Random.STREAM_CODEC),
        SINGLE(2, "single", CosmeticChooser.Single.CODEC, CosmeticChooser.Single.STREAM_CODEC);

        public static final Codec<CosmeticChooser.Type> CODEC = StringRepresentable.fromValues(CosmeticChooser.Type::values);
        private static final IntFunction<CosmeticChooser.Type> BY_ID = ByIdMap.continuous(Type::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<RegistryFriendlyByteBuf, CosmeticChooser.Type> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, CosmeticChooser.Type::getId, BY_ID::apply);

        private final int id;
        private final String name;
        private final MapCodec<? extends CosmeticChooser> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, ? extends CosmeticChooser> streamCodec;

        Type(int id, String name, MapCodec<? extends CosmeticChooser> codec, StreamCodec<RegistryFriendlyByteBuf, ? extends CosmeticChooser> streamCodec) {
            this.id = id;
            this.name = name;
            this.codec = codec;
            this.streamCodec = streamCodec;
        }

        public int getId() {
            return this.id;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public MapCodec<? extends CosmeticChooser> getCodec() {
            return this.codec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, ? extends CosmeticChooser> getStreamCodec() {
            return this.streamCodec;
        }
    }
}
