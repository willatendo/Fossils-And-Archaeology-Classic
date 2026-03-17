package ca.willatendo.fossilsclassic.client.gene_cosmetic;

import ca.willatendo.fossilsclassic.client.render.state.DinosaurRenderState;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public record GeneCosmetic(Identifier model, Either<Pair<Identifier, List<GeneCosmetic.TextureProvider>>, Identifier> texture, float baseWidthScale, float sizeWidthScale, float baseHeightScale, float sizeHeightScale, float baseShadowSize, float sizeShadowScale) {
    public static final Codec<GeneCosmetic> CODEC = RecordCodecBuilder.create(instance -> instance.group(Identifier.CODEC.fieldOf("model").forGetter(GeneCosmetic::model), Codec.either(Codec.pair(Identifier.CODEC.fieldOf("default_texture").codec(), Codec.list(GeneCosmetic.TextureProvider.CODEC).fieldOf("texture_providers").codec()), Identifier.CODEC).fieldOf("texture").forGetter(GeneCosmetic::texture), Codec.FLOAT.fieldOf("base_width_scale").forGetter(GeneCosmetic::baseWidthScale), Codec.FLOAT.fieldOf("size_width_scale").forGetter(GeneCosmetic::sizeWidthScale), Codec.FLOAT.fieldOf("base_height_scale").forGetter(GeneCosmetic::baseHeightScale), Codec.FLOAT.fieldOf("size_height_scale").forGetter(GeneCosmetic::sizeHeightScale), Codec.FLOAT.fieldOf("base_shadow_size").forGetter(GeneCosmetic::baseShadowSize), Codec.FLOAT.fieldOf("size_shadow_scale").forGetter(GeneCosmetic::sizeShadowScale)).apply(instance, GeneCosmetic::new));

    public GeneCosmetic(Identifier model, Identifier texture, float baseScale, float sizeScale, float baseShadowSize, float sizeShadowScale) {
        this(model, Either.right(texture), baseScale, sizeScale, baseScale, sizeScale, baseShadowSize, sizeShadowScale);
    }

    public GeneCosmetic(Identifier model, Identifier defaultTexture, List<GeneCosmetic.TextureProvider> textureProviders, float baseScale, float sizeScale, float baseShadowSize, float sizeShadowScale) {
        this(model, Either.left(Pair.of(defaultTexture, textureProviders)), baseScale, sizeScale, baseScale, sizeScale, baseShadowSize, sizeShadowScale);
    }

    public GeneCosmetic(Identifier model, Identifier defaultTexture, Identifier babyTexture, float baseScale, float sizeScale, float baseShadowSize, float sizeShadowScale) {
        this(model, defaultTexture, List.of(new GeneCosmetic.TextureProvider(babyTexture, GeneCosmetic.Conditions.IS_BABY)), baseScale, sizeScale, baseShadowSize, sizeShadowScale);
    }

    public Identifier getTexture(DinosaurRenderState dinosaurRenderState) {
        if (this.texture.right().isPresent()) {
            return this.texture.right().orElseThrow();
        }
        Pair<Identifier, List<GeneCosmetic.TextureProvider>> pair = this.texture().left().orElseThrow();
        for (GeneCosmetic.TextureProvider textureProvider : pair.getSecond().stream().sorted().toList()) {
            if (textureProvider.canUse(dinosaurRenderState)) {
                return textureProvider.texture();
            }
        }
        return pair.getFirst();
    }

    public record TextureProvider(List<Conditions> conditions, Identifier texture) implements Comparable<GeneCosmetic.TextureProvider> {
        public static final Codec<GeneCosmetic.TextureProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.list(GeneCosmetic.Conditions.CODEC).fieldOf("conditions").forGetter(GeneCosmetic.TextureProvider::conditions), Identifier.CODEC.fieldOf("texture").forGetter(GeneCosmetic.TextureProvider::texture)).apply(instance, GeneCosmetic.TextureProvider::new));

        public TextureProvider(Identifier texture, Conditions... conditions) {
            this(Arrays.asList(conditions), texture);
        }

        public boolean canUse(DinosaurRenderState dinosaurRenderState) {
            for (Conditions condition : this.conditions) {
                if (!condition.applies(dinosaurRenderState)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public int compareTo(GeneCosmetic.TextureProvider textureProvider) {
            return Integer.compare(textureProvider.conditions().size(), this.conditions().size());
        }
    }

    public enum Conditions implements StringRepresentable {
        IS_BABY("is_baby", dinosaurRenderState -> dinosaurRenderState.isBaby);

        public static final Codec<GeneCosmetic.Conditions> CODEC = StringRepresentable.fromValues(GeneCosmetic.Conditions::values);

        private final String name;
        private final Function<DinosaurRenderState, Boolean> condition;

        Conditions(String name, Function<DinosaurRenderState, Boolean> condition) {
            this.name = name;
            this.condition = condition;
        }

        public boolean applies(DinosaurRenderState dinosaurRenderState) {
            return this.condition.apply(dinosaurRenderState);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
