package com.sk.game.states;

import com.sk.game.graphics.Assets;
import com.sk.game.ui.UIImageButton;
import com.sk.game.ui.UIManager;
import com.sk.game.ui.UIObject;
import com.sk.game.utils.KeyHandler;
import com.sk.game.utils.MouseHandler;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuState extends GameState {

    private UIObject startButton, exitButton, optionsButton;
    private UIManager uiManager;

    public MenuState(GameStateManager gsm) {

        super(gsm);

        // Configure the Start Button
        startButton = new UIImageButton(200, 250, 128, 64, Assets.buttonStart, gsm) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(startButton.bounds.contains(e.getX(), e.getY())) {
                    getGsm().addAndPop(1, 0);
                }
            }
        };

        // Configure the Pause Button
        optionsButton = new UIImageButton(200, 350, 128, 64, Assets.buttonPause, gsm) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(optionsButton.bounds.contains(e.getX(), e.getY())) {
                    getGsm().addAndPop(2, 0);
                }
            }
        };

        // Configure the Exit Button
        exitButton = new UIImageButton(200, 450, 128, 64, Assets.buttonEnd, gsm) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(exitButton.bounds.contains(e.getX(), e.getY())) {
                    gsm.gameStop();
                }
            }
        };



        // Initialize the UIManager
        uiManager = new UIManager(gsm);

        // Add Buttons to the UIManager
        uiManager.addObject(startButton);
        uiManager.addObject(exitButton);
        // uiManager.addObject(optionsButton);
        // uiManager.addObject(exitButton);
    }

    public void update() {
        uiManager.update();
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        uiManager.input(mouse, key);
    }

    public void render(Graphics2D gp2d) {
        gp2d.drawImage(Assets.background[0],0,0,1280,720, Color.WHITE,null);
        uiManager.render(gp2d);
    }
}
