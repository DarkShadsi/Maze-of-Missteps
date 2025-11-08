package com.shadow.maze.controller;

import java.awt.Point;

public class PointHandler {
	private Point goals[] = new Point[6];
	private Point pLocs[] = new Point[6];
	
	public PointHandler() {
		initGoals();
		initPlayerLocs();
	}
	
	void initGoals() {
		int i = 0;
		//LEVEL 1
		goals[i] = new Point(25, 12);
		i++;
		//LEVEL 2
		goals[i] = new Point(9, 5);
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
	
	public Point getGoal(int index) {
		return goals[index];
	}
}
