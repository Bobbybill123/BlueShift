package BlueShift;

import BlueShift.entity.LeftWall;
import BlueShift.entity.Orb;
import BlueShift.entity.player.Move;
import BlueShift.entity.player.Player;
import BlueShift.entity.surface.Floor;
import BlueShift.entity.surface.Platform;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends PApplet {
	private final Map<Character, Move> keyBinds = new HashMap<>(4);
	public List<Platform> currentPlatforms = new ArrayList<>();
	public List<Orb> currentOrbs = new ArrayList<>();
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
		rectMode(CORNER);
		floor = new Floor();
		leftWall = new LeftWall();
		player = new Player();
		keyBinds.put('a', Move.LEFT);
		keyBinds.put('d', Move.RIGHT);
		keyBinds.put('w', Move.UP);
		keyBinds.put('s', Move.DOWN);
	}

	public void draw() {
		background(0);
		if(start) {
			gameSpeed += 0.01;
			for (int i = 0; i < keyPressed.length; i++) {
				if(keyPressed[i]) player.doAction(Move.values()[i]);
				if(i < 2) keyPressed[i] = false;
			}
		}
		checkPlayerCollisions();
		floor.draw();
		leftWall.draw();
		player.draw();
	}

	public void checkPlayerCollisions() {
		player.checkCollision(floor);
		for (Platform platform : currentPlatforms) {
			player.checkCollision(platform);
		}
		for (Orb orb : currentOrbs) {
			player.checkCollision(orb);
		}
	}
	
	public void checkHookCollisions() {
		/*for (Platform platform : currentPlatforms) {
			player.getHook().checkCollision(platform);
		}*/
		player.getHook().checkCollision(null);
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
		this.player.getHook().fire(new PVector(mouseX, mouseY));
		checkHookCollisions();
	}
	
	public void mouseReleased() {
		this.player.getHook().setPosition(null);
	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}