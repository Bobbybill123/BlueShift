package BlueShift.entity;

import BlueShift.Main;
import BlueShift.entity.surface.Platform;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

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
	public Rectangle getBounds() {
        return super.getBounds();
    }

	public void draw(){
        main.ellipseMode(PConstants.CENTER);

        //main.fill(color);
        if(color == Color.BLUE){
            main.fill(0,0,255);
        }else{
            main.fill(255,0,0);
        }
        main.ellipse(position.x, position.y, getWidth(), getHeight());
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


	public boolean intersectPlatform(java.util.List<Platform> currentPlatforms){

	    for(Platform p: currentPlatforms) {
            if (this.intersects(p)) {
                return true;
            }
        }
	    return false;
    }

}
