package ca.willatendo.fossilsclassic.server.structure;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.structure.pools.VikingShipPools;
import ca.willatendo.fossilsclassic.server.tags.FCBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;

public final class FCStructures {
    public static final ResourceKey<Structure> ACADEMY = FCStructures.create("academy");
    public static final ResourceKey<Structure> VIKING_SHIP = FCStructures.create("viking_ship");
    public static final ResourceKey<Structure> WEAPON_SHOP = FCStructures.create("weapon_shop");

    private static ResourceKey<Structure> create(String name) {
        return ResourceKey.create(Registries.STRUCTURE, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<Structure> bootstrapContext) {
        HolderGetter<Biome> biomes = bootstrapContext.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> structureTemplatePools = bootstrapContext.lookup(Registries.TEMPLATE_POOL);

        bootstrapContext.register(ACADEMY, new JigsawStructure(new Structure.StructureSettings.Builder(biomes.getOrThrow(FCBiomeTags.HAS_ACADEMY)).terrainAdapation(TerrainAdjustment.BEARD_THIN).build(), structureTemplatePools.getOrThrow(FCStructureTemplatePools.ACADEMY), 5, ConstantHeight.of(VerticalAnchor.absolute(-3)), false, Heightmap.Types.WORLD_SURFACE));
        bootstrapContext.register(VIKING_SHIP, new JigsawStructure(new Structure.StructureSettings.Builder(biomes.getOrThrow(FCBiomeTags.HAS_VIKING_SHIP)).spawnOverrides(Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedList.<MobSpawnSettings.SpawnerData>builder().add(new MobSpawnSettings.SpawnerData(FCEntityTypes.BONES.get(), 5, 20)).build()))).build(), structureTemplatePools.getOrThrow(VikingShipPools.START), 5, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.OCEAN_FLOOR));
        bootstrapContext.register(WEAPON_SHOP, new JigsawStructure(new Structure.StructureSettings.Builder(biomes.getOrThrow(FCBiomeTags.HAS_WEAPON_SHOP)).build(), structureTemplatePools.getOrThrow(FCStructureTemplatePools.WEAPON_SHOP), 5, ConstantHeight.of(VerticalAnchor.absolute(-5)), false, Heightmap.Types.OCEAN_FLOOR));
    }
}
