package com.shadow.maze.util;

import com.shadow.maze.model.Object;
import com.shadow.maze.view.GamePanel;

public class AssetSetter {
	GamePanel gp;
	int unitWidth, unitHeight;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		this.unitWidth = gp.gameFrame.GAMEUNITWIDTH;
		this.unitHeight = gp.gameFrame.GAMEUNITHEIGHT;
		setObjects();
	}
	
	public void setObjects() {
		
		
	}
	
	public void placeObject(Object obj, int x, int y, int currLevel) {
		gp.obj[currLevel][gp.currObjIndex[currLevel]] = obj;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldX = x*unitWidth;
		gp.obj[currLevel][gp.currObjIndex[currLevel]].worldY = y*unitHeight;
	}
	
}
