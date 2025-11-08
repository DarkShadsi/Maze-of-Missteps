package com.shadow.maze.util;

import java.util.Random;

import com.shadow.maze.model.MON_RedSlime;
import com.shadow.maze.model.Object;
import com.shadow.maze.view.GamePanel;

public class AssetSetter {
	GamePanel gp;
	Random randomizer = new Random();
	int unitWidth, unitHeight;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		this.unitWidth = gp.gameFrame.GAMEUNITWIDTH;
		this.unitHeight = gp.gameFrame.GAMEUNITHEIGHT;
	}
	
	public void setObjects(int currLevel) {
		for(int i = 0; i < 3; i++) {
			int startCol = 10;
			int endCol = 39;
			int startRow = 13;
			int endRow = 41;
		
			int x, y;
			int tileNum;
			for(int count = 0; count < 3; count++) {
				do {
					x = randomizer.nextInt(endCol - startCol) + startCol;
					y = randomizer.nextInt(endRow - startRow) + startRow;
					tileNum = gp.tileM.mapTileNum[currLevel][x][y];
				}while(gp.tileM.tile[tileNum].collision);
				System.out.println(x + " " + y);
				gp.monsters[currLevel][count] = new MON_RedSlime(gp);
				gp.monsters[currLevel][count].worldX = x*unitWidth;
				gp.monsters[currLevel][count].worldY = y*unitHeight;
			}
		}
		
	}
	
	public void placeObject(Object obj, int x, int y, int currLevel) {
		gp.obj[currLevel][gp.currObjIndex[currLevel]] = obj;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldX = x*unitWidth;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldY = y*unitHeight;
	}
	
}
