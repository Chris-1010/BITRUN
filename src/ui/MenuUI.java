package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.InputStream;

public class MenuUI {
	
	Graphics2D g2;
	UIManager uiM;
	Font gameFont;
	String text;
	int x;
	int y;
	
	public int menuCommandNum = 0;
	
	public int menuState;
	
	public final int MAIN_MENU_STATE = 0;
	public final int PICK_LEVEL_MENU_STATE = 1;
	public final int SETTINGS_MENU_STATE = 2;
	public final int CHARACTER_MENU_STATE = 3;
	
	MenuUI(UIManager uiM) {
		
		this.uiM = uiM;
		
		InputStream iS = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
		try {
			gameFont = Font.createFont(Font.TRUETYPE_FONT, iS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		menuState = MAIN_MENU_STATE;
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		if(menuState == MAIN_MENU_STATE) {
			drawMainMenuState();
		}
		else if(menuState == PICK_LEVEL_MENU_STATE) {
			drawPickLevelState();
		}
		else if(menuState == SETTINGS_MENU_STATE) {
			drawSettingsMenuState();
		}
		else if(menuState == CHARACTER_MENU_STATE)
			drawCharacterMenuState();
	}

	public void drawMainMenuState() {
		
		g2.setFont(gameFont);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 120f));
		
		text = "BIT RUN";	
		x = uiM.getXForCenteredText(text);
		y = uiM.gp.TILE_SIZE + 35;
		
		// DRAW GAME NAME
		
		g2.setColor(Color.lightGray);
		
		g2.drawString(text, x+5, y+5);
		
		g2.setColor(Color.white);
		
		g2.drawString(text, x, y);	
		
		// DRAW SPRITE ICON
		
		x = uiM.gp.SCREEN_WIDTH / 2 -(uiM.gp.TILE_SIZE*2) / 2;
		y+= uiM.gp.TILE_SIZE / 2;
		
		g2.drawImage(uiM.gp.player.behind1, x, y, uiM.gp.TILE_SIZE*2, uiM.gp.TILE_SIZE*2, null);
		
		// NEW GAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		text = "PICK MAP";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE*4 - 50;
		
		g2.drawString(text, x, y);
		
		if(menuCommandNum == 0) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
		
		// SETTINGS
		
		text = "SETTINGS";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE - 25;
		
		g2.drawString(text, x, y);
		
		if(menuCommandNum == 1) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
		
		// QUIT	
		
		text = "QUIT";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE - 25;
		
		g2.drawString(text, x, y);
		
		if(menuCommandNum == 2) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
		
		//HIGHSCORE

		g2.setColor(Color.red);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
		text = "HIGHSCORE: " + uiM.gp.highScore;	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE - 10;
	
		g2.drawString(text, x, y);
		
	}
	
	public void drawPickLevelState() {
		
		g2.setFont(gameFont.deriveFont(Font.BOLD, 80f));
		g2.setColor(Color.LIGHT_GRAY);
		
		text = "PICK MAP";	
		x = uiM.getXForCenteredText(text);
		y = uiM.gp.TILE_SIZE + 35;
		
		// DRAW PICK MAP TITLE
		
		g2.drawString(text, x, y);	
		
		// MAP 1
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		text = "MAP 1";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE*4 - 50;
				
		g2.drawString(text, x, y);
				
		if(menuCommandNum == 0) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
				
		// MAP 2
				
		text = "MAP 2";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE - 25;
				
		g2.drawString(text, x, y);
				
		if(menuCommandNum == 1) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
				
		// BACK	
				
		text = "BACK";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE + 50;

		g2.drawString(text, x, y);
				
		if(menuCommandNum == 2) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
		
	}
	
	public void drawSettingsMenuState() {
		
		g2.setFont(gameFont.deriveFont(Font.BOLD, 80f));
		g2.setColor(Color.LIGHT_GRAY);
		
		text = "SETTINGS";	
		x = uiM.getXForCenteredText(text);
		y = uiM.gp.TILE_SIZE + 35;
		
		// DRAW SETTINGS NAME
		
		g2.drawString(text, x, y);	
		
		// CHARACTER
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		text = "CHARACTER";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE*4 - 50;
				
		g2.drawString(text, x, y);
				
		if(menuCommandNum == 0) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
				
		// CONTROLS
				
		text = "CONTROLS";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE - 25;
				
		g2.drawString(text, x, y);
				
		if(menuCommandNum == 1) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
				
		// BACK	
				
		text = "BACK";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE + 50;

		g2.drawString(text, x, y);
				
		if(menuCommandNum == 2) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
		
	}
	
	public void drawCharacterMenuState() {
		
		g2.setFont(gameFont.deriveFont(Font.BOLD, 80f));
		g2.setColor(Color.LIGHT_GRAY);
		
		text = "CHARACTER";	
		x = uiM.getXForCenteredText(text);
		y = uiM.gp.TILE_SIZE + 35;
		
		// DRAW CHARACTER NAME
		
		g2.drawString(text, x, y);	
		
		// BOY
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		text = "BOY";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE*4 - 50;
				
		g2.drawString(text, x, y);
				
		if(menuCommandNum == 0) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
				
		// GIRL
				
		text = "GIRL";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE - 25;
				
		g2.drawString(text, x, y);
				
		if(menuCommandNum == 1) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
				
		// BACK	
				
		text = "BACK";	
		x = uiM.getXForCenteredText(text);
		y+= uiM.gp.TILE_SIZE + 50;

		g2.drawString(text, x, y);
				
		if(menuCommandNum == 2) {
			g2.drawString(">", x-uiM.gp.TILE_SIZE /2, y);
		}
		
	}

}
