package ca.willatendo.fossilsclassic.client.fossil_render;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

public record FossilRender(Identifier model, Identifier texture, float baseWidthScale, float sizeWidthScale, float baseHeightScale, float sizeHeightScale, float baseShadowSize, float sizeShadowScale) {
    public static final Codec<FossilRender> CODEC = RecordCodecBuilder.create(instance -> instance.group(Identifier.CODEC.fieldOf("model").forGetter(FossilRender::model), Identifier.CODEC.fieldOf("texture").forGetter(FossilRender::texture), Codec.FLOAT.fieldOf("base_width_scale").forGetter(FossilRender::baseWidthScale), Codec.FLOAT.fieldOf("size_width_scale").forGetter(FossilRender::sizeWidthScale), Codec.FLOAT.fieldOf("base_height_scale").forGetter(FossilRender::baseHeightScale), Codec.FLOAT.fieldOf("size_height_scale").forGetter(FossilRender::sizeHeightScale), Codec.FLOAT.fieldOf("base_shadow_size").forGetter(FossilRender::baseShadowSize), Codec.FLOAT.fieldOf("size_shadow_scale").forGetter(FossilRender::sizeShadowScale)).apply(instance, FossilRender::new));

    public FossilRender(Identifier model, Identifier texture, float baseScale, float sizeScale, float baseShadowSize, float sizeShadowScale) {
        this(model, texture, baseScale, sizeScale, baseScale, sizeScale, baseShadowSize, sizeShadowScale);
    }
}
