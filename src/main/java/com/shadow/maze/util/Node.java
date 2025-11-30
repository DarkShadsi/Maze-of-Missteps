package com.shadow.maze.util;

public class Node {
	Node parent;
	public int col;
	public int row;
	int gCost = 999;
	int hCost = 999;
	int fCost = 999;
	boolean open;
	boolean checked;
	boolean solid;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
