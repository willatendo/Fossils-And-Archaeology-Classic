package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.server.item.FCEquipmentAssets;
import ca.willatendo.simplelibrary.data.SimpleEquipmentAssetProvider;
import net.minecraft.data.PackOutput;

public final class FCEquipmentAssetProvider extends SimpleEquipmentAssetProvider {
    public FCEquipmentAssetProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    public void registerModels() {
        this.humanoidOnly(FCEquipmentAssets.ANCIENT, "ancient");
    }
}
