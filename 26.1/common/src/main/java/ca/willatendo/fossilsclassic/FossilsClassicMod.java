package ca.willatendo.fossilsclassic;

import ca.willatendo.fossilsclassic.server.attachment_type.FCAttachmentTypes;
import ca.willatendo.fossilsclassic.server.block.FCBlockEntityTypes;
import ca.willatendo.fossilsclassic.server.block.FCBlockTypes;
import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.entity.FCAttributes;
import ca.willatendo.fossilsclassic.server.entity.FCEntityDataSerializers;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.entity.command_type.FCCommandTypes;
import ca.willatendo.fossilsclassic.server.event.FossilsClassicEventListener;
import ca.willatendo.fossilsclassic.server.game_event.FCGameEvents;
import ca.willatendo.fossilsclassic.server.game_rules.FCGameRules;
import ca.willatendo.fossilsclassic.server.gene.FCGeneTypes;
import ca.willatendo.fossilsclassic.server.gene.FCJsonBehaviorTypes;
import ca.willatendo.fossilsclassic.server.item.FCCreativeModeTabs;
import ca.willatendo.fossilsclassic.server.item.FCDataComponents;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.loot.FCEntitySubPredicates;
import ca.willatendo.fossilsclassic.server.loot.FCLootPoolEntries;
import ca.willatendo.fossilsclassic.server.menu.FCMenuTypes;
import ca.willatendo.fossilsclassic.server.recipe.*;
import ca.willatendo.fossilsclassic.server.registry.FCBuiltInRegistries;
import ca.willatendo.fossilsclassic.server.sound_event.FCSoundEvents;
import ca.willatendo.fossilsclassic.server.stats.FCStats;
import ca.willatendo.fossilsclassic.server.structure.FCStructurePoolElementTypes;
import ca.willatendo.fossilsclassic.server.structure.FCStructureProcessorTypes;
import ca.willatendo.simplelibrary.server.ModInit;

public final class FossilsClassicMod {
    public static void modInit(ModInit modInit) {
        FCBuiltInRegistries.init();
        modInit.register(FCEntityDataSerializers.ENTITY_DATA_SERIALIZERS);
        modInit.register(FCAttachmentTypes.ATTACHMENT_TYPES);
        modInit.register(
                FCAttributes.ATTRIBUTES,
                FCJsonBehaviorTypes.JSON_BEHAVIOR_TYPES,
                FCGeneTypes.GENE_TYPES,
                FCStructureProcessorTypes.STRUCTURE_PROCESSOR_TYPES,
                FCStructurePoolElementTypes.STRUCTURE_POOL_ELEMENT_TYPES,
                FCStats.CUSTOM_STATS,
                FCSoundEvents.SOUND_EVENTS,
                FCDataComponents.DATA_COMPONENT_TYPES,
                FCRecipeBookCategories.RECIPE_BOOK_CATEGORIES,
                FCSlotDisplays.SLOT_DISPLAY_TYPES,
                FCRecipeDisplays.RECIPE_DISPLAY_TYPES,
                FCRecipeTypes.RECIPE_TYPE,
                FCRecipeSerializers.RECIPE_SERIALIZERS,
                FCCommandTypes.COMMAND_TYPES,
                FCGameRules.GAME_RULES,
                FCEntitySubPredicates.ENTITY_SUB_PREDICATES,
                FCLootPoolEntries.LOOT_POOL_ENTRY_TYPES,
                FCMenuTypes.MENU_TYPES,
                FCBlockTypes.BLOCK_TYPES,
                FCGameEvents.GAME_EVENTS,
                FCBlocks.BLOCKS,
                FCBlockEntityTypes.BLOCK_ENTITY_TYPES,
                FCEntityTypes.ENTITY_TYPES,
                FCItems.ITEMS,
                FCCreativeModeTabs.CREATIVE_MODE_TABS
        );
        modInit.eventListener(FossilsClassicEventListener.INSTANCE);
    }
}
