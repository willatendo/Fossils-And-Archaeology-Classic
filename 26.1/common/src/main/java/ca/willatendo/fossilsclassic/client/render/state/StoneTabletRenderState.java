package ca.willatendo.fossilsclassic.client.render.state;

import ca.willatendo.fossilsclassic.server.entity.stone_tablet_variant.StoneTabletVariant;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.Direction;

public class StoneTabletRenderState extends EntityRenderState {
    public Direction direction;
    public StoneTabletVariant variant;
    public int[] lightCoordsPerBlock;

    public StoneTabletRenderState() {
        this.direction = Direction.NORTH;
        this.lightCoordsPerBlock = new int[0];
    }
}
