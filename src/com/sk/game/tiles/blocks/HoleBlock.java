package com.sk.game.tiles.blocks;

import com.sk.game.utils.AABB;
import com.sk.game.utils.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class HoleBlock extends Block {
    public HoleBlock(BufferedImage img, Vector2f pos, int w, int h){
        super(img, pos, w, h);
    }

    public boolean update(AABB p) {
        System.out.println("hole");
        return false;
    }

    public boolean isInside(AABB p) {
        if(p.getPos().x + p.getXOffset() < pos.x) return false;
        if(p.getPos().y + p.getYOffset() < pos.y) return false;
        if(w + pos.x < p.getWidth() + (p.getPos().x + p.getXOffset())) return false;
        if(h + pos.y < p.getHeight() + (p.getPos().y + p.getYOffset())) return false;
        return true;
    }

    public void render(Graphics2D g) {
        super.render(g);
        //g.setColor(Color.orange);
        //g.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, w, h);
    }
}
