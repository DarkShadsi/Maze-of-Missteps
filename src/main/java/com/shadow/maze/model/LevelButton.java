package com.shadow.maze.model;

import javax.swing.*;

import com.shadow.maze.view.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LevelButton extends JButton{
	GameFrame gameFrame;
	ImageIcon icon1, icon2;
	int width1, height1;
	int width2, height2;

    public LevelButton(int x, int y, GameFrame gameFrame, int width, int height, int level) {
    	this.gameFrame = gameFrame;
        loadIcons(width, height, level);
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
        addListener(level);
        
    }
    
    void loadIcons(int width, int height, int level) {
    	//NORMAL
        BufferedImage img = gameFrame.uTool.scaleImage("/buttons/lvlbtn_" + level + ".png", width, height);
        width1 = img.getWidth();
        height1 = img.getHeight();
        icon1 = new ImageIcon(img);
        
        //HOVERED
        BufferedImage img2 = gameFrame.uTool.scaleImage("/buttons/lvlbtn_hov_" + level + ".png", width, height);
        width2 = img2.getWidth();
        height2 = img2.getHeight();
        icon2 = new ImageIcon(img2);
    }
    
    void addListener(int level) {
    	this.addMouseListener(new MouseAdapter() {
    		public void mouseEntered(MouseEvent e) {
    			setIcon(icon2);
    		}
    		
    		public void mouseExited(MouseEvent e) {
    			setIcon(icon1);
    		}
		});
    	
    	this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.startGame(level);
			}
		});
    }
    
}
