package com.sk.game.handler;

import com.sk.game.GamePanel;
import com.sk.game.states.GameStateManager;
import com.sk.game.tiles.TileManager;
import com.sk.game.utils.KeyHandler;
import com.sk.game.utils.MouseHandler;

public class Handler {
	
	private GamePanel game;
	private TileManager tm;
	private GameStateManager gsm;

	//Constructor
	public Handler(GamePanel game) {
		this.game = game	;
	}
	public Handler(GameStateManager gsm) {
		this.gsm = gsm;
	}
	public GameStateManager getGSM() {
		return gsm;
	}
	public void setGSM(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	//GetGamePanel
	public GamePanel getGame() {
		return game;
	}
	
	//SetGame
	public void setGame(GamePanel game) {
		this.game = game;
	}

	//GetWorld
	public TileManager getMap() {
		return tm;
	}

	//SetWorld
	public void setMap(TileManager tm) {
		this.tm = tm;
	}
	
	//GetWidth
	public int getWidth() {
		return game.getWidth()	;
	}
	//GetHeight
	public int getHeight() {
		return game.getHeight()	;
	}
		
	//GetKeyManager
	public KeyHandler getKey() {
			return game.getKey()	;
		}
		//GetMouseManager
		public MouseHandler getMouse() {
			return game.getMouse();
		}
	//getRunning
	public boolean getRunning() {
		return game.getRunning();
	}

	//GameStop
	public void gameStop() {
		game.setRunning(false);
		System.exit(0);
	}
}
