package BlueShift.entity.surface;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import processing.core.PVector;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Platform extends Surface {
	private Main main;
	private PVector position;
	private int width;
	private int height;

	public Platform(PVector position, int width, int height){
		this.position = position;
		this.width = width;
		this.height = height;
	}

	@Override
	public PVector getPosition() {
		return this.position;
	}

	@Override
	public float getHeight() {
		return this.height;
	}

	@Override
	public float getWidth() {
		return this.width;
	}

	public void draw() {
		main.rect(position.x, position.y, width, height);
	}

	@Override
	public boolean checkCollision(Entity other) {
		//LeannanSC apologises in advance for the terrible code :P
		//Platform hitbox dimensions (position.x, position.y, width, height)
		//Player hitbox dimensions (main.player.getPosition.x/y , main.player.getWidth/getHeight)
		//Player can move up through the hitbox but not down
		//the player must be moving down to 'walk' on the platform (??vertical velocity positive??)

		other.getBounds();
		PVector pos = other.getPosition();
		return pos.x + other.getWidth() < position.x && pos.x > position.x + width && pos.y + other.getHeight() == position.y;
	}

	@Override
	public EntityType getType() {
		return EntityType.PLATFORM;
	}

	@Override
	public float getSpeedModifier() {
		return 0.75f;
	}
}
