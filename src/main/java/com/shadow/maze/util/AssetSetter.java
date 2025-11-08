package com.shadow.maze.util;

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
	
}
