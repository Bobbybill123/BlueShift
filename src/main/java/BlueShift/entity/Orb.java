package BlueShift.entity;

import BlueShift.Main;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.geom.Rectangle2D;

/**
 * Created by DYLAN KUMAR on 21/04/2017.
 */
public class Orb extends Entity{

    private Main main;
    private PVector position;
    private static final float DIAM = 10;

    public Orb(PVector position){
        this.position = position;
        main = Main.instance;
    }

    @Override
    protected PVector getPosition() {
        return this.position;
    }

    @Override
	protected float getHeight() {
        return this.DIAM*2;
    }

    @Override
	protected float getWidth() {
        return this.DIAM*2;
    }

    @Override
	protected Rectangle2D.Float getBounds() {
        return super.getBounds();
    }

	protected void draw(){
        main.ellipseMode(PConstants.CENTER);
    }

	@Override
	protected void doPhysics() {

	}

	@Override
	protected void checkCollision(Entity other) {

	}


}
