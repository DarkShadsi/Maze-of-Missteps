package com.shadow.maze.model;

import com.shadow.maze.view.GamePanel;

public class OBJ_Key extends Object{
	GamePanel gp;
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Key";

		down1 = uTool.scaleImage("/objects/key.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
	}
}
