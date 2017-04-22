package BlueShift;

import BlueShift.entity.*;
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
	private List<Platform> currentPlatforms = new ArrayList<>();
	private List<Orb> currentOrbs = new ArrayList<>();
	private boolean[] keyPressed = new boolean[4];
	public static Main instance;
	public float gameSpeed = 3f;
	public Floor floor;

	//entities
	private LeftWall leftWall;
	private RightWall rightWall;
	public Player player;

	//channel
	public float channels[];

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
		rightWall = new RightWall();
		player = new Player();
		keyBinds.put('a', Move.LEFT);
		keyBinds.put('d', Move.RIGHT);
		keyBinds.put('w', Move.UP);
		keyBinds.put('s', Move.DOWN);
		Player.rightSprite = new Animation(player, "player\\right\\f", 13);
		Player.leftSprite = new Animation(player, "player\\left\\f", 13);
		LeftWall.sprite = new Animation(leftWall, "tentacles\\f", 27);
		Hook.sprite = loadImage("hook.png");
		Platform.leftSprite = loadImage("platform\\platform_left.png");
		Platform.midSprite = loadImage("platform\\platform_mid.png");
		Platform.rightSprite = loadImage("platform\\platform_right.png");
		//adding starting platforms
		currentPlatforms.add(new Platform(new PVector(width - 300, height - height/2), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width - 600, height - height/2), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width - 900, height - height/2), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width, height - height/4), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width + 300, height - height/4), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width + 600, height - height/4), 300, 50, 1));
		setupChannels();
	}

	public void draw() {
		background(0);
		for (int i = 0; i < keyPressed.length; i++) {
			if(keyPressed[i]) {
				player.doAction(Move.values()[i]);
			}
		}
		checkPlayerCollisions();
		for (Platform platform : currentPlatforms) {
			platform.draw();
		}
		leftWall.draw();
		player.draw();
		player.getHook().draw();
		floor.draw();

		moveTowardsTheLeftWall();
		removeIfOutOfScreen();
		increaseGameSpeed();
		if (millis() % 100 == 0) {
			generatePlatforms();
		}
		generatePlatforms();
		text(frameRate, 60, 60);

		generateOrbs();
		orbRendering();



	}


	/**
	 * Populate the channels with information regarding their y positions
     */
	public void setupChannels(){
		channels = new float[12];

		for(int i = 0; i < channels.length; i++){
			channels[i] = i*50;
		}
	}

	/**
	 * Generate platforms ahead of the screen as long as platforms are being deleted
	 */
	public void generatePlatforms(){
		int i = 0;

		/*if(currentPlatforms.size() < 20){*/
			int channelNumber = (int)random(0, 12);
			Platform p = new Platform(new PVector(width + random(0, width*2), channels[channelNumber]), (int)random(200, 500), 50, channelNumber);

			//As long as the generated platform intersects with another platform, generate another one
		/*while(p.intersectPlatform(currentPlatforms) && i < 10){
				channelNumber = (int)random(0, 12);
				p = new Platform(new PVector(width + random(0, width*2), channels[channelNumber]), (int)random(200, 500), 50, channelNumber);
				i++;
			}*/

			//if (!p.intersectPlatform(currentPlatforms)){
				currentPlatforms.add(p);
			//}
	//}
		//	}
		//}
	}

	public void generateOrbs(){
		if(currentOrbs.size() < 30){
			int channelNumber = (int)random(0, 12);
			Orb orb = new Orb(new PVector(((float)width + random(0, width*2)), channels[channelNumber]));
			while(orb.intersectPlatform(currentPlatforms)){
				channelNumber = (int)random(0, 12);
				orb = new Orb(new PVector(((float)width + random(0, width*2)), channels[channelNumber]));
			}
			currentOrbs.add(orb);
		}
	}

	/**
	 * Handles drawing the orb and also if it touches the player delete the orb
	 * TODO make it so that when you touch the orb, your player slows down/speeds up
     */
	public void orbRendering(){
		Iterator<Orb> orbIterator= currentOrbs.iterator();
		while (orbIterator.hasNext()) {
			Orb orb = orbIterator.next();
			orb.draw();
			if(orb.touchingPlayer(player)){
				orbIterator.remove();
			}
		}
	}


	/**
	 * Move the entities towards the left wall
	 */
	public void moveTowardsTheLeftWall() {
		if (player.getOn() != null) {
			player.moveLeft(gameSpeed);
		}
		player.getHook().moveLeft(gameSpeed);

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
	public void removeIfOutOfScreen() {

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
		//System.out.println(gameSpeed);
		if(gameSpeed <= 10) {
			gameSpeed = gameSpeed + (float) 0.001;
		}
	}

	public void checkPlayerCollisions() {
		player.checkCollision(floor);
		player.checkCollision(leftWall);
		boolean isOn = false;
		for (Platform platform : currentPlatforms) {
			if (player.checkCollision(platform)) {
				isOn = true;
			}
		}
		if (!isOn && player.getD().y <= height-60) {
			player.setOn(null);
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
	
	public boolean collideRectangles(PVector[] vert1, PVector[] vert2) {
		int A = 0;
		int B = 1;
		int C = 2;
		int D = 3;
		if (intersect(vert1[A], vert1[B], vert2[A], vert2[D])) {
			return true;
		} else if (intersect(vert1[A], vert1[B], vert2[B], vert2[C])) {
			return true;
		} else if (intersect(vert1[C], vert1[D], vert2[A], vert2[D])) {
			// touching top here
			return true;
		} else if (intersect(vert1[C], vert1[D], vert2[B], vert2[C])) {
			// touching top here
			return true;
		} else if (intersect(vert1[A], vert1[D], vert2[D], vert2[C])) {
			return true;
		} else if (intersect(vert1[B], vert1[C], vert2[D], vert2[C])) {
			return true;
		} else if (intersect(vert1[B], vert1[C], vert2[A], vert2[B])) {
			// touching top here
			return true;
		} else if (intersect(vert1[A], vert1[D], vert2[A], vert2[B])) {
			// touching top here
			return true;
		}
		return false;
	}

	public boolean intersect(PVector s, PVector e, PVector p, PVector q) {

		// determines the equation of the line in the form ax + by + c
		float A = -(q.y - p.y);
		float B = q.x - p.x;
		float C = q.y * p.x - q.x * p.y;

		float numer = A * s.x + B * s.y + C;
		float denom = A * (s.x - e.x) + B * (s.y - e.y);

		// I could have calculated everything in one step, but this was neater
		float t = numer / denom;

		if (0 > t || t > 1 || !checkIntersect(p, q, s, e)) {
			return false;
		}

		return true;
	}

	public boolean checkIntersect(PVector s, PVector e, PVector p, PVector q) {

		// determines the equation of the line in the form ax + by + c
		float A = -(q.y - p.y);
		float B = q.x - p.x;
		float C = q.y * p.x - q.x * p.y;

		float numer = A * s.x + B * s.y + C;
		float denom = A * (s.x - e.x) + B * (s.y - e.y);

		// I could have calculated everything in one step, but this was neater
		float t = numer / denom;

		return 0 < t && t < 1;
	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

}