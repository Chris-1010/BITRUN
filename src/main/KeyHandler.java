package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ui.MenuUI.MenuState;
import main.Panel.GameState;
import main.SoundManager.Sounds;

public class KeyHandler implements KeyListener {

	private SoundManager soundManager;

	public boolean left, right;

	Panel gp;

	public KeyHandler(Panel gp) {

		soundManager = gp.soundManager;
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (gp.getGameState() == GameState.MENU) {
			checkMenuKeyEvents(e);
		}

		if (gp.getGameState() == GameState.PLAY) {
			if (code == KeyEvent.VK_A) {
				left = true;
			}
			if (code == KeyEvent.VK_D) {
				right = true;
			}
		}

		if (code == KeyEvent.VK_ESCAPE) {
			if (gp.getGameState() == GameState.PLAY)
				gp.setGameState(GameState.PAUSE);
			else if (gp.getGameState() == GameState.PAUSE)
				gp.setGameState(GameState.PLAY);
			else if (gp.getGameState() == GameState.DEATH)
				gp.loadMenu();
		}
	}

	private void checkMenuKeyEvents(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP) {
			soundManager.playMusic(Sounds.MENU_MOVE.ordinal());
			gp.ui.menuUI.menuCommandNum--;
			if (gp.ui.menuUI.menuCommandNum < 0) {
				if (gp.ui.menuUI.getMenuState() == MenuState.SETTINGS) {
					gp.ui.menuUI.menuCommandNum = 3;
				} else {
					gp.ui.menuUI.menuCommandNum = 2;
				}
			}
		}
		if (code == KeyEvent.VK_DOWN) {
			soundManager.playMusic(Sounds.MENU_MOVE.ordinal());
			gp.ui.menuUI.menuCommandNum++;
			if ((gp.ui.menuUI.getMenuState() == MenuState.SETTINGS && gp.ui.menuUI.menuCommandNum > 3)
					|| (gp.ui.menuUI.getMenuState() != MenuState.SETTINGS && gp.ui.menuUI.menuCommandNum > 2))
				gp.ui.menuUI.menuCommandNum = 0;
		}

		if (code == KeyEvent.VK_ENTER) {
			switch (gp.ui.menuUI.getMenuState()) {
				case MAIN_MENU:
					soundManager.playMusic(Sounds.MENU_SELECT.ordinal());
					if (gp.ui.menuUI.menuCommandNum == 0) {
						gp.ui.menuUI.setMenuState(MenuState.PICK_LEVEL);
					} else if (gp.ui.menuUI.menuCommandNum == 1) {
						gp.ui.menuUI.setMenuState(MenuState.SETTINGS);
					} else {
						System.exit(0);
					}
					gp.ui.menuUI.menuCommandNum = 0;
					break;
				case PICK_LEVEL:
					if (gp.ui.menuUI.menuCommandNum == 0) {
						gp.startGame(1);
					} else if (gp.ui.menuUI.menuCommandNum == 1) {
						gp.startGame(2);
					} else {
						gp.ui.menuUI.setMenuState(MenuState.MAIN_MENU);
					}
					break;
				case SETTINGS:
					if (gp.ui.menuUI.menuCommandNum == 0) {
						soundManager.playMusic(Sounds.MENU_SELECT.ordinal());
						gp.ui.menuUI.setMenuState(MenuState.CHARACTER);
						gp.ui.menuUI.menuCommandNum = 0;
					} else if (gp.ui.menuUI.menuCommandNum == 1) {
						soundManager.toggleAudio();
					} else if (gp.ui.menuUI.menuCommandNum == 2) {
						soundManager.playMusic(Sounds.MENU_SELECT.ordinal());
						System.out.println("you're dumb...");
					} else {
						soundManager.playMusic(Sounds.MENU_SELECT.ordinal());
						gp.ui.menuUI.setMenuState(MenuState.MAIN_MENU);
						gp.ui.menuUI.menuCommandNum = 0;
					}
					break;
				case CHARACTER:
					soundManager.playMusic(Sounds.MENU_SELECT.ordinal());
					if (gp.ui.menuUI.menuCommandNum == 0) {
						gp.defaultPlayerSprite1 = "boyBehind1";
						gp.defaultPlayerSprite2 = "boyBehind2";
						gp.player.setPlayerImage(gp.defaultPlayerSprite1, gp.defaultPlayerSprite2);
					} else if (gp.ui.menuUI.menuCommandNum == 1) {
						gp.defaultPlayerSprite1 = "girlBehind1";
						gp.defaultPlayerSprite2 = "girlBehind2";
						gp.player.setPlayerImage(gp.defaultPlayerSprite1, gp.defaultPlayerSprite2);
					}
					gp.ui.menuUI.setMenuState(MenuState.SETTINGS);
					gp.ui.menuUI.menuCommandNum = 0;
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
