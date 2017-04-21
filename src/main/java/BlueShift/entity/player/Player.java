package BlueShift.entity.player;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import BlueShift.entity.Orb;
import BlueShift.entity.surface.Surface;
import processing.core.PVector;

import java.awt.*;

public class Player extends Entity {
	private static final float BASE_SPEED_LIMIT = 10;
	private Main main;
	private PVector position;
	private PVector velocity;
	private Surface on = null;
	private static float GRAVITY = 0.7f;
	private float speedLim;
	private int blue = 0;
	private int red = 0;
	private boolean onGround = false;
	private long jumpMillis;
	private boolean jump = false;

	public Player() {
		main = Main.instance;
		//setting the players starting position
		this.position = new PVector(20 + getWidth(), main.floor.getPosition().y - getHeight());
		System.out.println(main.floor.getPosition().y - getHeight());
		System.out.println(main.width);
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
		if(jump) jump();
		calculateSpeedLim();
		doPhysics();
		doMovement();
		if(on != null && on.getType() == EntityType.FLOOR) {
			onGround = true;
			position.y = main.floor.getPosition().y - getHeight();
		} /*else position.y = getHeight();*/
		main.fill(255 - blue, 255 - blue, 255);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
	}

	private void jump() {
		if(main.millis() < jumpMillis + 150) {
			GRAVITY = 0;
			velocity.y = -7;
		} else {
			jump = false;
			GRAVITY = 0.7f;
			velocity.y = 7;
		}


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
				jumpMillis = main.millis();
				jump = true;
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
}
