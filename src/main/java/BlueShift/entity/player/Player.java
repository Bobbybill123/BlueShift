package BlueShift.entity.player;

import BlueShift.entity.Entity;
import processing.core.PVector;

public class Player extends Entity {

	@Override
	protected float getWidth() {
		return 220;
	}

	@Override
	protected float getHeight() {
		return 320;
	}

	@Override
	protected PVector getPosition() {
		return null;
	}

	@Override
	protected void draw() {

	}

	@Override
	protected void doPhysics() {

	}
}
