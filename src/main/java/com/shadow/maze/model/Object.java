package com.shadow.maze.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

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
	public final int buff = 4;
	public final int debuff = 5;
		//FOR OBJECTS
			public String name;
			public int type;
			public int subType;
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
	public boolean isSlowed = false;
	public boolean isStunned = false;
	
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
	public int slowTime = 0;
	public int slowCounter = 0;
	public int stunDuration = 90;
	int stunCounter = 0;
	public int updateTime = 3;
	public int updateCounter = 0;
	
	Random randomizer = new Random();
	
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
		
		if(this.type == monster) {
			switch(direction) {
			case "up":
				image = (spriteNum == 1)? up1: up2;
				break;
			case "down":
				image = (spriteNum == 1)? down1: down2;
				break;
			case "left":
				image = (spriteNum == 1)? left1: left2;
				break;
			case "right":
				image = (spriteNum == 1)? right1: right2;
				break;
			}
		}else {
			image = down1;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
	}
	
	//******************** INTERACTIONS *****************************//
	public void interactObject(int objIndex) {}
	public void setAction() {}
	

	public void update() {

		setAction();
		checkCollision();
		updateCounter++;
		
		if(updateCounter%updateTime == 0) {
			updateCounter = 1;
			
			if(collisionOn) {
				do {
					int random = randomizer.nextInt(4);
					switch(random) {
					case 0:
						direction = "down";
						break;
					case 1:
						direction = "up";
						break;
					case 2:
						direction = "right";
						break;
					case 3:
						direction = "left";
						break;
					}
					checkCollision();
				}while(collisionOn);
			}
			
			if(isStunned) {
				stunCounter ++;
				if(stunCounter >= stunDuration) {
					stunCounter = 0;
					isStunned = false;
				}
			}else {
				switch(direction) {
					case "up": worldY -= speed; break;
					case "down": worldY += speed;break;
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
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
		
		int enCenterX = worldX + solidArea.x + solidArea.width/2;
		int enCenterY = worldY + solidArea.y + solidArea.height/2;
		int pCenterX = gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width/2;
		int pCenterY = gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height/2;
		
		int startCol = enCenterX/tileWidth;
		int startRow = enCenterY/tileHeight;
		int goalCol = pCenterX/tileWidth;
		int goalRow = pCenterY/tileHeight;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
		
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
			}else { //RANDOMIZE DIRECTION IF NONE OF THE CASES ARE ACCEPTED
				switch(randomizer.nextInt(4)) {
				case 0:
					direction = "up";
					break;
				case 1:
					direction = "down";
					break;
				case 2:
					direction = "left";
					break;
				case 3:
					direction = "right";
					break;
				}
			}
		}
	}
	
	public void checkCollision() {
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
		
	}
	
}
