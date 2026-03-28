package ca.willatendo.fossilsclassic.server.analyzation_result;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.item.FCItems;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

public final class FCAnalyzationResults {
    public static final ResourceKey<AnalyzationResult> BONE_MEAL = FCAnalyzationResults.create("bone_meal");
    public static final ResourceKey<AnalyzationResult> JURASSIC_FERN_SPORES = FCAnalyzationResults.create("jurassic_fern_spores");
    public static final ResourceKey<AnalyzationResult> TRICERATOPS_DNA = FCAnalyzationResults.create("triceratops_dna");
    public static final ResourceKey<AnalyzationResult> VELOCIRAPTOR_DNA = FCAnalyzationResults.create("velociraptor_dna");
    public static final ResourceKey<AnalyzationResult> TYRANNOSAURUS_DNA = FCAnalyzationResults.create("tyrannosaurus_dna");
    public static final ResourceKey<AnalyzationResult> PTERANODON_DNA = FCAnalyzationResults.create("pteranodon_dna");
    public static final ResourceKey<AnalyzationResult> NAUTILUS_DNA = FCAnalyzationResults.create("nautilus_dna");
    public static final ResourceKey<AnalyzationResult> FUTABASAURUS_DNA = FCAnalyzationResults.create("futabasaurus_dna");
    public static final ResourceKey<AnalyzationResult> MOSASAURUS_DNA = FCAnalyzationResults.create("mosasaurus_dna");
    public static final ResourceKey<AnalyzationResult> STEGOSAURUS_DNA = FCAnalyzationResults.create("stegosaurus_dna");
    public static final ResourceKey<AnalyzationResult> DILOPHOSAURUS_DNA = FCAnalyzationResults.create("dilophosaurus_dna");
    public static final ResourceKey<AnalyzationResult> BRACHIOSAURUS_DNA = FCAnalyzationResults.create("brachiosaurus_dna");
    public static final ResourceKey<AnalyzationResult> BEEF = FCAnalyzationResults.create("beef");
    public static final ResourceKey<AnalyzationResult> SMILODON_DNA = FCAnalyzationResults.create("smilodon_dna");
    public static final ResourceKey<AnalyzationResult> MAMMOTH_DNA = FCAnalyzationResults.create("mammoth_dna");
    public static final ResourceKey<AnalyzationResult> GRAVEL = FCAnalyzationResults.create("gravel");
    public static final ResourceKey<AnalyzationResult> STONE_TABLET = FCAnalyzationResults.create("stone_tablet");
    public static final ResourceKey<AnalyzationResult> FLINT = FCAnalyzationResults.create("flint");
    public static final ResourceKey<AnalyzationResult> BROKEN_ANCIENT_HELMET = FCAnalyzationResults.create("broken_ancient_helmet");
    public static final ResourceKey<AnalyzationResult> BROKEN_ANCIENT_SWORD = FCAnalyzationResults.create("broken_ancient_sword");

    private static ResourceKey<AnalyzationResult> create(String name) {
        return ResourceKey.create(FCRegistries.ANALYZATION_RESULT, FCCoreUtils.resource(name));
    }

    public static void bootstrap(BootstrapContext<AnalyzationResult> bootstrapContext) {
        bootstrapContext.register(BONE_MEAL, new AnalyzationResult(60, new ItemStackTemplate(Items.BONE_MEAL)));
        bootstrapContext.register(JURASSIC_FERN_SPORES, new AnalyzationResult(20, new ItemStackTemplate(FCItems.JURASSIC_FERN_SPORES.get())));
        bootstrapContext.register(TRICERATOPS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.TRICERATOPS_DNA.get())));
        bootstrapContext.register(VELOCIRAPTOR_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.VELOCIRAPTOR_DNA.get())));
        bootstrapContext.register(TYRANNOSAURUS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.TYRANNOSAURUS_DNA.get())));
        bootstrapContext.register(PTERANODON_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.PTERANODON_DNA.get())));
        bootstrapContext.register(NAUTILUS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.NAUTILUS_DNA.get())));
        bootstrapContext.register(FUTABASAURUS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.FUTABASAURUS_DNA.get())));
        bootstrapContext.register(MOSASAURUS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.MOSASAURUS_DNA.get())));
        bootstrapContext.register(STEGOSAURUS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.STEGOSAURUS_DNA.get())));
        bootstrapContext.register(DILOPHOSAURUS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.DILOPHOSAURUS_DNA.get())));
        bootstrapContext.register(BRACHIOSAURUS_DNA, new AnalyzationResult(2, new ItemStackTemplate(FCItems.BRACHIOSAURUS_DNA.get())));
        bootstrapContext.register(BEEF, new AnalyzationResult(1, new ItemStackTemplate(Items.BEEF)));
        bootstrapContext.register(SMILODON_DNA, new AnalyzationResult(1, new ItemStackTemplate(FCItems.SMILODON_DNA.get())));
        bootstrapContext.register(MAMMOTH_DNA, new AnalyzationResult(1, new ItemStackTemplate(FCItems.MAMMOTH_DNA.get())));
        bootstrapContext.register(GRAVEL, new AnalyzationResult(40, new ItemStackTemplate(Items.GRAVEL)));
        bootstrapContext.register(STONE_TABLET, new AnalyzationResult(30, new ItemStackTemplate(FCItems.STONE_TABLET.get())));
        bootstrapContext.register(FLINT, new AnalyzationResult(20, new ItemStackTemplate(Items.FLINT)));
        bootstrapContext.register(BROKEN_ANCIENT_HELMET, new AnalyzationResult(5, new ItemStackTemplate(FCItems.BROKEN_ANCIENT_HELMET.get())));
        bootstrapContext.register(BROKEN_ANCIENT_SWORD, new AnalyzationResult(5, new ItemStackTemplate(FCItems.BROKEN_ANCIENT_SWORD.get())));
    }
}
