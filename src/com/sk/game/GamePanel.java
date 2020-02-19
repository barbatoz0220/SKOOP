package com.sk.game;

import com.sk.game.graphics.Assets;
import com.sk.game.states.GameStateManager;
import com.sk.game.utils.KeyHandler;
import com.sk.game.utils.MouseHandler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class GamePanel extends JPanel implements Runnable {

    public static int width;
    public static int height;
    public static int oldFrameCount;
    public static boolean running;

    private Thread thread;

    private BufferedImage img;
    private Graphics2D gp2d;

    private MouseHandler mouse;
    private KeyHandler key;

    private GameStateManager gsm;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true); // Make this panel the focus of the application: everything will be implemented on this panel
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    // Game loop
    public void init() {
        running = true;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        gp2d = (Graphics2D) img.getGraphics();

        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        Assets.addButton();
        gsm = new GameStateManager();
    }

    public void run() {
        init();

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ; // Time Before Update

        final int MUBR = 5; // Most Update Before Render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS; // Total Time Before Render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        oldFrameCount = 0;

        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;
            while(((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) { // Checking if the hertz is on time
                update();
                input(mouse, key);
                lastUpdateTime += TBU;
                updateCount++;
            }

            if (now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            input(mouse, key);
            render();
            draw();
            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                if (frameCount != oldFrameCount) {
                    System.out.println("Second: " + thisSecond + " - " + "FPS: " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();

                try{
                    Thread.sleep(1);
                }
                catch(Exception e) {
                    System.out.println("ERROR: Yielding thread failed");
                }

                now = System.nanoTime();
            }
        }
    }

    public void update() {
        gsm.update();
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

    public void render() {
        if (gp2d != null) {
            gp2d.setColor(new Color(0, 13, 27));
            gp2d.fillRect(0, 0, width, height);
            gsm.render(gp2d);
        }
    }

    public void draw() {
        Graphics gp2 = (Graphics) this.getGraphics();
        gp2.drawImage(img, 0, 0, width, height, null);
        gp2.dispose();
    }

    //GetMouse
    public MouseHandler getMouse() {
        return mouse;
    }


    //SetMouse
    public void setMouse(MouseHandler mouse) {
        this.mouse = mouse;
    }


    //GetKey
    public KeyHandler getKey() {
        return key;
    }


    //SetKey
    public void setKey(KeyHandler key) {
        this.key = key;
    }

    public Boolean getRunning() {
        return running;
    }


    public void setRunning(Boolean running) {
        this.running = running;
    }
}
