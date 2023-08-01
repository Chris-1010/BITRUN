package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.KeyHandler;
import main.Panel;
import main.UtilityTool;

public class Player extends Entity{
	
	Panel gp;
	KeyHandler keyH;
	UtilityTool uTool;
	
	public Player(Panel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setOriginalValues();
		setPlayerImage(gp.defaultPlayerSprite1, gp.defaultPlayerSprite2);
		
	}

	private void setOriginalValues() {
		
		speed = gp.TILE_SIZE; 
		
		worldX = (gp.SCREEN_WIDTH - gp.TILE_SIZE) / 2;
		worldY = gp.SCREEN_HEIGHT*2;
		
		screenX = (gp.SCREEN_WIDTH - gp.TILE_SIZE) / 2;
		screenY = (gp.SCREEN_HEIGHT / 2) + gp.TILE_SIZE;
		
	}
	
	public void setPlayerImage(String fileName1, String fileName2) {
		
		uTool = new UtilityTool();
		
		behind1 = uTool.loadImage("/playerSprites/" + fileName1 + ".png",  gp.TILE_SIZE);
		
		behind2 = uTool.loadImage("/playerSprites/" + fileName2 + ".png", gp.TILE_SIZE);
		
	}
	
	public void update() {
		
		collisionOn = false;
		
		gp.cChecker.checkTile(this);
		
		if(!(collisionOn)) {
			if(keyH.left) {
				if(screenX > gp.TILE_SIZE *2) {
					screenX -= speed;
				}
			}
			if(keyH.right) {
				if(screenX < gp.SCREEN_WIDTH - gp.TILE_SIZE * 3) {
					screenX += speed;
				}
			}
			keyH.left = false;
			keyH.right = false;
				spriteCounter ++;
				if(spriteCounter > 80 /gp.camera.cameraSpeed) {
					if(spriteNum == 1) {
						spriteNum = 2;
					}
					else {
						spriteNum = 1;
					}
					spriteCounter = 0;
			}
		}
		
}
	
	public void draw(Graphics2D g2) {	
		
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			image = behind1;
		}
		else {
			image = behind2;
		}
		
		g2.drawImage(image, screenX, screenY, null);
		
	}
}
