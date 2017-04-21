package BlueShift.entity.player;

import BlueShift.Main;
import BlueShift.entity.Entity;
import processing.core.PVector;

public class Player extends Entity {

	private Main main;
	private PVector position;

	public Player(){
		main = Main.instance;
		//setting the players position temp
		this.position = new PVector(200, main.height - 500);
	}

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
		return position;
	}

	@Override
	 public void draw() {
		//player draw (temp)
		main.fill(0, 255, 0);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
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
