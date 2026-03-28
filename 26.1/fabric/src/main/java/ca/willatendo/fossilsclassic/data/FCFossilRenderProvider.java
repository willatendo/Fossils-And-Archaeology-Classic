package ca.willatendo.fossilsclassic.data;

import ca.willatendo.fossilsclassic.client.fossil_render.FossilRender;
import ca.willatendo.fossilsclassic.core.utils.FCCoreUtils;
import ca.willatendo.fossilsclassic.data.generic.FossilRenderProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;

public final class FCFossilRenderProvider extends FossilRenderProvider {
    public FCFossilRenderProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void add(BiConsumer<Identifier, FossilRender> biConsumer) {
        biConsumer.accept(FCCoreUtils.resource("brachiosaurus"), new FossilRender(FCCoreUtils.resource("brachiosaurus"), FCCoreUtils.resource("textures/entity/brachiosaurus/fossil/brachiosaurus.png"), 1.5F, 0.3F, 0.75F, 0.1F));
        biConsumer.accept(FCCoreUtils.resource("futabasaurus"), new FossilRender(FCCoreUtils.resource("futabasaurus"), FCCoreUtils.resource("textures/entity/futabasaurus/fossil/futabasaurus.png"), 1.5F, 0.3F, 0.75F, 0.1F));
        biConsumer.accept(FCCoreUtils.resource("pteranodon"), new FossilRender(FCCoreUtils.resource("pteranodon"), FCCoreUtils.resource("textures/entity/pteranodon/fossil/pteranodon.png"), 0.8F, 0.2F, 0.35F, 0.075F));
        biConsumer.accept(FCCoreUtils.resource("triceratops"), new FossilRender(FCCoreUtils.resource("triceratops"), FCCoreUtils.resource("textures/entity/triceratops/fossil/triceratops.png"), 1.5F, 0.3F, 0.5F, 0.1F));
    }
}
