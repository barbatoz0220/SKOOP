package com.sk.game.utils;

import com.sk.game.entities.Entity;
import com.sk.game.tiles.TileMapObj;
import com.sk.game.tiles.blocks.Block;
import com.sk.game.tiles.blocks.HoleBlock;
import com.sk.game.tiles.blocks.SpawnBlock;

public class TileCollision {

    private Entity e;
    private Object o;
    private Block block;

    public TileCollision(Entity e) {
        this.e = e;
    }

    public TileCollision(Object o) {
        this.o = o;
    }

    public boolean collisionTile(float ax, float ay) {
        for(int c = 0; c < 4; c++) {

            int xt = (int) ((e.getBounds().getPos().x + ax) + (c % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / 64;
            int yt = (int) ((e.getBounds().getPos().y + ay) + (c % 2) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / 64;

            if(TileMapObj.event_blocks[xt + (yt * TileMapObj.height)] instanceof Block) {
                Block block = TileMapObj.event_blocks[xt + (yt * TileMapObj.height)];
                if(block instanceof HoleBlock) {
                    return collisionHole(ax, ay, xt, yt, block);
                }
                return block.update(e.getBounds());
            }
        }

        return false;
    }

    private boolean collisionHole(float ax, float ay, float xt, float yt, Block block) {
        int nextXt = (int) ((( ((e.getBounds().getPos().x + ax) + e.getBounds().getXOffset()) / 64) + e.getBounds().getWidth() / 64));
        int nextYt = (int) ((( ((e.getBounds().getPos().y + ay) + e.getBounds().getYOffset()) / 64) + e.getBounds().getHeight() / 64));

        if(block.isInside(e.getBounds())) {
            e.setFallen(true);
            return false;
        } else if((nextXt == xt + 1) || (nextXt == yt + 1) || (nextYt == yt - 1) || (nextXt == xt - 1)) {
            if(TileMapObj.event_blocks[nextXt + (nextYt * TileMapObj.height)] instanceof HoleBlock) {
                if(e.getBounds().getPos().x > block.getPos().x) {
                    e.setFallen(true);
                }
                return false;
            }
        }
        e.setFallen(false);
        return false;
    }
}
