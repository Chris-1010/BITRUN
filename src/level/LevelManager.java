package level;

import java.util.Random;

import main.Panel;

public class LevelManager {
	
	Panel gp;
	Random random;
	TileManager tM;
	
	private int tileAddition;
	
	private int col;
	private int row;

	LevelManager(Panel gp, TileManager tM) {
		
		random = new Random();		
		this.gp = gp;
		this.tM = tM;	
		
		col = gp.LEVEL_COL;
		row = gp.ROW;

	}
	
	public int[][] generateStartLevel(int[][] tiles , int mapNum) {

		tileAddition = 10 * mapNum;

		
		for(int i = 0; i <col; i++) {
			
			for(int z = 0; z < row; z++) {
						
				switch(z) {
				case 0:
					tiles[i][z] = 0 + tileAddition;
					break;
				case 1:
					tiles[i][z] = 1 + tileAddition;
					break;
				case 2:
					tiles[i][z] = 2 + tileAddition;
					break;
				case 3:
					tiles[i][z] = 3 + tileAddition;
					break;
				case 4:
					tiles[i][z] = 4 + tileAddition;
					break;
				case 5:
					tiles[i][z] = 5 + tileAddition;
					break;
				case 6:
					tiles[i][z] = 6 + tileAddition;
					break;
				}
			}
		}
		return tiles;
	}
	
	public int[][] generateRandomLevel(int[][] tiles) {
		
		for(int i = 0; i <col; i++) {
			
			for(int z = 0; z < row; z++) {
						
				switch(z) {
				case 0:
					tiles[i][z] = 0 + tileAddition;
					break;
				case 1:
					tiles[i][z] = 1 + tileAddition;
					break;
				case 2:
					tiles[i][z] = 2 + tileAddition;
					break;
				case 3:
					tiles[i][z] = 3 + tileAddition;
					break;
				case 4:
					tiles[i][z] = 4 + tileAddition;
					break;
				case 5:
					tiles[i][z] = 5 + tileAddition;
					break;
				case 6:
					tiles[i][z] = 6 + tileAddition;
					break;
				}
			}
		}
		generateObjects(tiles, tileAddition);		
		
		return tiles;		
	}
	
	private void generateObjects(int[][] tiles, int tileAddition) {
		
		int i = 7;
		int currentRowIndex = random.nextInt(2, 5);
		
		while(i > 0) {
			while(currentRowIndex == gp.prevObjRowIndex) {
				currentRowIndex = random.nextInt(2, 5);
			} 
			
			gp.prevObjRowIndex = currentRowIndex;
			tiles[i][currentRowIndex] = random.nextInt(7 + tileAddition, 9 + tileAddition);
			
			i-=2;
		}
	}
	
	public int[][] loadPrevLevelTiles(int[][] allTiles, int[][] prevTiles) {	
		for(int i = 0; i<gp.SCREEN_COL*2; i++) {
			for(int z = 0; z < gp.ROW; z++) {
				prevTiles[i][z] = allTiles[i][z];
			}
		}		
		return prevTiles;		
	}
	
	public int[][] loadAllLevelTiles(int[][] allTiles, int[][] newTiles, int[][] prevTiles) {
		for(int i = 0; i<gp.LEVEL_COL;i++) {		
			for(int z=0; z<gp.ROW; z++) {
				if(i < gp.SCREEN_COL) {
					allTiles[i][z] = newTiles[i][z];
				}
				else {
					allTiles[i][z] = prevTiles[i-gp.SCREEN_COL][z];
				}
			}
		}
		return allTiles;		
	}
}
