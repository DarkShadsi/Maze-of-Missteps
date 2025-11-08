package com.shadow.maze.util;

import java.awt.Point;
import java.util.ArrayList;

import com.shadow.maze.view.GamePanel;

public class Pathfinder {
	GamePanel gp;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached;
	int step = 0;
	
	public Pathfinder(GamePanel gp) {
		this.gp = gp;
		instantiateNode();
	}
	
	void instantiateNode() {
		node = new Node[gp.COLS][gp.ROWS];
		
		for(int row = 0; row < gp.ROWS; row++) {
			for(int col = 0; col < gp.COLS; col++) {
				node[col][row] = new Node(col, row);
			}
		}
		
	}
	
	public void resetNodes() {
		
		for(int row = 0; row < gp.ROWS; row++) {
			for(int col = 0; col < gp.COLS; col++) {
				node[col][row].open = false;
				node[col][row].checked = false;
				node[col][row].solid = false;
			}
		}
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
		
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		resetNodes();
		
		//set start and goal node
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		
		//SET SOLID NODES
		for(int row = 0; row < gp.ROWS; row++) {
			for(int col = 0; col < gp.COLS; col++) {

				//CHECK TILE
				int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
				if(gp.tileM.tile[tileNum].collision) {
					node[col][row].solid = true;
				}
			}
		}

//		//CHECK INTERACTIVE TILES
//		for(int i = 0; i < gp.iTile[gp.currentMap].length; i++) {
//			if(gp.iTile[gp.currentMap][i] != null) {
//				if(gp.iTile[gp.currentMap][i].destructible) {
//					int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
//					int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
//					node[itCol][itRow].solid = true;
//				}
//			}
//		}
		
		//GET COST
		for(int row = 0; row < gp.ROWS; row++) {
			for(int col = 0; col < gp.COLS; col++) {
				getCost(node[col][row]);
			}
		}
		
	}
	
	void getCost(Node node) {
		
		//G COST
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		//H COST
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		//F COST
		node.fCost = node.gCost + node.hCost;
		
	}
	
	public boolean search() {
		while(!goalReached && step < 500) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			//MARK CURRENT NODE AS CHECKED
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//OPEN THE UPPER NODE
			if(row-1 >= 0) {
				openNode(node[col][row-1]);
			}
			
			//OPEN THE LOWER NODE
			if(row+1 < gp.ROWS) {
				openNode(node[col][row+1]);
			}
			
			//OPEN THE LEFT NODE
			if(col-1 >= 0) {
				openNode(node[col-1][row]);
			}
			
			//OPEN THE RIGHT NODE
			if(col+1 < gp.COLS) {
				openNode(node[col+1][row]);
			}

			
			//FIND THE BEST NODE
			int bestNodeIndex = 0;
			int bestNodeFCost = 999;
			
			for(int i = 0; i < openList.size(); i++) {
				//CHECK IF THIS NODE'S F COST IS BETTER
				if(openList.get(i).fCost < bestNodeFCost) {
					bestNodeIndex = i;
					bestNodeFCost = openList.get(i).fCost;
				}
				//IF F COST IS EQUAL, COMPARE G COST
				else if(openList.get(i).fCost == bestNodeFCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}

			//IF THERE'S NO NODE IN THE OPENLIST, END THE LOOP
			if(openList.size() == 0) {
				break;
			}
			
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
				step = 0;
			}
			
			step++;
			
		}
		
		return goalReached;
		
	}
	
	void openNode(Node node) {
		if(!node.open && !node.checked && !node.solid) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	void trackThePath() {
		
		Node current = goalNode;
		
		while(current != startNode) {
			pathList.add(0, current);
			current = current.parent;
		}
	}
	
}







