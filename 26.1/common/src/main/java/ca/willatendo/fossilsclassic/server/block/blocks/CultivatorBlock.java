package ca.willatendo.fossilsclassic.server.block.blocks;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.block.FCBlockEntityTypes;
import ca.willatendo.fossilsclassic.server.block.entities.CultivatorBlockEntity;
import ca.willatendo.fossilsclassic.server.game_rules.FCGameRules;
import ca.willatendo.fossilsclassic.server.stats.FCStats;
import ca.willatendo.simplelibrary.server.utils.ServerUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class CultivatorBlock extends BaseEntityBlock {
    public static final MapCodec<CultivatorBlock> CODEC = Block.simpleCodec(CultivatorBlock::new);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public CultivatorBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVE, false));
    }

    public static void shatter(Level level, BlockPos blockPos, CultivatorBlockEntity cultivatorBlockEntity) {
        level.setBlock(blockPos, Blocks.WATER.defaultBlockState(), 3);
        level.removeBlockEntity(blockPos);
        level.playLocalSound(blockPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F, false);

        if (level instanceof ServerLevel serverLevel) {
            List<ServerPlayer> players = serverLevel.getGameRules().get(FCGameRules.DO_LIMIT_NOTIFICATION_DISTANCE.get()) ? serverLevel.getPlayers(serverPlayer -> true, serverLevel.getGameRules().get(FCGameRules.NOTIFICATION_DISTANCE.get())) : serverLevel.players();
            players.forEach(serverPlayer -> serverPlayer.sendSystemMessage(FCCoreUtils.translation("block", "cultivator.shatter")));
        }

        Block.popResource(level, blockPos, new ItemStack(Items.IRON_INGOT, 3));
        Containers.dropContents(level, blockPos, cultivatorBlockEntity);

        int chance = level.getRandom().nextInt(100);
        LivingEntity monsterLivingEntity = null;
        if (chance <= 5) {
            monsterLivingEntity = EntityType.CREEPER.create(level, EntitySpawnReason.EVENT);
        }
        if (chance > 5 && chance < 10) {
            monsterLivingEntity = EntityType.ZOMBIFIED_PIGLIN.create(level, EntitySpawnReason.EVENT);
        }
        if (chance >= 10) {
            monsterLivingEntity = EntityType.ZOMBIE.create(level, EntitySpawnReason.EVENT);
            // monsterLivingEntity = FossilsLegacyEntityTypes.FAILURESAURUS.get().create(level);
        }

        monsterLivingEntity.snapTo(blockPos, level.getRandom().nextFloat() * 360F, 0.0F);
        level.addFreshEntity(monsterLivingEntity);
    }

    @Override
    protected MapCodec<? extends CultivatorBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof CultivatorBlockEntity cultivatorBlockEntity) {
                ServerUtils.openContainer(cultivatorBlockEntity, blockPos, serverPlayer);
                player.awardStat(FCStats.INTERACT_WITH_CULTIVATOR);
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
        return blockEntity == null ? false : blockEntity.triggerEvent(id, param);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CultivatorBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level instanceof ServerLevel serverLevel) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, FCBlockEntityTypes.CULTIVATOR.get(), (levelIn, blockPosIn, blockStateIn, cultivatorBlockEntityIn) -> CultivatorBlockEntity.serverTick(serverLevel, blockPosIn, blockStateIn, cultivatorBlockEntityIn));
        }
        return null;
    }
}
