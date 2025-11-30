package com.shadow.maze.model;

import com.shadow.maze.view.GamePanel;

public class OBJ_SpikeTrap extends Object{
	public OBJ_SpikeTrap(GamePanel gp) {
		super(gp);
		
		name = "Spike Trap";
		type = consumable;
		subType = debuff;
		
		value = 1;
		
		down1 = gp.gameFrame.uTool.scaleImage("/objects/spike_trap.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
	}
	
	public void useItem(Object user) {
		gp.ui.addMessage("You stepped on a spike trap");
		user.health -= value;
		user.isInvincible = true;
	}
}
