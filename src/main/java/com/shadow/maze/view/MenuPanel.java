package com.shadow.maze.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.shadow.maze.model.Button;


public class MenuPanel extends JPanel{
	GameFrame gameFrame;
	Button button;
	BufferedImage mainBackground = null;
	int currPage = 1;
	
	int prevPanel;
	
	
	public MenuPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.setLayout(null);
		this.setDoubleBuffered(true);
		
		mainBackground = gameFrame.uTool.scaleImage("/backgrounds/menu_bg1.png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
		initButtons();
	}
	
	//**************** SETTERS / GETTERS / INITIALIZERS ***************//
	
	void initButtons() {
		int y = 16*gameFrame.GAMEUNITHEIGHT;
		int width = 140, height = 60;
		int x = gameFrame.uTool.getCenter(gameFrame.COLS, (width/gameFrame.GAMEUNITWIDTH));
		
		button = new Button(x, y, gameFrame, "next", width, height);
		button.addActionListener((e)->{
			
			if(currPage < 4) {
				currPage++;
				mainBackground = gameFrame.uTool.scaleImage("/backgrounds/menu_bg" + currPage + ".png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
				if(currPage == 4) {
					button.loadIcons("back", width, height);
				}
				repaint();
			}else if(prevPanel == 0) {
				gameFrame.showMainMenu();
				currPage = 1;
				mainBackground = gameFrame.uTool.scaleImage("/backgrounds/menu_bg" + currPage + ".png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
				button.loadIcons("next", width, height);
				
			}else {
				gameFrame.startGame(gameFrame.gamePanel.currentMap + 1);
				currPage = 1;
				mainBackground = gameFrame.uTool.scaleImage("/backgrounds/menu_bg" + currPage + ".png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
				button.loadIcons("next", width, height);
			}
			
		});
		
		
		this.add(button);
		
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
