package com.shadow.maze.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shadow.maze.controller.KeyHandler;
import com.shadow.maze.view.GamePanel;

public class Player extends Object{
	KeyHandler keyH;
	
	public boolean searchPath;
	
	//PERSONAL ITEMS
	public int keys = 0;
	
	//PLAYER SPECIFIC
	public int sprintSpeed;
	public int sprintDuration;
	public int hintDuration;
	public int hintCooldown;
	public int hintTimer = 0;
	public int sprintCounter = 0;
	public int hintCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		tileWidth = gp.gameFrame.GAMEUNITWIDTH;
		tileHeight = gp.gameFrame.GAMEUNITHEIGHT;

		screenX = gp.gameFrame.SCREENWIDTH/2 - (tileWidth/2);
		screenY = gp.gameFrame.SCREENHEIGHT/2 - (tileHeight/2);
		
		solidArea.x = tileWidth/6;
		solidArea.y = tileHeight/5;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = tileWidth - (2 * solidAreaDefaultX);
		solidArea.height = tileHeight - solidAreaDefaultY - 4;
		
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		maxHealth = 3;
		health = maxHealth;
		defaultSpeed = tileWidth/16;
		speed = defaultSpeed;
		sprintSpeed = tileWidth/8;
		sprintDuration = 180;
		hintDuration = 180;
		hintCooldown = 300;
		keys = 0;
		direction = "down";
		defaultInivincibiltyTimer = 60;
		invincibiltyTimer = defaultInivincibiltyTimer;
	}
	
	public void setStartValues() {
		Point loc = gp.pHandler.getPlayerLoc(gp.currentMap);
		worldX = (loc.x)*tileWidth;
		worldY = (loc.y)*tileHeight;
		health = maxHealth;
		keys = 0;
		direction = "down";
		sprintCounter = 0;
		invincibiltyTimer = defaultInivincibiltyTimer;
	}
	
	
	//******************** HELPER METHODS **************************//
	void getPlayerImage(){
		int width = tileWidth;
		int height = tileHeight;
		
		up1 = uTool.scaleImage("/player/boy_up_1.png", width, height);
		up2 = uTool.scaleImage("/player/boy_up_2.png", width, height);
		down1 = uTool.scaleImage("/player/boy_down_1.png", width, height);
		down2 = uTool.scaleImage("/player/boy_down_2.png", width, height);
		left1 = uTool.scaleImage("/player/boy_left_1.png", width, height);
		left2 = uTool.scaleImage("/player/boy_left_2.png", width, height);
		right1 = uTool.scaleImage("/player/boy_right_1.png", width, height);
		right2 = uTool.scaleImage("/player/boy_right_2.png", width, height);
	}
	
	
	//******************* UPDATE AND PAINT ***********************//
	
	public void update() {
		checkState();
		
		if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
			
			//player movements
			if(keyH.upPressed) {
				direction = "up";
			}else if(keyH.downPressed) {
				direction = "down";
			}else if(keyH.leftPressed) {
				direction = "left";
			}else if(keyH.rightPressed) {
				direction = "right";
			}

			//CHECKS TILE COLLISION
			collisionOn = false;
			gp.colHandler.checkTile(this);
			gp.colHandler.checkEntity(this, gp.monsters);
			//CHECKS OBJECT COLLISION
			int objIndex = gp.colHandler.checkObject(this, true);
			interactObject(objIndex);


			if(!collisionOn && !keyH.enterPressed) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed;break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
				//	SEARCH PATH EVERYTIME POSITION CHANGES
				if(searchPath && hintCounter < hintDuration) {
					setPath();
				}
				
				spriteCounter ++;
				if(spriteCounter >= 13) {
					spriteNum = (spriteNum == 1)? 2: 1;
					spriteCounter = 0;
				}
			}
			
			keyH.enterPressed = false;
		
		}else {
			standCounter++;
			if(standCounter >= 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
		
	}
	
	
	public void draw(Graphics2D g2) {

		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		BufferedImage image = null;
		

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
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
	}
	
	public void interactObject(int objIndex) {
		if(objIndex != 999) {
			Object obj = gp.obj[gp.currentMap][objIndex];
			if(keyH.enterPressed) {
				if(obj.name.equals("Door")) {
					if(keys >= 3) {
						gp.gameState = gp.stopped;
						gp.obj[gp.currentMap][objIndex] = null;
						keys = 0;
						upgrade();
						keyH.resetKeys();
						gp.gameFrame.showResultsPanel(true);
					}else {
						gp.ui.setSpeaker(obj);
						gp.gameState = gp.dialogueState;
						gp.redo();
					}
				}
			}else {
				if(obj.type == consumable) {
					if(obj.name.equals("Key")) keys++;
					obj.useItem(this);
					gp.obj[gp.currentMap][objIndex] = null;
				}
			}
		}
	}
	
	public void setPath() {
		int col = (worldX + (tileWidth/2))/tileWidth;
		int row = (worldY + (tileHeight/2))/tileHeight;
		
		Point goal = gp.pHandler.getGoal();
		if(goal != null) {
			gp.pFinder.setNodes(col, row, goal.x, goal.y, this);
			gp.pFinder.search();
			gp.tileM.goalPath = gp.pFinder.copyPath();
		}
	}
	
	void checkState() {
		if(isInvincible) {
			invincibleCounter++;
			if(invincibleCounter >= invincibiltyTimer) {
				invincibleCounter = 0;
				if(invincibiltyTimer != defaultInivincibiltyTimer) {
					invincibiltyTimer = defaultInivincibiltyTimer;
				}
				isInvincible = false;
			}
		}
		if(isSlowed) {
			slowCounter++;
			if(slowCounter >= slowTime) {
				slowCounter = 0;
				speed = defaultSpeed;
				isSlowed = false;
			}
		}else if(keyH.shiftPressed && sprintCounter < sprintDuration && (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)) {
			speed = sprintSpeed;
			sprintCounter++;
		}else {
			if(!isSlowed) {
				speed = defaultSpeed;
			}
		}
		
		if(searchPath && hintCounter < hintDuration) {
			hintCounter++;
		}else {
			if(hintTimer < hintCooldown) {
				hintTimer++;
			}
			if(hintTimer == hintCooldown) {
				hintCounter = 0;
			}
			searchPath = false;
			gp.tileM.drawPath = false;
		}
		
		
		if(health <= 0) {
			gp.gameState = gp.stopped;
			gp.gameFrame.showResultsPanel(false);
			keyH.resetKeys();
		}
	}
	
	void upgrade() {
		
		int addHp = 0;
		int addSprintDuration = 0;
		int addhintDuration = 0;
		int addHintCooldown = 0;
		
		if(gp.currentMap == gp.passedLevel) {
			addHp = 1;
			addSprintDuration = (gp.currentMap+1)*60;
			addhintDuration = 20;
			addHintCooldown = 20;
			if(gp.currentMap >= 2) {
				addSprintDuration += gp.currentMap - 1;
			}
			gp.passedLevel++;
		}
		
		String text = "Max HP: + " + addHp + 
						"/Sprint Duration: + " + 100*addSprintDuration/sprintDuration + "%" +
						"/Hint Duration: + " + 100*addhintDuration/hintDuration + "%" +
						"/Hint Cooldown: + " + 100*addHintCooldown/hintCooldown + "%";

		maxHealth += addHp;
		sprintDuration += addSprintDuration;
		hintCooldown += addHintCooldown;
		hintDuration += addhintDuration;
		gp.ui.setText(text);
		
	}
}
