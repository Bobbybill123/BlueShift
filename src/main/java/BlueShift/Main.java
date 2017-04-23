package BlueShift;

import BlueShift.entity.*;
import BlueShift.entity.player.Move;
import BlueShift.entity.player.Player;
import BlueShift.entity.surface.Floor;
import BlueShift.entity.surface.Platform;
import BlueShift.menu.Button;
import BlueShift.menu.Menu;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main extends PApplet {
	private final Map<Character, Move> keyBinds = new HashMap<>(4);
	private Map<String, Menu> menus = new HashMap<>();
	private List<Platform> currentPlatforms = new ArrayList<>();
	private List<Orb> currentOrbs = new ArrayList<>();
	private boolean[] keyPressed = new boolean[4];
	private boolean playing = false;
	private String currentMenu = "Main";
	public static Main instance;
	public float gameSpeed = 3f;
	public Floor floor;

	//entities
	private LeftWall leftWall;
	public Player player;
	public double score = 0;
	private float speedIncreasedBy = 0;

	//channel
	public float channels[];

	public PVector oldPlayerPosition;

	private Main(){
		instance = this;
	}

	public void settings() {
		size(1800, 900);
	}

	public void setup() {
		frameRate(100);
		rectMode(CORNER);
		keyBinds.put('a', Move.LEFT);
		keyBinds.put('d', Move.RIGHT);
		keyBinds.put('w', Move.UP);
		keyBinds.put('s', Move.DOWN);
		floor = new Floor();
		player = new Player();
		leftWall = new LeftWall();
		Color buttonBlue = new Color(0, 0, 127);
		menus.put("Main", new Menu("Blue Shift", new Button("Play", buttonBlue, () -> playing = true),
				new Button("Quit", buttonBlue, this::exit)));
		menus.put("Over", new Menu("Game Over!", new Button("Restart", buttonBlue, () -> playing = true),
				new Button("Quit", buttonBlue, this::exit)));
		Player.rightSprite = new Animation(player, "player\\right\\f", 13);
		Player.leftSprite = new Animation(player, "player\\left\\f", 13);
		LeftWall.sprite = new Animation(null, "tentacles\\f", 27);
		Floor.sprite = loadImage("floor.png");
		Hook.sprite = loadImage("hook.png");
		Platform.sprite = loadImage("platform.png");
		leftWall.setupSprite();
		//adding starting platforms
		setupPlatforms();
		setupChannels();
	}

	private void setupPlatforms() {
		currentPlatforms.add(new Platform(new PVector(width - 300, height - height/2), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width - 600, height - height/2), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width - 900, height - height/2), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width, height - height/4), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width + 300, height - height/4), 300, 50, 1));
		currentPlatforms.add(new Platform(new PVector(width + 600, height - height/4), 300, 50, 1));
	}

	public void draw() {
		background(127, 0, 0);
		if (playing) {
			oldPlayerPosition = player.getPosition().copy();
			for (int i = 0; i < keyPressed.length; i++) {
				if (keyPressed[i]) {
					player.doAction(Move.values()[i]);
				}
			}
			checkPlayerCollisions();
			for (Platform platform : currentPlatforms) {
				platform.draw();
			}
			player.draw();
			player.getHook().draw();
			floor.draw();
			moveTowardsTheLeftWall();
			removeIfOutOfScreen();
			increaseGameSpeed();
			generatePlatforms();
			generateOrbs();
			orbRendering();
			boundPlayer();
			speedUpGame();
			leftWall.draw();
			fill(255, 255, 255);
			text(frameRate, 60, 60);
			text("Score: " + (int) score, width / 2, 60);
			score = score + 0.01;
			if (!player.getHook().isHooked()) {
				setHookCoolDownAngle((float) Math.min(Math.PI * 2, getHookCoolDownAngle() + Math.PI/36));
			}
		} else {
			cursor(CROSS);
			menus.get(currentMenu).draw();
		}
	}


	/**
	 * Populate the channels with information regarding their y positions
	 */
	public void setupChannels(){
		channels = new float[14];
		for(int i = 0; i < channels.length; i++){
			channels[i] = i*((height - floor.getHeight())/12);
		}
	}

	/**
	 * Ensure that the player does not leave the top or right side of the screen
	 */
	public void boundPlayer(){
		if(player.getPosition().x + player.getWidth() > width){
			player.getPosition().x = width - player.getWidth();
		}

//		if(player.getPosition().y + player.getHeight()*2 < 0){
//			player.getPosition().y =  -player.getHeight()*2;
//		}

//
//		if(player.getPosition().x + player.getWidth() > width){
//			player.setPosition(oldPlayerPosition.copy());
//		}
		if(player.getPosition().y + player.getHeight()*2 < 0){
			player.setPosition(oldPlayerPosition.copy());
			player.getPosition().y = player.getPosition().y - 50;
		}

	}






	/**
	 * Generate platforms ahead of the screen as long as platforms are being deleted
	 */
	public void generatePlatforms(){
		if(currentPlatforms.size() < 20) {
			int channelNumber = (int) random(0, 12);

			if (!currentPlatforms.isEmpty()) {
				Platform lastPlatform = currentPlatforms.get(currentPlatforms.size() - 1); //getting the last platform

				Platform p = new Platform(new PVector(lastPlatform.getPosition().x + random(100, 400), channels[channelNumber]), (int) random(200, 500), 50, channelNumber);
				currentPlatforms.add(p);
			}
		}
	}

	/**
	 * Speed up the game as get very close to the right wall
     */
	public void speedUpGame(){

		if(player.getPosition().x > 0.95*this.width){
			gameSpeed = gameSpeed + 0.1f;
			speedIncreasedBy  = speedIncreasedBy + 0.1f;
		}

		if(player.getPosition().x > 0.85*this.width){
			gameSpeed = gameSpeed +  0.01f;
			speedIncreasedBy  = speedIncreasedBy + 0.01f;
		}

		if(player.getPosition().x > 0.75*this.width){
			gameSpeed = gameSpeed + + 0.005f;
			speedIncreasedBy  = speedIncreasedBy + 0.005f;
		}else{
			gameSpeed = gameSpeed - speedIncreasedBy*0.7f;
			speedIncreasedBy = 0;
		}


	}

	public void generateOrbs(){
		if(currentOrbs.size() < 10){
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
	 */
	public void orbRendering(){
		Iterator<Orb> orbIterator= currentOrbs.iterator();
		while (orbIterator.hasNext()) {
			Orb orb = orbIterator.next();
			orb.draw();
			if(orb.touchingPlayer(player)){
				orbIterator.remove();
				player.pickupObject(orb);
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
		currentPlatforms.removeIf(platform -> platform.getPosition().x + platform.getWidth() < 0);
		currentOrbs.removeIf(orb -> orb.getPosition().x + orb.getWidth() < 0);

	}

	/**
	 * Increase the gameSpeed over time
	 */
	public void increaseGameSpeed(){
		if(gameSpeed <= 10) {
			gameSpeed = gameSpeed + 0.001f;
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

	public boolean checkHookCollisions() {
		for (Platform platform : currentPlatforms) {
			if (player.getHook().checkCollision(platform)) {
				return true;
			}
		}
		return false;
	}

	public void gameOver() {
		playing = false;
		currentMenu = "Over";
		currentPlatforms.clear();
		gameSpeed = 3f;
		currentOrbs.clear();
		setupPlatforms();
		score = 0;
		player.reset();
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
	private int hookCoolDownMillis = 0;
	private float hookCoolDownAngle = 0;
	
	public void mousePressed() {
		if(!playing) {
			menus.get(currentMenu).onClick(mouseX, mouseY);
		} else {
			if(hookCoolDownMillis == 0 || hookCoolDownMillis + 2000 <= millis()) {
				hookCoolDownMillis = millis();
				this.player.getHook().fire(new PVector(mouseX, mouseY));
				if (checkHookCollisions()) {
					setHookCoolDownAngle(0);
				}
			}
		}
	}

	public void mouseReleased() {
		this.player.getHook().release();
	}

	public void mouseMoved() {
		if(!playing) menus.get(currentMenu).onMouseMove(mouseX, mouseY);
	}

	public static void main(String[] args) {
		ProcessingRunner.run(new Main());
	}

	public float getHookCoolDownAngle() {
		return hookCoolDownAngle;
	}

	public void setHookCoolDownAngle(float hookCoolDownAngle) {
		this.hookCoolDownAngle = hookCoolDownAngle;
	}

}