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

import java.util.*;

public class Main extends PApplet {
	private final Map<Character, Move> keyBinds = new HashMap<>(4);
	public List<Platform> currentPlatforms = new ArrayList<>();
	public List<Orb> currentOrbs = new ArrayList<>();
	private boolean[] keyPressed = new boolean[4];
	public static Main instance;
	public float gameSpeed = (float) 0.1;
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

		//adding starting platforms
		currentPlatforms.add(new Platform(new PVector(width - 300, height - height/2), 300, 50));
		currentPlatforms.add(new Platform(new PVector(width - 600, height - height/2), 300, 50));
		currentPlatforms.add(new Platform(new PVector(width - 900, height - height/2), 300, 50));
		currentPlatforms.add(new Platform(new PVector(width - 300, height - height/4), 300, 50));
		currentPlatforms.add(new Platform(new PVector(width - 600, height - height/4), 300, 50));
		currentPlatforms.add(new Platform(new PVector(width - 900, height - height/4), 300, 50));
	}

	public void draw() {
		background(0);
		gameSpeed += 0.01;
		for (int i = 0; i < keyPressed.length; i++) {
			if(keyPressed[i]) {
				player.doAction(Move.values()[i]);
				//System.out.println(Move.values()[i]);
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

		moveTowardsTheLeftWall();
		removeIfOutOfScreen();
		increaseGameSpeed();
		generatePlatforms();
	}

	/**
	 * Generate platforms ahead of the screen as long as platforms are being deleted
     */
	public void generatePlatforms(){
		if(currentPlatforms.size() < 6){
			Platform p = new Platform(new PVector(width + random(0, width), random(100, height - height/3)), (int)random(200, 400), (int)random(50, 80));
			//As long as the generated platform intersects with another platform, generate another one
			while(p.intersectPlatform(currentPlatforms)){
				p = new Platform(new PVector(width + random(0, width), random(100, height - height/3)), (int)random(200, 400), (int)random(50, 80));
			}
			currentPlatforms.add(p);
		}
	}


	/**
	 * Move the entities towards the left wall
     */
	public void moveTowardsTheLeftWall(){

		//player.moveLeft(gameSpeed);

		for(Platform platform: currentPlatforms){
			platform.moveLeft(gameSpeed);
		}

		for(Orb orb: currentOrbs){
			orb.moveLeft(gameSpeed);
		}
	}

	/**
	 * This methods removes the entities if they go out of the screen
     */
	public void removeIfOutOfScreen(){

		//Platform Iterator
		Iterator<Platform> platformIterator = currentPlatforms.iterator();
		while (platformIterator.hasNext()) {
			Platform platform = platformIterator.next();

			if(platform.getPosition().x + platform.getWidth() < 0){
				platformIterator.remove();
			}
		}

		//Orb Iterator
		Iterator<Orb> orbIterator= currentOrbs.iterator();
		while (orbIterator.hasNext()) {
			Orb orb = orbIterator.next();

			if(orb.getPosition().x + orb.getWidth() < 0){
				orbIterator.remove();
			}
		}

	}

	/**
	 * Increase the gameSpeed over time
     */
	public void increaseGameSpeed(){
		if(gameSpeed < 1) {
			gameSpeed = gameSpeed + (float) 0.001;
		}
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
		for (Platform platform : currentPlatforms) {
			player.getHook().checkCollision(platform);
		}
		//player.getHook().checkCollision(null);
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