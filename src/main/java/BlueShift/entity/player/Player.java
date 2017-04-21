package BlueShift.entity.player;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import BlueShift.entity.Orb;
import BlueShift.entity.surface.Surface;
import processing.core.PVector;

import java.awt.*;

public class Player extends Entity {
	private Main main;
	private PVector position;
	private PVector velocity;
	private Surface on;
	private static final float GRAVITY = 2f;
	private float speedLim;
	private int blue = 0;
	private int red = 0;
	private boolean onGround;

	public Player() {
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
		calculateSpeedLim();
		doPhysics();
		doMovement();
		if(position.y + getHeight() >= Main.GROUND) {
			onGround = true;
			position.y = Main.GROUND - getHeight();
		} else position.y = getHeight();
		main.fill(255 - blue, 255 - blue, 255);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
	}

	@Override
	public void doPhysics() {
		if(!onGround) {
			velocity.y += GRAVITY;
		} else velocity.y = 0;
	}

	private void doMovement() {
		position.x += velocity.x;
		if(!onGround) position.y = Math.min(position.y + velocity.y, Main.GROUND);
		position.add(velocity);
	}

	@Override
	public void checkCollision(Entity other) {
		if(intersects(other)) {
			if(other.getType() == EntityType.SURFACE) {

			}
		}
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

	public void calculateSpeedLim() {
		speedLim = 10;
		if(on != null) {
			speedLim *= on.getSpeedModifier();
		}
	}

	public void doAction(Move action) {
		switch(action) {
			case RIGHT:
				velocity.x = Math.min(speedLim, velocity.x + speedLim/50);
				break;
			case LEFT:
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

	public void released(Move released) {
		if(released == Move.LEFT|| released == Move.RIGHT) {
			velocity.x = 0;
		}
	}
}
