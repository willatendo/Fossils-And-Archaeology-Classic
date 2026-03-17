package ca.willatendo.fossilsclassic.server.entity.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Objects;
import java.util.Optional;

public final class HolderUtils {
    private HolderUtils() {
    }

    public static <T> void writeVariant(ValueOutput valueOutput, String tag, Holder<T> variant) {
        variant.unwrapKey().ifPresent(resourceKey -> valueOutput.store(tag, Identifier.CODEC, resourceKey.identifier()));
    }

    public static <T> Optional<Holder<T>> readVariant(ValueInput valueInput, String tag, ResourceKey<? extends Registry<T>> registryKey) {
        Optional<ResourceKey<T>> optional = valueInput.read(tag, Identifier.CODEC).map(identifier -> ResourceKey.create(registryKey, identifier));
        HolderLookup.Provider provider = valueInput.lookup();
        Objects.requireNonNull(provider);
        return optional.flatMap(provider::get);
    }
}
