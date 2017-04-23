package BlueShift.entity;

import BlueShift.Main;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class LeftWall extends Entity {
	public static Animation sprite;
	private List<Animation> animations = new ArrayList<>();
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
		if(main.gameSpeed >= 49){
			main.gameSpeed = 49;
		}
		if(main.frameCount % (10 - ((int) (main.gameSpeed / 5))) == 0) {
			animations.forEach(animation -> animation.animate(0, animations.indexOf(animation) * animation.getHeight()));
		} else {
			animations.forEach(animation -> animation.displayCurr(0, animations.indexOf(animation) * animation.getHeight()));
		}
	}

	@Override
	public boolean checkCollision(Entity other) {
		return other.getPosition().x + other.getWidth() < 0;
	}

	@Override
	public EntityType getType() {
		return EntityType.LEFT_WALL;
	}

	public void setupSprite() {
		sprite.resize(100, 0);
		for (int i = 0; i < main.height; i+=(main.height/(getHeight()/sprite.getHeight()))) {
			Animation animation = sprite.copy();
			animations.add(animation);
			animation.setFrame((int) main.random(animation.getImageCount()));
		}
	}
}
