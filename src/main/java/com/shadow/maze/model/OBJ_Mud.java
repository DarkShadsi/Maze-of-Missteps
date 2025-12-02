package com.shadow.maze.model;

import com.shadow.maze.view.GamePanel;

public class OBJ_Mud extends Object{
	public OBJ_Mud(GamePanel gp) {
		super(gp);
		
		name = "Mud";
		type = consumable;
		subType = debuff;
		value = gp.gameFrame.GAMEUNITWIDTH/22;
		
		down1 = gp.gameFrame.uTool.scaleImage("/objects/mud.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
	}
	
	public void useItem(Object user) {
		gp.ui.addMessage("You stepped on a sticky mud");
		user.speed = user.defaultSpeed - value;
		user.isSlowed = true;
		user.slowTime = 180;
	}
}
