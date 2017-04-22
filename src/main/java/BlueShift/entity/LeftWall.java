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
		return 100;
	}

	@Override
	public float getHeight() {
		return main.height;
	}

	@Override
	public PVector getPosition() {
		return this.position;
	}

	@Override
	public void draw() {
		for (int i = 0; i < main.height; i+=(main.height/(getWidth()/4))) {
			if(main.frameCount % 10 == 0)
				sprite.animate(position.x, position.y + i);
			else sprite.displayCurr(position.x, position.y + i);
		}
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
