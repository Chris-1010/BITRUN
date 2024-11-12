package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	public int screenX;
	public int screenY;
	public int worldX;
	public int worldY;
	public int speed;

	public boolean collisionOn = false;

	public BufferedImage behind1;
	public BufferedImage behind2;

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle solidArea;

}
