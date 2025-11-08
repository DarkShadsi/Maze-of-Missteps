package com.shadow.maze.model;
import com.shadow.maze.view.GamePanel;

public class OBJ_Door_Iron extends Object{
	GamePanel gp;
	
	public OBJ_Door_Iron(GamePanel gp) {
		super(gp);
		this.gp = gp;

		name = "Door Iron";
		down1 = uTool.scaleImage("/objects/door_iron.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = gp.gameFrame.GAMEUNITWIDTH;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		collision = true; 
	}
}
