package com.shadow.maze.model;

import java.util.Random;

import com.shadow.maze.view.GamePanel;

public class MON_RedSlime extends Object{
	GamePanel gp;
	Random randomizer = new Random();
	int tileWidth, tileHeight;

	public MON_RedSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.tileWidth = gp.gameFrame.GAMEUNITWIDTH;
		this.tileHeight = gp.gameFrame.GAMEUNITHEIGHT;
		
		name = "Red Slime";
		direction = "down";
		type = monster;
		defaultSpeed = 2;
		speed = defaultSpeed;
		attack = 1;
		
		solidArea.x = 5;
		solidArea.y = 10;
		solidArea.height = tileHeight - solidArea.y;
		solidArea.width = tileWidth - solidArea.x*2;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = uTool.scaleImage("/monsters/redslime_down_1.png", tileWidth, tileHeight);
		up2 = uTool.scaleImage("/monsters/redslime_down_2.png", tileWidth, tileHeight);
		down1 = uTool.scaleImage("/monsters/redslime_down_1.png", tileWidth, tileHeight);
		down2 = uTool.scaleImage("/monsters/redslime_down_2.png", tileWidth, tileHeight);
		left1 = uTool.scaleImage("/monsters/redslime_down_1.png", tileWidth, tileHeight);
		left2 = uTool.scaleImage("/monsters/redslime_down_2.png", tileWidth, tileHeight);
		right1 = uTool.scaleImage("/monsters/redslime_down_1.png", tileWidth, tileHeight);
		right2 = uTool.scaleImage("/monsters/redslime_down_2.png", tileWidth, tileHeight);
	}
	
	public void setAction() {

		actionLockCounter++;
		searchPath();
		
	}

}
