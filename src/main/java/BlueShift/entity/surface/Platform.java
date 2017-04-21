package BlueShift.entity.surface;

import BlueShift.Main;
import BlueShift.entity.Entity;
import BlueShift.entity.EntityType;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.geom.Rectangle2D;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Platform extends Surface {
    private Main main;
    private PVector position;
    private int width;
    private int height;

    public Platform(PVector position, int width, int height){
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
        //Platform hitbox dimensions (position.x, position.y, width, height)
        //Player hitbox dimensions (main.player.getPosition.x/y , main.player.getWidth/getHeight)
        //Player can move up through the hitbox but not down
        //the player must be moving down to 'walk' on the platform (??vertical velocity positive??)

        //Identifies when player is on the platform
        boolean playerPlatformCollision = false;
        main.player.getBounds();
        PVector playerPos = main.player.getPosition();
        if ((playerPos.x + main.player.getWidth()) < position.x && (playerPos.x > (position.x + width)) && ((playerPos.y + main.player.getHeight()) == position.y)) {
            if (main.player.getVelocity().y >= 0)
                playerPlatformCollision = true;
        }
        if (playerPlatformCollision)
            System.out.println("I DID IT!!!");
	}

	@Override
	public EntityType getType() {
		return EntityType.SURFACE;
	}

	@Override
	public float getSpeedModifier() {
		return 0.75f;
	}
}
