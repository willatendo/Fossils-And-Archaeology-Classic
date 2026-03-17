package ca.willatendo.fossilsclassic.server.gene.genes;


import ca.willatendo.fossilsclassic.server.gene.FCGeneTypes;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public record AttributeGene(Component name, List<AttributeGene.InitialAttribute> initialAttributes) implements Gene {
    public static final MapCodec<AttributeGene> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ComponentSerialization.CODEC.fieldOf("name").forGetter(AttributeGene::getName), Codec.list(AttributeGene.InitialAttribute.CODEC).fieldOf("initial_attributes").forGetter(AttributeGene::initialAttributes)).apply(instance, AttributeGene::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AttributeGene> STREAM_CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_STREAM_CODEC, AttributeGene::getName, AttributeGene.InitialAttribute.STREAM_CODEC.apply(ByteBufCodecs.list()), AttributeGene::initialAttributes, AttributeGene::new);

    @Override
    public Component getName() {
        return this.name();
    }

    @Override
    public GeneType<?> getType() {
        return FCGeneTypes.ATTRIBUTE.get();
    }

    public static AttributeGene.Builder builder(Component name) {
        return new AttributeGene.Builder(name);
    }

    public static final class Builder {
        private final List<AttributeGene.InitialAttribute> initialAttributes = Lists.newArrayList();
        private final Component name;

        private Builder(Component name) {
            this.name = name;
        }

        public AttributeGene.Builder add(Holder<Attribute> attribute, double baseValue) {
            this.initialAttributes.add(new InitialAttribute(attribute, baseValue));
            return this;
        }

        public AttributeGene build() {
            return new AttributeGene(this.name, this.initialAttributes);
        }
    }

    public record InitialAttribute(Holder<Attribute> attribute, double baseValue) {
        public static final Codec<AttributeGene.InitialAttribute> CODEC = RecordCodecBuilder.create(instance -> instance.group(Attribute.CODEC.fieldOf("attribute").forGetter(AttributeGene.InitialAttribute::attribute), Codec.DOUBLE.fieldOf("base_value").forGetter(AttributeGene.InitialAttribute::baseValue)).apply(instance, AttributeGene.InitialAttribute::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, AttributeGene.InitialAttribute> STREAM_CODEC = StreamCodec.composite(Attribute.STREAM_CODEC, AttributeGene.InitialAttribute::attribute, ByteBufCodecs.DOUBLE, AttributeGene.InitialAttribute::baseValue, AttributeGene.InitialAttribute::new);
    }
}
