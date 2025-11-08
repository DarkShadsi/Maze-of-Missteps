package com.shadow.maze.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.shadow.maze.view.GameFrame;

public class UtilityTool {
	GameFrame gameFrame;
	
	public UtilityTool(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
	
	public BufferedImage scaleImage(String imagePath, int width, int height) {
		
			BufferedImage scaledImage = null;
			BufferedImage original = null;
			try {
				original = ImageIO.read(getClass().getResourceAsStream("/images" + imagePath));
				scaledImage = new BufferedImage(width, height, original.getType());
				Graphics2D g2 = scaledImage.createGraphics();
				g2.drawImage(original, 0, 0, width, height, null);
				g2.dispose();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return scaledImage;
	}
	

	public int getCenter(int storageWidthUnits, int objectWidthUnits) {
	    return ((storageWidthUnits*gameFrame.GAMEUNITWIDTH) - (objectWidthUnits*gameFrame.GAMEUNITWIDTH)) / 2;
	}
	
	public int getCenterOfText(String text, int width, Graphics2D g2) {		//Method for centering strings
		return (width - (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth())/2;
	}

}
