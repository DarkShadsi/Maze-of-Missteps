package com.shadow.maze.util;

import java.awt.Point;
import java.util.Random;

import com.shadow.maze.model.MON_Bat;
import com.shadow.maze.model.MON_RedSlime;
import com.shadow.maze.model.OBJ_Door;
import com.shadow.maze.model.OBJ_Heart;
import com.shadow.maze.model.OBJ_Key;
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
	
	public void setObjects(int currLevel) {
		mapAreas = gp.pHandler.mapAreas;
		
		if(currLevel >= 0) {
			gp.currObjIndex[currLevel] = 0;
			setGoal(currLevel);
			randomizeMonsterLoc(currLevel);
			randomizePowerUps(currLevel);
			randomizeDebuffs(currLevel);
		}
	}
	
	public void placeObject(Object obj, int x, int y, int currLevel) {
		gp.obj[currLevel][gp.currObjIndex[currLevel]] = obj;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldX = x*unitWidth;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldY = y*unitHeight;
	}
	
	void setGoal(int currLevel) {
		//keys
		randomizeKeys(currLevel);
		//exit
		placeExit(currLevel);
	}
	
	void randomizeMonsterLoc(int currLevel) {
		int x, y;
		int tileNum;
		int maxEnemies = randomizer.nextInt(currLevel + 2) + 3;
		int updateTime = 4, attack = 1;

		if(currLevel == 1) {
			attack = 2;
		}else if(currLevel == 2) {
			attack = 2;
			updateTime = 3;
			if(maxEnemies <= 4) maxEnemies = 5;
		}else if(currLevel == 3) {
			attack = 3;
			updateTime = 2;
			if(maxEnemies <= 5) maxEnemies = 6;
		}else if(currLevel == 4){
			attack = 3;
			updateTime = 1;
			if(maxEnemies <= 6) maxEnemies = 7;
		}
		for(int count = 0; count < maxEnemies; count++) {
			do {
				x = randomizer.nextInt(mapAreas[currLevel][1] - mapAreas[currLevel][0]) + mapAreas[currLevel][0];
				y = randomizer.nextInt(mapAreas[currLevel][3] - mapAreas[currLevel][2]) + mapAreas[currLevel][2];
				tileNum = gp.tileM.mapTileNum[gp.currentMap][x][y];
			}while(gp.tileM.tile[tileNum].collision);
			
			if(count < 5) {
				gp.monsters[currLevel][count] = new MON_RedSlime(gp);
			}else {
				gp.monsters[currLevel][count] = new MON_Bat(gp);
			}
			
			gp.monsters[currLevel][count].worldX = x*unitWidth;
			gp.monsters[currLevel][count].worldY = y*unitHeight;
			gp.monsters[currLevel][count].attack = attack;
			gp.monsters[currLevel][count].updateTime = updateTime;
			gp.monsters[currLevel][count].stunDuration = gp.monsters[currLevel][count].stunDuration/updateTime;
		}
	}
	
	void randomizePowerUps(int currLevel) {
		int x, y;
		int tileNum;
		
		int numberOfPowerUps = randomizer.nextInt(3 + currLevel) + 2;
		int powerUp = 0;
		for(int i = 0; i < numberOfPowerUps; i++) {
			do {
				x = randomizer.nextInt(mapAreas[currLevel][1] - mapAreas[currLevel][0]) + mapAreas[currLevel][0];
				y = randomizer.nextInt(mapAreas[currLevel][3] - mapAreas[currLevel][2]) + mapAreas[currLevel][2];
				tileNum = gp.tileM.mapTileNum[gp.currentMap][x][y];
			}while(gp.tileM.tile[tileNum].collision || gp.colHandler.checkOverlapObject(x, y));
			
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
		
		int numberOfDebuffs = randomizer.nextInt(3 + currLevel) + currLevel + 1;
		int debuff = 0;
		for(int i = 0; i < numberOfDebuffs; i++) {
			do {
				x = randomizer.nextInt(mapAreas[currLevel][1] - mapAreas[currLevel][0]) + mapAreas[currLevel][0];
				y = randomizer.nextInt(mapAreas[currLevel][3] - mapAreas[currLevel][2]) + mapAreas[currLevel][2];
				tileNum = gp.tileM.mapTileNum[gp.currentMap][x][y];
			}while(gp.tileM.tile[tileNum].collision || gp.colHandler.checkOverlapObject(x, y));
			
			debuff = randomizer.nextInt(2);
			if (debuff == 0) {
			    placeObject(new OBJ_Mud(gp), x, y, currLevel);
			} else {
			    placeObject(new OBJ_SpikeTrap(gp), x, y, currLevel);
			}
			gp.currObjIndex[currLevel]++;
		}
	}
	

	void randomizeKeys(int currLevel) {
	
		int x, y;
		int tileNum;
		for(int count = 0; count < 3; count++) {
			do {
				x = randomizer.nextInt(mapAreas[currLevel][1] - mapAreas[currLevel][0]) + mapAreas[currLevel][0];
				y = randomizer.nextInt(mapAreas[currLevel][3] - mapAreas[currLevel][2]) + mapAreas[currLevel][2];
				tileNum = gp.tileM.mapTileNum[currLevel][x][y];
			}while(gp.tileM.tile[tileNum].collision);
			gp.aSetter.placeObject(new OBJ_Key(gp), x, y, currLevel);
			gp.currObjIndex[currLevel]++;
			gp.pHandler.goals[currLevel].add( new Point(x, y));			
		}
	}
	
	void placeExit(int currLevel) {
		switch(currLevel) {
		case 0:
			gp.pHandler.exits[currLevel] = new Point(35, 24);
			placeObject(new OBJ_Door(gp), 35, 24, currLevel);
			gp.currObjIndex[currLevel]++;
			break;
		case 1:
			gp.pHandler.exits[currLevel] = new Point(25, 40);
			placeObject(new OBJ_Door(gp), 25, 40, currLevel);
			gp.currObjIndex[currLevel]++;
			break;
		case 2:
			gp.pHandler.exits[currLevel] = new Point(25, 12);
			placeObject(new OBJ_Door(gp), 25, 12, currLevel);
			gp.currObjIndex[currLevel]++;
			break;
		case 3:
			gp.pHandler.exits[currLevel] = new Point(9, 5);
			placeObject(new OBJ_Door(gp), 9, 5, currLevel);
			gp.currObjIndex[currLevel]++;
			break;
		case 4:
			gp.pHandler.exits[currLevel] = new Point(25, 48);
			placeObject(new OBJ_Door(gp), 25, 48, currLevel);
			gp.currObjIndex[currLevel]++;
			break;
		}
	}
	
}
