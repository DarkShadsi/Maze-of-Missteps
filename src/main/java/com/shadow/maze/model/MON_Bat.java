package com.shadow.maze.model;

import java.util.Random;

import com.shadow.maze.view.GamePanel;

public class MON_Bat extends Object{
	GamePanel gp;
	Random randomizer = new Random();
	int tileWidth, tileHeight;

	public MON_Bat(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.tileWidth = gp.gameFrame.GAMEUNITWIDTH;
		this.tileHeight = gp.gameFrame.GAMEUNITHEIGHT;
		
		name = "Bat";
		direction = "down";
		type = monster;
		defaultSpeed = 3;
		speed = defaultSpeed;
		attack = 2;

		solidArea.x = tileWidth/6;
		solidArea.y = tileHeight/3;
		solidArea.height = tileHeight - solidArea.y;
		solidArea.width = tileWidth - solidArea.x*2;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = uTool.scaleImage("/monsters/bat_down_1.png", tileWidth, tileHeight);
		up2 = uTool.scaleImage("/monsters/bat_down_2.png", tileWidth, tileHeight);
		down1 = uTool.scaleImage("/monsters/bat_down_1.png", tileWidth, tileHeight);
		down2 = uTool.scaleImage("/monsters/bat_down_2.png", tileWidth, tileHeight);
		left1 = uTool.scaleImage("/monsters/bat_down_1.png", tileWidth, tileHeight);
		left2 = uTool.scaleImage("/monsters/bat_down_2.png", tileWidth, tileHeight);
		right1 = uTool.scaleImage("/monsters/bat_down_1.png", tileWidth, tileHeight);
		right2 = uTool.scaleImage("/monsters/bat_down_2.png", tileWidth, tileHeight);
	}
	
	public void setAction() {

		actionLockCounter++;
		searchPath();
		
	}

}
