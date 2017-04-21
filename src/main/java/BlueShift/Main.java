package BlueShift;

import BlueShift.entity.LeftWall;
import BlueShift.entity.player.Key;
import BlueShift.entity.player.Player;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;

public class Main extends PApplet {

	private final Map<Character, Key> keyBinds = new HashMap<>(4);
	public static Main instance;

	public static final int GROUND = 1040;

	//entities
	LeftWall leftWall;
	public Player player;

	private Main(){
		instance = this;
	}

	public void settings() {
		size(1920, 1080);
	}

	public void setup() {
		frameRate(100);
		leftWall = new LeftWall();
		player = new Player();
		keyBinds.put('a', Key.RIGHT);
		keyBinds.put('d', Key.LEFT);
		keyBinds.put('w', Key.UP);
		keyBinds.put('s', Key.DOWN);
	}

	public void draw() {
		background(255);
		leftWall.draw();
		player.draw();
	}

	public void keyPressed() {
		Key pressed = keyBinds.get(key);
		if(pressed != null) {
			player.doAction(pressed);
		}
	}

	public void keyReleased() {

	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}