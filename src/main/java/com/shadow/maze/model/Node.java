package com.shadow.maze.model;

public class Node {
	public Node parent;
	public int col;
	public int row;
	public int gCost = 999;
	public int hCost = 999;
	public int fCost = 999;
	public boolean open;
	public boolean checked;
	public boolean solid;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
