package BlueShift;

import BlueShift.entity.Floor;
import BlueShift.entity.LeftWall;
import BlueShift.entity.player.Key;
import BlueShift.entity.player.Player;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;

public class Main extends PApplet{

	private final Map<Key, Character> keyBinds = new HashMap<>(6);
	public static Main instance;

	//entities
	LeftWall leftWall;
	Floor floor;
	public Player player;

	public Main(){
		instance = this;
	}

	public void settings() {
		size(1920, 1080);
	}
	public void setup() {
		leftWall = new LeftWall();
		floor = new Floor();
		player = new Player();

	}
	public void draw() {
		background(255);
		floor.draw();
		leftWall.draw();
	}
	public void keyPressed() {

	}
	public void keyReleased() {

	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}