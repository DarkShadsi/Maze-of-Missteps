package com.shadow.maze.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.shadow.maze.view.GameFrame;
import com.shadow.maze.model.Object;
import com.shadow.maze.view.GamePanel;

public class UI {
	GamePanel gp;
	GameFrame gameFrame;
	Graphics2D g2;
	Object speaker;
	
	public String currentDialogue = "";
	ArrayList<String> messages = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	String combinedString = "";
	int charIndex = 0;
	String text;
	
	BufferedImage heart_full_img, key_img, boots;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		this.gameFrame = gp.gameFrame;
		initHudImages();
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			drawMessages();
			drawLife();
			drawKeys();
			drawStats();
		}
		
		//DIALOGUE STATE
		else if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		
		//MENU STATE
		else if(gp.gameState == gp.menuState) {
			drawLife();
			drawKeys();
			drawStats();
		}
		
	}
	
	public void drawDialogueScreen() {
		//DRAW WINDOW
		int windowX = 4*gameFrame.GAMEUNITWIDTH;
		int windowY = gameFrame.GAMEUNITHEIGHT;
		int windowWidth = gameFrame.SCREENWIDTH - (8*gameFrame.GAMEUNITWIDTH);
		int windowHeight = 5*gameFrame.GAMEUNITHEIGHT;
		drawSubWindow(windowX, windowY, windowWidth, windowHeight);
		
		//DRAW DIALOGUE
		g2.setColor(Color.WHITE);
		

		int x = windowX + gameFrame.GAMEUNITWIDTH;
		int y = windowY + gameFrame.GAMEUNITHEIGHT;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30f));
		
		if(speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex] != null) {
			currentDialogue = speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex];
			
			//DRAWING DIALOGUES LETTER BY LETTER
			
			char characters[] = speaker.dialogues[speaker.dialogueSet][speaker.dialogueIndex].toCharArray();
			
			if(charIndex < characters.length) {
				
				String s = String.valueOf(characters[charIndex]);
				combinedString = combinedString + s;
				
				currentDialogue = combinedString;
				
				charIndex ++;
			}
			
			if(gp.keyH.enterPressed) {
				charIndex = 0;
				combinedString = "";
				
				if(gp.gameState == gp.dialogueState) {
					speaker.dialogueIndex ++;
					gp.keyH.enterPressed = false;
				}
			}
			
		}else {
			speaker.dialogueIndex = 0;
			
			if(gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
				gp.redo();
			}
			
		}
		
		for(String line: currentDialogue.split("/")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {

	    //SHADOW
	    Color shadowColor = new Color(0, 0, 0, 120);
	    g2.setColor(shadowColor);
	    g2.fillRoundRect(x + 5, y + 5, width, height, 30, 30);

	    //BACKGROUND
	    Color bgColor = new Color(20, 20, 20, 200);
	    g2.setColor(bgColor);
	    g2.fillRoundRect(x, y, width, height, 30, 30);

	    // BORDER
	    g2.setColor(Color.WHITE);
	    g2.setStroke(new BasicStroke(4));
	    g2.drawRoundRect(x, y, width, height, 30, 30);

	    //INNER BORDER
	    g2.setColor(new Color(200, 200, 200));
	    g2.setStroke(new BasicStroke(2));
	    g2.drawRoundRect(x + 4, y + 4, width - 8, height - 8, 26, 26);
	}

	
	void drawMessages() {
		g2.setColor(Color.WHITE);
		for(int i = 0; i < messages.size(); i++) {
			if(messages.get(i) != null) {
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28f));
				int x = gameFrame.GAMEUNITWIDTH;
				int y = 5 * gameFrame.GAMEUNITHEIGHT + (i * gameFrame.GAMEUNITHEIGHT);
				
				g2.drawString(messages.get(i), x, y);
				messageCounter.set(i, messageCounter.get(i)+1);
				
				if(messageCounter.get(i) >= 120) {
					messages.remove(i);
					messageCounter.remove(i);
				}
			}
			
		}
	}
	
	public void drawLife() {
		int x = gameFrame.GAMEUNITWIDTH/2;
		int y = gameFrame.GAMEUNITHEIGHT/2;
		for(int i = 0; i < gp.player.health; i++) {
			g2.drawImage(heart_full_img, x, y, null);
			x += gameFrame.GAMEUNITWIDTH;
		}
	}
	
	public void drawKeys() {
		int x = gameFrame.GAMEUNITWIDTH/2;
		int y = (int)(gameFrame.GAMEUNITHEIGHT*1.5);
		
		g2.drawImage(key_img, x, y, null);
		
		x += gameFrame.GAMEUNITWIDTH;
		y = (int)(gameFrame.GAMEUNITHEIGHT*2.3);
		g2.setPaint(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35f));
		g2.drawString("x" + gp.player.keys, x, y);
	}
	
	public void drawStats() {
		int width = gameFrame.GAMEUNITWIDTH*8;
		int height = gameFrame.GAMEUNITHEIGHT/4;
		int x = gameFrame.uTool.getCenter(gameFrame.COLS, 8) + gameFrame.GAMEUNITWIDTH;
		int y = gameFrame.GAMEUNITHEIGHT;
		double unit = (double)width/gp.player.sprintDuration;
		int sprintWidth = (int)((gp.player.sprintDuration - gp.player.sprintCounter) * unit);
		
		g2.drawImage(boots, x - gameFrame.GAMEUNITWIDTH, y - (gameFrame.GAMEUNITHEIGHT/2), null);
		
		g2.setPaint(Color.GRAY);
		g2.fillRect(x, y, width, height);
		
		g2.setPaint(Color.BLUE);
		g2.fillRect(x, y, sprintWidth, height);
		

		width = gameFrame.GAMEUNITWIDTH*5;
		x = gameFrame.uTool.getCenter(gameFrame.COLS, 5);
		y += gameFrame.GAMEUNITWIDTH;
		g2.setPaint(Color.GRAY);
		g2.fillRect(x, y, width, height);
		
		if(gp.player.searchPath) {
			unit = (double)width/gp.player.hintDuration;
			int hintWidth = (int)((gp.player.hintDuration - gp.player.hintCounter) * unit);
			g2.setPaint(Color.YELLOW);
			g2.fillRect(x, y, hintWidth, height);
			
		}else {
			unit = (double)width/gp.player.hintCooldown;
			int hintTimerWidth = (int)(gp.player.hintTimer * unit);
			g2.setPaint(Color.GREEN);
			g2.fillRect(x, y, hintTimerWidth, height);
		}
	}
	
	public void addMessage(String m) {
		messages.add(m);
		messageCounter.add(0);
	}
	
	public void setSpeaker(Object speaker) {
		this.speaker = speaker;
	}
	
	void initHudImages() {
		heart_full_img = gameFrame.uTool.scaleImage("/objects/heart_full.png", gameFrame.GAMEUNITWIDTH, gameFrame.GAMEUNITHEIGHT);
		key_img = gameFrame.uTool.scaleImage("/objects/key.png", gameFrame.GAMEUNITWIDTH, gameFrame.GAMEUNITHEIGHT);
		boots = gameFrame.uTool.scaleImage("/objects/boots.png", gameFrame.GAMEUNITWIDTH, gameFrame.GAMEUNITHEIGHT);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void drawUpgrades(Graphics2D g2d) {
		int x = gameFrame.GAMEUNITWIDTH * 12;
		int y = gameFrame.GAMEUNITHEIGHT * 7;
		
		g2d.setPaint(Color.WHITE);
		g2d.setFont(g2d.getFont().deriveFont(35f));
		for(String line: text.split("/")) {
			g2d.drawString(line, x, y);
			y += 40;
		}
	}
}
