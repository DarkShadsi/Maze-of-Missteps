package com.shadow.maze.util;

public class Node {
	Node parent;
	public int col;
	public int row;
	int gCost;
	int hCost;
	int fCost;
	boolean open;
	boolean checked;
	boolean solid;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
