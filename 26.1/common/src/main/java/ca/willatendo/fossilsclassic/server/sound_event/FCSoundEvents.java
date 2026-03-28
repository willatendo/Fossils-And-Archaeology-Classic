package ca.willatendo.fossilsclassic.server.sound_event;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.sub.SoundEventsSubRegistry;
import net.minecraft.sounds.SoundEvent;

public final class FCSoundEvents {
    public static final SoundEventsSubRegistry SOUND_EVENTS = new SoundEventsSubRegistry(FCCoreUtils.ID);

    public static final SimpleHolder<SoundEvent> SMILODON_AMBIENT = SOUND_EVENTS.registerVariableRange("entity.smilodon.ambient");
    public static final SimpleHolder<SoundEvent> SMILODON_DEATH = SOUND_EVENTS.registerVariableRange("entity.smilodon.death");
    public static final SimpleHolder<SoundEvent> SMILODON_HURT = SOUND_EVENTS.registerVariableRange("entity.smilodon.hurt");
    public static final SimpleHolder<SoundEvent> STEGOSAURUS_AMBIENT = SOUND_EVENTS.registerVariableRange("entity.stegosaurus.ambient");
    public static final SimpleHolder<SoundEvent> STEGOSAURUS_DEATH = SOUND_EVENTS.registerVariableRange("entity.stegosaurus.death");
    public static final SimpleHolder<SoundEvent> STEGOSAURUS_HURT = SOUND_EVENTS.registerVariableRange("entity.stegosaurus.hurt");
    public static final SimpleHolder<SoundEvent> TRICERATOPS_AMBIENT = SOUND_EVENTS.registerVariableRange("entity.triceratops.ambient");
    public static final SimpleHolder<SoundEvent> TRICERATOPS_DEATH = SOUND_EVENTS.registerVariableRange("entity.triceratops.death");
    public static final SimpleHolder<SoundEvent> TRICERATOPS_HURT = SOUND_EVENTS.registerVariableRange("entity.triceratops.hurt");

    public static final SimpleHolder<SoundEvent> DRUM_HIT = SOUND_EVENTS.registerVariableRange("block.drum.hit");
    public static final SimpleHolder<SoundEvent> DRUM_TRIPLE_HIT = SOUND_EVENTS.registerVariableRange("block.drum.triple_hit");
}
