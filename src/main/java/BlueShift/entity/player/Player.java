package BlueShift.entity.player;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import BlueShift.entity.Orb;
import processing.core.PVector;

import java.awt.*;

public class Player extends Entity {

	private Main main;
	private PVector position;
	private PVector velocity;
	private static final float GRAVITY = 9.81f;
	private int blue = 0;
	private int red = 0;
	private boolean onGround;

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

	public PVector getVelocity() {
		return velocity;
	}

	public void draw() {
		doPhysics();
		doMovement();
		main.color(255 - blue, 255 - blue, 255);
		main.fill(0, 255, 0);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
	}

	@Override
	public void doPhysics() {
		if(!onGround) {
			velocity.y += GRAVITY;
		}
	}

	public void doMovement() {
		position.x += velocity.x;
		if(!onGround) position.y = Math.min(position.y + velocity.y, Main.GROUND);
		position.add(velocity);
	}

	@Override
	public void checkCollision(Entity other) {

	}

	@Override
	public EntityType getType() {
		return EntityType.PLAYER;
	}

	public void pickupObject(Entity e) {
		if(e.getType() == EntityType.ORB) {
			Orb orb = ((Orb) e);
			if(orb.getColor().equals(Color.BLUE)) {
				if (red != 0) red--;
				else blue++;
			}
		}
	}

	public void doAction(Key action) {
		switch(action) {
			case LEFT:
				velocity.x++;
				break;
			case RIGHT:
				velocity.x--;
				break;
			case UP:
				velocity.y++;
				break;
			case DOWN:
				velocity.y--;
				break;
			case GRAPPLE:
				break;
		}
	}
}
