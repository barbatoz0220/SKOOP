package com.sk.game.ui;

import com.sk.game.states.GameStateManager;
import com.sk.game.utils.KeyHandler;
import com.sk.game.utils.MouseHandler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class UIManager {
	
	private GameStateManager game;
	private ArrayList<UIObject> objects;

	public UIManager(GameStateManager game){
		this.game = game;
		objects = new ArrayList<>();
	}
	
	public void update(){
		for(UIObject o : objects)
			o.update();
	}
	
	public void render(Graphics g){
		for(UIObject o : objects)
			o.render(g);
	}
	
	public void onMouseMove(MouseEvent e){
		for(UIObject o : objects)
			o.onMouseMove(e);
			
	}
	
	public void onMouseRelease(MouseEvent e){
		for(UIObject o : objects)
			o.onMouseRelease(e);
	}
	
	public void addObject(UIObject o){
		objects.add(o);
	}
	public void removeObject(UIObject o){
		objects.remove(o);
	}

	public GameStateManager getGame() {
		return game;
	}
	public void setGame(GameStateManager game) {
		this.game = game;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}
	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		// TODO Auto-generated method stub
		for(Object o : objects) {
			mouse.addMouseListener((MouseListener)o);
			((UIImageButton) o).setMouse(mouse);
		}
	}
}