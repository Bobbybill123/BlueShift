package BlueShift.entity.player;

import BlueShift.Main;
import BlueShift.entity.Entity;
import processing.core.PVector;

public class Player extends Entity {

	private Main main;
	private PVector position;
	private PVector velocity;
	private int blue = 0;

	public Player(){
		main = Main.instance;
		//setting the players position temp
		this.position = new PVector(200, main.height - 500);
		velocity = new PVector();
	}

	@Override
	public float getWidth() {
		return 52;
	}

	@Override
	public float getHeight() {
		return 75;
	}

	@Override
	public PVector getPosition() {
		return position;
	}

	public void draw() {
		main.color(255 - blue, 255 - blue, 255);
		main.fill(0, 255, 0);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
	}

	@Override
	public void doPhysics() {

	}

	@Override
	public void checkCollision(Entity other) {

	}

	public void pickupObject(Entity e) {

	}

	public void doAction(Key action) {

	}
}
