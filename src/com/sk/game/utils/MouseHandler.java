package com.sk.game.utils;

import com.sk.game.GamePanel;
import com.sk.game.handler.Handler;
import com.sk.game.ui.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MouseHandler implements MouseListener, MouseMotionListener {
    private boolean leftPressed , rightPressed , middlePressed;
    private Handler handler;
    private UIManager uiManager;
    private int mouseX,mouseY;
    private ArrayList<MouseListener> ml;
    private ArrayList<MouseMotionListener> mml;

    public MouseHandler(GamePanel game) {
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        ml = new ArrayList<MouseListener>();
        mml = new ArrayList<MouseMotionListener>() ;
    }

    public int getX() {
        return mouseX;
    }
    public int getY() {
        return mouseY;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        for(MouseListener m : ml) {
            m.mousePressed(e);
        }
    }

    public int getMouseX() {
        return mouseX;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public int getMouseY() {
        return mouseY;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isMiddlePressed() {
        return middlePressed;
    }

    //setUIManager
    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    //MOVED
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        mouseX = e.getX();
        mouseY = e.getY();

        if(uiManager != null) {
            uiManager.onMouseMove(e);
        }
        for(MouseMotionListener m : mml) {
            m.mouseMoved(e);
        }
    }

    //PRESSED
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = true;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = true;
        else if(e.getButton() == MouseEvent.BUTTON2)
            middlePressed = true;

        for(MouseListener m : ml) {
            m.mousePressed(e);
        }

    }

    //RELEASED
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = false;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = false;
        else if(e.getButton() == MouseEvent.BUTTON2)
            middlePressed = false;
        if(uiManager != null)
            uiManager.onMouseRelease(e);
        for(MouseListener m : ml) {
            m.mouseReleased(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        for(MouseListener m : ml) {
            m.mouseClicked(e);
        }
    }

    public int getButton() {
        // TODO Auto-generated method stub
        return -1;
    }

    public void addMouseListener(MouseListener m) {
        ml.add(m);
    }
}
