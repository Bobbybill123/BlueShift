package BlueShift.entity.surface;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import processing.core.PVector;

public class Floor extends Surface {
	private Main main = Main.instance;
	@Override
	public float getWidth() {
		return main.width;
	}

	@Override
	public float getHeight() {
		return 40;
	}

	@Override
	public PVector getPosition() {
		return new PVector(0, main.height - getHeight());
	}

	@Override
	public void draw() {
		main.fill(0, 127, 0);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
}

	@Override
	public boolean checkCollision(Entity other) {
		if(other.getPosition().y + other.getHeight() >= getPosition().y) {
			other.getPosition().y = getPosition().y - other.getHeight();
			return true;
		}
		return false;
	}

	@Override
	public EntityType getType() {
		return EntityType.FLOOR;
	}

	@Override
	public float getSpeedModifier() {
		return 0.5f;
	}
}
