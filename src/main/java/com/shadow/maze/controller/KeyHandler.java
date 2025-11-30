package com.shadow.maze.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.shadow.maze.view.*;

public class KeyHandler implements KeyListener{
	public boolean upPressed, downPressed , leftPressed, rightPressed, enterPressed, shiftPressed;
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
		
		//MENUSTATE
		else if(gp.gameState == gp.menuState) {
			menuState(code);
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
		}else if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}else if(code == KeyEvent.VK_O) {
			gp.hamMenu_btn.doClick();
		}else if(code == KeyEvent.VK_R) {
			int currentMap = gp.currentMap;
			gp.stopGame();
			gp.gameFrame.startGame(currentMap + 1);
		}
	}
	
	void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
	}
	
	void menuState(int code) {
		if(code == KeyEvent.VK_O) {
			gp.hamMenu_btn.doClick();
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
		}else if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}


}
