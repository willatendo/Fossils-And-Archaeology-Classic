package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.tags.FCItemTags;
import ca.willatendo.simplelibrary.data.providers.tag.SimpleItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public final class FCItemTagsProvider extends SimpleItemTagsProvider {
    public FCItemTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCItemTags.ANCIENT_TOOL_MATERIALS);
        this.tag(FCItemTags.DAGGER_TOOL_MATERIALS);
        this.tag(FCItemTags.DRUM_INSTRUMENT).add(Items.STICK);
        this.tag(FCItemTags.ICED_MEAT_TOOL_MATERIALS);
        this.tag(FCItemTags.PIGLIN_TAMING_ARMOR).add(FCItems.ANCIENT_HELMET.get());
        this.tag(FCItemTags.REPAIRS_ANCIENT_ARMOR);
        this.tag(FCItemTags.SCARAB_GEM_TOOL_MATERIALS);
        this.tag(FCItemTags.STEGOSAURUS_COMMANDABLES).add(Items.STICK);
        this.tag(FCItemTags.TRICERATOPS_COMMANDABLES).add(Items.STICK);
    }
}
