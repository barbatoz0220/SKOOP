package com.sk.game.tiles;

import com.sk.game.utils.AABB;
import com.sk.game.utils.Camera;

import java.awt.Graphics2D;

public abstract class TileMap {
    public abstract void render(Graphics2D g, AABB cam);
}
