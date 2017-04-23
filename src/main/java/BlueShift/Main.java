package BlueShift;

import BlueShift.audio.Audio;
import BlueShift.entity.Animation;
import BlueShift.entity.Hook;
import BlueShift.entity.LeftWall;
import BlueShift.entity.Orb;
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
	private static final int MAX_GAME_SPEED = 20;
	private final Map<Character, Move> keyBinds = new HashMap<>(4);
	private Map<String, Menu> menus = new HashMap<>();
	private Audio[] running = new Audio[7];
	private Audio death;
	private Audio menu;
	private Audio currentRun;
	private List<Platform> currentPlatforms = new ArrayList<>();
	private List<Orb> currentOrbs = new ArrayList<>();
	private boolean[] keyPressed = new boolean[4];
	private boolean playing = false;
	private String currentMenu = "Main";
	public static Main instance;
	public float gameSpeed = 3f;
	public Floor floor;
	private static final int HOOK_COOL_DOWN = 100;
	private float hookCoolDownAngle = 0;
	private boolean coolingDown = false;

	//entities
	private LeftWall leftWall;
	public Player player;
	public double score = 0;
	private float speedIncreasedBy = 0;

	//channel
	public float channels[];

	public PVector oldPlayerPosition;
	public PVector[] oldPositions;
	boolean posSet = false;
	int lastIndex = 0;
	int startTrail = 0;
	private int hookTimer = 1000;

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
		Runnable play = () -> {
			playing = true;
			currentRun.stopSound();
			currentRun = running[0];
			currentRun.playSound(true);
		};
		menus.put("Main", new Menu("Blue Shift", new Button("Play", buttonBlue, play),
				new Button("Quit", buttonBlue, this::exit)));
		menus.put("Over", new Menu("Game Over!", new Button("Restart", buttonBlue, play),
				new Button("Quit", buttonBlue, this::exit)));
		Player.rightRunningSprite = new Animation(player, "player\\right\\f", 13);
		Player.leftRunningSprite = new Animation(player, "player\\left\\f", 13);
		
		Player.rightGrapplingSprite = loadImage("player\\right\\grapple1.png");
		Player.leftGrapplingSprite = loadImage("player\\left\\grapple1.png");
		
		Player.rightJumpingSprite = loadImage("player\\right\\jump1.png");
		Player.leftJumpingSprite = loadImage("player\\left\\jump1.png");
		
		Player.rightStandingSprite = loadImage("player\\right\\standing1.png");
		Player.leftStandingSprite = loadImage("player\\left\\standing1.png");

		player.setCurrentSprite(Player.rightStandingSprite);
		LeftWall.sprite = new Animation(null, "tentacles\\f", 27);
		Floor.sprite = loadImage("floor.png");
		Hook.sprite = loadImage("hook.png");
		Platform.sprite = loadImage("platform.png");
		leftWall.setupSprite();
		//Load in audio files
		for (int i = 0; i < running.length; i++) {
			running[i] = new Audio(String.format("audio\\run_%d.wav", i+1));
		}
		death = new Audio("audio\\death.wav");
		menu = new Audio("audio\\title.wav");
		currentRun = menu;
		//adding starting platforms
		setupPlatforms();
		setupChannels();
		oldPositions = new PVector[10];
		currentRun.playSound(true);
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
		int blue = player.getBlueOrbsCollected()*5;
		int red = player.getRedOrbsCollected()*5;
		if(blue - red < 0){
			background(abs(blue-red), 0, 0);
		}else{
			if(blue - red > 255){
				blue = 255;
			}
			background(0, 0, blue);
		}

		if (playing) {
			storePlayerPositions();
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
			speedUpGame();
			leftWall.draw();
			fill(255, 255, 255);
			text(frameRate, 60, 60);
			text("Score: " + ((int) score + player.getBlueOrbsCollected()), width / 2, 60);
			score = score + 0.01;
			if (!player.getHook().isHooked() && coolingDown) {
				setHookCoolDownAngle((float) Math.min(Math.PI * 2, getHookCoolDownAngle() + Math.PI/45));
				hookTimer++;
			}	

			if(player.getPosition().y + player.getHeight() > this.height){
				gameOver();
			}
			drawTrail();

		} else {
			cursor(CROSS);
			menus.get(currentMenu).draw();
		}
	}

	public void drawTrail(){
		if(player.isMoving()) {
			int i = startTrail;
			int a = 0;
			while (i < lastIndex) { //but also do the ones which are out of bounds
				if (oldPositions[i] != null) {
					//fill(0, 100, 25 * i);
					//ellipse(oldPositions[i].x - 20, oldPositions[i].y + player.getHeight(), 10, 10);
					tint(255, (a+1)*10);
					player.draw(oldPositions[i]);
					tint(255);
				}
				i++;
				a++;
			}

			if(lastIndex < startTrail){
				for(int j = lastIndex; j < startTrail; j++){
					//fill(0, 100, 25 * j);
					//ellipse(oldPositions[i].x - 20, oldPositions[i].y + player.getHeight(), 10, 10);
					tint(255, (a+1)*10);
					player.draw(oldPositions[j]);
					tint(255);
					a++;
				}
			}
		}
	}

	public void storePlayerPositions(){
		//setting up old player positions for storage
		oldPlayerPosition = player.getPosition().copy();
		if(!posSet) {
			for (int i = 0; i < oldPositions.length; i++) {
				if(oldPositions[i] == null){
					oldPositions[i] = player.getPosition().copy();
					break;
				}
			}
			if(oldPositions[oldPositions.length-1] != null){
				posSet = true;
			}
		}else{
			oldPositions[lastIndex] = player.getPosition().copy();
			lastIndex++;
			if(lastIndex >= oldPositions.length){
				startTrail++;
				lastIndex = 0;
			}
			if(startTrail >= oldPositions.length){
				startTrail = 0;
			}
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
	 * Generate platforms ahead of the screen as long as platforms are being deleted
	 */
	public void generatePlatforms(){
		if(currentPlatforms.size() < 20) {
			int channelNumber = (int) (random(1, 12));
			while (channelNumber % 2 != 0) {
				channelNumber = (int) (random(1, 12));
			}

			if (!currentPlatforms.isEmpty()) {
				Platform lastPlatform = currentPlatforms.get(currentPlatforms.size() - 1); //getting the last platform

				Platform p = new Platform(new PVector(lastPlatform.getPosition().x + random(100, 400), 
						channels[channelNumber]), 
						(int) random(200, 500), 
						50, 
						channelNumber);
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
		if(gameSpeed <= MAX_GAME_SPEED) {
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
		currentRun.stopSound();
		death.playSound(false);
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
			player.setMoving(true);
			keyPressed[pressed.ordinal()] = true;
		}
	}

	public void keyReleased() {
		Move released = keyBinds.get(key);
		if(released != null) {
			keyPressed[released.ordinal()] = false;
			if (!player.getHook().isHooked()) {
				player.setMoving(false);
				for (boolean b : keyPressed) {
					if (b) {
						player.setMoving(true);
					}
				}
			}
			player.released(released);
		}
	}
	public void mousePressed() {
		if(!playing) {
			menus.get(currentMenu).onClick(mouseX, mouseY);
		} else {
			if(hookTimer >= HOOK_COOL_DOWN) {
				this.player.getHook().fire(new PVector(mouseX, mouseY));
				if (checkHookCollisions()) {
					setHookCoolDownAngle(0);
				}
			}
		}
	}

	public void mouseReleased() {
		if (player.getHook().isHooked()) {
			coolingDown  = true;
			hookTimer = 0;
		}
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