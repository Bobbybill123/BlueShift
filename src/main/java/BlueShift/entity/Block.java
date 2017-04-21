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

    public Block(PVector position, int width, int height){
        this.position = position;
        this.width = width;
        this.height = height;
    }

    @Override
	protected PVector getPosition() {
        return this.position;
    }

    @Override
	protected float getHeight() {
        return this.height;
    }

    @Override
	protected float getWidth() {
        return this.width;
    }

    @Override
	protected Rectangle2D.Float getBounds() {
        return super.getBounds();
    }

	protected void draw(){
        //do we want to draw from top left corner or centre?
        main.rectMode(PConstants.CORNER);
        main.rect(position.x, position.y, width, height);
    }

	@Override
	protected void doPhysics() {

	}

	@Override
	protected void checkCollision(Entity other) {

	}


}
