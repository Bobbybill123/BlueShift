package BlueShift.entity.surface;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import processing.core.PVector;

import java.util.List;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Platform extends Surface {
	private Main main;
	private PVector position;
	private int width;
	private int height;

	public Platform(PVector position, int width, int height) {
		main = Main.instance;
		this.position = position;
		this.height = height;
		this.width = width - width%height;
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
		main.fill(255, 0, 0);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
	}

	@Override
	public boolean checkCollision(Entity other) {
		PVector pos = other.getPosition();
		if (pos.x >= position.x && pos.x + other.getWidth() <= position.x + width) { // within x bounds
			if (pos.y + other.getWidth() < position.y && pos.y + other.getWidth() > position.y - 5) { // either on or 5 above the y bounds
				return true;
			}
		}
		return false;
	}

	@Override
	public EntityType getType() {
		return EntityType.PLATFORM;
	}

	@Override
	public float getSpeedModifier() {
		return 0.75f;
	}


	/**
	 * Checks if you are intersecting with another platform				
	 * @param currentPlatforms
	 * @return
     */
	public boolean intersectPlatform(List<Platform> currentPlatforms){
		for(Platform p: currentPlatforms){
			if(intersects(p)){
				return true;
			}
		}
		return false;
	}
}
