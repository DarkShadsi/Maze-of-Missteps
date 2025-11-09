package com.shadow.maze.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.shadow.maze.model.OBJ_Door;
import com.shadow.maze.model.OBJ_Key;
import com.shadow.maze.view.GamePanel;

public class PointHandler {
	private Point exits[] = new Point[6];
	private ArrayList<Point> goals[] = new ArrayList[5];
	private Point pLocs[] = new Point[6];
	private Random randomizer = new Random();
	GamePanel gamePanel;
	
	public PointHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		initGoals();
		initPlayerLocs();
	}
	
	void initGoals() {
		
		for(int i = 0; i < 5; i++) {
			goals[i]= new ArrayList<Point>();
		}
		
		int currLevel = 0;

		int startCol = 10;
		int endCol = 39;
		int startRow = 13;
		int endRow = 41;
		
		//LEVEL 1
			//keys
			randomizeKeys(currLevel, startCol, endCol, startRow, endRow);
			//exit
			exits[currLevel] = new Point(25, 12);
			gamePanel.aSetter.placeObject(new OBJ_Door(gamePanel), 25, 12, currLevel);
			gamePanel.currObjIndex[currLevel]++;
			currLevel++;
		//LEVEL 2
			//keys
			randomizeKeys(currLevel, startCol, endCol, startRow, endRow);
			exits[currLevel] = new Point(9, 5);
			currLevel++;
	}
	
	void randomizeKeys(int currLevel, int startCol, int endCol, int startRow, int endRow) {
	
		int x, y;
		int tileNum;
		for(int count = 0; count < 3; count++) {
			do {
				x = randomizer.nextInt(endCol - startCol) + startCol;
				y = randomizer.nextInt(endRow - startRow) + startRow;
				tileNum = gamePanel.tileM.mapTileNum[currLevel][x][y];
			}while(gamePanel.tileM.tile[tileNum].collision);
			System.out.println(x + " " + y);
			gamePanel.aSetter.placeObject(new OBJ_Key(gamePanel), x, y, currLevel);
			gamePanel.currObjIndex[currLevel]++;
			goals[currLevel].add( new Point(x, y));			
		}
	}
	
	void initPlayerLocs() {
		int i = 0;
		//LEVEL 1
		pLocs[i] = new Point(25, 41);
		i++;
		//LEVEL 2
		pLocs[i] = new Point(38, 41);
	}
	
	public Point getPlayerLoc(int index) {
		return pLocs[index];
	}
	
	public void removeGoal(int worldX, int worldY) {
		for(Point p: goals[gamePanel.currentMap]) {
			int x = worldX/gamePanel.gameFrame.GAMEUNITWIDTH;
			int y = worldY/gamePanel.gameFrame.GAMEUNITHEIGHT;
			if(p != null && p.x == x && p.y == y) {
				goals[gamePanel.currentMap].remove(p);
				break;
			}
		}
	}
	
	public Point getGoal() {
		int index = 20;
		if(gamePanel.player.keys < 3) {
			int pX = gamePanel.player.worldX/gamePanel.gameFrame.GAMEUNITWIDTH;
			int pY = gamePanel.player.worldY/gamePanel.gameFrame.GAMEUNITHEIGHT;
			index = gamePanel.gameFrame.uTool.getNearest(pX, pY, goals[gamePanel.currentMap]);
			return goals[gamePanel.currentMap].get(index);
		}else {
			return exits[gamePanel.currentMap];
		}
	}
}
