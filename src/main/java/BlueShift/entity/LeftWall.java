package BlueShift.entity;

import BlueShift.Main;
import processing.core.PVector;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class LeftWall extends Entity {

    private Main main;
    private PVector position;

    public LeftWall(){
        this.position = new PVector(0, 0);
        main = Main.instance;
    }

    @Override
    protected float getWidth() {
        return 50;
    }

    @Override
    protected float getHeight() {
        return main.height;
    }

    @Override
    protected PVector getPosition() {
        return this.position;
    }

    @Override
    public void draw() {
        main.fill(0,0, 0);
        main.rect(position.x, position.y, getWidth(), getHeight());
    }

    @Override
    protected void doPhysics() {

    }

    @Override
    protected void checkCollision(Entity other) {

    }
}
