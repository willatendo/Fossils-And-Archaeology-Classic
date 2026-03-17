package ca.willatendo.fossilsclassic.server.gene.genes;

import ca.willatendo.fossilsclassic.server.gene.FCGeneTypes;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;

import java.util.Optional;

public record SoundGene(Component name, Optional<Holder<SoundEvent>> ambientSound, Optional<Holder<SoundEvent>> hurtSound, Optional<Holder<SoundEvent>> deathSound) implements Gene {
    public static final MapCodec<SoundGene> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ComponentSerialization.CODEC.fieldOf("name").forGetter(SoundGene::getName), SoundEvent.CODEC.optionalFieldOf("ambient_sound").forGetter(SoundGene::ambientSound), SoundEvent.CODEC.optionalFieldOf("hurt_sound").forGetter(SoundGene::hurtSound), SoundEvent.CODEC.optionalFieldOf("death_sound").forGetter(SoundGene::deathSound)).apply(instance, SoundGene::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SoundGene> STREAM_CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_STREAM_CODEC, SoundGene::getName, ByteBufCodecs.optional(SoundEvent.STREAM_CODEC), SoundGene::ambientSound, ByteBufCodecs.optional(SoundEvent.STREAM_CODEC), SoundGene::hurtSound, ByteBufCodecs.optional(SoundEvent.STREAM_CODEC), SoundGene::deathSound, SoundGene::new);

    @Override
    public Component getName() {
        return this.name();
    }

    @Override
    public GeneType<?> getType() {
        return FCGeneTypes.SOUND.get();
    }

    public static SoundGene.Builder builder(Component name) {
        return new SoundGene.Builder(name);
    }

    public static final class Builder {
        private final Component name;
        private Optional<Holder<SoundEvent>> ambientSound;
        private Optional<Holder<SoundEvent>> hurtSound;
        private Optional<Holder<SoundEvent>> deathSound;

        private Builder(Component name) {
            this.name = name;
        }

        public SoundGene.Builder ambientSound(Holder<SoundEvent> ambientSound) {
            this.ambientSound = Optional.of(ambientSound);
            return this;
        }

        public SoundGene.Builder hurtSound(Holder<SoundEvent> hurtSound) {
            this.hurtSound = Optional.of(hurtSound);
            return this;
        }

        public SoundGene.Builder deathSound(Holder<SoundEvent> deathSound) {
            this.deathSound = Optional.of(deathSound);
            return this;
        }

        public SoundGene build() {
            return new SoundGene(this.name, this.ambientSound, this.hurtSound, this.deathSound);
        }
    }
}
