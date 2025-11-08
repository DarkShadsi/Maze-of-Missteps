package com.shadow.maze.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.shadow.maze.util.Pathfinder;
import com.shadow.maze.util.AssetSetter;
import com.shadow.maze.util.CollisionHandler;
import com.shadow.maze.controller.KeyHandler;
import com.shadow.maze.controller.PointHandler;
import com.shadow.maze.util.UI;
import com.shadow.maze.model.Player;
import com.shadow.maze.model.Object;
import com.shadow.maze.util.TileManager;

public class GamePanel extends JPanel implements Runnable{
	public GameFrame gameFrame;
	
	private final int FPS = 60;
	Thread gameThread;
	
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
	public int currObjIndex[] = new int[50];
	//MAPS AND TILES
	public int maxMap = 10;
	public int currentMap = 0;
	public int ROWS = 50, COLS = 50;
	
	//GAME STATES
	public final int playState = 0;
	public final int dialogueState = 1;
	public int gameState = playState;
	public int passedLevel = 1;
	
	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.setLayout(null);
		this.setFocusable(true);
		
		initObjects();
		this.addKeyListener(keyH);
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
		aSetter.setObjects(currentMap);
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
				System.out.println("FPS: " + drawCounter);
				drawCounter = 0;
				timer = 0;
			}
		}
	}
	
}
