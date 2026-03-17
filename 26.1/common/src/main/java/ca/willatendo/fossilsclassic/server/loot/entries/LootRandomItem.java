package ca.willatendo.fossilsclassic.server.loot.entries;

import ca.willatendo.fossilsclassic.server.loot.FCLootPoolEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.function.Consumer;

public class LootRandomItem extends LootPoolSingletonContainer {
    public static final MapCodec<LootRandomItem> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.INT.fieldOf("max_weight").orElse(100).forGetter(lootRandomItem -> lootRandomItem.randomRange), Codec.list(RandomItemEntry.CODEC).fieldOf("entries").forGetter(lootRandomItem -> lootRandomItem.randomItemEntries)).and(LootItem.singletonFields(instance)).apply(instance, LootRandomItem::new));
    private final List<RandomItemEntry> randomItemEntries;
    private final int randomRange;

    public static LootPoolSingletonContainer.Builder<?> randomItem(int randomRange, RandomItemEntry... randomItemEntries) {
        return LootItem.simpleBuilder((weight, quality, conditions, compositeFunctions) -> new LootRandomItem(randomRange, List.of(randomItemEntries), weight, quality, conditions, compositeFunctions));
    }

    private LootRandomItem(int randomRange, List<RandomItemEntry> randomItemEntries, int weight, int quaility, List<LootItemCondition> conditions, List<LootItemFunction> compositeFunction) {
        super(weight, quaility, conditions, compositeFunction);
        this.randomItemEntries = randomItemEntries;
        this.randomRange = randomRange;
    }

    @Override
    protected void createItemStack(Consumer<ItemStack> consumer, LootContext lootContext) {
        int hit = lootContext.getRandom().nextInt(this.randomRange);
        for (RandomItemEntry randomItemEntry : this.randomItemEntries) {
            if (randomItemEntry.hitFrom() <= hit && hit < randomItemEntry.hitTo()) {
                ItemStack itemStack = new ItemStack(randomItemEntry.item());
                consumer.accept(itemStack);
                break;
            }
        }
    }

    @Override
    public LootPoolEntryType getType() {
        return FCLootPoolEntries.LOOT_RANDOM_ITEM.get();
    }

    public record RandomItemEntry(Holder<Item> item, int hitFrom, int hitTo) {
        private static final Codec<RandomItemEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(analyzationRecipe -> analyzationRecipe.item), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("hit_from").orElse(0).forGetter(analyzationRecipe -> analyzationRecipe.hitFrom), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("hit_to").orElse(0).forGetter(analyzationRecipe -> analyzationRecipe.hitTo)).apply(instance, RandomItemEntry::new));

        public RandomItemEntry(ItemLike itemLike, int hitFrom, int hitTo) {
            this(itemLike.asItem().builtInRegistryHolder(), hitFrom, hitTo);
        }
    }
}
