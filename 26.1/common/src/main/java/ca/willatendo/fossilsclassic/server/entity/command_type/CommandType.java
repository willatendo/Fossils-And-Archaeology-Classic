package ca.willatendo.fossilsclassic.server.entity.command_type;

import ca.willatendo.fossilsclassic.server.registry.FCBuiltInRegistries;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record CommandType(String name, int code) {
    public static final StreamCodec<RegistryFriendlyByteBuf, CommandType> DIRECT_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, CommandType::name, ByteBufCodecs.INT, CommandType::code, CommandType::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CommandType>> STREAM_CODEC = ByteBufCodecs.holder(FCRegistries.COMMAND_TYPE, DIRECT_STREAM_CODEC);

    public Component getName() {
        Identifier identifier = FCBuiltInRegistries.COMMAND_TYPES.getKey(this);
        return Component.translatable("command_type." + identifier.getNamespace() + "." + identifier.getPath());
    }
}
