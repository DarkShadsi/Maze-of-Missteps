package com.shadow.maze.util;

import com.shadow.maze.view.GamePanel;
import com.shadow.maze.model.Object;

public class CollisionHandler {
	GamePanel gp;
	int unitWidth, unitHeight;

	public CollisionHandler(GamePanel gp) {
		this.gp = gp;
		unitWidth = gp.gameFrame.GAMEUNITWIDTH;
		unitHeight = gp.gameFrame.GAMEUNITHEIGHT;
	}

	public void checkTile(Object entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBotWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX/unitWidth;
		int entityRightCol = entityRightWorldX/unitWidth;
		int entityTopRow = entityTopWorldY/unitHeight;
		int entityBotRow = entityBotWorldY/unitHeight;



		int tileNum1, tileNum2;
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/unitHeight;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBotRow = (entityBotWorldY + entity.speed)/unitHeight;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBotRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBotRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/unitWidth;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBotRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/unitWidth;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBotRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			break;
		}
	}

	public int checkObject(Object entity, boolean player) {
		int index = 999;

		for(int i = 0; i < gp.obj[gp.currentMap].length; i++) {
			if(gp.obj[gp.currentMap][i] != null) {

				//Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				//Get object's solid area position
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

				switch(entity.direction) {
					case "up":
						entity.solidArea.y -= entity.speed; break;
					case "down":
						entity.solidArea.y += entity.speed; break;
					case "left":
						entity.solidArea.x -= entity.speed; break;
					case "right":
						entity.solidArea.x += entity.speed; break;
				}
				
				if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
					if(gp.obj[gp.currentMap][i].collision) {
						entity.collisionOn = true;
					}
					if(player) {
						index = i;
					}
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;

				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}
	
	public boolean checkOverlapObject(int x, int y) {
		boolean hasOverlap = false;
		
		for(Object obj: gp.obj[gp.currentMap]) {
			if(obj != null) {
				if(obj.worldX == x*unitWidth && obj.worldY == y*unitHeight) {
					hasOverlap = true;
				}
			}
		}
		
		return hasOverlap;
	}

	public int checkEntity(Object entity, Object[][] target) {
		int index = 999;

		for(int i = 0; i < target[gp.currentMap].length; i++) {
			if(target[gp.currentMap][i] != null) {

				//Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				//Get target's solid area position
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

				switch(entity.direction) {
					case "up":
						entity.solidArea.y -= entity.speed;
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						break;
					case "right":
						entity.solidArea.x += entity.speed;
						break;
					}

				if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea) && entity != target[gp.currentMap][i]) {
					entity.collisionOn = true;
					index = i;
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;

				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}
	
	public boolean checkPlayer(Object entity) {
		boolean isHit = false;
		
		//Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		//Get target's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		switch(entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed; break;
			case "down":
				entity.solidArea.y += entity.speed; break;
			case "left":
				entity.solidArea.x -= entity.speed;break;
			case "right":
				entity.solidArea.x += entity.speed; break;
		}
		

		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			isHit = true;
		}
		

		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return isHit;
	}
}
