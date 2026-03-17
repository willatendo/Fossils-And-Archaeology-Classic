package ca.willatendo.fossilsclassic.client;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ArmorModelSet;

public final class FCLayers {
    public static final ModelLayerLocation BONES = FCLayers.createMain("bones");
    public static final ArmorModelSet<ModelLayerLocation> BONES_ARMOR = registerArmorSet("skeleton");
    public static final ModelLayerLocation BRACHIOSAURUS = FCLayers.createMain("brachiosaurus");
    public static final ModelLayerLocation EGG = FCLayers.createMain("egg");
    public static final ModelLayerLocation FAILURESAURUS = FCLayers.createMain("failuresaurus");
    public static final ModelLayerLocation FUTABASAURUS = FCLayers.createMain("futabasaurus");
    public static final ModelLayerLocation PTERANODON = FCLayers.createMain("pteranodon");
    public static final ModelLayerLocation THROWN_JAVELIN = FCLayers.createMain("thrown_javelin");
    public static final ModelLayerLocation TRICERATOPS = FCLayers.createMain("triceratops");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(FCCoreUtils.resource(name), "main");
    }

    private static ModelLayerLocation create(String name, String layer) {
        return new ModelLayerLocation(FCCoreUtils.resource(name), layer);
    }

    private static ArmorModelSet<ModelLayerLocation> registerArmorSet(String path) {
        return new ArmorModelSet<>(FCLayers.create(path, "helmet"), FCLayers.create(path, "chestplate"), FCLayers.create(path, "leggings"), FCLayers.create(path, "boots"));
    }
}
