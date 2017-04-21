package BlueShift;

import BlueShift.entity.player.Key;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;

public class Main extends PApplet{
	private final Map<Key, Character> keyBinds = new HashMap<>(6);

	public static Main instance;

	private Main(){
		instance = this;
	}

	public void settings() {

	}
	public void setup() {

	}
	public void draw() {

	}
	public void keyPressed() {

	}
	public void keyReleased() {

	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}