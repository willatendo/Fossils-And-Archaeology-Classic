package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.client.gene_cosmetic.GeneCosmetic;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.data.generic.GeneCosmeticProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;

public final class FCGeneCosmeticProvider extends GeneCosmeticProvider {
    public FCGeneCosmeticProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void add(BiConsumer<Identifier, GeneCosmetic> biConsumer) {
        biConsumer.accept(
                FCCoreUtils.resource("cosmetics/stegosaurus"),
                new GeneCosmetic(
                        FCCoreUtils.resource("stegosaurus"),
                        FCCoreUtils.resource("textures/entity/stegosaurus/stegosaurus.png"),
                        FCCoreUtils.resource("textures/entity/stegosaurus/baby_stegosaurus.png"),
                        1.5F,
                        0.3F,
                        0.5F,
                        0.1F
                )
        );
        biConsumer.accept(
                FCCoreUtils.resource("cosmetics/brown_triceratops"),
                new GeneCosmetic(
                        FCCoreUtils.resource("triceratops"),
                        FCCoreUtils.resource("textures/entity/triceratops/brown_triceratops.png"),
                        FCCoreUtils.resource("textures/entity/triceratops/brown_baby_triceratops.png"),
                        1.5F,
                        0.3F,
                        0.5F,
                        0.1F
                )
        );
        biConsumer.accept(
                FCCoreUtils.resource("cosmetics/green_triceratops"),
                new GeneCosmetic(
                        FCCoreUtils.resource("triceratops"),
                        FCCoreUtils.resource("textures/entity/triceratops/green_triceratops.png"),
                        FCCoreUtils.resource("textures/entity/triceratops/green_baby_triceratops.png"),
                        1.5F,
                        0.3F,
                        0.5F,
                        0.1F
                )
        );

        biConsumer.accept(
                FCCoreUtils.resource("cosmetics/citipati"),
                new GeneCosmetic(
                        FCCoreUtils.resource("citipati"),
                        FCCoreUtils.resource("textures/entity/citipati/citipati.png"),
                        1.0F,
                        0.0F,
                        0.5F,
                        0.0F
                )
        );
        biConsumer.accept(
                FCCoreUtils.resource("cosmetics/dromaeosaurus"),
                new GeneCosmetic(
                        FCCoreUtils.resource("dromaeosaurus"),
                        FCCoreUtils.resource("textures/entity/dromaeosaurus/dromaeosaurus.png"),
                        1.0F,
                        0.0F,
                        0.5F,
                        0.0F
                )
        );
    }
}
