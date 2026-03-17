package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.client.FCAtlasIds;
import ca.willatendo.simplelibrary.data.SimpleAtlasProvider;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;

import java.util.List;

public final class FCAtlasProvider extends SimpleAtlasProvider {
    public FCAtlasProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    private List<SpriteSource> noPrefixMapper(String path) {
        return List.of(new DirectoryLister(path, ""));
    }

    @Override
    protected void getAll() {
        this.add(FCAtlasIds.STONE_TABLETS, this.noPrefixMapper("stone_tablet"));
    }
}
