package BlueShift;

import BlueShift.entity.Animation;
import BlueShift.entity.Hook;
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
		Player.rightSprite = new Animation(player, "player\\right\\f", 13);
		Player.leftSprite = new Animation(player, "player\\left\\f", 13);
		LeftWall.sprite = new Animation(leftWall, "tentacles\\f", 27);
		Hook.sprite = loadImage("hook.png");
		currentPlatforms.add(new Platform(new PVector(300, height - 100), 210, 50));
	}

	public void draw() {
		background(0);
		gameSpeed += 0.01;
		for (int i = 0; i < keyPressed.length; i++) {
			if(keyPressed[i]) {
				player.doAction(Move.values()[i]);
				System.out.println(Move.values()[i]);
			}
		}
		checkPlayerCollisions();
		for (Platform platform : currentPlatforms) {
			platform.draw();
		}
		floor.draw();
		leftWall.draw();
		player.draw();
		player.getHook().draw();
	}

	public void checkPlayerCollisions() {
		player.checkCollision(floor);
		player.checkCollision(leftWall);
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
		System.out.println("You Died!");
	}

	public void keyPressed() {
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
		this.player.getHook().release();
	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}