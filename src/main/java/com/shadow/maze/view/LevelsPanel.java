package com.shadow.maze.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.shadow.maze.model.Button;
import com.shadow.maze.model.LevelButton;


public class LevelsPanel extends JPanel{
	GameFrame gameFrame;
	Button backButton;
	BufferedImage mainBackground = null;
	boolean messageOn = false;
	int messageCounter = 0;
	
	
	public LevelsPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.setLayout(null);
		this.setDoubleBuffered(true);
		
		mainBackground = gameFrame.uTool.scaleImage("/backgrounds/levels_bg.png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
		initButtons();
		addLevels();
	}
	
	//**************** SETTERS / GETTERS / INITIALIZERS ***************//
	
	void initButtons() {
		int y = 15*gameFrame.GAMEUNITHEIGHT;
		int width = 140, height = 60;
		int x = gameFrame.uTool.getCenter(gameFrame.COLS, (width/gameFrame.GAMEUNITWIDTH));
		
		backButton = new Button(x, y, gameFrame, "back", width, height);
		backButton.addActionListener((e)->{
			gameFrame.showMainMenu();
		});
		
		
		this.add(backButton);
	}
	
	
	void addLevels() {
		
		int size = 3 * gameFrame.GAMEUNITWIDTH;
		int x = 11 * gameFrame.GAMEUNITWIDTH;
		int y = 5 * gameFrame.GAMEUNITHEIGHT;
		
		for(int i = 1; i < 6; i++) {
			this.add(new LevelButton(x, y, gameFrame, size, size, i));
			
			x += (4 * gameFrame.GAMEUNITWIDTH);
			if(i == 3) {
				x = 13 * gameFrame.GAMEUNITWIDTH;
				y += (4 * gameFrame.GAMEUNITHEIGHT);
			}
		}
		
		
	}
	
	public void drawMessage() {
		messageOn = true;
		new Thread(() -> {
		    try {
				repaint();
		        Thread.sleep(1000);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
			messageOn = false;
			repaint();
		}).start();
	}
	
	//******************** PAINT ***********************//
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.drawImage(mainBackground, 0, 0, gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT, null);
		
		if(messageOn){
			g2.setColor(Color.RED);
			g2.setFont(g2.getFont().deriveFont(30f));
			
			String message = "Pass previous level first!";
			int x = gameFrame.uTool.getCenterOfText(message, gameFrame.SCREENWIDTH, g2);
			int y = 13 * gameFrame.GAMEUNITHEIGHT;
			
			g2.drawString(message, x, y);
		}
		
		
		
		
	}

	
}
