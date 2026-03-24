package ca.willatendo.fossilsclassic.server.block.blocks;

import ca.willatendo.fossilsclassic.server.block.FCBlockEntityTypes;
import ca.willatendo.fossilsclassic.server.block.entities.ArchaeologyWorkbenchBlockEntity;
import ca.willatendo.fossilsclassic.server.stats.FCStats;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ArchaeologyWorkbenchBlock extends BaseEntityBlock {
    public static final MapCodec<ArchaeologyWorkbenchBlock> CODEC = Block.simpleCodec(ArchaeologyWorkbenchBlock::new);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public ArchaeologyWorkbenchBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(ACTIVE, false));
    }

    @Override
    protected MapCodec<? extends ArchaeologyWorkbenchBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof ArchaeologyWorkbenchBlockEntity archaeologyWorkbenchBlockEntity) {
                ServerUtils.openContainer(archaeologyWorkbenchBlockEntity, blockPos, serverPlayer);
                player.awardStat(FCStats.INTERACT_WITH_ARCHAEOLOGY_WORKBENCH);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(blockState, serverLevel, blockPos);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos, Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
    }

    @Override
    public boolean triggerEvent(BlockState blockState, Level level, BlockPos blockPos, int id, int param) {
        super.triggerEvent(blockState, level, blockPos, id, param);
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(ACTIVE, false);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(HORIZONTAL_FACING, rotation.rotate(blockState.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, ACTIVE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArchaeologyWorkbenchBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level instanceof ServerLevel serverLevel) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, FCBlockEntityTypes.ARCHAEOLOGY_WORKBENCH.get(), (levelIn, blockPosIn, blockStateIn, archaeologyWorkbenchBlockEntityIn) -> ArchaeologyWorkbenchBlockEntity.serverTick(serverLevel, blockPosIn, blockStateIn, archaeologyWorkbenchBlockEntityIn));
        }
        return null;
    }
}
