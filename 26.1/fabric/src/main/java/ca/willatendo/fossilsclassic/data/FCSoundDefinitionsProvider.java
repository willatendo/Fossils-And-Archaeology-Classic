package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.server.sound_event.FCSoundEvents;
import ca.willatendo.simplelibrary.data.providers.SimpleSoundDefinitionsProvider;
import net.minecraft.data.PackOutput;

public final class FCSoundDefinitionsProvider extends SimpleSoundDefinitionsProvider {
    public FCSoundDefinitionsProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    public void registerSounds() {
        this.entity(FCSoundEvents.SMILODON_AMBIENT.get(), "smilodon", "ambientSound", "smilodon/smilodon_ambient_1", "smilodon/smilodon_ambient_2", "smilodon/smilodon_ambient_3");
        this.entity(FCSoundEvents.SMILODON_DEATH.get(), "smilodon", "deathSound", "smilodon/smilodon_death");
        this.entity(FCSoundEvents.SMILODON_HURT.get(), "smilodon", "hurtSound", "smilodon/smilodon_hurt");
        this.entity(FCSoundEvents.STEGOSAURUS_AMBIENT.get(), "stegosaurus", "ambientSound", "stegosaurus/stegosaurus_ambient_1", "stegosaurus/stegosaurus_ambient_2", "stegosaurus/stegosaurus_ambient_3");
        this.entity(FCSoundEvents.STEGOSAURUS_DEATH.get(), "stegosaurus", "deathSound", "stegosaurus/stegosaurus_death");
        this.entity(FCSoundEvents.STEGOSAURUS_HURT.get(), "stegosaurus", "hurtSound", "stegosaurus/stegosaurus_hurt");
        this.entity(FCSoundEvents.TRICERATOPS_AMBIENT.get(), "triceratops", "ambientSound", "triceratops/triceratops_ambient_1", "triceratops/triceratops_ambient_2", "triceratops/triceratops_ambient_3");
        this.entity(FCSoundEvents.TRICERATOPS_DEATH.get(), "triceratops", "deathSound", "triceratops/triceratops_death");
        this.entity(FCSoundEvents.TRICERATOPS_HURT.get(), "triceratops", "hurtSound", "triceratops/triceratops_hurt_1", "triceratops/triceratops_hurt_2");

        this.block(FCSoundEvents.DRUM_HIT.get(), "drum", "hit", "drum_hit");
        this.block(FCSoundEvents.DRUM_TRIPLE_HIT.get(), "drum", "triple_hit", "drum_triple_hit");
    }
}
