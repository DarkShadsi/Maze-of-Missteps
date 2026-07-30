package com.shadow.maze.model;

import com.shadow.maze.controller.MusicHandler;
import com.shadow.maze.view.GamePanel;

public class OBJ_Key extends Object{
	GamePanel gp;
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Key";
		type = consumable;

		down1 = uTool.scaleImage("/objects/key.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
	}
	
	public void useItem(Object user) {
		gp.ui.addMessage("You picked up a key!");
		MusicHandler.playKeySound();
		gp.pHandler.removeGoal(this.worldX, this.worldY);
	}
}
