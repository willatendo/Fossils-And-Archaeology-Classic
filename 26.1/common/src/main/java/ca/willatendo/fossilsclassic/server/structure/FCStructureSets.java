package ca.willatendo.fossilsclassic.server.structure;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public final class FCStructureSets {
    public static final ResourceKey<StructureSet> ACADEMY = FCStructureSets.create("academy");
    public static final ResourceKey<StructureSet> SHIPS = FCStructureSets.create("ships");
    public static final ResourceKey<StructureSet> WEAPON_SHOP = FCStructureSets.create("weapon_shop");

    private static ResourceKey<StructureSet> create(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<StructureSet> bootstrapContext) {
        HolderGetter<Structure> structures = bootstrapContext.lookup(Registries.STRUCTURE);

        bootstrapContext.register(ACADEMY, new StructureSet(structures.getOrThrow(FCStructures.ACADEMY), new RandomSpreadStructurePlacement(64, 8, RandomSpreadType.LINEAR, 1476272409)));
        bootstrapContext.register(SHIPS, new StructureSet(structures.getOrThrow(FCStructures.VIKING_SHIP), new RandomSpreadStructurePlacement(128, 16, RandomSpreadType.LINEAR, 1476272410)));
        bootstrapContext.register(WEAPON_SHOP, new StructureSet(structures.getOrThrow(FCStructures.WEAPON_SHOP), new RandomSpreadStructurePlacement(64, 8, RandomSpreadType.LINEAR, 1476272411)));
    }
}
