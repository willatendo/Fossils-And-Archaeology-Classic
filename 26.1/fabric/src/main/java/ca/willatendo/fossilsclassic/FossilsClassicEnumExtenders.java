package ca.willatendo.fossilsclassic;

import ca.willatendo.simplelibrary.enum_extender.EnumExtenderInitializer;

import java.util.List;

public final class FossilsClassicEnumExtenders implements EnumExtenderInitializer {
    @Override
    public List<String> getRecipeBookTypes() {
        return List.of("analyzation", "cultivation", "restoration");
    }
}
