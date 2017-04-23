package BlueShift.entity.player;

import BlueShift.Main;
import BlueShift.entity.*;
import BlueShift.entity.surface.Surface;
import processing.core.PVector;

import java.awt.*;

public class Player extends Entity {
	private static final float BASE_SPEED_LIMIT = 10;
	private static final float GRAVITY = 0.7f;
	public static Animation leftSprite;
	public static Animation rightSprite;
	private Animation currentSprite;
	private Main main;
	private PVector position;
	private PVector velocity;
	private Surface on = null;
	private float speedLim;
	private float blue = 0;
	private float red = 0;
	private boolean onGround = false;
	private Hook hook;
	private int blueOrbsCollected = 0;

	public Player() {
		main = Main.instance;
		reset();
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
		//System.out.println(getOn() != null);
		calculateSpeedLim();
		doMovement();
		doPhysics();
		if(getOn() != null && getOn().getType() == EntityType.FLOOR) {
			setOnGround(true);
			position.y = main.floor.getPosition().y - getHeight();
		}
		main.fill(255 - blue, 255 - blue, 255);
		if(velocity.x < 0) currentSprite = leftSprite;
		else currentSprite = rightSprite;
		if(getOn() != null) {
			if(main.frameCount % 2 == 0) {
				currentSprite.animate(getPosition().x, getPosition().y);
			} else currentSprite.displayCurr(position.x, position.y);
		} else currentSprite.display(getPosition().x, getPosition().y, 12);
		//System.out.println(velocity);
	}

	@Override
	public void doPhysics() {
		if(getOn() == null && !onGround) {
			velocity.y += GRAVITY;
		} else {
			velocity.y = 0;
		}
		if(velocity.x > speedLim) {
			velocity.x -= (velocity.x - speedLim)/5;
		}
		velocity.x -= (velocity.x/20);
	}

	private void doMovement() {
		if (getOn() != null && getD().y < getOn().getPosition().y) {
			this.position.y = getOn().getPosition().y - this.getHeight()+1;
		}
		position.x += velocity.x;
		if(getOn() != null && velocity.y > 0) return;
		position.y += velocity.y;
	}

	@Override
	public boolean checkCollision(Entity other) {
		if(other.isSurface()) {
			if (other.getType() == EntityType.FLOOR) {
				if(intersects(other)) {
					setOn((Surface) other);
					if (other.checkCollision(this)) {
						setOnGround(true);
						return true;
					}
				}
			}
		}
		if (this.position.dist(other.getPosition()) < 250 || 
				this.position.dist(new PVector(other.getPosition().x + other.getWidth(), 
						other.getPosition().y + other.getHeight())) < 250) {
			if (intersects(other)) {
				if (other.isSurface()) {
					if (other.checkCollision(this)) {
						setOn((Surface) other);
						if (getOn().getType() == EntityType.PLATFORM) {
							if (main.oldPlayerPosition.y < other.getPosition().y) {
								setOn((Surface) other);
								this.getPosition().y = other.getPosition().y - this.getHeight() - 1;
							} else {
								this.on = null;
							}
						}
					}
				} else if (other.getType() == EntityType.ORB) {
					pickupObject(other);
				} else if (other.getType() == EntityType.LEFT_WALL) {
					main.gameOver();
				} else {
					this.on = null;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public EntityType getType() {
		return EntityType.PLAYER;
	}

	@Override
	public void moveLeft(float gameSpeed) {
		if(on != null) {
			gameSpeed /= on.getSpeedModifier();
		}
		super.moveLeft(gameSpeed);
	}

	public void pickupObject(Entity e) {
		if(e.getType() == EntityType.ORB) {
			Orb orb = ((Orb) e);
			if(orb.getColor().equals(Color.BLUE)) {
				blueOrbsCollected++;
				this.velocity.x += 0.5;
			}else {
				this.velocity.x -= 4;
			}
		}
	}

	private void calculateSpeedLim() {
		if(!isOnGround()) {
			speedLim = 20;
			return;
		}
		speedLim = BASE_SPEED_LIMIT;
		if(getOn() != null && !onGround) {
			speedLim *= getOn().getSpeedModifier();
		}
		speedLim *= main.gameSpeed;
	}

	public void doAction(Move action) {
		switch(action) {
			case RIGHT:
				//velocity.x = Math.min(speedLim, velocity.x + speedLim);
				if (velocity.x < 10) {
					velocity.x += 5;
				}
				break;
			case LEFT:
				if (velocity.x > -10) {
					velocity.x -= 5;
				}
				//velocity.x = Math.max(-speedLim, velocity.x - speedLim);
				break;
			case UP:
				if(getOn() == null) break;
				velocity.y -= 20;
				setOnGround(false);
				setOn(null);
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

	public void reset() {
		this.position = new PVector(main.width/2 + getWidth(), main.floor.getPosition().y - getHeight() - 5);
		velocity = new PVector();
		setHook(new Hook());
	}

	public PVector getVelocity() {
		return this.velocity;
	}

	public void addVelocity(PVector p) {
		this.velocity = new PVector(Math.min(30, this.velocity.x + p.x), Math.min(5, this.velocity.y + p.y));
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public Surface getOn() {
		return on;
	}

	public void setOn(Surface on) {
		this.on = on;
	}

	public void setPosition(PVector p){
		this.position = p.copy();
	}

}
