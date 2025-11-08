package com.shadow.maze.util;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.shadow.maze.model.Tile;
import com.shadow.maze.view.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	public boolean drawPath = true;
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatus = new ArrayList<>();
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		//READ TILE DATE FILE
		loadTileData("/data/maps/tiledata_1.txt");
		

		mapTileNum = new int[gp.maxMap][gp.COLS][gp.ROWS];
				
		
		//LOAD MAPS
		loadMap("/data/maps/maze1.txt", 0);
		loadMap("/data/maps/maze2.txt", 1);
	}
	
	public void getTileImage() {
		
		
		for(int i = 0; i < fileNames.size(); i++) {
			String fileName;
			boolean collision;
			
			//GET FILE NAME
			fileName = fileNames.get(i);
			
			//GET COLLISION
			if(collisionStatus.get(i).equalsIgnoreCase("true")) {
				collision = true;
			}else {
				collision = false;
			}
			
			setUp(i, fileName, collision);
			
		}
		
		
		
	}
	
	public void setUp(int index, String path, boolean collision) {
		
		
		UtilityTool uTool = gp.gameFrame.uTool;
		int width = gp.gameFrame.GAMEUNITWIDTH;
		int height = gp.gameFrame.GAMEUNITHEIGHT;
		
		tile[index] = new Tile();
		tile[index].image = uTool.scaleImage("/tiles/" + path, width, height);
		tile[index].collision = collision;
	}
	
	public void loadTileData(String path) {
		
		InputStream is = getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		//GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
		
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				fileNames.add(line);
				collisionStatus.add(br.readLine());
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//INITIALIZING THE ARRAY BASED ON THE FILENAME SIZE
		tile = new Tile[fileNames.size()];
		getTileImage();
		
	}
	
	public void loadMap(String filePath, int mapNum) {
		
		//GET MAX WORLD COL AND ROW
		InputStream is = getClass().getResourceAsStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		try {
			String maxTile[] = br.readLine().split(" ");
			
			gp.COLS = maxTile.length;
			gp.ROWS = maxTile.length;
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			is = getClass().getResourceAsStream(filePath);
			br = new BufferedReader(new InputStreamReader(is));
			
			int i = 0, j = 0;
			
			while(i < gp.COLS && j < gp.ROWS) {
				String line = br.readLine();
				
				while(i < gp.COLS) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[i]);
					mapTileNum[mapNum][i][j] = num;
					i++;
				}
				if(i == gp.COLS) {
					i = 0;
					j++;
				}
			}
			
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g2) {
		int width = gp.gameFrame.GAMEUNITWIDTH;
		int height = gp.gameFrame.GAMEUNITHEIGHT;
		
		for(int i = 0; i < gp.COLS; i++) {
			for(int j = 0; j < gp.ROWS; j++) {
				
				
				int worldX = i*width;
				int worldY = j*height;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				if(worldX + 2*width > gp.player.worldX - gp.player.screenX &&
					worldX - 2*width < gp.player.worldX + gp.player.screenX && 
					worldY + 2*height > gp.player.worldY - gp.player.screenY &&
					worldY - 2*height < gp.player.worldY + gp.player.screenY) {

					g2.drawImage(tile[mapTileNum[gp.currentMap][i][j]].image, screenX, screenY, null);
					
				}
			}
		}
		
		if(drawPath) {
			
			ArrayList<Node> pathListCopy = new ArrayList<>(gp.pFinder.pathList);
			
			for(Node n: pathListCopy) {
				int worldX = n.col * width;
				int worldY = n.row * height;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				g2.setColor(new Color(0, 0, 255, 65));
				g2.fillRect(screenX, screenY, width, height);
				
			}
		}
		
	}
}
