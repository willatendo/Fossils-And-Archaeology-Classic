package ca.willatendo.fossilsclassic.client.event;

import ca.willatendo.fossilsclassic.client.*;
import ca.willatendo.fossilsclassic.client.fossil_render.FossilRenderLoader;
import ca.willatendo.fossilsclassic.client.gene_cosmetic.GeneCosmeticLoader;
import ca.willatendo.fossilsclassic.client.json_animation.CustomAnimationLoader;
import ca.willatendo.fossilsclassic.client.json_model.CustomModelLayerDefinitionResourceManager;
import ca.willatendo.fossilsclassic.client.json_model.CustomModelLoader;
import ca.willatendo.fossilsclassic.client.model.*;
import ca.willatendo.fossilsclassic.client.render.*;
import ca.willatendo.fossilsclassic.client.screen.AnalyzerScreen;
import ca.willatendo.fossilsclassic.client.screen.ArchaeologyWorkbenchScreen;
import ca.willatendo.fossilsclassic.client.screen.CultivatorScreen;
import ca.willatendo.fossilsclassic.client.screen.FeederScreen;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.menu.FCMenuTypes;
import ca.willatendo.fossilsclassic.server.recipe.display.AnalyzationRecipeDisplay;
import ca.willatendo.fossilsclassic.server.recipe.display.CultivationRecipeDisplay;
import ca.willatendo.fossilsclassic.server.recipe.display.RestorationRecipeDisplay;
import ca.willatendo.simplelibrary.client.ClientEventListener;
import ca.willatendo.simplelibrary.client.screen.recipe_book.CustomOverlayRecipeComponent;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class FossilsClassicClientEventListener implements ClientEventListener {
    public static final FossilsClassicClientEventListener INSTANCE = new FossilsClassicClientEventListener();

    private FossilsClassicClientEventListener() {
    }

    @Override
    public void registerClientReloadListener(ClientReloadListenerRegister clientReloadListenerRegister) {
        clientReloadListenerRegister.apply(FCCoreUtils.resource("custom_animation"), CustomAnimationLoader.INSTANCE);
        clientReloadListenerRegister.apply(FCCoreUtils.resource("custom_model"), CustomModelLoader.INSTANCE);
        clientReloadListenerRegister.apply(FCCoreUtils.resource("custom_layers"), CustomModelLayerDefinitionResourceManager.INSTANCE);
        clientReloadListenerRegister.apply(FCCoreUtils.resource("fossil_render"), FossilRenderLoader.INSTANCE);
        clientReloadListenerRegister.apply(FCCoreUtils.resource("gene_cosmetic"), GeneCosmeticLoader.INSTANCE);
    }

    @Override
    public void registerKeyMappings(KeyMappingRegister keyMappingRegister) {
        keyMappingRegister.apply(FCKeys.INGENS_LIST);
    }

    @Override
    public void registerLayerDefinitions(LayerDefinitionRegister layerDefinitionRegister) {
        ArmorModelSet<LayerDefinition> armorModelSet = HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION, LayerDefinitions.OUTER_ARMOR_DEFORMATION).map(meshDefinition -> LayerDefinition.create(meshDefinition, 64, 32));
        layerDefinitionRegister.apply(FCLayers.BONES, BonesModel::createBodyLayer);
        this.add(layerDefinitionRegister, FCLayers.BONES_ARMOR, armorModelSet);
        layerDefinitionRegister.apply(FCLayers.BRACHIOSAURUS, BrachiosaurusModel::createBodyLayer);
        layerDefinitionRegister.apply(FCLayers.EGG, EggModel::createBodyLayer);
        layerDefinitionRegister.apply(FCLayers.FAILURESAURUS, FailuresaurusModel::createBodyLayer);
        layerDefinitionRegister.apply(FCLayers.FUTABASAURUS, FutabasaurusModel::createBodyLayer);
        layerDefinitionRegister.apply(FCLayers.PTERANODON, PteranodonModel::createBodyLayer);
        layerDefinitionRegister.apply(FCLayers.THROWN_JAVELIN, ThrownJavelinModel::createBodyLayer);
        layerDefinitionRegister.apply(FCLayers.TRICERATOPS, TriceratopsModel::createBodyLayer);
    }

    private void add(LayerDefinitionRegister layerDefinitionRegister, ArmorModelSet<ModelLayerLocation> armorModelSet, ArmorModelSet<LayerDefinition> other) {
        layerDefinitionRegister.apply(armorModelSet.head(), other::head);
        layerDefinitionRegister.apply(armorModelSet.chest(), other::chest);
        layerDefinitionRegister.apply(armorModelSet.legs(), other::legs);
        layerDefinitionRegister.apply(armorModelSet.feet(), other::feet);
    }

    @Override
    public void registerRenderers(RendererRegister rendererRegister) {
        rendererRegister.apply(FCEntityTypes.BONES.get(), BonesRenderer::new);
        rendererRegister.apply(FCEntityTypes.FAILURESAURUS.get(), FailuresaurusRenderer::new);

        rendererRegister.apply(FCEntityTypes.ANCIENT_LIGHTNING_BOLT.get(), LightningBoltRenderer::new);

        rendererRegister.apply(FCEntityTypes.STEGOSAURUS.get(), StegosaurusRenderer::new);
        rendererRegister.apply(FCEntityTypes.TRICERATOPS.get(), TriceratopsRenderer::new);
        //rendererRegister.apply(FCEntityTypes.CUSTOM.get(), CustomRenderer::new);

        rendererRegister.apply(FCEntityTypes.FOSSIL.get(), FossilRenderer::new);
        rendererRegister.apply(FCEntityTypes.STONE_TABLET.get(), StoneTabletRenderer::new);

        rendererRegister.apply(FCEntityTypes.THROWN_WOODEN_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/wooden_javelin.png")));
        rendererRegister.apply(FCEntityTypes.THROWN_STONE_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/stone_javelin.png")));
        rendererRegister.apply(FCEntityTypes.THROWN_COPPER_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/copper_javelin.png")));
        rendererRegister.apply(FCEntityTypes.THROWN_IRON_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/iron_javelin.png")));
        rendererRegister.apply(FCEntityTypes.THROWN_GOLDEN_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/golden_javelin.png")));
        rendererRegister.apply(FCEntityTypes.THROWN_DIAMOND_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/diamond_javelin.png")));
        rendererRegister.apply(FCEntityTypes.THROWN_NETHERITE_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/netherite_javelin.png")));
        rendererRegister.apply(FCEntityTypes.THROWN_SCARAB_GEM_JAVELIN.get(), context -> new ThrownJavelinRenderer(context, FCCoreUtils.resource("textures/entity/javelin/scarab_gem_javelin.png")));

        rendererRegister.apply(FCEntityTypes.TRICERATOPS_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/triceratops_egg.png")));
        rendererRegister.apply(FCEntityTypes.VELOCIRAPTOR_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/velociraptor_egg.png")));
        rendererRegister.apply(FCEntityTypes.TYRANNOSAURUS_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/tyrannosaurus_egg.png")));
        rendererRegister.apply(FCEntityTypes.PTERANODON_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/pteranodon_egg.png")));
        rendererRegister.apply(FCEntityTypes.FUTABASAURUS_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/futabasaurus_egg.png")));
        rendererRegister.apply(FCEntityTypes.MOSASAURUS_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/mosasaurus_egg.png")));
        rendererRegister.apply(FCEntityTypes.STEGOSAURUS_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/stegosaurus_egg.png")));
        rendererRegister.apply(FCEntityTypes.DILOPHOSAURUS_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/dilophosaurus_egg.png")));
        rendererRegister.apply(FCEntityTypes.BRACHIOSAURUS_EGG.get(), context -> new EggRenderer(context, FCCoreUtils.resource("textures/entity/egg/brachiosaurus_egg.png")));
        rendererRegister.apply(FCEntityTypes.INCUBATED_CHICKEN_EGG.get(), ThrownItemRenderer::new);
        rendererRegister.apply(FCEntityTypes.INCUBATED_PARROT_EGG.get(), ThrownItemRenderer::new);
    }

    @Override
    public void registerRecipeBookOverlay(RecipeBookOverlayRegister recipeBookOverlayRegister) {
        recipeBookOverlayRegister.apply(FCRecipeBookOverlays.ANALYZER, Pair.of((recipeDisplay, contextMap) -> {
            if (recipeDisplay instanceof AnalyzationRecipeDisplay analyzationRecipeDisplay) {
                List<ItemStack> list = analyzationRecipeDisplay.ingredient().resolveForStacks(contextMap);
                if (!list.isEmpty()) {
                    return List.of(CustomOverlayRecipeComponent.createGridPos(1, 1, list));
                }
            }
            return List.of();
        }, (enabled, hoveredOrFocused) -> {
            if (enabled) {
                return hoveredOrFocused ? FCCoreUtils.resource("recipe_book/analyzation_overlay_highlighted") : FCCoreUtils.resource("recipe_book/analyzation_overlay");
            } else {
                return hoveredOrFocused ? FCCoreUtils.resource("recipe_book/analyzation_overlay_disabled_highlighted") : FCCoreUtils.resource("recipe_book/analyzation_overlay_disabled");
            }
        }));
        recipeBookOverlayRegister.apply(FCRecipeBookOverlays.ARCHAEOLOGY_WORKBENCH, Pair.of((recipeDisplay, contextMap) -> {
            if (recipeDisplay instanceof RestorationRecipeDisplay restorationRecipeDisplay) {
                List<ItemStack> list = restorationRecipeDisplay.ingredient().resolveForStacks(contextMap);
                if (!list.isEmpty()) {
                    return List.of(CustomOverlayRecipeComponent.createGridPos(1, 1, list));
                }
            }
            return List.of();
        }, (enabled, hoveredOrFocused) -> {
            if (enabled) {
                return hoveredOrFocused ? FCCoreUtils.resource("recipe_book/restoration_overlay_highlighted") : FCCoreUtils.resource("recipe_book/restoration_overlay");
            } else {
                return hoveredOrFocused ? FCCoreUtils.resource("recipe_book/restoration_overlay_disabled_highlighted") : FCCoreUtils.resource("recipe_book/restoration_overlay_disabled");
            }
        }));
        recipeBookOverlayRegister.apply(FCRecipeBookOverlays.CULTIVATOR, Pair.of((recipeDisplay, contextMap) -> {
            if (recipeDisplay instanceof CultivationRecipeDisplay cultivationRecipeDisplay) {
                List<ItemStack> list = cultivationRecipeDisplay.ingredient().resolveForStacks(contextMap);
                if (!list.isEmpty()) {
                    return List.of(CustomOverlayRecipeComponent.createGridPos(1, 1, list));
                }
            }
            return List.of();
        }, (enabled, hoveredOrFocused) -> {
            if (enabled) {
                return hoveredOrFocused ? FCCoreUtils.resource("recipe_book/cultivation_overlay_highlighted") : FCCoreUtils.resource("recipe_book/cultivation_overlay");
            } else {
                return hoveredOrFocused ? FCCoreUtils.resource("recipe_book/cultivation_overlay_disabled_highlighted") : FCCoreUtils.resource("recipe_book/cultivation_overlay_disabled");
            }
        }));
    }

    @Override
    public void registerMenuScreens(MenuScreenRegister menuScreenRegister) {
        menuScreenRegister.apply(FCMenuTypes.ANALYZER.get(), AnalyzerScreen::new);
        menuScreenRegister.apply(FCMenuTypes.ARCHAEOLOGY_WORKBENCH.get(), ArchaeologyWorkbenchScreen::new);
        menuScreenRegister.apply(FCMenuTypes.CULTIVATOR.get(), CultivatorScreen::new);
        menuScreenRegister.apply(FCMenuTypes.FEEDER.get(), FeederScreen::new);
    }

    @Override
    public void registerTextureAtlases(TextureAtlasRegister textureAtlasRegister) {
        textureAtlasRegister.apply(new AtlasManager.AtlasConfig(FCSheets.STONE_TABLETS_SHEET, FCAtlasIds.STONE_TABLETS, false));
    }
}
