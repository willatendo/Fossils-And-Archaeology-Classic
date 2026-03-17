package ca.willatendo.fossilsclassic.core.utils;

import ca.willatendo.simplelibrary.core.utils.CoreUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class FCCoreUtils {
    public static final String ID = "fossilsclassic";

    public static Identifier resource(String path) {
        return CoreUtils.resource(ID, path);
    }

    public static Component translation(String type, String key) {
        return CoreUtils.translation(type, ID, key);
    }

    public static Component translationWithArguments(String type, String key, Object... args) {
        return Component.translatable(type + "." + ID + "." + key, args);
    }
}
