package ca.willatendo.fossilsclassic.server.entity.utils;

import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import ca.willatendo.fossilsclassic.server.entity.command_type.FCCommandTypes;
import com.google.common.collect.Maps;
import net.minecraft.core.Holder;

import java.util.Map;

public final class CommandTypeUtils {
    public static final Map<Integer, Holder<CommandType>> COMMAND_TYPES_BY_CODE = Maps.newHashMap();
    public static final Map<String, Integer> NAME_TO_CODE = Maps.newHashMap();

    public static void register(String name, int code, Holder<CommandType> holder) {
        NAME_TO_CODE.put(name, code);
        COMMAND_TYPES_BY_CODE.put(code, holder);
    }

    public static Holder<CommandType> get(String commandTypeName) {
        return COMMAND_TYPES_BY_CODE.getOrDefault(NAME_TO_CODE.getOrDefault(commandTypeName, 0), FCCommandTypes.STAY);
    }

    public static Holder<CommandType> getNext(String commandTypeName) {
        return COMMAND_TYPES_BY_CODE.getOrDefault(NAME_TO_CODE.getOrDefault(commandTypeName, 0) + 1, FCCommandTypes.STAY);
    }

    public static Holder<CommandType> getNext(Holder<CommandType> commandType) {
        return COMMAND_TYPES_BY_CODE.getOrDefault(commandType.value().code() + 1, FCCommandTypes.STAY);
    }
}
