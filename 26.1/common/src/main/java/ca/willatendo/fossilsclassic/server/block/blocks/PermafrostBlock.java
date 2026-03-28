package ca.willatendo.fossilsclassic.server.block.blocks;

import ca.willatendo.fossilsclassic.server.block.FCBlocks;
import ca.willatendo.fossilsclassic.server.tags.FCBlockTags;
import ca.willatendo.fossilsclassic.server.tags.FCFluidTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class PermafrostBlock extends Block {
    public static final MapCodec<PermafrostBlock> CODEC = Block.simpleCodec(PermafrostBlock::new);

    public PermafrostBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack) {
        for (int targetX = -1; targetX <= 1; targetX++) {
            for (int targetY = -1; targetY <= 1; targetY++) {
                for (int targetZ = -1; targetZ <= 1; targetZ++) {
                    if (level.getBlockState(new BlockPos(blockPos.getX() + targetX, blockPos.getY() + targetY, blockPos.getZ() + targetZ)).is(FCBlockTags.PERMAFROST_FROSTABLE)) {
                        level.setBlock(new BlockPos(blockPos.getX() + targetX, blockPos.getY() + targetY, blockPos.getZ() + targetZ), FCBlocks.ICED_STONE.get().defaultBlockState(), 3);
                    }
                    if (level.getFluidState(new BlockPos(blockPos.getX() + targetX, blockPos.getY() + targetY, blockPos.getZ() + targetZ)).is(FCFluidTags.PERMAFROST_FREEZABLE)) {
                        level.setBlock(new BlockPos(blockPos.getX() + targetX, blockPos.getY() + targetY, blockPos.getZ() + targetZ), Blocks.ICE.defaultBlockState(), 3);
                    }
                }
            }
        }
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    }


    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for (Direction direction : Direction.values()) {
            BlockPos relativeBlockPos = blockPos.relative(direction);
            BlockState relativeBlockState = serverLevel.getBlockState(relativeBlockPos);
            if (relativeBlockState.isAir() && serverLevel.getBrightness(LightLayer.BLOCK, relativeBlockPos) > 10 - relativeBlockState.getLightDampening()) {
                this.melt(serverLevel, blockPos);
            }
        }
    }

    protected void melt(Level level, BlockPos blockPos) {
        level.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
        level.neighborChanged(blockPos, Blocks.DIRT, null);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }
}
