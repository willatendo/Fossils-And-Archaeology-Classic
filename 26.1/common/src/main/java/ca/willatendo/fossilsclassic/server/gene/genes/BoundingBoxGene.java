package ca.willatendo.fossilsclassic.server.gene.genes;

import ca.willatendo.fossilsclassic.server.gene.FCGeneTypes;
import ca.willatendo.fossilsclassic.server.gene.type.GeneType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EntityAttachments;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record BoundingBoxGene(Component name, List<BoundingBoxGene.PoseAndBoundingBoxPair> map) implements Gene {
    public static final MapCodec<BoundingBoxGene> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ComponentSerialization.CODEC.fieldOf("name").forGetter(BoundingBoxGene::getName), Codec.list(BoundingBoxGene.PoseAndBoundingBoxPair.CODEC).fieldOf("map").forGetter(BoundingBoxGene::map)).apply(instance, BoundingBoxGene::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BoundingBoxGene> STREAM_CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_STREAM_CODEC, BoundingBoxGene::getName, PoseAndBoundingBoxPair.STREAM_CODEC.apply(ByteBufCodecs.list()), BoundingBoxGene::map, BoundingBoxGene::new);

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public GeneType<?> getType() {
        return FCGeneTypes.BOUNDING_BOX.get();
    }

    public BoundingBoxGene.BoundingBox get(Pose pose) {
        return this.map.stream().collect(Collectors.toMap(PoseAndBoundingBoxPair::pose, PoseAndBoundingBoxPair::boundingBox)).get(pose);
    }

    public static BoundingBoxGene.Builder builder(Component name) {
        return new BoundingBoxGene.Builder(name);
    }

    public static final class Builder {
        private final List<BoundingBoxGene.PoseAndBoundingBoxPair> map = new ArrayList<>();
        private final Component name;

        private Builder(Component name) {
            this.name = name;
        }

        public BoundingBoxGene.Builder add(BoundingBoxGene.BoundingBox boundingBox, Pose... poses) {
            for (Pose pose : poses) {
                this.map.add(new PoseAndBoundingBoxPair(pose, boundingBox));
            }
            return this;
        }

        public BoundingBoxGene build() {
            return new BoundingBoxGene(this.name, this.map);
        }
    }

    public record PoseAndBoundingBoxPair(Pose pose, BoundingBoxGene.BoundingBox boundingBox) {
        public static final Codec<BoundingBoxGene.PoseAndBoundingBoxPair> CODEC = RecordCodecBuilder.create(instance -> instance.group(Pose.CODEC.fieldOf("pose").forGetter(BoundingBoxGene.PoseAndBoundingBoxPair::pose), BoundingBoxGene.BoundingBox.CODEC.fieldOf("bounding_box").forGetter(BoundingBoxGene.PoseAndBoundingBoxPair::boundingBox)).apply(instance, BoundingBoxGene.PoseAndBoundingBoxPair::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BoundingBoxGene.PoseAndBoundingBoxPair> STREAM_CODEC = StreamCodec.composite(Pose.STREAM_CODEC, BoundingBoxGene.PoseAndBoundingBoxPair::pose, BoundingBoxGene.BoundingBox.STREAM_CODEC, BoundingBoxGene.PoseAndBoundingBoxPair::boundingBox, BoundingBoxGene.PoseAndBoundingBoxPair::new);
    }

    public record BoundingBox(float boundingBoxBaseWidth, float boundingBoxBaseHeight, float boundingBoxWidthGrowth, float boundingBoxHeightGrowth, Optional<Float> eyeHeight, Optional<List<BoundingBoxGene.EntityAttachmentAndVecsPair>> attachments) {
        public static final Codec<BoundingBoxGene.BoundingBox> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf("bounding_box_base_width").forGetter(BoundingBoxGene.BoundingBox::boundingBoxBaseWidth), Codec.FLOAT.fieldOf("bounding_box_base_height").forGetter(BoundingBoxGene.BoundingBox::boundingBoxBaseHeight), Codec.FLOAT.fieldOf("bounding_box_width_growth").forGetter(BoundingBoxGene.BoundingBox::boundingBoxWidthGrowth), Codec.FLOAT.fieldOf("bounding_box_height_growth").forGetter(BoundingBoxGene.BoundingBox::boundingBoxHeightGrowth), Codec.FLOAT.optionalFieldOf("eye_height").forGetter(BoundingBoxGene.BoundingBox::eyeHeight), Codec.list(BoundingBoxGene.EntityAttachmentAndVecsPair.CODEC).optionalFieldOf("attachments").forGetter(BoundingBoxGene.BoundingBox::attachments)).apply(instance, BoundingBoxGene.BoundingBox::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BoundingBoxGene.BoundingBox> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, BoundingBoxGene.BoundingBox::boundingBoxBaseWidth, ByteBufCodecs.FLOAT, BoundingBoxGene.BoundingBox::boundingBoxBaseHeight, ByteBufCodecs.FLOAT, BoundingBoxGene.BoundingBox::boundingBoxWidthGrowth, ByteBufCodecs.FLOAT, BoundingBoxGene.BoundingBox::boundingBoxHeightGrowth, ByteBufCodecs.optional(ByteBufCodecs.FLOAT), BoundingBoxGene.BoundingBox::eyeHeight, ByteBufCodecs.optional(BoundingBoxGene.EntityAttachmentAndVecsPair.STREAM_CODEC.apply(ByteBufCodecs.list())), BoundingBoxGene.BoundingBox::attachments, BoundingBoxGene.BoundingBox::new);

        public BoundingBox(float boundingBoxBaseSize, float boundingBoxBaseGrowth, float eyeHeight) {
            this(boundingBoxBaseSize, boundingBoxBaseSize, boundingBoxBaseGrowth, boundingBoxBaseGrowth, Optional.of(eyeHeight), Optional.empty());
        }

        public BoundingBox(float boundingBoxBaseSize, float boundingBoxBaseGrowth) {
            this(boundingBoxBaseSize, boundingBoxBaseSize, boundingBoxBaseGrowth, boundingBoxBaseGrowth, Optional.empty(), Optional.empty());
        }

        public EntityDimensions toDimensions(int size) {
            float width = this.boundingBoxBaseWidth() + (this.boundingBoxWidthGrowth() * size);
            float height = this.boundingBoxBaseHeight() + (this.boundingBoxHeightGrowth() * size);
            EntityDimensions entityDimensions = EntityDimensions.scalable(width, height);
            if (this.eyeHeight().isPresent()) {
                entityDimensions = entityDimensions.withEyeHeight(this.eyeHeight().get());
            } else {
                entityDimensions = entityDimensions.withEyeHeight(height * 0.85F);
            }
            if (this.attachments().isPresent()) {
                EntityAttachments.Builder builder = EntityAttachments.builder();
                this.attachments.get().forEach(entityAttachmentAndVecsPair -> entityAttachmentAndVecsPair.attachments().forEach(attachment -> builder.attach(entityAttachmentAndVecsPair.entityAttachment(), attachment)));
                entityDimensions = entityDimensions.withAttachments(builder);
            }
            return entityDimensions;
        }
    }

    public record EntityAttachmentAndVecsPair(EntityAttachment entityAttachment, List<Vec3> attachments) {
        public static final Codec<EntityAttachment> ENTITY_ATTACHMENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("index").forGetter(Enum::ordinal)).apply(instance, index -> EntityAttachment.values()[index]));
        public static final StreamCodec<ByteBuf, EntityAttachment> ENTITY_ATTACHMENT_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, Enum::ordinal, index -> EntityAttachment.values()[index]);
        public static final Codec<BoundingBoxGene.EntityAttachmentAndVecsPair> CODEC = RecordCodecBuilder.create(instance -> instance.group(ENTITY_ATTACHMENT_CODEC.fieldOf("entity_attachment").forGetter(BoundingBoxGene.EntityAttachmentAndVecsPair::entityAttachment), Codec.list(Vec3.CODEC).fieldOf("attachments").forGetter(BoundingBoxGene.EntityAttachmentAndVecsPair::attachments)).apply(instance, BoundingBoxGene.EntityAttachmentAndVecsPair::new));
        public static final StreamCodec<ByteBuf, BoundingBoxGene.EntityAttachmentAndVecsPair> STREAM_CODEC = StreamCodec.composite(ENTITY_ATTACHMENT_STREAM_CODEC, BoundingBoxGene.EntityAttachmentAndVecsPair::entityAttachment, Vec3.STREAM_CODEC.apply(ByteBufCodecs.list()), BoundingBoxGene.EntityAttachmentAndVecsPair::attachments, BoundingBoxGene.EntityAttachmentAndVecsPair::new);
    }
}
