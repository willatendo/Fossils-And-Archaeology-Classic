package ca.willatendo.fossilsclassic.server.item;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.chromosome.Chromosome;
import ca.willatendo.fossilsclassic.server.entity.fossil_variant.FossilVariant;
import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import ca.willatendo.simplelibrary.core.holder.SimpleHolder;
import ca.willatendo.simplelibrary.core.registry.SimpleRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public final class FCDataComponents {
    public static final SimpleRegistry<DataComponentType<?>> DATA_COMPONENT_TYPES = new SimpleRegistry<>(Registries.DATA_COMPONENT_TYPE, FCCoreUtils.ID);

    public static final SimpleHolder<DataComponentType<Holder<Chromosome>>> CHROMOSOME = DATA_COMPONENT_TYPES.register("chromosome", () -> DataComponentType.<Holder<Chromosome>>builder().persistent(Chromosome.CODEC).networkSynchronized(Chromosome.STREAM_CODEC).build());
    public static final SimpleHolder<DataComponentType<Holder<FossilVariant>>> FOSSIL_VARIANT = DATA_COMPONENT_TYPES.register("fossil/variant", () -> DataComponentType.<Holder<FossilVariant>>builder().persistent(FossilVariant.CODEC).networkSynchronized(FossilVariant.STREAM_CODEC).build());
    public static final SimpleHolder<DataComponentType<Holder<StoneTabletVariant>>> STONE_TABLET_VARIANT = DATA_COMPONENT_TYPES.register("stone_tablet/variant", () -> DataComponentType.<Holder<StoneTabletVariant>>builder().persistent(StoneTabletVariant.CODEC).networkSynchronized(StoneTabletVariant.STREAM_CODEC).build());
}
