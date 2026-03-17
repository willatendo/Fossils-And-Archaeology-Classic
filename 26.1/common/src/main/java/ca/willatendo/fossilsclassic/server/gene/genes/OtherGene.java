package ca.willatendo.fossilsclassic.server.gene.genes;

import ca.willatendo.fossilsclassic.server.gene.FCGeneTypes;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record OtherGene(Component name, Either<Holder<Item>, TagKey<Item>> foodItem) implements Gene {
    public static final MapCodec<OtherGene> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ComponentSerialization.CODEC.fieldOf("name").forGetter(OtherGene::getName), Codec.either(Item.CODEC, TagKey.codec(Registries.ITEM)).fieldOf("food_item").forGetter(OtherGene::foodItem)).apply(instance, OtherGene::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, OtherGene> STREAM_CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_STREAM_CODEC, OtherGene::getName, ByteBufCodecs.either(Item.STREAM_CODEC, TagKey.streamCodec(Registries.ITEM)), OtherGene::foodItem, OtherGene::new);

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public GeneType<?> getType() {
        return FCGeneTypes.OTHER.get();
    }

    public boolean isFoodItem(ItemStack itemStack) {
        if (this.foodItem().left().isPresent()) {
            return itemStack.is(this.foodItem().left().get());
        } else {
            return itemStack.is(this.foodItem().right().get());
        }
    }
}
