package ca.willatendo.fossilsclassic.server.structure.pools;

import ca.willatendo.fossilsclassic.server.structure.FCStructurePoolElementTypes;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;
import java.util.function.Function;

public class AcademyPoolElement extends SinglePoolElement {
    public static final MapCodec<AcademyPoolElement> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(templateCodec(), processorsCodec(), projectionCodec(), overrideLiquidSettingsCodec(), Codec.INT.fieldOf("ground_level_delta").forGetter(AcademyPoolElement::getGroundLevelDelta)).apply(instance, AcademyPoolElement::new));
    private final int groundLevelDelta;

    public static Function<StructureTemplatePool.Projection, AcademyPoolElement> academy(String id, Holder<StructureProcessorList> structureProcessorList) {
        return projection -> new AcademyPoolElement(Either.left(Identifier.parse(id)), structureProcessorList, projection, Optional.empty(), 3);
    }

    protected AcademyPoolElement(Either<Identifier, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings, int groundLevelDelta) {
        super(template, processors, projection, overrideLiquidSettings);
        this.groundLevelDelta = groundLevelDelta;
    }

    @Override
    public int getGroundLevelDelta() {
        return this.groundLevelDelta;
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return FCStructurePoolElementTypes.ACADEMY.get();
    }
}
