package com.shadow.maze.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.shadow.maze.util.UtilityTool;

public class GameFrame extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel panelHolder;
    private MainMenuPanel mainMenuPanel;
    public GamePanel gamePanel;
    private MenuPanel menuPanel;
    private LevelsPanel levelsPanel;
    private ResultPanel resultPanel;
    private SplashPanel splashPanel;
	public UtilityTool uTool;
    
    public int SCREENWIDTH;
    public int SCREENHEIGHT;
    //SCREEN RATIO 16:9
    public final int ROWS = 18;
    public final int COLS = 32;
    public int GAMEUNITWIDTH;
    public int GAMEUNITHEIGHT;
    
    public GameFrame() {
        initFrame();
        showSplashPanel();
        setVisible(true);
        initTools();
        initPanels();
    }
    
    //******************  SETTERS / GETTERS / INITIALIZERS ******************//
    
    private void initFrame() {
    	setUndecorated(true);  
        setResizable(false);
        setScreenResolutions();
        cardLayout = new CardLayout();
        panelHolder = new JPanel(cardLayout);
        splashPanel = new SplashPanel(this);
        addPanel(splashPanel, "SplashPanel");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(panelHolder);
    }
    
    private void initPanels() {
        mainMenuPanel = new MainMenuPanel(this);
        gamePanel = new GamePanel(this);
        menuPanel = new MenuPanel(this);
        levelsPanel = new LevelsPanel(this);
        resultPanel = new ResultPanel(this);
        
        addPanel(mainMenuPanel, "MainMenu");
        addPanel(gamePanel, "GamePanel");
        addPanel(menuPanel, "MenuPanel");
        addPanel(levelsPanel, "LevelsPanel");
        addPanel(resultPanel, "ResultPanel");
    }
    
    private void initTools() {
	   	 uTool = new UtilityTool(this);
    }
    
    private void setScreenResolutions() {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREENWIDTH = screenSize.width;
        SCREENHEIGHT = screenSize.height;
        
        GAMEUNITWIDTH = SCREENWIDTH/COLS;
        GAMEUNITHEIGHT = SCREENHEIGHT/ROWS;
        
        System.out.println("Detected resolution: " + SCREENWIDTH + "x" + SCREENHEIGHT);
        System.out.println("Unit size: " + GAMEUNITWIDTH + " x " + GAMEUNITHEIGHT);

        setSize(SCREENWIDTH, SCREENHEIGHT);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }
    
    //******************** HELPER METHODS ********************//
    
    public void addPanel(JPanel panel, String name) {
        panelHolder.add(panel, name);
    }
    
    public void showPanel(String name) {
        cardLayout.show(panelHolder, name);
    }
    
    public void showMainMenu() {
        showPanel("MainMenu");
    }
    
    public void showMenuPanel(int prevPanel) {
    	menuPanel.setPrevPanel(prevPanel);
    	showPanel("MenuPanel");
    }
    
    public void showLevelsPanel() {
    	showPanel("LevelsPanel");
    }
    
    public void showResultsPanel(boolean passed) {
    	resultPanel.setResult(passed);
    	resultPanel.drawUpgrades();
    	showPanel("ResultPanel");
    }
    
    public void showSplashPanel() {
    	showPanel("SplashPanel");
    	splashPanel.startSplash();
    }

    public void startGame(int level) {
    	gamePanel.stopGame();
    	if(gamePanel.wasPaused) {
    		gamePanel.currentMap = gamePanel.lastMap;
    		gamePanel.wasPaused = false;
    	}
    	if(level <= gamePanel.passedLevel + 1) {
            showPanel("GamePanel");
            gamePanel.startGameThread(level);
    	}else {
    		levelsPanel.drawMessage();
    	}
    }
}
