package main;

import entity.Entity;

public class CollisionChecker {

	Panel gp;

	CollisionChecker(Panel gp) {

		this.gp = gp;

	}

	public void checkTile(Entity entity) {

		int tileNum = gp.tileManager.allLevelTiles[(gp.camera.cameraY + (entity.solidArea.y * 2))
				/ gp.TILE_SIZE][entity.screenX / gp.TILE_SIZE];

		if (gp.tileManager.tile[tileNum - (10)].collision) {
			entity.collisionOn = true;

			gp.gameDeath();
		}
	}
}
