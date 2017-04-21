package BlueShift.entity;

import BlueShift.Main;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by DYLAN KUMAR on 21/04/2017.
 */
public class Orb extends Entity {
    private Main main;
    private PVector position;
    private static final float DIAM = 10;
	private Color color;

	public Orb(PVector position){
        this.position = position;
        main = Main.instance;
        color = main.random(2) > 1 ? Color.BLUE : Color.ORANGE;
    }

    @Override
    public PVector getPosition() {
        return this.position;
    }

    @Override
	public float getHeight() {
        return DIAM*2;
    }

    @Override
	public float getWidth() {
        return DIAM*2;
    }

    @Override
	public Rectangle2D.Float getBounds() {
        return super.getBounds();
    }

	public void draw(){
        main.ellipseMode(PConstants.CENTER);
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
		return EntityType.ORB;
	}


	public Color getColor() {
		return color;
	}
}
