package ca.willatendo.fossilsclassic.server.item.items;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class ModSmithingTemplateItem extends SmithingTemplateItem {
    private static final Component GEM_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", FCCoreUtils.resource("smithing_template.scarab_gem_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE);
    private static final Component GEM_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", FCCoreUtils.resource("smithing_template.scarab_gem_upgrade.ingredient"))).withStyle(ChatFormatting.BLUE);
    private static final Component GEM_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", FCCoreUtils.resource("smithing_template.scarab_gem_upgrade.base_slot_description")));
    private static final Component GEM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", FCCoreUtils.resource("smithing_template.scarab_gem_upgrade.additions_slot_description")));
    private static final Identifier EMPTY_SLOT_GEM = FCCoreUtils.resource("container/slot/scarab_gem");

    public ModSmithingTemplateItem(Component appliesTo, Component ingredients, Component baseSlotDescription, Component additionsSlotDescription, List<Identifier> baseSlotEmptyIcons, List<Identifier> additionalSlotEmptyIcons, Properties properties) {
        super(appliesTo, ingredients, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons, properties);
    }

    public static ModSmithingTemplateItem createGemUpgradeTemplate(Properties properties) {
        return new ModSmithingTemplateItem(GEM_UPGRADE_APPLIES_TO, GEM_UPGRADE_INGREDIENTS, GEM_UPGRADE_BASE_SLOT_DESCRIPTION, GEM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, createGemUpgradeIconList(), createGemUpgradeMaterialList(), properties);
    }

    private static List<Identifier> createGemUpgradeIconList() {
        return List.of(SmithingTemplateItem.EMPTY_SLOT_HELMET, SmithingTemplateItem.EMPTY_SLOT_SWORD, SmithingTemplateItem.EMPTY_SLOT_CHESTPLATE, SmithingTemplateItem.EMPTY_SLOT_PICKAXE, SmithingTemplateItem.EMPTY_SLOT_LEGGINGS, SmithingTemplateItem.EMPTY_SLOT_AXE, SmithingTemplateItem.EMPTY_SLOT_BOOTS, SmithingTemplateItem.EMPTY_SLOT_HOE, SmithingTemplateItem.EMPTY_SLOT_SHOVEL);
    }

    private static List<Identifier> createGemUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_GEM);
    }
}
