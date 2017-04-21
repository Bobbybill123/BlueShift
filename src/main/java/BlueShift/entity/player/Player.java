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
	 public void draw() {

	}

	@Override
	protected void doPhysics() {

	}

	@Override
	protected void checkCollision(Entity other) {

	}

	public void pickupObject(Entity e) {

	}

	public void doAction(Key action) {

	}
}
