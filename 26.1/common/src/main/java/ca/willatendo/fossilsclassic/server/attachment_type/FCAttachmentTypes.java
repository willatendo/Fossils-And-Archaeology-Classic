package ca.willatendo.fossilsclassic.server.attachment_type;

import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.server.attachment_type.attachment_types.Pregnancy;
import ca.willatendo.simplelibrary.core.registry.AttachmentTypeBuilder;
import ca.willatendo.simplelibrary.core.registry.sub.AttachmentTypesSubRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class FCAttachmentTypes {
    public static final AttachmentTypesSubRegistry ATTACHMENT_TYPES = AttachmentTypesSubRegistry.create(FCCoreUtils.ID);

    public static final Identifier PREGNANCY = ATTACHMENT_TYPES.register("pregnancy", AttachmentTypeBuilder.builder(new Pregnancy(Component.empty())).serialize(Pregnancy.CODEC).sync(Pregnancy.STREAM_CODEC));
}
