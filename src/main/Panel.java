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
import main.SoundManager.Sounds;
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

	public SoundManager soundManager;

	// SCREEN DIMENSIONS

	private final int ORIGINAL_TILE_SIZE = 16;
	private final int SCALE = 5;
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

	public final int ROW = 7;
	public final int SCREEN_COL = 8;
	public final int LEVEL_COL = 24;

	public final int SCREEN_WIDTH = ROW * TILE_SIZE;
	public final int SCREEN_HEIGHT = SCREEN_COL * TILE_SIZE;

	// GAME FUNCTIONALITY VARIABLES

	private final int FPS = 60;

	public int gameScore;
	public int highScore;

	public int prevObjRowIndex = 0;

	private int speedIncrement = 2000;
	private int scoreIncrement = 1;

	private GameState gameState;
	public enum GameState {
		MENU,
		PLAY,
		PAUSE,
		DEATH
	}

	public String defaultPlayerSprite1 = "boyBehind1";
	public String defaultPlayerSprite2 = "boyBehind2";

	Panel() {

		soundManager = new SoundManager();
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

		setGameState(GameState.MENU);

		ui = new UIManager(this);

		camera = new Camera(this);

		highScore = getHighScore(highScore);

		cChecker = new CollisionChecker(this);

		player = new Player(this, keyHandler);

		soundManager.playMusic(Sounds.BGM_MENU.ordinal());
	}

	public void startGame(int mapNum) {

		tileManager = new TileManager(this, camera, mapNum);

		gameScore = 0;

		soundManager.stopMusic();			// Stop the menu music
		soundManager.playMusic(mapNum);		// The mapNum aligns with the position of the corresponding background music
		setGameState(GameState.PLAY);

	}

	public void gameDeath() {

		soundManager.stopMusic();
		setGameState(GameState.DEATH);

		if (gameScore > highScore) {

			setHighScore(Integer.toString(gameScore + 1));
			highScore = getHighScore(highScore);
		}

	}

	public void setHighScore(String strHighScore) {
		try {

			File highScoreFile = new File("res/ui/highScore.txt");

			FileWriter fileWriter = new FileWriter(highScoreFile, false);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			writer.write(strHighScore);

			writer.close();

		} catch (IOException e) {
			System.err.println("Error writing high score to file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public int getHighScore(int highScore) {

		try {
			FileReader fr = new FileReader("res/ui/highScore.txt");
			BufferedReader bR = new BufferedReader(fr);

			highScore = Integer.parseInt(bR.readLine());

			bR.close();

			return highScore;

		} catch (IOException e) {
			System.err.println("Error reading high score file: " + e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.println("Invalid high score format in file: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;

			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	private void update() {

		if (getGameState() == GameState.PLAY) {
			tileManager.update();
			player.update();

			gameScore += scoreIncrement;

			// SPEED RELATIVE TO SCORE CALCULATOR

			if (gameScore == speedIncrement) {
				camera.cameraSpeed++;
				speedIncrement *= 2;
				scoreIncrement += 0.5;
			}
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		if (getGameState() == GameState.MENU) {
			ui.draw(g2);
		} else {
			tileManager.draw(g2);
			ui.draw(g2);
			player.draw(g2);
		}

		g2.dispose();
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}
