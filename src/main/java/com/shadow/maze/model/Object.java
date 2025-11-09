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
	public int defaultSpeed;
	public int attack;
	public int maxHealth;
	public int health;
	final int player = 0;
	final int monster = 1;
	final int consumable = 3;
		//FOR OBJECTS
			public String name;
			public int type;
			int value;
			
	//SOLID AREA
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	
	//IMAGE
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	//STATE
	public String direction;
	public boolean collisionOn;
	public boolean isInvincible;
	
	//DIALOGUES
	public String dialogues[][] = new String[10][20];
	public int dialogueSet = 0, dialogueIndex = 0;
	
	//COUNTERS
	public int spriteNum = 1;
	public int spriteCounter = 0;
	public int standCounter = 0;
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	int defaultInivincibiltyTimer = 60;
	public int invincibiltyTimer = defaultInivincibiltyTimer;
	
	public Object(GamePanel gp) {
		this.gp = gp;
		uTool = gp.gameFrame.uTool;
		this.tileWidth = gp.gameFrame.GAMEUNITWIDTH;
		this.tileHeight = gp.gameFrame.GAMEUNITHEIGHT;
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
	public void setAction() {}
	

	public void update() {

		setAction();
		checkCollision();
		
		if(!collisionOn) {
			switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed;break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
		}
		
		spriteCounter++;
		if(spriteCounter >= 13) {
			spriteNum = (spriteNum == 1)? 2: 1;
			spriteCounter = 0;
		}
	}
	
	
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
	
	public void useItem(Object obj) {}
	
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
	public void searchPath() {
		
		int startCol = (worldX + solidArea.x)/tileWidth;
		int startRow = (worldY + solidArea.y)/tileHeight;
		int goalCol = (gp.player.worldX + gp.player.solidArea.x)/tileWidth;
		int goalRow = (gp.player.worldY + gp.player.solidArea.y)/tileHeight;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if(gp.pFinder.search()) {
			//NEXT WORLDX AND WORLDY
			int nextX = gp.pFinder.pathList.get(0).col * tileWidth;
			int nextY = gp.pFinder.pathList.get(0).row * tileHeight;
			
			//ENTITY'S SOLID AREA POSITION
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = enTopY + solidArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + tileWidth) {
				direction = "up";
			}else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + tileWidth) {
				direction = "down";
			}else if(enTopY >= nextY && enBottomY < nextY + tileHeight) {
				//left or right
				if(enLeftX > nextX) {
					direction = "left";
				}else if(enLeftX < nextX) {
					direction = "right";
				}
			}else if(enTopY > nextY && enLeftX > nextX) {
				direction = "up";
				checkCollision();
				if(collisionOn) {
					direction = "left";
				}
			}else if(enTopY > nextY && enLeftX < nextX) {
				checkCollision();
				if(collisionOn) {
					direction = "right";
				}
			}else if(enTopY < nextY && enLeftX > nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn) {
					direction = "left";
				}
			}else if(enTopY < nextY && enLeftX < nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn) {
					direction = "right";
				}
			}
		}
	}
	
	public void checkCollision() {
		
		//TEMPORARILY CHANGE DIRECTION IF KNOCKBACKED
		String currentDirection = direction;
		
		// CHECKS TILE COLLISION
		collisionOn = false;
		gp.colHandler.checkTile(this);
		gp.colHandler.checkObject(this, false);
		gp.colHandler.checkEntity(this, gp.monsters);
		
		//CHECKS COLLISION WITH THE PLAYER & GIVE DAMAGE UPON CONTACT
		if(gp.colHandler.checkPlayer(this) && type ==  monster) {
			if(!gp.player.isInvincible) {
				gp.player.health -= attack;
				gp.player.isInvincible = true;
				gp.gameFrame.uTool.teleportObject(gp.player, gp);
			}
		}
		
		direction = currentDirection;
		
	}
	
}
