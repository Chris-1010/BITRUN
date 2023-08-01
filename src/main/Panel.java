package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;

import entity.Camera;
import entity.Player;
import level.TileManager;
import ui.UIManager;

public class Panel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// GAME OBJECTS
	
	private Thread gameThread;
	
	public TileManager tileManager;
	
	public CollisionChecker cChecker;
	
	public UIManager ui;
	
	KeyHandler keyHandler;
	public Player player;
	public Camera camera;
	
	// SCREEN DIMENSIONS
	
	private final int ORIGINAL_TILE_SIZE = 16;
	private final int SCALE = 5;
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

	public final int ROW = 7;
	public final int SCREEN_COL = 8;
	public final int LEVEL_COL = 24;
	
	public final int SCREEN_WIDTH = ROW * TILE_SIZE;
	public final int SCREEN_HEIGHT = SCREEN_COL* TILE_SIZE;
	
	// GAME FUNCTIONALITY VARIABLES
	
	private final int FPS = 60;
	
	public int gameScore;
	public int highScore;
	
	public int prevObjRowIndex = 0;
	
	private int speedIncrement = 2000;
	private int scoreIncrement = 1;
	
	public int gameState;
	
	public final int MENU_STATE = 0;
	public final int PLAY_STATE = 1;
	public final int PAUSE_STATE = 2;
	public final int DEATH_STATE = 3;
	
	public String defaultPlayerSprite1 = "boyBehind1";
	public String defaultPlayerSprite2 = "boyBehind2";
	
	Panel() {
		
		keyHandler = new KeyHandler(this);
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.addKeyListener(keyHandler);	
		this.setFocusable(true);
		this.setBackground(Color.black);	
		this.setDoubleBuffered(true);
		
		loadMenu();		
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void loadMenu() {
		
		gameState = MENU_STATE;
		
		ui = new UIManager(this);
		
		camera = new Camera(this);
		
		highScore = getHighScore(highScore);
		
		cChecker = new CollisionChecker(this);
		
		player = new Player(this, keyHandler);
	}
	
	public void startGame(int mapNum) {
		
		tileManager = new TileManager(this, camera, mapNum);
		
		gameScore = 0;
		
		gameState = PLAY_STATE;

	}
	
	public void gameDeath() {
		
		gameState = DEATH_STATE;
		
		if(gameScore > highScore) {
			
			setHighScore(Integer.toString(gameScore + 1));		
			highScore = getHighScore(highScore);
		}
		
	}
	
	public void setHighScore(String strHighScore) {
		try {
			
			File highScoreFile = new File("C:\\Users\\alber\\eclipse-workspace\\BIT_RUN_V_1\\res\\ui\\highScore.txt");
			
			FileWriter fileWriter = new FileWriter(highScoreFile, false);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			writer.write(strHighScore);
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public int getHighScore(int highScore) {

		try {
			FileReader fr = new FileReader("C:\\Users\\alber\\eclipse-workspace\\BIT_RUN_V_1\\res\\ui\\highScore.txt");
			BufferedReader bR = new BufferedReader(fr);
			
			highScore = Integer.parseInt(bR.readLine());
			
			bR.close();
			
			return highScore;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    		
		return 0;
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();		
				delta --;	
			}
		}		
	}
	
	private void update() {
		
		if(gameState == PLAY_STATE) {
			tileManager.update();
			player.update();
			
			gameScore += scoreIncrement;
			
			// SPEED RELATIVE TO SCORE CALCULATOR

			if(gameScore == speedIncrement) {
				camera.cameraSpeed ++;
				speedIncrement *= 2;
				scoreIncrement += 0.5;
			}		
		}
	}
	
	public void paintComponent(Graphics g) {
	
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState == MENU_STATE) {
			ui.draw(g2);
		}
		else {
			tileManager.draw(g2);		
			ui.draw(g2);
			player.draw(g2);
		}
		
		g2.dispose();	
	}
}
