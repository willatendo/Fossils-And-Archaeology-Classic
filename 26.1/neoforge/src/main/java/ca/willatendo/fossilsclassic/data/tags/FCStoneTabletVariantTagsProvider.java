package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.data.generic.tag.StoneTabletVariantTagsProvider;
import ca.willatendo.fossilsclassic.server.entity.FCStoneTabletVariants;
import ca.willatendo.fossilsclassic.server.tags.FCStoneTabletVariantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class FCStoneTabletVariantTagsProvider extends StoneTabletVariantTagsProvider {
    public FCStoneTabletVariantTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId) {
        super(packOutput, registries, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FCStoneTabletVariantTags.PLACEABLE).add(FCStoneTabletVariants.LIGHTING, FCStoneTabletVariants.SOCIAL, FCStoneTabletVariants.GREAT_WAR, FCStoneTabletVariants.ANU_DEATH, FCStoneTabletVariants.PORTAL, FCStoneTabletVariants.HEROBRINE, FCStoneTabletVariants.SKELETON_AND_CREEPER, FCStoneTabletVariants.ZOMBIE_AND_SPIDER, FCStoneTabletVariants.TYRANNOSAURUS_IN_ICEBERG, FCStoneTabletVariants.TYRANNOSAURUS_TRANSPORT, FCStoneTabletVariants.TYRANNOSAURUS_MELT, FCStoneTabletVariants.TYRANNOSAURUS_ATTACK, FCStoneTabletVariants.TYRANNOSAURUS_DEATH, FCStoneTabletVariants.TYRANNOSAURUS_CORPSE, FCStoneTabletVariants.PRINCESS, FCStoneTabletVariants.MOSASAURUS, FCStoneTabletVariants.HOLY_MOSASAURUS, FCStoneTabletVariants.PAST, FCStoneTabletVariants.TIME_MACHINE, FCStoneTabletVariants.FUTURE);
    }
}
