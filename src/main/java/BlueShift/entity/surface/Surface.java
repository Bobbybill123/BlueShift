package BlueShift.entity.surface;

import BlueShift.entity.Entity;

public abstract class Surface extends Entity {
    //The floor isn't worth having in its own class, so the modifier is in here
    public static final float FLOOR_MODIFIER = 0.5f;
    public abstract float getSpeedModifier();
}
