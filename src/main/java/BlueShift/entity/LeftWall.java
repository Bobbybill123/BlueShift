package BlueShift.entity;

import BlueShift.Main;
import processing.core.PVector;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class LeftWall extends Entity {

	public static Animation sprite;
	private Main main;
	private PVector position;

	public LeftWall(){
		this.position = new PVector(0, 0);
		main = Main.instance;
	}

	@Override
	public float getWidth() {
		return 60;
	}

	@Override
	public float getHeight() {
		return 242/6;
	}

	@Override
	public PVector getPosition() {
		return this.position;
	}

	@Override
	public void draw() {
		main.fill(200,0, 0);
		for (int i = 0; i < main.height; i+=(main.height/(getHeight()/2))) {
			if(main.frameCount % 10 == 0)
				sprite.animate(position.x, position.y + i);
			else sprite.displayCurr(position.x, position.y + i);
		}
//		main.rect(position.x, position.y, getWidth(), getHeight());
	}

	@Override
	public void doPhysics() {

	}

	@Override
	public boolean checkCollision(Entity other) {
		return false;
	}

	@Override
	public EntityType getType() {
		return EntityType.LEFT_WALL;
	}
}
