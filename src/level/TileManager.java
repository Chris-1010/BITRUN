package level;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Camera;
import main.Panel;
import main.UtilityTool;

public class TileManager {

	private Panel gp;
	private Camera camera;
	private LevelManager lM;

	public Tile tile[];

	private int prevLevelTiles[][];
	private int newLevelTiles[][];
	public int allLevelTiles[][];

	private int tileNum;
	private int mapNum;
	private int worldX;
	private int worldY;
	private int screenX;
	private int screenY;

	public TileManager(Panel gp, Camera camera, int mapNum) {

		this.gp = gp;
		this.camera = camera;
		this.mapNum = mapNum;

		tile = new Tile[27];

		lM = new LevelManager(gp, this);

		generateStartLevel();

		setupTileImage();

	}

	public void generateStartLevel() {

		prevLevelTiles = new int[gp.SCREEN_COL * 2][gp.ROW];
		newLevelTiles = new int[gp.SCREEN_COL][gp.ROW];
		allLevelTiles = new int[gp.LEVEL_COL][gp.ROW];

		allLevelTiles = lM.generateStartLevel(allLevelTiles, mapNum);

	}

	public void generateNewLevel() {

		prevLevelTiles = lM.loadPrevLevelTiles(allLevelTiles, prevLevelTiles);
		newLevelTiles = lM.generateRandomLevel(allLevelTiles);
		allLevelTiles = lM.loadAllLevelTiles(allLevelTiles, newLevelTiles, prevLevelTiles);

	}

	public void setupTileImage() {

		// MAP 1 (+ 10)
		scaleTileImage(0, "/map1/Lava", false);
		scaleTileImage(1, "/map1/WoodLeft", false);
		scaleTileImage(2, "/map1/WoodMiddle", false);
		scaleTileImage(3, "/map1/WoodMiddle", false);
		scaleTileImage(4, "/map1/WoodMiddle", false);
		scaleTileImage(5, "/map1/WoodRight", false);
		scaleTileImage(6, "/map1/Lava", false);
		scaleTileImage(7, "/map1/barrel", true);
		scaleTileImage(8, "/map1/barrel2", true);

		// MAP 2 (+ 20)
		scaleTileImage(10, "/map2/Sky", false);
		scaleTileImage(11, "/map2/BrickLeft", false);
		scaleTileImage(12, "/map2/BrickMiddle", false);
		scaleTileImage(13, "/map2/BrickMiddle", false);
		scaleTileImage(14, "/map2/BrickMiddle", false);
		scaleTileImage(15, "/map2/BrickRight", false);
		scaleTileImage(16, "/map2/Sky", false);
		scaleTileImage(17, "/map2/DirtHole", true);
		scaleTileImage(18, "/map2/Rock", true);

		// MAP3 (+ 30)

	}

	public void scaleTileImage(int index, String imageName, boolean collision) {

		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.TILE_SIZE, gp.TILE_SIZE);
			tile[index].collision = collision;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {

		if (!(gp.player.collisionOn)) {
			camera.update();

			// ADDING - TILE SIZE TO REDUCE DROPPING OF FRAMES AT HIGHER SPEEDS
			// (TEMPORARY FIX)
			if (camera.cameraY <= gp.SCREEN_HEIGHT - (gp.TILE_SIZE)) {
				generateNewLevel();

				camera.cameraY = (gp.SCREEN_HEIGHT * 2) - (gp.TILE_SIZE);
			}
		}
	}

	public void draw(Graphics2D g2) {

		for (int i = 0; i < gp.ROW; i++) {
			for (int z = 0; z < gp.LEVEL_COL; z++) {
				tileNum = allLevelTiles[z][i] - 10;
				worldX = i * gp.TILE_SIZE;
				worldY = z * gp.TILE_SIZE;
				// MAPS HORIZONTAL POSITION IS CONSTANT (MAP DOES NOT MOVE IN THIS DIRECTION)
				screenX = worldX;
				// MAP DOES NOT MOVE RELATIVE TO PLAYER - IT MOVES RELATIVE TO CAMERA (VERTICAL)
				screenY = calculateScreenY(worldY);

				if (isTileVisible(worldY)) {
					g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				}
			}
		}
	}

	private int calculateScreenY(int worldY) {
		return worldY - camera.cameraY + gp.player.screenY;
	}

	private boolean isTileVisible(int worldY) {
		return worldY + gp.TILE_SIZE > camera.cameraY - gp.player.screenY
				&& worldY - gp.TILE_SIZE < camera.cameraY + gp.player.screenY;
	}
}
