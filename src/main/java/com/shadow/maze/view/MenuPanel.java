package com.shadow.maze.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.shadow.maze.model.Button;


public class MenuPanel extends JPanel{
	GameFrame gameFrame;
	Button backButton;
	BufferedImage mainBackground = null;
	
	int prevPanel;
	
	
	public MenuPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.setLayout(null);
		this.setDoubleBuffered(true);
		
		mainBackground = gameFrame.uTool.scaleImage("/backgrounds/menu_bg.png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
		initButtons();
	}
	
	//**************** SETTERS / GETTERS / INITIALIZERS ***************//
	
	void initButtons() {
		int y = 13*gameFrame.GAMEUNITHEIGHT;
		int width = 140, height = 60;
		int x = gameFrame.uTool.getCenter(gameFrame.COLS, (width/gameFrame.GAMEUNITWIDTH));
		
		backButton = new Button(x, y, gameFrame, "back", width, height);
		backButton.addActionListener((e)->{
			if(prevPanel == 0) {
				gameFrame.showMainMenu();
			}else {
				gameFrame.startGame(gameFrame.gamePanel.currentMap + 1);
			}
		});
		
		
		this.add(backButton);
		
	}
	
	public void setPrevPanel(int prevPanel) {
		this.prevPanel = prevPanel;
	}
	
	//******************** PAINT ***********************//
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.drawImage(mainBackground, 0, 0, gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT, null);
		
	}

	
}
