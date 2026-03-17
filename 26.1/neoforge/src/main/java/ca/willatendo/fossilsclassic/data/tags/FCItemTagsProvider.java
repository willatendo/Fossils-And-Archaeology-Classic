package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.tags.FCItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public final class FCItemTagsProvider extends ItemTagsProvider {
    public FCItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId) {
        super(packOutput, registries, modId);
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
        this.tag(FCItemTags.TRICERATOPS_COMMANDABLES).add(Items.STICK);
    }
}
