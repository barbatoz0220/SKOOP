package com.sk.game.ui;

import com.sk.game.states.GameStateManager;
import com.sk.game.utils.MouseHandler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class UIImageButton extends UIObject {

	private BufferedImage[] images;
	private UIManager ui;
	private Rectangle bounds;
	private GameStateManager gsm;

	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, GameStateManager gsm) {
		super(x, y, width, height);
		this.images = images;
		this.gsm = gsm;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics g) {
		if (isHovering())
			g.drawImage(images[0], (int) x, (int) y, width, height, null); // Image when mouse's not hovering over button
		else
			g.drawImage(images[1], (int) x, (int) y, width, height, null); // Image when mouse's hovering over button
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	public GameStateManager getGsm() {
		return gsm;
	}

	public void setGsm(GameStateManager gsm) {
		this.gsm = gsm;
	}

	//SetMouse
	@Override
	public void setMouse(MouseHandler mouse) {
		if (bounds.contains(mouse.getMouseX(), mouse.getMouseY())) {
			hovering = true;
		} else {
			hovering = false;
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}