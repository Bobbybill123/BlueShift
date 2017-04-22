package BlueShift.entity.surface;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import processing.core.PImage;
import processing.core.PVector;

import java.util.List;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Platform extends Surface {
	private Main main;
	public static PImage leftSprite;
	public static PImage midSprite;
	public static PImage rightSprite;
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
		int blockCount = (int) (getWidth()/getHeight());
		main.image(leftSprite, position.x, position.y, getHeight(), getHeight());
		for (int i = 1; i < blockCount - 1; i++) {
			main.image(midSprite, position.x + getHeight()*i, position.y, getHeight(), getHeight());
		}
		main.image(rightSprite, position.x + getHeight() * (blockCount - 1), position.y, getHeight(), getHeight());
	}

	@Override
	public boolean checkCollision(Entity other) {
        PVector[] thisVertices = new PVector[]{this.getA(), this.getB(), this.getC(), this.getD()};
        PVector[] otherVertices = new PVector[]{other.getA(), other.getB(), other.getC(), other.getD()};
		return main.collideRectangles(thisVertices, otherVertices);
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
