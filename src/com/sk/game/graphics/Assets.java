package com.sk.game.graphics;


import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Assets {
	public static BufferedImage[] buttonStart, buttonEnd, buttonPause, buttonOptions, background;

	public static void addButton() {
		Sprite backgroundSprite = new Sprite(ImageLoader.loadImage("/font/background.jpg"));

		Sprite playButtonNormal = new Sprite(ImageLoader.loadImage("/font/play.png"));
		Sprite playButtonChosen = new Sprite(ImageLoader.loadImage("/font/play_chosen.png"));

		Sprite optionsButtonNormal = new Sprite(ImageLoader.loadImage("/font/options.png"));
		Sprite optionsButtonChosen = new Sprite(ImageLoader.loadImage("/font/options_chosen.png"));

		Sprite exitButtonNormal = new Sprite(ImageLoader.loadImage("/font/exit.png"));
		Sprite exitButtonChosen = new Sprite(ImageLoader.loadImage("/font/exit_chosen.png"));

		Sprite pauseButtonNormal = new Sprite(ImageLoader.loadImage("/font/pause.png"));
		Sprite pauseButtonChosen = new Sprite(ImageLoader.loadImage("/font/pause_chosen.png"));

		background = new BufferedImage[1];
		buttonStart = new BufferedImage[2];
		buttonPause = new BufferedImage[3];
		buttonEnd = new BufferedImage[4];
		buttonOptions = new BufferedImage[5];

		// Start Button
		buttonStart[1] = playButtonNormal.crop(0,0,756,320);
		buttonStart[0] = playButtonChosen.crop(0,0,756,320);

		// Options Button
		buttonPause[1] = optionsButtonNormal.crop(0,0,756,320);
		buttonPause[0] = optionsButtonChosen.crop(0,0,756,320);

		// Exit Button
		buttonEnd[1] = exitButtonNormal.crop(0,0,756,320);
		buttonEnd[0] = exitButtonChosen.crop(0,0,756,320);;

		background[0] = backgroundSprite.crop(0,0,1440,900);
	}
}
