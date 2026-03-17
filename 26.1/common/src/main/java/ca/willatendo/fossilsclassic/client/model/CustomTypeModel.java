package ca.willatendo.fossilsclassic.client.model;

import ca.willatendo.fossilsclassic.client.json_animation.ClassicAnimationType;
import ca.willatendo.fossilsclassic.client.json_animation.CustomAnimation;
import ca.willatendo.fossilsclassic.client.json_animation.KeyframeAnimationType;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.Function;

public class CustomTypeModel<S extends LivingEntityRenderState> extends EntityModel<S> {
    private final List<KeyframeAnimation> keyframeAnimations = Lists.newArrayList();
    private final List<ClassicAnimationType> classicAnimations = Lists.newArrayList();
    private final Function<String, ModelPart> partLookup;

    public CustomTypeModel(List<CustomAnimation> customAnimations, ModelPart root) {
        super(root);
        this.partLookup = root.createPartLookup();

        customAnimations.forEach(customAnimation -> {
            if (customAnimation instanceof KeyframeAnimationType keyframeAnimationType) {
                this.keyframeAnimations.add(keyframeAnimationType.toAnimationDefinition().bake(root));
            } else if (customAnimation instanceof ClassicAnimationType classicAnimationType) {
                this.classicAnimations.add(classicAnimationType);
            }
        });
    }

    @Override
    public void setupAnim(S livingRenderState) {
        super.setupAnim(livingRenderState);

        this.classicAnimations.forEach(classicAnimationType -> {
            float[] parameters = new float[classicAnimationType.requiredParameters().size()];
            for (int i = 0; i < classicAnimationType.requiredParameters().size(); i++) {
                parameters[i] = switch (classicAnimationType.requiredParameters().get(i)) {
                    case HEAD_X_ROTATION -> livingRenderState.xRot * ((float) Math.PI / 180F);
                    case HEAD_Y_ROTATION -> livingRenderState.yRot * ((float) Math.PI / 180F);
                    case WALK_POSITION -> livingRenderState.walkAnimationPos;
                    case WALK_SPEED -> livingRenderState.walkAnimationSpeed;
                };
            }
            classicAnimationType.animationEvents().forEach(animationEvent -> animationEvent.bones().forEach(bone -> {
                ModelPart modelPart = this.partLookup.apply(bone);

                Function<ClassicAnimationType.Parameters, Integer> index = parameter -> classicAnimationType.requiredParameters().indexOf(parameter);
                switch (animationEvent.axis()) {
                    case X_ROTATION -> modelPart.xRot = animationEvent.floatReturn().value(parameters, index);
                    case Y_ROTATION -> modelPart.yRot = animationEvent.floatReturn().value(parameters, index);
                    case Z_ROTATION -> modelPart.zRot = animationEvent.floatReturn().value(parameters, index);
                }
            }));
        });
    }
}
