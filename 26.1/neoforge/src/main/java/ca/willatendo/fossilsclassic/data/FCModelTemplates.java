package ca.willatendo.fossilsclassic.data;

import ca.willatendo.simplelibrary.core.utils.CoreUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;

import java.util.Optional;

public final class FCModelTemplates {
    public static final ModelTemplate FROGSPAWN = FCModelTemplates.createMC("frogspawn", TextureSlot.TEXTURE, TextureSlot.PARTICLE).extend().renderType("cutout").build();

    public static ModelTemplate createMC(String name, TextureSlot... requiredSlots) {
        return new ModelTemplate(Optional.of(CoreUtils.minecraft("block/" + name)), Optional.empty(), requiredSlots);
    }
}
