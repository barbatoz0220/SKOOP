package com.sk.game.states;

import com.sk.game.graphics.Assets;
import com.sk.game.ui.UIImageButton;
import com.sk.game.ui.UIManager;
import com.sk.game.ui.UIObject;
import com.sk.game.utils.KeyHandler;
import com.sk.game.utils.MouseHandler;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseState extends GameState {

    private MouseHandler mouse;
    private KeyHandler key;
    private UIObject u = null,u1=null;
    private Font font;
    private UIManager  uiManager;

    public PauseState(GameStateManager gsm) {
        super(gsm);
        font = new Font("font/ZeldaFont.png", 16, 16);
        uiManager = new UIManager(gsm);
    }

    @Override
    public void update() {}

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {}

    @Override
    public void render(Graphics2D gp2d) {}
}
