package com.shadow.maze.util;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

import com.shadow.maze.view.GamePanel;
import com.shadow.maze.model.Node;
import com.shadow.maze.model.Object;

public class Pathfinder {
	GamePanel gp;
	Node[][] node;
	PriorityQueue<Node> openList = new PriorityQueue<>(new Comparator<Node>() {
		@Override
		public int compare(Node n1, Node n2) {
			if(n1.fCost != n2.fCost) {
				return n1.fCost - n2.fCost;
			}
			return n1.gCost - n2.gCost;
		}
	});
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached;
	int step = 0;
	Object finder;
	
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
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Object finder) {
		this.finder = finder;
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
		
		for(Object obj: gp.obj[gp.currentMap]) {
			if(obj != null) {
				if(obj.subType == obj.debuff) {
					int col = obj.worldX/gp.gameFrame.GAMEUNITWIDTH;
					int row = obj.worldY/gp.gameFrame.GAMEUNITHEIGHT;
					node[col][row].solid = true;
				}
			}
		}
		
		if(finder == gp.player) {
			for(Object mon: gp.monsters[gp.currentMap]) {
				if(mon != null && mon != finder) {
					int centerX = mon.worldX + mon.solidArea.x + mon.solidArea.width / 2;
					int centerY = mon.worldY + mon.solidArea.y + mon.solidArea.height / 2;

					int col = centerX / gp.gameFrame.GAMEUNITWIDTH;
					int row = centerY / gp.gameFrame.GAMEUNITHEIGHT;
					node[col][row].solid = true;
				}
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
		while(!goalReached && step < 2000) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			//MARK CURRENT NODE AS CHECKED
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//OPEN THE UPPER NODE
			if(row-1 >= 0) {
				getCost(node[col][row-1]);
				openNode(node[col][row-1]);
			}
			
			//OPEN THE LOWER NODE
			if(row+1 < gp.ROWS) {
				getCost(node[col][row+1]);
				openNode(node[col][row+1]);
			}
			
			//OPEN THE LEFT NODE
			if(col-1 >= 0) {
				getCost(node[col-1][row]);
				openNode(node[col-1][row]);
			}
			
			//OPEN THE RIGHT NODE
			if(col+1 < gp.COLS) {
				getCost(node[col+1][row]);
				openNode(node[col+1][row]);
			}

			//IF THERE'S NO NODE IN THE OPENLIST, END THE LOOP
			if(openList.isEmpty()) {
				break;
			}
			
			// GET THE BEST NODE
			currentNode = openList.poll();
			
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
	
	public ArrayList<Node> copyPath() {
		ArrayList<Node> pathCopy = new ArrayList<Node>();
		
		for(Node n: pathList) {
			pathCopy.add(n);
		}
		
		return pathCopy;
	}
	
}