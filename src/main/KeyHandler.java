package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean left, right;
	
	Panel gp;
	
	public KeyHandler(Panel gp) {
		
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(gp.gameState == gp.MENU_STATE) {
			checkMenuKeyEvents(e);
		}
		
		if(gp.gameState == gp.PLAY_STATE) {
			if(code == KeyEvent.VK_A) {
				left = true;
			}
			if(code == KeyEvent.VK_D) {
				right = true;
			}	
		}
		
		if(code == KeyEvent.VK_ESCAPE) {
			if(gp.gameState == gp.PLAY_STATE) {
				gp.gameState = gp.PAUSE_STATE;
			}
			else if (gp.gameState == gp.PAUSE_STATE) {
				gp.gameState = gp.PLAY_STATE;
			}
			else if(gp.gameState == gp.DEATH_STATE) {
				gp.loadMenu();
			}
		}
	}

	private void checkMenuKeyEvents(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		
 
		if(code == KeyEvent.VK_UP) {
			gp.ui.menuUI.menuCommandNum --;
			if(gp.ui.menuUI.menuCommandNum < 0) {
				gp.ui.menuUI.menuCommandNum = 2 ;
			}
		}
		if(code == KeyEvent.VK_DOWN) {
			gp.ui.menuUI.menuCommandNum ++;
			if(gp.ui.menuUI.menuCommandNum > 2) {
				gp.ui.menuUI.menuCommandNum = 0 ;
			}
		}
		
		if(gp.ui.menuUI.menuState == gp.ui.menuUI.MAIN_MENU_STATE) {
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.menuUI.menuCommandNum == 0) {
					gp.ui.menuUI.menuState = gp.ui.menuUI.PICK_LEVEL_MENU_STATE;
				}
				else if(gp.ui.menuUI.menuCommandNum == 1) {
					gp.ui.menuUI.menuState = gp.ui.menuUI.SETTINGS_MENU_STATE;
				}
				else {
					System.exit(0);
				}
			}
		}
		else if(gp.ui.menuUI.menuState == gp.ui.menuUI.PICK_LEVEL_MENU_STATE) {
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.menuUI.menuCommandNum == 0) {
					gp.startGame(1);
				}
				else if(gp.ui.menuUI.menuCommandNum == 1) {
					gp.startGame(2);
				}
				else {
					gp.ui.menuUI.menuState = gp.ui.menuUI.MAIN_MENU_STATE;
				}
			}
		}
		else if(gp.ui.menuUI.menuState == gp.ui.menuUI.SETTINGS_MENU_STATE) {
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.menuUI.menuCommandNum == 0) {
					gp.ui.menuUI.menuState = gp.ui.menuUI.CHARACTER_MENU_STATE;
				}
				else if(gp.ui.menuUI.menuCommandNum == 1) {
					System.out.println("your dumb...");
				}
				else {
					gp.ui.menuUI.menuState = gp.ui.menuUI.MAIN_MENU_STATE;
				}
			}
		}
		else if(gp.ui.menuUI.menuState == gp.ui.menuUI.CHARACTER_MENU_STATE) {
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.menuUI.menuCommandNum == 0) {
	
					gp.defaultPlayerSprite1 = "boyBehind1";
					gp.defaultPlayerSprite2 = "boyBehind2";
					gp.player.setPlayerImage(gp.defaultPlayerSprite1, gp.defaultPlayerSprite2);
				}
				else if(gp.ui.menuUI.menuCommandNum == 1) {
					gp.defaultPlayerSprite1 = "girlBehind1";
					gp.defaultPlayerSprite2 = "girlBehind2";
					gp.player.setPlayerImage(gp.defaultPlayerSprite1, gp.defaultPlayerSprite2);
				}
				else {
					gp.ui.menuUI.menuState = gp.ui.menuUI.SETTINGS_MENU_STATE;
				}
			}
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
