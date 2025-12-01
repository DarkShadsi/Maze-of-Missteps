package com.shadow.maze.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.shadow.maze.model.Button;


public class ResultPanel extends JPanel{
	GameFrame gameFrame;
	Button backButton;
	BufferedImage mainBackground = null;
	
	public boolean drawText = false;
	boolean passed;
	
	public ResultPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.setLayout(null);
		this.setDoubleBuffered(true);
		
		initButton();
	}
	
	//**************** SETTERS / GETTERS / INITIALIZERS ***************//
	
	void initButton() {
		int y = 16*gameFrame.GAMEUNITHEIGHT;
		int width = 140, height = 60;
		int x = gameFrame.uTool.getCenter(gameFrame.COLS, (width/gameFrame.GAMEUNITWIDTH)) + gameFrame.GAMEUNITWIDTH/3;
		
		backButton = new Button(x, y, gameFrame, "back", width, height);
		backButton.addActionListener((e)->{
			gameFrame.showMainMenu();
		});
		
		
		this.add(backButton);
		
	}
	
	public void setResult(boolean passed) {
		this.passed = passed;
		if(passed) {
			mainBackground = gameFrame.uTool.scaleImage("/backgrounds/passed_bg.png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
		}else {
			mainBackground = gameFrame.uTool.scaleImage("/backgrounds/failed_bg.png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
		}
	}
	
	public void drawUpgrades() {
		drawText = true;
		repaint();
	}
	
	//******************** PAINT ***********************//
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.drawImage(mainBackground, 0, 0, gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT, null);
		if(drawText && passed) {
			gameFrame.gamePanel.ui.drawUpgrades(g2);
			drawText = false;
		}
		
	}

	
}
