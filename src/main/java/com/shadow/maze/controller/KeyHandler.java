package com.shadow.maze.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.shadow.maze.view.*;

public class KeyHandler implements KeyListener{
	public boolean upPressed, downPressed , leftPressed, rightPressed, enterPressed;
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//PLAYSTATE
		if(gp.gameState == gp.playState) {
			playState(code);
		}
		
		//DIALOGUESTATE
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		
	}
	
	void playState(int code) {
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = true;
		}else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}else if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}else if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}else if(code == KeyEvent.VK_H) {
			if(gp.player.searchPath) {
				gp.player.searchPath = false;
				gp.tileM.drawPath = false;
			}else {
				gp.player.searchPath = true;
				gp.tileM.drawPath = true;
				gp.player.setPath();
			}
		}
	}
	
	void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}else if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}


}
