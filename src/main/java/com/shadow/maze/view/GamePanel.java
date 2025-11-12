package com.shadow.maze.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.shadow.maze.util.Pathfinder;
import com.shadow.maze.util.AssetSetter;
import com.shadow.maze.util.CollisionHandler;
import com.shadow.maze.controller.KeyHandler;
import com.shadow.maze.controller.PointHandler;
import com.shadow.maze.util.UI;
import com.shadow.maze.model.Player;
import com.shadow.maze.model.Button;
import com.shadow.maze.model.Object;
import com.shadow.maze.util.TileManager;

public class GamePanel extends JPanel implements Runnable{
	public GameFrame gameFrame;
	
	private final int FPS = 60;
	private boolean drawSubMenu = false;
	Thread gameThread;
	BufferedImage subWindow;
	
	//HANDLERS
	public KeyHandler keyH;
	public TileManager tileM;
	public AssetSetter aSetter;
	public CollisionHandler colHandler;
	public UI ui;
	public Pathfinder pFinder;
	public PointHandler pHandler;
	
	//OBJECTS
	public Player player;
	public Object monsters[][] = new Object[5][10];
	public Object obj[][] = new Object[5][50];
	public Object debuffs[][] = new Object[5][10];
	public int currObjIndex[] = new int[50];
	//MAPS AND TILES
	public int maxMap = 10;
	public int currentMap = 0;
	public int ROWS = 50, COLS = 50;
	
	//GAME STATES
	public final int playState = 0;
	public final int dialogueState = 1;
	public final int menuState = 2;
	public int gameState = playState;
	public int passedLevel = 1;
	
	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.setLayout(null);
		this.setFocusable(true);
		
		initObjects();
		initButtons();
		
		this.addKeyListener(keyH);
		subWindow = gameFrame.uTool.scaleImage("/backgrounds/subMenu_bg.png", gameFrame.GAMEUNITWIDTH*5, gameFrame.GAMEUNITHEIGHT*7);
	}
	
	
	//***************** VARIABLES *********************************//
	
	
	
	//******************* SETTERS / INITIALIZERS ******************//
	void initObjects() {
		keyH = new KeyHandler(this);
		tileM = new TileManager(this);
		aSetter = new AssetSetter(this);
		pHandler = new PointHandler(this);
		player = new Player(this, keyH);
		tileM = new TileManager(this);
		colHandler = new CollisionHandler(this);
		pFinder = new Pathfinder(this);
		ui =new UI(this);
		
	}
	
	void initButtons() {
		double width = 1.3;
		double height = 1;
		
		//DRAW WHEN MENU BUTTON IS TOGGLED
		if(drawSubMenu) {
			drawSubMenu();
		}
		
		//DRAWING THE HAMBURGER MENU
		int x = gameFrame.SCREENWIDTH - (int)(gameFrame.GAMEUNITWIDTH*3);
		int y = (int)(gameFrame.GAMEUNITHEIGHT/2);
		width = gameFrame.GAMEUNITHEIGHT*1.5;
		height = gameFrame.GAMEUNITHEIGHT*1.5;
		
		Button hamMenu_btn= new Button(x, y, gameFrame, "hamMenu", (int)width, (int)height);
		hamMenu_btn.setHover(false);
		if(drawSubMenu) hamMenu_btn.changeIcon();
		hamMenu_btn.addActionListener((e)->{
			gameState = (gameState == playState)? menuState: playState;
			hamMenu_btn.changeIcon();
			drawSubMenu = (drawSubMenu)? false : true;
			redo();
		});
		
		this.add(hamMenu_btn);
	}
	
	void drawSubMenu() {
		double width = gameFrame.GAMEUNITWIDTH*2.5;
		double height = gameFrame.GAMEUNITHEIGHT*1.2;
		int x = gameFrame.SCREENWIDTH - (int)(gameFrame.GAMEUNITWIDTH*5.3);
		int y = (int)(gameFrame.GAMEUNITHEIGHT*1.5);
		Button home_btn = new Button(x, y, gameFrame, "subHome", (int)width, (int)height);
		home_btn.addActionListener((e)->{
			drawSubMenu = false;
			redo();
			gameFrame.showMainMenu();
		});
		
		y += (int)(1.2*gameFrame.GAMEUNITHEIGHT);
		Button menu_btn = new Button(x, y, gameFrame, "subMenu", (int)width, (int)height);
		menu_btn.addActionListener((e)->{
			gameFrame.showMenuPanel();
		});
		
		y += (int)(1.2*gameFrame.GAMEUNITHEIGHT);
		Button hint_btn = new Button(x, y, gameFrame, "subHint", (int)width, (int)height);
		hint_btn.addActionListener((e)->{
			drawSubMenu = false;
			redo();
			gameState = playState;
			if(player.searchPath) {
				player.searchPath = false;
				tileM.drawPath = false;
			}else {
				player.searchPath = true;
				tileM.drawPath = true;
				player.setPath();
			}
		});
		
		y += (int)(1.2*gameFrame.GAMEUNITHEIGHT);
		Button exit_btn = new Button(x, y, gameFrame, "subExit", (int)width, (int)height);
		exit_btn.addActionListener((e)->{
			System.exit(0);
		});
		
		this.add(exit_btn);
		this.add(home_btn);
		this.add(menu_btn);
		this.add(hint_btn);
	}
	
	void redo() {
		this.removeAll();
		initButtons();
		this.revalidate();
		this.repaint();
	}
	
	//************ UPDATE AND PAINT ******************************//
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//temp bg
		g2.fillRect(0, 0, gameFrame.SCREENWIDTH, gameFrame.SCREENHEIGHT);
		
		tileM.draw(g2);
		
		for(Object object: obj[currentMap]) {
			if(object != null) {
				object.draw(g2);
			}
		}
		
		for(Object m: monsters[currentMap]) {
			if(m != null) {
				m.draw(g2);
			}
		}
		
		if(drawSubMenu) {
			int x = (int)(gameFrame.SCREENWIDTH - gameFrame.GAMEUNITWIDTH*6.5);
			int y = gameFrame.GAMEUNITHEIGHT/2;
			
			g2.drawImage(subWindow, x, y, null);
		}
		
		player.draw(g2);
		ui.draw(g2);
		
	}
	
	public void update() {
		if(gameState == playState) {
			player.update();
			for(Object m: monsters[currentMap]) {
				if(m != null) {
					m.update();
				}
			}
		}
	}
	
	
	//************THREAD THAT HANDLES EVERY UPDATES ON THE SCREEN*************//
	
	public void startGameThread(int level) {
		currentMap = level-1;
		player.setDefaultValues();
		gameThread  = new Thread(this);
		gameThread.setDaemon(true);
		this.requestFocusInWindow();
		aSetter.setObjects();
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime(); 
		long currentTime;
		int timer = 0, drawCounter = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime )/drawInterval;
			timer += (currentTime - lastTime );
			lastTime = currentTime;
			
			if(delta >= 1) {

				update();
				repaint();
				
				delta--;
				drawCounter++;
			}
			
			if(timer >= 1000000000) {
				//System.out.println("FPS: " + drawCounter);
				drawCounter = 0;
				timer = 0;
			}
		}
	}
	
}
