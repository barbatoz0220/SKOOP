package com.sk.game.graphics;

import com.sk.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Sprite {
    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;

    public static Font currentFont;
    private Image bg;

    public Sprite(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading: " + file + " ...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("Loading: " + file + " ...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public void setWidth(int i) {
        w = i;
        wSprite = SPRITESHEET.getWidth() / w;
    }
    public void setHeight(int i) {
        h = i;
        hSprite = SPRITESHEET.getHeight() / h;
    }

    public int getWidth() { return w; }
    public int getHeight() { return h; }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {
            System.out.println("ERROR - SPRITE: Could not load file: " + file);
        }

        return sprite;
    }

    // Total number of sprites in the spritesheet, with wSprite = no. of columns, hSprite = no. of rows
    public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for(int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public BufferedImage getSpriteSheet() {
        return SPRITESHEET;
    }

    public BufferedImage getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public BufferedImage[][] getSpriteArray2(int i) {
        return spriteArray;
    }

    // Used to draw the fonts
    public static void drawArray(Graphics2D gp2d, Font f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        currentFont = f;

        for (int i = 0; i < word.length(); i++) {
            // Every char has a number, and the characters we need are from >= 32 (32 is the space)
            if(word.charAt(i) != 32) {
                gp2d.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
                x += xOffset;
                y += yOffset;}
        }
    }

    public BufferedImage crop(int x,int y,int width,int height) {
        return SPRITESHEET.getSubimage(x, y, width, height);
    }

    public int getSpriteSheetWidth() { return SPRITESHEET.getWidth(); }

    public int getSpriteSheetHeight() { return SPRITESHEET.getHeight(); }

    //Additional constructor for Assets
    public Sprite(BufferedImage image) {
        SPRITESHEET = image;
    }
}
