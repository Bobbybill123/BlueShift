package BlueShift.entity;

import processing.core.PVector;

import java.awt.geom.Rectangle2D;

/**
 * Created by DYLAN KUMAR on 21/04/2017.
 */

public abstract class Entity {

	public abstract float getWidth();

	public abstract float getHeight();

	public abstract PVector getPosition();

	public abstract void draw();

	//Empty so that not all classes have to implement it
	public void doPhysics() {}

	public abstract boolean checkCollision(Entity other);

	public abstract EntityType getType();

	public Rectangle2D.Float getBounds() {
		return new Rectangle2D.Float(getPosition().x, getPosition().y, getWidth(), getHeight());
	}

	protected boolean intersects(Entity other) {
		return getBounds().intersects(other.getBounds());
	}

	public boolean isSurface() {
		return false;
	}

	public void moveLeft(float gameSpeed){
		//should this need a setter?
		this.getPosition().x = this.getPosition().x - gameSpeed;
	}

}
