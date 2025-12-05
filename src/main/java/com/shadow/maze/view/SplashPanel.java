package com.shadow.maze.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SplashPanel extends JPanel {
    
    private GameFrame gameFrame;
    private float alpha = 0f;
    private boolean fadingIn = true;
    private Timer timer;
    private int displayTime = 0;
    private final int FADE_DURATION = 60;
    private final int DISPLAY_DURATION = 70;
    
    private String gameName = "Maze of Missteps";
    private String qoute = "In Every Turn Lies Another Mistakes";
    
    public SplashPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        setBackground(Color.BLACK);
        setFocusable(false);
    }
    
    //FADE IN
    public void startSplash() {
        alpha = 0f;
        fadingIn = true;
        displayTime = 0;
        
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSplash();
            }
        });
        timer.start();
    }
    
    private void updateSplash() {
        if (fadingIn) {
            alpha += 1f / FADE_DURATION;
            if (alpha >= 1f) {
                alpha = 1f;
                fadingIn = false;
            }
        } else if (displayTime < DISPLAY_DURATION) {
            displayTime++;
        } else {
            // FADE OUT
            alpha -= 1f / FADE_DURATION;
            if (alpha <= 0f) {
                alpha = 0f;
                timer.stop();
                gameFrame.showMainMenu();
            }
        }
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        //ENABLE ANTI-ALIASING
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        int alphaValue = (int) (255 * alpha);
        Color fadeColor = new Color(255, 255, 255, alphaValue);
        
        Font gameFont = new Font("Arial", Font.BOLD, gameFrame.SCREENHEIGHT / 11);
        g2.setFont(gameFont);
        g2.setColor(fadeColor);
        
        int gameWidth = g2.getFontMetrics().stringWidth(gameName);
        g2.drawString(gameName, centerX - gameWidth / 2, centerY - gameFrame.SCREENHEIGHT / 15);
        
        Font studioFont = new Font("Arial", Font.PLAIN, gameFrame.SCREENHEIGHT / 30);
        g2.setFont(studioFont);
        g2.setColor(new Color(200, 200, 200, alphaValue));
        
        int studioWidth = g2.getFontMetrics().stringWidth(qoute);
        g2.drawString(qoute, centerX - studioWidth / 2, centerY + gameFrame.SCREENHEIGHT / 30);
        
    }
    
   //----------------------- SETTERS / GETTERS --------------------------------------------//
    
    public void setGameName(String name) {
        this.gameName = name;
    }
    
    public void setStudioName(String name) {
        this.qoute = name;
    }
}