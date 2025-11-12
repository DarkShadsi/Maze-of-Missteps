package com.shadow.maze.util;

import java.util.Random;

import com.shadow.maze.model.MON_RedSlime;
import com.shadow.maze.model.OBJ_Heart;
import com.shadow.maze.model.OBJ_Mud;
import com.shadow.maze.model.OBJ_Shield_Blue;
import com.shadow.maze.model.OBJ_SpikeTrap;
import com.shadow.maze.model.Object;
import com.shadow.maze.view.GamePanel;

public class AssetSetter {
	GamePanel gp;
	Random randomizer = new Random();
	int mapAreas[][];
	int unitWidth, unitHeight;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		this.unitWidth = gp.gameFrame.GAMEUNITWIDTH;
		this.unitHeight = gp.gameFrame.GAMEUNITHEIGHT;
	}
	
	public void setObjects() {
		mapAreas = gp.pHandler.mapAreas;
		int currLevel = 0;
		
		//LEVEL 1 MONSTERS
		randomizeMonsterLoc(currLevel);
		//LEVEL 1 POWER-UPS
		randomizePowerUps(currLevel);
		//LEVEL 1 DEBUFFS
		randomizeDebuffs(currLevel);
		
		
		
		currLevel++;
		//LEVEL 2 MONSTERS
		randomizeMonsterLoc(currLevel);
		//LEVEL 2 POWER-UPS
		randomizePowerUps(currLevel);
		//LEVEL 2 DEBUFFS
		randomizeDebuffs(currLevel);
	}
	
	public void placeObject(Object obj, int x, int y, int currLevel) {
		gp.obj[currLevel][gp.currObjIndex[currLevel]] = obj;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldX = x*unitWidth;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldY = y*unitHeight;
	}
	
	void randomizeMonsterLoc(int currLevel) {
		int x, y;
		int tileNum;
		
		for(int count = 0; count < 3; count++) {
			do {
				x = randomizer.nextInt(mapAreas[currLevel][1] - mapAreas[currLevel][0]) + mapAreas[currLevel][0];
				y = randomizer.nextInt(mapAreas[currLevel][3] - mapAreas[currLevel][2]) + mapAreas[currLevel][2];
				tileNum = gp.tileM.mapTileNum[gp.currentMap][x][y];
			}while(gp.tileM.tile[tileNum].collision);
			System.out.println(x + " " + y);
			gp.monsters[currLevel][count] = new MON_RedSlime(gp);
			gp.monsters[currLevel][count].worldX = x*unitWidth;
			gp.monsters[currLevel][count].worldY = y*unitHeight;
		}
	}
	
	void randomizePowerUps(int currLevel) {
		int x, y;
		int tileNum;
		
		int numberOfPowerUps = randomizer.nextInt(3) + 2;
		int powerUp = 0;
		for(int i = 0; i < numberOfPowerUps; i++) {
			do {
				x = randomizer.nextInt(mapAreas[currLevel][1] - mapAreas[currLevel][0]) + mapAreas[currLevel][0];
				y = randomizer.nextInt(mapAreas[currLevel][3] - mapAreas[currLevel][2]) + mapAreas[currLevel][2];
				tileNum = gp.tileM.mapTileNum[gp.currentMap][x][y];
			}while(gp.tileM.tile[tileNum].collision);
			
			powerUp = randomizer.nextInt(2);
			if (powerUp == 0) {
			    placeObject(new OBJ_Heart(gp), x, y, currLevel);
			} else {
			    placeObject(new OBJ_Shield_Blue(gp), x, y, currLevel);
			}
			gp.currObjIndex[currLevel]++;
		}
	}
	

	void randomizeDebuffs(int currLevel) {
		int x, y;
		int tileNum;
		
		int numberOfDebuffs = randomizer.nextInt(3) + 2;
		int debuff = 0;
		for(int i = 0; i < numberOfDebuffs; i++) {
			do {
				x = randomizer.nextInt(mapAreas[currLevel][1] - mapAreas[currLevel][0]) + mapAreas[currLevel][0];
				y = randomizer.nextInt(mapAreas[currLevel][3] - mapAreas[currLevel][2]) + mapAreas[currLevel][2];
				tileNum = gp.tileM.mapTileNum[gp.currentMap][x][y];
			}while(gp.tileM.tile[tileNum].collision);
			
			debuff = randomizer.nextInt(2);
			if (debuff == 0) {
			    placeObject(new OBJ_Mud(gp), x, y, currLevel);
			} else {
			    placeObject(new OBJ_SpikeTrap(gp), x, y, currLevel);
			}
			gp.currObjIndex[currLevel]++;
		}
	}
	
}
