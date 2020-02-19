package com.sk.game.states;

import com.sk.game.GamePanel;
import com.sk.game.graphics.Sprite;
import com.sk.game.handler.Handler;
import com.sk.game.utils.KeyHandler;
import com.sk.game.utils.MouseHandler;
import com.sk.game.utils.Vector2f;
import com.sk.game.graphics.Font;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameStateManager {

    private GameState states[];
    private GamePanel game;

    public static Vector2f map;

    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;

    public int onTopState = 0;

    public static Font font;

    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);

        states = new GameState[4];

        font = new Font("font/font.png", 10, 10);
        Sprite.currentFont = font;
        add(0);
    }

    public boolean getState(int state) {
        return states[state] != null;
    }

    public void pop(int state) {
        states[state] = null;
    }

    public void add(int state) {
        if(states[state] != null)
            return;

        if(state == PLAY) {
            states[PLAY] = new PlayState(this);
        }

        if(state == MENU) {
            states[MENU] = new MenuState(this);
        }

        if(state == PAUSE) {
            states[PAUSE] = new PauseState(this);
        }
    }

    public void addAndPop(int addedState, int removedState) {
        pop(removedState);
        add(addedState);
    }

    public void update() {
        for (int i = 0; i < states.length; i++) {
            if(states[i] != null) {
                states[i].update();
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        for (int i = 0; i < states.length; i++) {
            if(states[i] != null) {
                states[i].input(mouse, key);
            }
        }
    }

    public void render(Graphics2D gp2d) {
        for (int i = 0; i < states.length; i++) {
            if(states[i] != null) {
                states[i].render(gp2d);
            }
        }
    }
    //getRunningGSM
    public Boolean getRunning() {

        return game.getRunning();
    }

    //setRunningGSM
    public void setRunning(Boolean running) {
        game.setRunning(running);
    }

    public void gameStop() {
        System.exit(1);
    }
}
