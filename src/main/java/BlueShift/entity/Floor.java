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
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public PVector getPosition() {
        return this.position;
    }

    @Override
    public void draw() {
        main.fill(0,0, 0);
        main.rect(position.x, position.y, getWidth(), getHeight());
    }

    @Override
    public void doPhysics() {

    }

    @Override
    public void checkCollision(Entity other) {

    }
}
