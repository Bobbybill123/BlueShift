package BlueShift.entity.surface;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import BlueShift.entity.player.Player;
import processing.core.PImage;
import processing.core.PVector;

public class Floor extends Surface {
	private Main main = Main.instance;
	public static PImage sprite;
	@Override
	public float getWidth() {
		return main.width;
	}

	@Override
	public float getHeight() {
		return 40;
	}

	@Override
	public PVector getPosition() {
		return new PVector(0, main.height - getHeight());
	}

	@Override
	public void draw() {
		main.fill(0, 127, 0);
		main.rect(getPosition().x, getPosition().y, getWidth(), getHeight());
		for (int i = 0; i < getWidth()/sprite.width; i++) {
			System.out.println(sprite.width);
			main.image(sprite, getPosition().x + sprite.width*i, getPosition().y);
		}
}

	@Override
	public boolean checkCollision(Entity other) {
		if(other.getPosition().y + other.getHeight() >= getPosition().y) {
			other.getPosition().y = getPosition().y - other.getHeight();
			if(other.getType() == EntityType.PLAYER) {
				((Player) other).getVelocity().y = 0;
			}
			return true;
		}
		return false;
	}

	@Override
	public EntityType getType() {
		return EntityType.FLOOR;
	}

	@Override
	public float getSpeedModifier() {
		return 0.5f;
	}
}
