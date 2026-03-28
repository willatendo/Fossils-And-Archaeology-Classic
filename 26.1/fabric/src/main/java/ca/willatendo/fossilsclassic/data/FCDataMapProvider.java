package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.server.game_event.FCGameEvents;
import ca.willatendo.simplelibrary.data.providers.SimpleDataMapProvider;
import ca.willatendo.simplelibrary.server.data_maps.SimpleLibraryDataMaps;
import ca.willatendo.simplelibrary.server.data_maps.buillt_in.VibrationFrequency;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;

import java.util.concurrent.CompletableFuture;

public class FCDataMapProvider extends SimpleDataMapProvider {
    public FCDataMapProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }

    @Override
    protected void gather(HolderLookup.Provider registries) {
        this.builder(SimpleLibraryDataMaps.VIBRATION_FREQUENCIES).add(FCGameEvents.CULTIVATOR_SHATTERED, new VibrationFrequency(13), false);
    }
}
