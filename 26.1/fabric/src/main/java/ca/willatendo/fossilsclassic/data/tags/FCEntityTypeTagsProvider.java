package ca.willatendo.fossilsclassic.data.tags;

import ca.willatendo.fossilsclassic.server.entity.FCEntityTypes;
import ca.willatendo.fossilsclassic.server.tags.FCEntityTypeTags;
import ca.willatendo.simplelibrary.data.providers.tag.SimpleEntityTypeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public final class FCEntityTypeTagsProvider extends SimpleEntityTypeTagsProvider {
    public FCEntityTypeTagsProvider(PackOutput packOutput, String modId, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, modId, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES).add(FCEntityTypes.BONES.get());
        this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add(FCEntityTypes.BONES.get());
        this.tag(EntityTypeTags.BURN_IN_DAYLIGHT).add(FCEntityTypes.BONES.get(), FCEntityTypes.FAILURESAURUS.get());
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(FCEntityTypes.BONES.get());
        this.tag(EntityTypeTags.SKELETONS).add(FCEntityTypes.BONES.get());
        this.tag(EntityTypeTags.UNDEAD).add(FCEntityTypes.FAILURESAURUS.get());

        this.tag(FCEntityTypeTags.EGGS).add(FCEntityTypes.TRICERATOPS_EGG.get(), FCEntityTypes.VELOCIRAPTOR_EGG.get(), FCEntityTypes.TYRANNOSAURUS_EGG.get(), FCEntityTypes.PTERANODON_EGG.get(), FCEntityTypes.FUTABASAURUS_EGG.get(), FCEntityTypes.MOSASAURUS_EGG.get(), FCEntityTypes.STEGOSAURUS_EGG.get(), FCEntityTypes.DILOPHOSAURUS_EGG.get(), FCEntityTypes.BRACHIOSAURUS_EGG.get());
        this.tag(FCEntityTypeTags.JAVELINS).add(FCEntityTypes.THROWN_WOODEN_JAVELIN.get(), FCEntityTypes.THROWN_STONE_JAVELIN.get(), FCEntityTypes.THROWN_COPPER_JAVELIN.get(), FCEntityTypes.THROWN_IRON_JAVELIN.get(), FCEntityTypes.THROWN_GOLDEN_JAVELIN.get(), FCEntityTypes.THROWN_DIAMOND_JAVELIN.get(), FCEntityTypes.THROWN_NETHERITE_JAVELIN.get(), FCEntityTypes.THROWN_SCARAB_GEM_JAVELIN.get());
        this.tag(FCEntityTypeTags.MAMMALS).add(EntityType.ARMADILLO, EntityType.BAT, EntityType.CAMEL, EntityType.CAMEL, EntityType.CAT, EntityType.COW, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.FOX, EntityType.GOAT, EntityType.HORSE, EntityType.LLAMA,
                // FAEntityTypes.MAMMOTH.get(),
                EntityType.MULE, EntityType.OCELOT, EntityType.PANDA, EntityType.PIG, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP,
                // FAEntityTypes.SMILODON.get(),
                EntityType.WOLF);
    }
}
