package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.server.game_event.FCGameEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;

import java.util.concurrent.CompletableFuture;

public class FCDataMapProvider extends DataMapProvider {
    public FCDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void gather(HolderLookup.Provider registries) {
        this.builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES).add(FCGameEvents.CULTIVATOR_SHATTERED, new VibrationFrequency(13), false);
    }
}
