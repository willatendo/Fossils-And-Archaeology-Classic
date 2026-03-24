package ca.willatendo.fossilsclassic.server.item.dinopedia;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.entity.entities.Dinosaur;
import ca.willatendo.fossilsclassic.server.entity.utils.RideableDinosaur;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DinopediaInformation {
    private final List<Component> lines = new ArrayList<>();

    private DinopediaInformation(List<Component> components) {
        this.lines.addAll(components);
    }

    public List<Component> getLines() {
        return this.lines;
    }

    public static DinopediaInformation basicLogic(Dinosaur dinosaur, Player player) {
        if (dinosaur.isOwnedBy(player) || player.isCreative()) {
            return DinopediaInformation.basicEntity(dinosaur);
        } else {
            return dinosaur.isTame() ? DinopediaInformation.notOwner(dinosaur) : DinopediaInformation.wildEntity(dinosaur);
        }
    }

    public static DinopediaInformation basicEntity(Dinosaur dinosaur) {
        DinopediaInformation.Builder builder = DinopediaInformation.builder();
        builder.addEntityName(dinosaur);
        if (dinosaur.isTame()) {
            builder.addLine(FCCoreUtils.translationWithArguments("dinopedia", "owner", dinosaur.getOwnerReference().getEntity(dinosaur.level(), LivingEntity.class).getDisplayName().copy().withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY));
        }
        builder.addLine(FCCoreUtils.translationWithArguments("dinopedia", "age", Component.literal(dinosaur.getDaysAlive() + "").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY), FCCoreUtils.translationWithArguments("dinopedia", "health", Component.literal((int) dinosaur.getHealth() + "").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY), Component.literal((int) dinosaur.getMaxHealth() + "").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY), FCCoreUtils.translationWithArguments("dinopedia", "hunger", Component.literal((int) dinosaur.getHunger() + "").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY), Component.literal((int) dinosaur.getMaxHunger() + "").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY)).copy().withStyle(ChatFormatting.GRAY));
        if (dinosaur instanceof RideableDinosaur rideableDinosaur && dinosaur.getGrowthStage() >= rideableDinosaur.getMinRideableAge()) {
            builder.addLine(FCCoreUtils.translationWithArguments("dinopedia", "rideable").copy().withStyle(ChatFormatting.GRAY));
        }
        return builder.build();
    }

    public static DinopediaInformation wildEntity(Dinosaur dinosaur) {
        DinopediaInformation.Builder builder = DinopediaInformation.builder();
        builder.addEntityName(dinosaur);
        builder.addLine(FCCoreUtils.translationWithArguments("dinopedia", "wild").copy().withStyle(ChatFormatting.GRAY));
        return builder.build();
    }

    public static DinopediaInformation notOwner(Dinosaur dinosaur) {
        DinopediaInformation.Builder builder = DinopediaInformation.builder();
        builder.addEntityName(dinosaur);
        builder.addLine(FCCoreUtils.translationWithArguments("dinopedia", "not_the_owner").copy().withStyle(ChatFormatting.GRAY));
        return builder.build();
    }

    public static DinopediaInformation.Builder builder() {
        return new DinopediaInformation.Builder();
    }

    public static final class Builder {
        private final List<Component> lines = new ArrayList<>();

        private Builder() {
        }

        public Builder addLine(Component... components) {
            this.lines.addAll(Arrays.asList(components));
            return this;
        }

        public Builder addEntityName(Entity entity) {
            this.addLine(FCCoreUtils.translationWithArguments("dinopedia", "name", entity.getDisplayName().copy().withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD)));
            return this;
        }

        public DinopediaInformation build() {
            return new DinopediaInformation(this.lines);
        }
    }
}
