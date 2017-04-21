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
	public PVector getPosition() {
        return this.position;
    }

    @Override
	public float getHeight() {
        return this.height;
    }

    @Override
	public float getWidth() {
        return this.width;
    }

    @Override
	public Rectangle2D.Float getBounds() {
        return super.getBounds();
    }

	public void draw(){
        //do we want to draw from top left corner or centre?
        main.rectMode(PConstants.CORNER);
        main.rect(position.x, position.y, width, height);
    }

	@Override
	public void doPhysics() {

	}

	@Override
	public void checkCollision(Entity other) {
        //LeannanSC apologises in advance for the terrible code :P
        //Block hitbox dimensions (top, left, width, height)
        //Player can move up through the hitbox but not down

        //Identifies when player is on the block
        main.player.getBounds();
        //if ();
	}


}
