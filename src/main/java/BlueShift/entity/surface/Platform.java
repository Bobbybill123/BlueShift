package BlueShift.entity.surface;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import BlueShift.entity.player.Player;
import processing.core.PImage;
import processing.core.PVector;

import java.util.List;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Platform extends Surface {
	private Main main;
	public static PImage sprite;
	private PImage localSprite;
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
		localSprite = sprite.copy();
		localSprite.resize(width, height);
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
//		main.tint(0);
		main.image(localSprite, position.x, position.y);
//		main.tint(255);
	}

	@Override
	public boolean checkCollision(Entity other) {
		Player p = null;
		if (other.getType() == EntityType.PLAYER) {
			p = (Player) other;
//			p.getPosition().x = p.getPosition().x + main.gameSpeed*0.9f;
		}
		if (p != null) {
			if(p.getA().y < this.getD().y && !(p.getD().y >= position.y)) {
				return false;
			}
			if ((p.getA().x > position.x || p.getB().x < this.getB().x) && p.getD().y >= position.y && p.getVelocity().y > 0) {
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
}
