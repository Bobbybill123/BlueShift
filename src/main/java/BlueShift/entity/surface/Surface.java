package BlueShift.entity.surface;

import BlueShift.entity.Entity;

public abstract class Surface extends Entity {
	
	public abstract float getSpeedModifier();

	@Override
	public boolean isSurface() {
		return true;
	}
}
