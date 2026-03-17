package ca.willatendo.fossilsclassic.server.item;

import ca.willatendo.fossilsclassic.server.tags.FCItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public final class FCToolMaterials {
    public static final ToolMaterial ANCIENT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, FCItemTags.ANCIENT_TOOL_MATERIALS);
    public static final ToolMaterial DAGGER = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, FCItemTags.DAGGER_TOOL_MATERIALS);
    public static final ToolMaterial SCARAB_GEM = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 4231, 10.0F, 5.0F, 17, FCItemTags.SCARAB_GEM_TOOL_MATERIALS);
    public static final ToolMaterial ICED_MEAT = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 4, 4.0F, 6.0F, 1, FCItemTags.ICED_MEAT_TOOL_MATERIALS);
}
