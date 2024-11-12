package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.InputStream;

import main.Panel;

public class UIManager {

	Graphics2D g2;

	Panel gp;
	Font gameFont;
	String text;
	int x;
	int y;
	public MenuUI menuUI;

	public UIManager(Panel gp) {
		this.gp = gp;

		menuUI = new MenuUI(this);

		InputStream iS = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
		try {
			gameFont = Font.createFont(Font.TRUETYPE_FONT, iS);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		switch (gp.getGameState()) {
			case MENU:
				menuUI.draw(g2);
				break;
			case PLAY:
				drawPlayState();
				break;
			case PAUSE:
				drawPlayState();
				drawPauseState();
				break;
			case DEATH:
				drawDeathState();
				break;

		}
	}

	public void drawPlayState() {

		g2.setFont(gameFont.deriveFont(Font.BOLD, 40f));
		g2.setColor(Color.white);
		text = "Score: " + gp.gameScore;

		x = (30);
		y = (50);

		g2.drawString(text, x, y);
	}

	public void drawPauseState() {

		g2.setFont(gameFont);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));
		g2.setColor(Color.white);

		text = "PAUSED";
		x = getXForCenteredText(text);
		y = (gp.SCREEN_HEIGHT / 2);

		g2.drawString(text, x, y);
	}

	public void drawDeathState() {

		g2.setFont(gameFont);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));
		g2.setColor(Color.white);

		String text = "GAME OVER";
		x = getXForCenteredText(text);
		y = (gp.SCREEN_HEIGHT / 2) - gp.TILE_SIZE;

		g2.drawString(text, x, y);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

		text = "YOUR SCORE WAS:" + gp.gameScore;
		x = getXForCenteredText(text);
		y = (gp.SCREEN_HEIGHT / 2);

		g2.drawString(text, x, y);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42f));

		text = "MAIN MENU: PRESS ESC";
		x = getXForCenteredText(text);
		y = (gp.SCREEN_HEIGHT / 2 + (gp.TILE_SIZE * 3));

		g2.drawString(text, x, y);
	}

	public int getXForCenteredText(String inputText) {

		return (gp.SCREEN_WIDTH / 2) - ((int) g2.getFontMetrics().getStringBounds(inputText, g2).getWidth()) / 2;
	}
}
