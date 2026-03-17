package ca.willatendo.fossilsclassic.server.entity.command_type;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.utils.CommandTypeUtils;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;

public final class FCCommandTypes {
    public static final SimpleRegistry<CommandType> COMMAND_TYPES = new SimpleRegistry<>(FCRegistries.COMMAND_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<CommandType> STAY = FCCommandTypes.register("stay", 0);
    public static final SimpleHolder<CommandType> FOLLOW = FCCommandTypes.register("follow", 1);
    public static final SimpleHolder<CommandType> FREE_MOVE = FCCommandTypes.register("free_move", 2);

    private static SimpleHolder<CommandType> register(String name, int code) {
        SimpleHolder<CommandType> commandType = COMMAND_TYPES.register(name, () -> new CommandType(name, code));
        CommandTypeUtils.register(name, code, commandType);
        return commandType;
    }
}
