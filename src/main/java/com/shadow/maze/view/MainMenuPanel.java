package com.shadow.maze.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.shadow.maze.model.Button;


public class MainMenuPanel extends JPanel{
	GameFrame gameFrame;
	Button menu, startButton, exitButton;
	BufferedImage mainBackground = null;
	
	
	public MainMenuPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.setLayout(null);
		this.setDoubleBuffered(true);
		
		mainBackground = gameFrame.uTool.scaleImage("/backgrounds/main_bg.png", gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
		initButtons();
	}
	
	//**************** SETTERS / GETTERS / INITIALIZERS ***************//
	
	void initButtons() {
		int y = 13*gameFrame.GAMEUNITHEIGHT;
		int width = 140, height = 60;
		int x = gameFrame.uTool.getCenter(gameFrame.COLS, (width/gameFrame.GAMEUNITWIDTH)) - 4*gameFrame.GAMEUNITWIDTH;
		
		menu = new Button(x, y, gameFrame, "menu", width, height);
		menu.addActionListener((e)->{
			gameFrame.showMenuPanel(0);
		});
		
		x += 4*gameFrame.GAMEUNITWIDTH;
		startButton = new Button(x, y, gameFrame, "new", width, height);
		startButton.addActionListener((e)->{
			gameFrame.showLevelsPanel();
		});
		
		x += 4*gameFrame.GAMEUNITWIDTH;
		exitButton  = new Button(x, y, gameFrame, "exit", width, height);
		exitButton.setBounds(x, y, width, height);
		exitButton.addActionListener((e)->{
			System.exit(0);
		});
		
		this.add(menu);
		this.add(startButton);
		this.add(exitButton);
		
	}
	
	//******************** PAINT ***********************//
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.drawImage(mainBackground, 0, 0, gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT, null);
		
	}

	
}
