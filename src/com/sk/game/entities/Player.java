package com.sk.game.entities;

import com.sk.game.GamePanel;
import com.sk.game.graphics.Sprite;
import com.sk.game.states.PlayState;
import com.sk.game.utils.*;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity {
    protected float maxHP;
    public int exp;
    public int level;
    public int expToNext = 100;
    public float xOrigin;
    public float yOrigin;

    public void setExp(int exp) { this.exp = exp; }
    public int getExp() { return exp; }
    public void setExpToNext(int expToNext) { this.expToNext = expToNext; }
    public int getExpToNext() { return expToNext; }
    public float getMaxHP() { return maxHP; }
    public void setMaxHP(float maxHP) { this.maxHP = maxHP; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }


    public Player(Sprite sprite, Vector2f origin, int size, float HP, float damage, int level) {
        super(sprite, origin, size, HP, damage);
        this.level = level;
        this.xOrigin = origin.x;
        this.yOrigin = origin.y;
        maxHP = this.HP;
        acc = 1f;
        maxSpeed = 4f;

        bounds = new AABB(origin, size, size);
        bounds.setWidth(25);
        bounds.setHeight(20);
        bounds.setXOffset(24);
        bounds.setYOffset(40);

        hitBounds = new AABB(origin, size, size);
        hitBounds.setXOffset(size / 2);
    }

    private void move() {
        if (up) {
            dy -= acc;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if (dy < 0) {
                dy += deacc;
                if (dy > 0) {
                    dy = 0;
                }
            }
        }

        if (down) {
            dy += acc;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if (dy > 0) {
                dy -= deacc;
                if (dy < 0) {
                    dy = 0;
                }
            }
        }

        if (left) {
            dx -= acc;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if (dx < 0) {
                dx += deacc;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        if (right) {
            dx += acc;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= deacc;
                if (dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    private void resetPosition() {
        System.out.println("Resetting Player...");
        this.HP -= 0.25 * this.maxHP;
        System.out.println(this.HP);
        pos.x = xOrigin; // Reset player's x position
        PlayState.map.x = 0; // Reset camera's x position
        pos.y = yOrigin; // Reset player's y position
        PlayState.map.y = 0; // Reset camera's y position
    }

    public void update(Enemy enemy) {
        super.update();

        if(attack && hitBounds.collides(enemy.getBounds())) {
            enemy.HP -= this.damage;
            System.out.println(enemy.HP);
            if (enemy.HP == 0) {
                this.exp += enemy.expDrop;
                enemy.dead = true;
            }
        }

        if(!fallen) {
            move();
            if (!tc.collisionTile(dx, 0)) {
                pos.x += dx;
                xCol = false;
            } else {
                xCol = true;
            }
            if (!tc.collisionTile(0, dy)) {
                pos.y += dy;
                yCol = false;
            } else {
                yCol = true;
            }
        } else {
            xCol = true;
            yCol = true;
            if (ani.hasPlayedOnce()) {
                resetPosition();
                dx = 0;
                dy = 0;
                fallen = false;
            }
        }
    }

    public void showHP(Graphics2D gp2d) {
        gp2d.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset() - 16),
                (int) (pos.getWorldVar().y + bounds.getYOffset()) - 50, 50, 10);
        if ((this.HP/this.maxHP) > 0.5){
            gp2d.setColor(Color.green);
        } else if ((this.HP/this.maxHP) < 0.5 && (this.HP/this.maxHP) > 0.2) {
            gp2d.setColor(Color.orange);
        } else {
            gp2d.setColor(Color.red);
        }
        gp2d.fillRect((int) (pos.getWorldVar().x + bounds.getXOffset() - 16),
                (int) (pos.getWorldVar().y + bounds.getYOffset()) - 50, Math.round((this.HP / this.maxHP) * 50),10);
    }

    @Override
    public void render(Graphics2D gp2d) {
        gp2d.setColor(Color.red);
        gp2d.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (mouse.isLeftPressed()) {
            System.out.println("Player: " + pos.x + ", " + pos.y);
        }
        if (!fallen) {
            if (key.up.down) {
                up = true;
            } else {
                up = false;
            }
            if (key.down.down) {
                down = true;
            } else {
                down = false;
            }
            if (key.left.down) {
                left = true;
            } else {
                left = false;
            }
            if (key.right.down) {
                right = true;
            } else {
                right = false;
            }
            if (key.attack.down) {
                attack = true;
            } else {
                attack = false;
            }

        } else {
            up = false;
            down = false;
            left = false;
            right = false;
        }
    }
}

