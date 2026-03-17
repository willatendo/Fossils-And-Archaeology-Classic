package ca.willatendo.fossilsclassic.server.stats;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.simplelibrary.core.registry.sub.CustomStatsSubRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;

public final class FCStats {
    public static final CustomStatsSubRegistry CUSTOM_STATS = new CustomStatsSubRegistry(FCCoreUtils.ID);

    public static final Identifier INTERACT_WITH_ANALYZER = CUSTOM_STATS.registerCustomStat("interact_with_analyzer", StatFormatter.DEFAULT);
    public static final Identifier INTERACT_WITH_ARCHAEOLOGY_WORKBENCH = CUSTOM_STATS.registerCustomStat("interact_with_archaeology_workbench", StatFormatter.DEFAULT);
    public static final Identifier INTERACT_WITH_CULTIVATOR = CUSTOM_STATS.registerCustomStat("interact_with_cultivator", StatFormatter.DEFAULT);
}
