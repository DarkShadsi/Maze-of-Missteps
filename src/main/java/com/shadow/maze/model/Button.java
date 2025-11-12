package com.shadow.maze.model;

import javax.swing.*;

import com.shadow.maze.view.GameFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button extends JButton{
	GameFrame gameFrame;
	ImageIcon icon1, icon2;
	int width1, height1;
	int width2, height2;
	boolean hover = true;

    public Button(int x, int y, GameFrame gameFrame, String imageName, int width, int height) {
    	this.gameFrame = gameFrame;
        loadIcons(imageName, width, height);
        setBounds(
            x, 
            y, 
            width1, 
            height1
        );
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setIcon(icon1);
        addListener();
        
    }
    
    void loadIcons(String imageName, int width, int height) {
    	//NORMAL
        BufferedImage img = gameFrame.uTool.scaleImage("/buttons/btn_" + imageName + ".png", width, height);
        width1 = img.getWidth();
        height1 = img.getHeight();
        icon1 = new ImageIcon(img);
        
        //HOVERED
        BufferedImage img2 = gameFrame.uTool.scaleImage("/buttons/btn_hov_" + imageName + ".png", width, height);
        width2 = img2.getWidth();
        height2 = img2.getHeight();
        icon2 = new ImageIcon(img2);
    }
    
    void addListener() {
    	this.addMouseListener(new MouseAdapter() {
    		public void mouseEntered(MouseEvent e) {
    			if(hover) setIcon(icon2);
    		}
    		
    		public void mouseExited(MouseEvent e) {
    			if(hover) setIcon(icon1);
    		}
		});
    }
    
    public void setHover(boolean hover) {
    	this.hover = hover;
    }
    
    public void changeIcon() {
    	setIcon((this.getIcon().equals(icon1))? icon2: icon1);
    }
    
}
