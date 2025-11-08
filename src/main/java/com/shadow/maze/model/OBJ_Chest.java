package com.shadow.maze.model;

import com.shadow.maze.view.GamePanel;

public class OBJ_Chest extends Object{
	GamePanel gp;
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		this.gp = gp;

		name = "Chest";
		down1 = uTool.scaleImage("/objects/chest.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
		
		solidArea.x = 4;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
