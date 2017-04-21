package BlueShift.entity;

import BlueShift.Main;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.geom.Rectangle2D;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Block extends Entity {

    private Main main;
    private PVector position;
    private int width;
    private int height;

    Block(PVector position, int width, int height){
        this.position = position;
        this.width = width;
        this.height = height;
    }

    @Override
    PVector getPosition() {
        return this.position;
    }

    @Override
    float getHeight() {
        return this.height;
    }

    @Override
    float getWidth() {
        return this.width;
    }

    @Override
    Rectangle2D.Float getBounds() {
        return super.getBounds();
    }

    public void draw(){
        //do we want to draw from top left corner or centre?
        main.rectMode(PConstants.CORNER);
        main.rect(position.x, position.y, width, height);
    }


}
