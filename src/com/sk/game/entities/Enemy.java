package com.sk.game.entities;

import com.sk.game.graphics.Sprite;
import com.sk.game.utils.AABB;
import com.sk.game.utils.Camera;
import com.sk.game.utils.Vector2f;
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Entity {

    private AABB sense;
    private int r;
    private Camera cam;
    private float maxHP;
    public int expDrop;

    public Enemy(Camera cam, Sprite sprite, Vector2f origin, int size, float HP, float damage, int expDrop) {
        super(sprite, origin, size, HP, damage);
        this.cam = cam;
        this.expDrop = expDrop; // Enemy's EXP drop
        maxHP = this.HP; // Enemy's HP
        acc = 0.1f; // Enemy's acceleration
        maxSpeed = 2f; // Enemy's max speed (accelerates until the limit is reached)
        r = 300; // Size of enemy's sense

        bounds = new AABB(origin, 20, 20); // Set the position of enemy's bounds and its size (the same as it's sprite)
        bounds.setXOffset(20); // The amount in width that the enemy's bound is off from the center
        bounds.setYOffset(40); // The amount in height that the enemy's bound is off from the center

        hitBounds = new AABB(origin, size, size); // Enemy's hitbounds

        sense = new AABB(new Vector2f(origin.x + size / 2 - r / 2, origin.y + size / 2 - r / 2), r);
    }

    private void move(Player player) {
        if (sense.colCircleBox(player.getBounds())) {
            if (pos.y > player.pos.y + 1) { //Moving Up
                dy -= acc;
                up = true;
                down = false;
                if (dy < -maxSpeed) {
                    dy = -maxSpeed;
                }
            } else if (pos.y < player.pos.y - 1) { // Moving Down
                dy += acc;
                down = true;
                up = false;
                if (dy > maxSpeed) {
                    dy = maxSpeed;
                }
            } else {
                dy = 0;
                up = false;
                down = false;
            }

            if (pos.x > player.pos.x + 1) { // Moving Left
                dx -= acc;
                left = true;
                right = false;
                if (dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            } else if (pos.x < player.pos.x - 1) { // Moving Right
                dx += acc;
                right = true;
                left = false;
                if (dx > maxSpeed) {
                    dx = maxSpeed;
                }
            } else {
                dx = 0;
                left = false;
                right = false;
            }
        } else {
            dx = 0;
            dy = 0;
            up = false;
            down = false;
            left = false;
            right = false;
        }
    }

    public void update(Player player) {
        //if (cam.getBoundsOnScreen().collides(this.bounds)) {
            super.update();
            move(player);
            if (!fallen) {
                if (!tc.collisionTile(dx, 0)) {
                    // Monster moving around
                    sense.getPos().x += dx;
                    pos.x += dx;

                    // In the x direction
                    if (player.bounds.collides(this.bounds)) {
                        attack = true;
                        player.HP -= this.damage;
                        System.out.println(player.getHealth());
                        if (player.HP == 0) {
                            this.attack = false;
                            player.dead = true;
                            player.HP = 0;
                        }
                    } else {
                        attack = false;
                    }
                }

                // In the y direction
                if (!tc.collisionTile(0, dy)) {
                    sense.getPos().y += dy;
                    pos.y += dy;

                    if (player.bounds.collides(this.bounds)) {
                        attack = true;
                        player.HP -= this.damage;
                        if (player.HP == 0) {
                            this.attack = false;
                            player.dead = true;
                            player.HP = 0;
                        }
                    } else {
                        attack = false;
                    }
                }
            } else {
                attack = false;
                fallen = false;
            }
        //}
    }

    public void showHP(Graphics2D gp2d) {
        gp2d.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset())-16,
                (int) (pos.getWorldVar().y + bounds.getYOffset()) - 50, 50, 10);
        gp2d.setColor(Color.yellow);
        gp2d.fillRect((int) (pos.getWorldVar().x + bounds.getXOffset())-16,
                (int) (pos.getWorldVar().y + bounds.getYOffset()) - 50, Math.round(((float) this.HP / maxHP) * 50), 10);
    }

    @Override
    public void render(Graphics2D gp2d) {
        // Draw the enemy's image
        gp2d.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
