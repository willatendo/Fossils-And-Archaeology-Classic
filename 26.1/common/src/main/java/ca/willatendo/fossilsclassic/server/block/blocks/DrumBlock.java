package ca.willatendo.fossilsclassic.server.block.blocks;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.command_type.CommandType;
import ca.willatendo.fossilsclassic.server.entity.utils.CommandTypeUtils;
import ca.willatendo.fossilsclassic.server.entity.utils.CommandableEntity;
import ca.willatendo.fossilsclassic.server.registry.FCBuiltInRegistries;
import ca.willatendo.fossilsclassic.server.sound_event.FCSoundEvents;
import ca.willatendo.fossilsclassic.server.tags.FCItemTags;
import ca.willatendo.simplelibrary.server.block.properties.StringProperty;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class DrumBlock extends Block {
    public static final MapCodec<DrumBlock> CODEC = Block.simpleCodec(DrumBlock::new);
    public static final StringProperty COMMAND_TYPE_PROPERTY = StringProperty.create("command_type", "stay", "follow", "free_move");

    public DrumBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(COMMAND_TYPE_PROPERTY, "stay"));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (itemStack.is(FCItemTags.DRUM_INSTRUMENT)) {
            if (level.isClientSide()) {
                player.playSound(FCSoundEvents.DRUM_TRIPLE_HIT.get());
            } else {
                CommandType currentCommandType = FCBuiltInRegistries.COMMAND_TYPES.getValue(FCCoreUtils.resource(blockState.getValue(DrumBlock.COMMAND_TYPE_PROPERTY)));
                List<LivingEntity> allEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPos).inflate(50.0D));
                for (LivingEntity livingEntity : allEntities) {
                    if (livingEntity instanceof CommandableEntity commandableEntity) {
                        if (commandableEntity.willListenToDrum(player, interactionHand)) {
                            commandableEntity.setCommand(CommandTypeUtils.get(blockState.getValue(COMMAND_TYPE_PROPERTY)));
                        }
                    }
                }
                player.sendOverlayMessage(FCCoreUtils.translationWithArguments("block", "drum.send_command", itemStack.getHoverName(), currentCommandType.getName()));
            }
        } else {
            if (level.isClientSide()) {
                player.playSound(FCSoundEvents.DRUM_HIT.get());
            } else {
                CommandType nextCommandType = CommandTypeUtils.getNext(blockState.getValue(DrumBlock.COMMAND_TYPE_PROPERTY)).value();
                level.setBlock(blockPos, blockState.setValue(DrumBlock.COMMAND_TYPE_PROPERTY, nextCommandType.name()), 10);
                player.sendOverlayMessage(FCCoreUtils.translationWithArguments("block", "drum.choose_command", nextCommandType.getName()));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COMMAND_TYPE_PROPERTY);
    }
}
