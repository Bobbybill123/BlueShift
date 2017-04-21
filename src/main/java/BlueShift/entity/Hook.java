package BlueShift.entity;

import BlueShift.Main;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Hook extends Entity {

	private Main main;
	/**
	 * The current position of the hook's end. Is null unless hooked on something.
	 */
	public static PImage sprite;
	private PVector position;
	
	private PVector target;

	//A normalized PVector of the direction the hook is going in (set in 'direction' method)
	private PVector direction;
	
	/**
	 * Whether or not the hook is latched on to something.
	 */
	private boolean hooked = false;
	private boolean pulling = false;

	private static final int DIAM = 5;

	public Hook(){
		main = Main.instance;
	}

	@Override
	 public float getWidth() {
		return DIAM*2;
	}

	@Override
	public float getHeight() {
		return DIAM*2;
	}

	@Override
	public PVector getPosition() {
		return this.position;
	}

	@Override
	public void draw() {
		
			if (this.pulling) {
				setDirection();
				reelIn();
			}
			moveHook();
		if (this.position != null && this.target != null) {
			main.fill(200,0, 0);
			main.image(sprite, position.x, position.y, getWidth(), getHeight());
			hookLine(main.player.getPosition());
		}
	}

	/**
	 *  Draws a line from the player's position to where the hook is
	 * @param playerPosition the position of the player
	 */
	public void hookLine(PVector playerPosition){
		main.stroke(255);
		main.line(playerPosition.x + main.player.getWidth()/2, playerPosition.y + main.player.getHeight()/2, position.x, position.y);
		main.stroke(0);
	}

	/**
	 * Moves the hook through the air until it collides with a wall (or something) and moves you towards it
	 *
	 */
	public void moveHook(){
		if (this.direction != null && this.hooked) {
			if (this.position.dist(this.target) < 10) {
				pulling = true;
			} else {
				this.position.add(this.direction.copy().mult(20));
			}
		}
	}
	
	/**
	 * Reels the player in towards the hook location.
	 */
	public void reelIn() {
		if (this.hooked && main.player.getPosition().dist(this.target) < 50) {
			release();
		} else {
			main.player.setOnGround(false);
			main.player.setOn(null);
			main.player.addVelocity(new PVector(this.direction.x * 2, this.direction.y * 2));
		}
	}

	/**
	 * Sets the direction that the hook is going in
	 */
	public void setDirection() {
		direction = this.target.copy().sub(main.player.getPosition()).normalize();
	}
	
	public void fire(PVector mousePosition) {
		if (!this.hooked) {
			this.target = mousePosition;
		}
	}
	
	public void release() {
		this.pulling = false;
		this.hooked = false;
		this.position = null;
		this.target = null;
		this.direction = null;
	}

	@Override
	public void doPhysics() {
		
	}
	
	@Override
	public boolean checkCollision(Entity other) {
		if (this.target != null) {
			//Rectangle2D.Float bBox = other.getBounds();
			hooked = true;/*other instanceof Surface && 
					this.target.x >= bBox.x && 
					this.target.x <= bBox.x + bBox.width && 
					this.target.y >= bBox.y &&
					this.target.y <= bBox.y + bBox.height;*/
			this.position = main.player.getPosition().copy();
			setDirection();
			return hooked;
		}
		return false;
	}

	@Override
	public EntityType getType() {
		return EntityType.HOOK;
	}

	public void setTarget(PVector pVector) {
		this.target = pVector;
	}

	public void setPosition(PVector pVector) {
		this.position = pVector;
	}
}
