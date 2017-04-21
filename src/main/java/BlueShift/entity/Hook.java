package BlueShift.entity;

import java.awt.geom.Rectangle2D;

import BlueShift.Main;
import BlueShift.entity.surface.Surface;
import processing.core.PVector;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Hook extends Entity {

	private Main main;
	/**
	 * The current position of the hook's end. Is null unless hooked on something.
	 */
	private PVector position;
	
	private PVector target;

	//A normalized PVector of the direction the hook is going in (set in 'direction' method)
	private PVector direction;
	
	/**
	 * Whether or not the hook is latched on to something.
	 */
	private boolean hooked = false;

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
		if (this.position != null) {
			moveHook();
			main.fill(200,0, 0);
			main.ellipse(position.x, position.y, getWidth(), getHeight());
			hookLine(main.player.getPosition());
		}
	}

	/**
	 *  Draws a line from the player's position to where the hook is
	 * @param playerPosition the position of the player
	 */
	public void hookLine(PVector playerPosition){
		main.line(playerPosition.x, playerPosition.y, position.x, position.y);
	}

	/**
	 * Moves the hook through the air until it collides with a wall (or something) and moves you towards it
	 *
	 */
	public void moveHook(){
		if (this.hooked && main.player.getPosition().dist(this.position) < 10) {
			this.hooked = false;
			this.position = null;
			this.target = null;
		}
		if (this.direction != null && this.hooked) {
			this.position.add(this.direction.mult(5));
		}
	}

	/**
	 * Sets the direction that the hook is going in
	 */
	public void setDirection() {
		direction = main.player.getPosition().copy().sub(this.target).normalize();
	}
	
	public void fire(PVector mousePosition) {
		if (!this.hooked) {
			this.target = mousePosition;
		}
	}
	
	public void release() {
		this.hooked = false;
		this.direction = null;
		this.position = null;
		this.target = null;
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
			this.position = main.player.getPosition();
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
