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

    Orb(PVector position){
        this.position = position;
        main = Main.instance;
    }

    @Override
    PVector getPosition() {
        return this.position;
    }

    @Override
    float getHeight() {
        return this.DIAM*2;
    }

    @Override
    float getWidth() {
        return this.DIAM*2;
    }

    @Override
    Rectangle2D.Float getBounds() {
        return super.getBounds();
    }

    public void draw(){
        main.ellipseMode(PConstants.CENTER);
    }




}
