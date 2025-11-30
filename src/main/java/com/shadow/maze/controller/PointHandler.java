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
		pLocs[i] = new Point(25, 41);
		i++;
		//LEVEL 2
		pLocs[i] = new Point(38, 41);
	}
	
	void initMapAreas() {
		int level = 0;
		int startCol = 0;
		int endCol = 1;
		int startRow = 2;
		int endRow = 3; 
		
		mapAreas[level][startCol] = 10;
		mapAreas[level][endCol] = 39;
		mapAreas[level][startRow] = 13;
		mapAreas[level][endRow] = 41;
		
		level++;
		mapAreas[level][startCol] = 10;
		mapAreas[level][endCol] = 39;
		mapAreas[level][startRow] = 5;
		mapAreas[level][endRow] = 41;
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
