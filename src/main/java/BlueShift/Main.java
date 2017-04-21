package BlueShift;

import BlueShift.entity.LeftWall;
import BlueShift.entity.player.Key;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;

public class Main extends PApplet{

	private final Map<Key, Character> keyBinds = new HashMap<>(6);
	public static Main instance;

	//entities
	LeftWall leftWall;

	public Main(){
		instance = this;
	}

	public void settings() {
		size(1920, 1080);
	}
	public void setup() {
		leftWall = new LeftWall();

	}
	public void draw() {
		background(255);
		//bottom path test (going to need to be changed into a type of block)
		leftWall.draw();
		//rect(0, height-50, width, 50);
	}
	public void keyPressed() {

	}
	public void keyReleased() {

	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}