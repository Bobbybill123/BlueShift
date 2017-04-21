package BlueShift.entity;

import processing.core.PVector;

import java.awt.geom.Rectangle2D;

/**
 * Created by DYLAN KUMAR on 21/04/2017.
 */

public abstract class Entity {

    protected abstract float getWidth();

    protected abstract float getHeight();

    protected abstract PVector getPosition();

    protected abstract void draw();

	protected abstract void doPhysics();

	protected Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(getPosition().x, getPosition().y, getWidth(), getHeight());
    }

    protected boolean RectangleIntersection(Entity other) {
        return (getBounds().intersects(other.getBounds()));
    }

	protected boolean pointIntersection(PVector point){
        return(getBounds().contains(point.x, point.y));
    }

}
