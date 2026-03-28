package ca.willatendo.fossilsclassic.server.structure.processor;

import ca.willatendo.fossilsclassic.server.structure.FCStructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HolesProcessor extends StructureProcessor {
    public static final MapCodec<HolesProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.fieldOf("replace_with_water_under_sea_level").forGetter(academyStructureProcessor -> academyStructureProcessor.replaceWithWaterUnderSeaLevel), IntProviders.codec(1, 99).fieldOf("hole_count").forGetter(holesProcessor -> holesProcessor.holeCount), IntProviders.codec(1, 99).fieldOf("hole_size").forGetter(holesProcessor -> holesProcessor.holeSize)).apply(instance, HolesProcessor::new));
    private final boolean replaceWithWaterUnderSeaLevel;
    private final IntProvider holeCount;
    private final IntProvider holeSize;
    private HolesProcessor.RelicHoleList relicHoleList;

    public HolesProcessor(boolean replaceWithWaterUnderSeaLevel, IntProvider holeCount, IntProvider holeSize) {
        this.replaceWithWaterUnderSeaLevel = replaceWithWaterUnderSeaLevel;
        this.holeCount = holeCount;
        this.holeSize = holeSize;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor serverLevelAccessor, BlockPos offsetBlockPos, BlockPos blockPos, List<StructureTemplate.StructureBlockInfo> originalBlockInfos, List<StructureTemplate.StructureBlockInfo> processedBlockInfos, StructurePlaceSettings structurePlaceSettings) {
        if (!originalBlockInfos.isEmpty()) {
            Map<BlockPos, BlockState> map = processedBlockInfos.stream().collect(Collectors.toMap(StructureTemplate.StructureBlockInfo::pos, StructureTemplate.StructureBlockInfo::state));
            List<BlockPos> blockPoses = originalBlockInfos.stream().map(StructureTemplate.StructureBlockInfo::pos).toList();
            RandomSource randomSource = serverLevelAccessor.getRandom();
            if (this.relicHoleList == null) {
                this.relicHoleList = new HolesProcessor.RelicHoleList(randomSource, blockPoses, this.holeCount.sample(randomSource), this.holeSize);
            }
            originalBlockInfos.forEach(originalStructureBlockInfo -> {
                BlockPos structureBlockPos = originalStructureBlockInfo.pos();
                boolean isHole = this.relicHoleList.isHole(structureBlockPos);
                BlockState blockState = isHole ? (this.replaceWithWaterUnderSeaLevel && blockPos.getY() <= serverLevelAccessor.getLevel().getSeaLevel()) ? Blocks.WATER.defaultBlockState() : Blocks.GOLD_BLOCK.defaultBlockState() : originalStructureBlockInfo.state();
                if (isHole) {
                    int index = originalBlockInfos.indexOf(originalStructureBlockInfo);
                    processedBlockInfos.set(index, new StructureTemplate.StructureBlockInfo(structureBlockPos, blockState, isHole ? new CompoundTag() : originalStructureBlockInfo.nbt()));
                }
            });
            /*
            processedBlockInfos.forEach(structureBlockInfo -> {
                BlockState blockState = structureBlockInfo.state();
                int index = processedBlockInfos.indexOf(structureBlockInfo);
                ServerLevel serverLevel = serverLevelAccessor.getLevel();
                if (blockState.getBlock() instanceof IronBarsBlock) {
                    BlockPos structureBlockPos = structureBlockInfo.pos();
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        BlockPos relativeBlockPos = structureBlockPos.relative(direction);
                        if (map.containsKey(relativeBlockPos)) {
                            blockState = blockState.updateShape(serverLevel, serverLevel, blockPos, direction, relativeBlockPos, map.get(relativeBlockPos), serverLevel.getRandom());
                        }
                    }
                    if (blockState != structureBlockInfo.state()) {
                        processedBlockInfos.set(index, new StructureTemplate.StructureBlockInfo(structureBlockInfo.pos(), blockState, structureBlockInfo.nbt()));
                    }
                }
            });
             */
            return processedBlockInfos;
        }
        return processedBlockInfos;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return FCStructureProcessorTypes.HOLES_PROCESSOR.get();
    }

    private static final class RelicHoleList {
        private final RelicHole[] relicHoles;

        public RelicHoleList(RandomSource randomSource, List<BlockPos> structureBlockPoses, int holeCount, IntProvider holeSize) {
            this.relicHoles = new RelicHole[holeCount + 1];
            for (int i = 0; i < this.relicHoles.length; i++) {
                BlockPos blockPos = structureBlockPoses.get(randomSource.nextInt(structureBlockPoses.size()));
                this.relicHoles[i] = new RelicHole(randomSource, blockPos, holeSize.sample(randomSource));
            }
        }

        public boolean isHole(BlockPos blockPos) {
            for (RelicHole relicHole : this.relicHoles) {
                if (relicHole.isHole(blockPos)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final class RelicHole {
        public final BlockPos blockPos;
        public final int range;

        public RelicHole(RandomSource randomSource, BlockPos blockPos, int holeSize) {
            this.blockPos = blockPos;
            this.range = randomSource.nextInt(holeSize) + 1;
        }

        public boolean isHole(BlockPos blockPos) {
            int x = blockPos.getX();
            int y = blockPos.getY();
            int z = blockPos.getZ();
            int atX = this.blockPos.getX();
            int atY = this.blockPos.getY();
            int atZ = this.blockPos.getZ();
            int distance = (int) Math.sqrt(Math.pow((atX - x), 2) + Math.pow((atY - y), 2) + Math.pow((atZ - z), 2));
            return (distance <= this.range);
        }
    }
}
