package com.shadow.maze.model;

import com.shadow.maze.view.GamePanel;

public class OBJ_Heart extends Object{
	GamePanel gp;
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Heart";
		value = 1;
		type = consumable;
		subType = buff;
		
		down1 = gp.gameFrame.uTool.scaleImage("/objects/heart_full.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
		
	}

	public void useItem(Object user) {
		gp.ui.addMessage("+" + value + " life");
		user.health += value;
		System.out.println(user.health);
	}

}
