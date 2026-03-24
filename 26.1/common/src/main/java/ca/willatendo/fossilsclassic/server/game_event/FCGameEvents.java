package ca.willatendo.fossilsclassic.server.game_event;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;

public final class FCGameEvents {
    public static final SimpleRegistry<GameEvent> GAME_EVENTS = new SimpleRegistry<>(Registries.GAME_EVENT, FCCoreUtils.ID);

    public static final SimpleHolder<GameEvent> CULTIVATOR_SHATTERED = GAME_EVENTS.register("culitvator_shattered", () -> new GameEvent(16));
}
