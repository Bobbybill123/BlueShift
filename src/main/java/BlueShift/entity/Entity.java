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

	public abstract void doPhysics();

	public abstract void checkCollision(Entity other);

	public abstract EntityType getType();

	public Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(getPosition().x, getPosition().y, getWidth(), getHeight());
    }

    public boolean intersects(Entity other) {
        return (getBounds().intersects(other.getBounds()));
    }

	public boolean pointIntersection(PVector point){
        return(getBounds().contains(point.x, point.y));
    }

}
