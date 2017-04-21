package entity;

import processing.core.PVector;

import java.awt.geom.Rectangle2D;

/**
 * Created by DYLAN KUMAR on 21/04/2017.
 */

public abstract class Entity {

    abstract float getWidth();
    abstract float getHeight();
    abstract PVector getPosition();

    Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(getPosition().x, getPosition().y, getWidth(), getHeight());
    }

    boolean RectangleIntersection(Entity other) {
        return (getBounds().intersects(other.getBounds()));
    }

    boolean pointIntersection(PVector point){
        return(getBounds().contains(point.x, point.y));
    }

}
