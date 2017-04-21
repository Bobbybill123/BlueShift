package BlueShift.entity.player;

import BlueShift.Main;
import BlueShift.entity.*;
import BlueShift.entity.surface.Surface;
import processing.core.PVector;

import java.awt.*;

public class Player extends Entity {
	private static final float BASE_SPEED_LIMIT = 10;

	public static Animation sprite;
	private Main main;
	private PVector position;
	private PVector velocity;
	private Surface on = null;
	private static float GRAVITY = 0.7f;
	private float speedLim;
	private int blue = 0;
	private int red = 0;
	private boolean onGround = false;
	private Hook hook;

	public Player() {
		main = Main.instance;
		//setting the players starting position
		this.position = new PVector(20 + getWidth(), main.floor.getPosition().y - getHeight());
		System.out.println(main.floor.getPosition().y - getHeight());
		System.out.println(main.width);
		velocity = new PVector();
		setHook(new Hook());
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
		calculateSpeedLim();
		doMovement();
		doPhysics();
		if(on != null && on.getType() == EntityType.FLOOR) {
			onGround = true;
			position.y = main.floor.getPosition().y - getHeight();
		}
		main.fill(255 - blue, 255 - blue, 255);
		sprite.display(getPosition().x, getPosition().y);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
	}

	@Override
	public void doPhysics() {
		if(!onGround) {
			velocity.y += GRAVITY;
		} else velocity.y = 0;
	}

	private void doMovement() {
		if(velocity.x > speedLim) velocity.x -= speedLim/10;
		position.x += velocity.x;
		if(on != null && velocity.y > 0) return;
		position.y += velocity.y;
	}

	@Override
	public boolean checkCollision(Entity other) {
		if(intersects(other)) {
			if(other.isSurface()) {
				if(other.checkCollision(this)) {
					on = (Surface) other;
					if(on.getType() == EntityType.FLOOR) {
						onGround = true;
					}
				}
			} else if (other.getType() == EntityType.ORB) {
				pickupObject(other);
			} else if (other.getType() == EntityType.LEFT_WALL) {
				main.gameOver();
			}
			return true;
		}
		return false;
	}

	@Override
	public EntityType getType() {
		return EntityType.PLAYER;
	}

	private void pickupObject(Entity e) {
		if(e.getType() == EntityType.ORB) {
			Orb orb = ((Orb) e);
			if(orb.getColor().equals(Color.BLUE)) {
				if (red != 0) red--;
				else blue++;
			}
		}
	}

	private void calculateSpeedLim() {
		if(!onGround) {
			speedLim = Integer.MAX_VALUE;
			return;
		}
		speedLim = BASE_SPEED_LIMIT;
		if(on != null) {
			speedLim *= on.getSpeedModifier();
		}
		speedLim *= main.gameSpeed;
	}

	public void doAction(Move action) {
		switch(action) {
			case RIGHT:
				velocity.x = Math.min(speedLim, velocity.x + speedLim);
				break;
			case LEFT:
				velocity.x = Math.max(-speedLim, velocity.x - speedLim);
				break;
			case UP:
				if(!onGround) break;
				velocity.y -= 20;
				onGround = false;
				on = null;
				break;
/*			case DOWN:
				velocity.y--;
				break;*/
		}
	}

	public void released(Move released) {
		if(released == Move.LEFT || released == Move.RIGHT) {
			velocity.x = 0;
		}
	}

	public Hook getHook() {
		return hook;
	}

	public void setHook(Hook hook) {
		this.hook = hook;
	}
	
	public PVector getVelocity() {
		return this.velocity;
	}
	
	public void addVelocity(PVector p) {
		this.velocity.add(p);
	}
}
