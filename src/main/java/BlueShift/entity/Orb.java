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
    public PVector getPosition() {
        return this.position;
    }

    @Override
	public float getHeight() {
        return this.DIAM*2;
    }

    @Override
	public float getWidth() {
        return this.DIAM*2;
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
	public void checkCollision(Entity other) {

	}


}
