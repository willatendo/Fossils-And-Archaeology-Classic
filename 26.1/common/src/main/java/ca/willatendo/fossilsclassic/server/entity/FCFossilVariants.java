package ca.willatendo.fossilsclassic.server.entity;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.registry.FCRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public final class FCFossilVariants {
    public static final ResourceKey<FossilVariant> BRACHIOSAURUS = FCFossilVariants.create("brachiosaurus");
    public static final ResourceKey<FossilVariant> FUTABASAURUS = FCFossilVariants.create("futabasaurus");
    public static final ResourceKey<FossilVariant> PTERANODON = FCFossilVariants.create("pteranodon");
    public static final ResourceKey<FossilVariant> TRICERATOPS = FCFossilVariants.create("triceratops");

    private static ResourceKey<FossilVariant> create(String name) {
        return ResourceKey.create(FCRegistries.FOSSIL_VARIANT, FCCoreUtils.resource(name));
    }

    private static void register(BootstrapContext<FossilVariant> bootstrapContext, ResourceKey<FossilVariant> resourceKey, int maxGrowthStage, float boundingBoxBaseWidth, float boundingBoxBaseHeight, float boundingBoxWidthGrowth, float boundingBoxHeightGrowth) {
        bootstrapContext.register(resourceKey, new FossilVariant(Optional.of(Component.translatable(resourceKey.identifier().toLanguageKey("fossil", "name")).withStyle(ChatFormatting.YELLOW)), maxGrowthStage, boundingBoxBaseWidth, boundingBoxBaseHeight, boundingBoxWidthGrowth, boundingBoxHeightGrowth));
    }

    public static void bootstrap(BootstrapContext<FossilVariant> bootstrapContext) {
        FCFossilVariants.register(bootstrapContext, BRACHIOSAURUS, 36, 1.75F, 2.5F, 0.3F, 0.6F);
        FCFossilVariants.register(bootstrapContext, FUTABASAURUS, 12, 2.0F, 0.75F, 0.25F, 0.25F);
        FCFossilVariants.register(bootstrapContext, PTERANODON, 8, 0.5F, 0.5F, 0.2F, 0.2F);
        FCFossilVariants.register(bootstrapContext, TRICERATOPS, 12, 0.75F, 0.75F, 0.15F, 0.15F);
    }
}
