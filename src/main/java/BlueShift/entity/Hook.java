package BlueShift.entity;

import BlueShift.Main;
import processing.core.PVector;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Hook extends Entity {

    private Main main;
    private PVector position;

    //A normalized PVector of the direction the hook is going in (set in 'direction' method)
    private PVector direction;

    private static final int DIAM = 5;

    public Hook(PVector position){
        this.position = position;
        main = Main.instance;
    }

    @Override
     public float getWidth() {
        return DIAM*2;
    }

    @Override
    public float getHeight() {
        return DIAM*2;
    }

    @Override
    public PVector getPosition() {
        return this.position;
    }

    @Override
    public void draw() {
        main.fill(200,0, 0);
        main.ellipse(position.x, position.y, getWidth(), getHeight());
    }

    /**
     *  Draws a line from the player's position to where the hook is
     * @param playerPosition the position of the player
     */
    public void hookLine(PVector playerPosition){
        main.line(playerPosition.x, playerPosition.y, position.x, position.y);
    }

    /**
     * Moves the hook through the air until it collides with a wall (or something) and moves you towards it
     *
     */
    public void moveHook(){

    }

    /**
     * sets the direction that the hook is going in
     * @param playerPosition
     * @param mousePosition
     * @return
     */

    public void direction(PVector playerPosition, PVector mousePosition){
        PVector direction = playerPosition.sub(mousePosition).normalize();
    }

    @Override
    public void doPhysics() {

    }

    @Override
    public void checkCollision(Entity other) {

    }
}
