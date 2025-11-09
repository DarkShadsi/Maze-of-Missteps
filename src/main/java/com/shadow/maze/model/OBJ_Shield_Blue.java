package com.shadow.maze.model;

import com.shadow.maze.view.GamePanel;

public class OBJ_Shield_Blue extends Object{
	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		
		name = "Blue Shield";
		type = consumable;
		value = 180;
		
		down1 = gp.gameFrame.uTool.scaleImage("/objects/shield_blue.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
	}
	
	public void useItem(Object user) {
		gp.ui.addMessage("Gained invincibilty");
		user.isInvincible = true;
		user.invincibiltyTimer = value;
	}
}
