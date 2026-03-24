package ca.willatendo.fossilsclassic.server.menu;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.menu.menus.AnalyzerMenu;
import ca.willatendo.fossilsclassic.server.menu.menus.ArchaeologyWorkbenchMenu;
import ca.willatendo.fossilsclassic.server.menu.menus.CultivatorMenu;
import ca.willatendo.fossilsclassic.server.menu.menus.FeederMenu;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.sub.MenuTypeSubRegistry;
import net.minecraft.world.inventory.MenuType;

public final class FCMenuTypes {
    public static final MenuTypeSubRegistry MENU_TYPES = new MenuTypeSubRegistry(FCCoreUtils.ID);

    public static final SimpleHolder<MenuType<AnalyzerMenu>> ANALYZER = MENU_TYPES.registerSimple("analyzer", AnalyzerMenu::new, AnalyzerMenu::new);
    public static final SimpleHolder<MenuType<ArchaeologyWorkbenchMenu>> ARCHAEOLOGY_WORKBENCH = MENU_TYPES.registerSimple("archaeology_workbench", ArchaeologyWorkbenchMenu::new, ArchaeologyWorkbenchMenu::new);
    public static final SimpleHolder<MenuType<CultivatorMenu>> CULTIVATOR = MENU_TYPES.registerSimple("cultivator", CultivatorMenu::new, CultivatorMenu::new);
    public static final SimpleHolder<MenuType<FeederMenu>> FEEDER = MENU_TYPES.registerSimple("feeder", FeederMenu::new, FeederMenu::new);
}
