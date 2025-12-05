package com.shadow.maze.util;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.shadow.maze.model.Object;
import com.shadow.maze.view.GameFrame;
import com.shadow.maze.view.GamePanel;

public class UtilityTool {
	GameFrame gameFrame;
	Random randomizer = new Random();
	
	public UtilityTool(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
	
	public BufferedImage scaleImage(String imagePath, int width, int height) {
		
			BufferedImage scaledImage = null;
			BufferedImage original = null;
			try {
				original = ImageIO.read(getClass().getResourceAsStream("/images" + imagePath));
				scaledImage = new BufferedImage(width, height, original.getType());
				Graphics2D g2 = scaledImage.createGraphics();
				g2.drawImage(original, 0, 0, width, height, null);
				g2.dispose();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return scaledImage;
	}
	

	public int getCenter(int storageWidthUnits, int objectWidthUnits) {
	    return ((storageWidthUnits*gameFrame.GAMEUNITWIDTH) - (objectWidthUnits*gameFrame.GAMEUNITWIDTH)) / 2;
	}
	
	public int getCenterOfText(String text, int width, Graphics2D g2) {		//Method for centering strings
		return (width - (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
	}
	
	public int getNearest(int startX, int startY, ArrayList<Point> goals) {
		int index = 0;
		int shortest = 999;
		for(int i = 0; i < goals.size(); i++) {
			if(goals.get(i) != null) {
				int distance = getCost(startX, startY, goals.get(i));
				if(distance < shortest) {
					shortest = distance;
					index = i;
				}
			}
		}
		return index;
	}
	
	int getCost(int startX, int startY, Point p) {
		
		int xDistance = Math.abs(p.x - startX);
		int yDistance = Math.abs(p.y - startY);
		int cost = xDistance + yDistance;
		
		return cost;
		
	}
	
	public void teleportObject(Object obj, GamePanel gamePanel) {
		int x, y;
		int tileNum;
		int mapAreas[] = gamePanel.pHandler.mapAreas[gamePanel.currentMap];
		do {
			x = randomizer.nextInt(mapAreas[1] - mapAreas[0]) + mapAreas[0];
			y = randomizer.nextInt(mapAreas[3] - mapAreas[2]) + mapAreas[2];
			tileNum = gamePanel.tileM.mapTileNum[gamePanel.currentMap][x][y];
		}while(gamePanel.tileM.tile[tileNum].collision);

		obj.worldX = x * gameFrame.GAMEUNITWIDTH;
		obj.worldY = y * gameFrame.GAMEUNITHEIGHT;
	}
	

}
