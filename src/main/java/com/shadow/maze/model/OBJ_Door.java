package com.shadow.maze.model;
import com.shadow.maze.view.GamePanel;

public class OBJ_Door extends Object{
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		super(gp);
		this.gp = gp;

		name = "Door";
		down1 = uTool.scaleImage("/objects/door.png", gp.gameFrame.GAMEUNITWIDTH, gp.gameFrame.GAMEUNITHEIGHT);
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = gp.gameFrame.GAMEUNITWIDTH;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDialogues();

		collision = true; 
	}
	
	void setDialogues() {
		int i = 0, j = 0;
		dialogues[i][j] = "You need a key to unlock";
	}
	
}
