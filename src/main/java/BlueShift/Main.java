package BlueShift;

import BlueShift.entity.LeftWall;
import BlueShift.entity.player.Move;
import BlueShift.entity.player.Player;
import BlueShift.entity.surface.Floor;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;

public class Main extends PApplet {
	private final Map<Character, Move> keyBinds = new HashMap<>(4);
	private boolean[] keyPressed = new boolean[4];
	public static Main instance;
	public int gameSpeed = 1;
	private boolean start = false;
	public Floor floor;

	//entities
	private LeftWall leftWall;
	public Player player;

	private Main(){
		instance = this;
	}

	public void settings() {
		size(1800, 900);
	}

	public void setup() {
		frameRate(100);
		floor = new Floor();
		leftWall = new LeftWall();
		player = new Player();
		keyBinds.put('a', Move.LEFT);
		keyBinds.put('d', Move.RIGHT);
		keyBinds.put('w', Move.UP);
		keyBinds.put('s', Move.DOWN);
	}

	public void draw() {
		if(start) {
			gameSpeed += 0.01;
			for (int i = 0; i < keyPressed.length; i++) {
				if(keyPressed[i]) player.doAction(Move.values()[i]);
				if(i < 2) keyPressed[i] = false;
			}
		}
		background(0);
		leftWall.draw();
		player.draw();
	}

	public void gameOver() {
		//TODO Make game over screen
	}

	public void keyPressed() {
		start = true;
		Move pressed = keyBinds.get(key);
		if(pressed != null) {
			keyPressed[pressed.ordinal()] = true;
		}
	}

	public void keyReleased() {
		Move released = keyBinds.get(key);
		if(released != null) {
			keyPressed[released.ordinal()] = false;
			player.released(released);
		}
	}

	public void mousePressed() {

	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}