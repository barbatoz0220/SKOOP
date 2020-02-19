package com.sk.game.states;

import com.sk.game.utils.KeyHandler;
import com.sk.game.utils.MouseHandler;

import java.awt.*;

public abstract class GameState {

    public GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void update();
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D gp2d);
}