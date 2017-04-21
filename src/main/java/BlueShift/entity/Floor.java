package BlueShift.entity;

import BlueShift.Main;
import processing.core.PVector;

/**
 * Created by TML_TEST on 21/04/2017.
 */
public class Floor extends Entity {

    private Main main;
    private PVector position;
    private float width;
    private float height;

    public Floor(){
        main = Main.instance;
        height = 40;
        width = main.width;
        this.position = new PVector(0, main.height - height);
    }

    @Override
    protected float getWidth() {
        return this.width;
    }

    @Override
    protected float getHeight() {
        return this.height;
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
