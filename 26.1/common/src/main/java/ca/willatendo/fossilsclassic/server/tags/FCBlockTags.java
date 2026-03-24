package ca.willatendo.fossilsclassic.server.tags;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class FCBlockTags {
    public static final TagKey<Block> FEEDER = FCBlockTags.create("feeder");
    public static final TagKey<Block> JURASSIC_FERN_PLANTABLE_ON = FCBlockTags.create("jurassic_fern_plantable_on");
    public static final TagKey<Block> PERMAFROST_FROSTABLE = FCBlockTags.create("permafrost_frostable");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, FCCoreUtils.resource(name));
    }
}
