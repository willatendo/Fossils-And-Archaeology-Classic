package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.client.model.*;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.data.generic.CustomModelProvider;
import net.minecraft.data.PackOutput;

import java.util.List;

public final class FCCustomModelProvider extends CustomModelProvider {
    public FCCustomModelProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void addAll() {
        this.add("brachiosaurus", BrachiosaurusModel.createBodyLayer(), "main");
        this.add("futabasaurus", FutabasaurusModel.createBodyLayer(), "main");
        this.add("pteranodon", PteranodonModel.createBodyLayer(), "main");
        this.add("triceratops", TriceratopsModel.createBodyLayer(), "main");

        this.add("citipati", PalaeocraftModel.citipati(), "main", List.of(FCCoreUtils.resource("citipati/head"), FCCoreUtils.resource("citipati/walk")));
        this.add("dromaeosaurus", PalaeocraftModel.dromaeosaurus(), "main");
    }
}
