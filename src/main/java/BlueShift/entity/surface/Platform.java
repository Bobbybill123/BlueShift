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

	private int channelNumber;

	public Platform(PVector position, int width, int height, int channelNumber) {
		main = Main.instance;
		this.position = position;
		this.height = height;
		this.width = width - width%height;
		this.channelNumber = channelNumber;
		//System.out.println(channelNumber);
	}

	public int getChannelNumber(){
		return this.channelNumber;
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
	 * Checks if you are intersecting with another platform (or near another channel)
	 * @param currentPlatforms
	 * @return
	 */
	public boolean intersectPlatform(List<Platform> currentPlatforms){
		for(Platform p: currentPlatforms){
//
//			//main.rect(p.getPosition().x, p.getPosition().y  - p.getHeight()*10, (int)(p.getWidth()), (int)p.getHeight()*20);
//	//this.intersects(p) ||
//			if(this.channelNumber == (p.channelNumber) ||
//						this.channelNumber == (p.channelNumber + 1) ||
//						this.channelNumber == (p.channelNumber + 2) ||
//						this.channelNumber == (p.channelNumber - 1) ||
//						this.channelNumber == (p.channelNumber -2)){
//					System.out.println(this.channelNumber);
//				return true;
//			}

			PVector p1 = new PVector(p.getPosition().x, p.getPosition().y);
			PVector p2 = new PVector(this.getPosition().x, this.getPosition().y);

			if (p1.dist(p2.add(this.getWidth(),0)) < 200 || p1.add(p.getWidth(),0).dist(p2) < 200) return true;

		if(this.intersects(p)){
			return true;
		}


		}
		return false;
	}
}
