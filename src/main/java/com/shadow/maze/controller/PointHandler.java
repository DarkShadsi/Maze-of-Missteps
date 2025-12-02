package com.shadow.maze.controller;

import java.awt.Point;
import java.util.ArrayList;

import com.shadow.maze.view.GamePanel;

public class PointHandler {
	public Point exits[] = new Point[6];
	public ArrayList<Point> goals[] = new ArrayList[5];
	private Point pLocs[] = new Point[6];
	public int mapAreas[][] = new int[5][4];
	GamePanel gamePanel;
	
	public PointHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		initMapAreas();
		initGoals();
		initPlayerLocs();
	}
	
	void initGoals() {
		
		for(int i = 0; i < 5; i++) {
			goals[i]= new ArrayList<Point>();
		}
	}
	
	public void initPlayerLocs() {
		int i = 0;
		//LEVEL 1
		pLocs[i] = new Point(16, 24);
		i++;
		//LEVEL 2
		pLocs[i] = new Point(25, 25);
		i++;
		//LEVEL 3
		pLocs[i] = new Point(25, 41);
		i++;
		//LEVEL 4
		pLocs[i] = new Point(38, 41);
		i++;	
		//LEVEL 5
		pLocs[i] = new Point(25, 1);
		
	}
	
	void initMapAreas() {
		int level = 0;
		int startCol = 0;
		int endCol = 1;
		int startRow = 2;
		int endRow = 3; 
		
		mapAreas[level][startCol] = 15;
		mapAreas[level][endCol] = 35;
		mapAreas[level][startRow] = 15;
		mapAreas[level][endRow] = 35;
		
		level++;
		mapAreas[level][startCol] = 10;
		mapAreas[level][endCol] = 40;
		mapAreas[level][startRow] = 10;
		mapAreas[level][endRow] = 40;
		
		level++;
		mapAreas[level][startCol] = 9;
		mapAreas[level][endCol] = 40;
		mapAreas[level][startRow] = 12;
		mapAreas[level][endRow] = 42;
		
		level++;
		mapAreas[level][startCol] = 9;
		mapAreas[level][endCol] = 40;
		mapAreas[level][startRow] = 4;
		mapAreas[level][endRow] = 42;
		
		level++;
		mapAreas[level][startCol] = 0;
		mapAreas[level][endCol] = 49;
		mapAreas[level][startRow] = 0;
		mapAreas[level][endRow] = 49;
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
		if(gamePanel.player.keys < 3 && !goals[gamePanel.currentMap].isEmpty()) {
			int pX = gamePanel.player.worldX/gamePanel.gameFrame.GAMEUNITWIDTH;
			int pY = gamePanel.player.worldY/gamePanel.gameFrame.GAMEUNITHEIGHT;
			index = gamePanel.gameFrame.uTool.getNearest(pX, pY, goals[gamePanel.currentMap]);
			return goals[gamePanel.currentMap].get(index);
		}else if(gamePanel.player.keys >= 3){
			return exits[gamePanel.currentMap];
		}else {
			return null;
		}
	}
}
