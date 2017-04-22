package BlueShift.entity;

import processing.core.PVector;

import java.awt.Rectangle;
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

	public Rectangle getBounds() {
		return new Rectangle((int) (getPosition().x), (int) (getPosition().y), (int) (getWidth()), (int) (getHeight()));
	}

	protected boolean intersects(Entity other) {
		
		return getBounds().intersects(other.getBounds());
	}

	public boolean isSurface() {
		return false;
	}

	public void moveLeft(float gameSpeed){
		this.getPosition().x -= gameSpeed;
	}
	
	public PVector getA() {
		return this.getPosition();
	}

	public PVector getB() {
		return new PVector(this.getPosition().x + this.getWidth(), this.getPosition().y);
	}

	public PVector getC() {
		return new PVector(this.getPosition().x + this.getWidth(), this.getPosition().y + this.getHeight());
	}

	public PVector getD() {
		return new PVector(this.getPosition().x, this.getPosition().y + this.getHeight());
	}
}
