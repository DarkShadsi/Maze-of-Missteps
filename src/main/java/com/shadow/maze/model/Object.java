package com.shadow.maze.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.shadow.maze.util.UtilityTool;
import com.shadow.maze.view.GamePanel;

public class Object {
	public GamePanel gp;
	public UtilityTool uTool;
	public int tileWidth;
	public int tileHeight;
	
	//ATTRIBUTES
	public int screenX, screenY;
	public int worldX, worldY;
	public int speed;
		//FOR OBJECTS
			public String name;
			
	//SOLID AREA
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	
	//IMAGE
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	//STATE
	public String direction;
	public boolean collisionOn;
	
	//DIALOGUES
	public String dialogues[][] = new String[10][20];
	public int dialogueSet = 0, dialogueIndex = 0;
	
	//COUNTERS
	public int spriteNum = 1;
	public int spriteCounter = 0;
	public int standCounter = 0;
	
	public Object(GamePanel gp) {
		this.gp = gp;
		uTool = gp.gameFrame.uTool;
		int tileWidth = gp.gameFrame.GAMEUNITWIDTH;
		int tileHeight = gp.gameFrame.GAMEUNITHEIGHT;
		solidArea = new Rectangle(0, 0, tileWidth, tileHeight);
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		int tempScreenX = getScreenX();
		int tempScreenY = getScreenY();
		
		image = down1;
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		g2.setColor(Color.RED);
		g2.drawRect(tempScreenX + solidArea.x, tempScreenY + solidArea.y, solidArea.width, solidArea.height);
		
	}
	
	//******************** INTERACTIONS *****************************//
	public void interactObject(int objIndex) {}
	
	//******************** HELPER METHODS ***************************//
	public int getCenterX() {
		return worldX + (down1.getWidth()/2);
	}
	
	public int getCenterY() {
		return worldY + (down1.getHeight()/2);
	}
	
	public int getScreenX() {
		return worldX - gp.player.worldX + gp.player.screenX;
	}
	
	public int getScreenY() {
		return worldY - gp.player.worldY + gp.player.screenY;
	}
	
	public boolean isInCamera() {
		if(getCenterX() + 2*tileWidth > gp.player.worldX - gp.player.screenX &&
			getCenterX() - 2*tileWidth < gp.player.worldX + gp.player.screenX && 
			getCenterY() + 2*tileHeight > gp.player.worldY - gp.player.screenY &&
			getCenterY() - 2*tileHeight < gp.player.worldY + gp.player.screenY) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
