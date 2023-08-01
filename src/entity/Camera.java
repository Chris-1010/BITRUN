package entity;

import main.Panel;

public class Camera {
	
	public int cameraSpeed = 6;
	public final int cameraX = 5;
	public int cameraY;
	
	public Camera(Panel gp) {
		
		// DEFAULT CAMERA
		cameraY = gp.SCREEN_HEIGHT * 2;
	}
	
	public void update() {
		
		cameraY -= cameraSpeed;
		
	}

}
