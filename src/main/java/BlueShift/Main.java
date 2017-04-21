package BlueShift;

import BlueShift.entity.LeftWall;
import BlueShift.entity.player.Move;
import BlueShift.entity.player.Player;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends PApplet {

	private final Map<Character, Move> keyBinds = new HashMap<>(4);
	public static Main instance;
	private List<Move> pressedKeys;
	public int gameSpeed = 0;

	public static int GROUND;

	//entities
	LeftWall leftWall;
	public Player player;

	private Main(){
		instance = this;
	}

	public void settings() {
		size(1800, 900);
	}

	public void setup() {
		frameRate(100);
		GROUND = height - 40;
		pressedKeys = new ArrayList<>();
		leftWall = new LeftWall();
		player = new Player();
		keyBinds.put('a', Move.LEFT);
		keyBinds.put('d', Move.RIGHT);
		keyBinds.put('w', Move.UP);
		keyBinds.put('s', Move.DOWN);
	}

	public void draw() {
		System.out.println(frameRate);
		gameSpeed+=0.01;
		for (Move pressedKey : pressedKeys) {
			player.doAction(pressedKey);
		}
		background(0);
		leftWall.draw();
		player.draw();
	}

	public void keyPressed() {
		Move pressed = keyBinds.get(key);
		if(pressed != null) {
			pressedKeys.add(pressed);
		}
	}

	public void keyReleased() {
		Move released = keyBinds.get(key);
		if(released != null) {
			pressedKeys.remove(released);
			player.released(released);
		}
	}

	public void mousePressed() {

	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}