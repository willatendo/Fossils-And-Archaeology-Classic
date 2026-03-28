package ca.willatendo.fossilsclassic.server.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class IcedStoneBlock extends Block {
    public static final MapCodec<IcedStoneBlock> CODEC = Block.simpleCodec(IcedStoneBlock::new);

    public IcedStoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for (Direction direction : Direction.values()) {
            BlockPos relativeBlockPos = blockPos.relative(direction);
            if (serverLevel.getBlockState(relativeBlockPos).is(Blocks.AIR) && serverLevel.getBrightness(LightLayer.BLOCK, relativeBlockPos) > 10 - blockState.getLightDampening()) {
                this.melt(serverLevel, blockPos);
            }
        }
    }

    protected void melt(ServerLevel serverLevel, BlockPos blockPos) {
        serverLevel.setBlockAndUpdate(blockPos, Blocks.STONE.defaultBlockState());
        serverLevel.neighborChanged(blockPos, Blocks.STONE, null);
    }

    @Override
    protected MapCodec<? extends IcedStoneBlock> codec() {
        return CODEC;
    }
}
