package com.sk.game.ui;

import com.sk.game.utils.MouseHandler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class UIObject implements MouseListener, MouseMotionListener{
	
	protected float x, y;
	protected int width, height;
	public  Rectangle bounds;
	protected  boolean hovering = false;
	protected MouseHandler mh;

	public UIObject(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}
	
	public abstract void update() ;
	
	public abstract void render(Graphics g);
	
	public void onMouseMove(MouseEvent e) {
		if (bounds.contains(e.getX(), e.getY())) {
			hovering = true;
		} else {
			hovering = false;
		}
	}

	public void onMouseRelease(MouseEvent e){
		onMouseMove(e);
	}
	
	// Getters and setters
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHovering() {
		return hovering;
	}
	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	
	//IMPLEMENT MOUSELISTENER
	
		@Override
	  public void mouseClicked(MouseEvent e) {


	  }

	  @Override
	  public void mousePressed(MouseEvent e) {

	  }

	   @Override
	   public void mouseReleased(MouseEvent e) {

	   }

	   @Override
	    public void mouseEntered(MouseEvent e) {
			if(bounds.contains(e.getX(), e.getY())) {
				hovering = true;
			} else
				hovering = false;
	    }

	    //MOUSEEXITED
	  	@Override
	    public void mouseExited(MouseEvent e) {
	    	mouseEntered(e);
	    	// if(hovering)
	    		// System.out.println("Mouse is hovering");
	    }

	 	//MOUSEMOVE
	  	@Override
	  	public void mouseMoved(MouseEvent e) {}
	  
	  //SetMouse
	  public abstract void setMouse(MouseHandler mouse);
}

