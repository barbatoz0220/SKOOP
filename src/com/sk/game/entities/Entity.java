package com.sk.game.entities;

import com.sk.game.GamePanel;
import com.sk.game.graphics.Animation;
import com.sk.game.graphics.Sprite;
import com.sk.game.states.PlayState;
import com.sk.game.utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    private final int UP = 3;
    private final int DOWN = 2;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int FALLEN = 4;
    private final int ATTACK_RIGHT = 5;
    private final int ATTACK_LEFT = 6;
    private final int ATTACK_DOWN = 7;
    private final int ATTACK_UP = 8;
    private final int DEAD = 9;
    protected int currentAnimation;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected TileCollision tc;

    protected int size;
    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected boolean fallen;
    protected boolean dead;
    protected boolean attackSpeed;
    protected boolean attackDuration;

    public boolean xCol = false;
    public boolean yCol = false;

    protected float dx;
    protected float dy;

    protected float maxSpeed = 2.5f;
    protected float acc = 1.75f;
    protected float deacc = 0.2f;
    protected float HP;
    protected float damage;

    protected AABB hitBounds;
    protected AABB bounds;

    public Entity(Sprite sprite, Vector2f origin, int size, float HP, float damage) {
        this.sprite = sprite;
        pos = origin;
        this.size = size;
        this.HP = HP;
        this.damage = damage;
        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
        tc = new TileCollision(this);
    }

    public Vector2f getPos() { return pos; }
    public void setPos(Vector2f pos) { this.pos = pos; }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public boolean isFallen() { return fallen; }
    public void setFallen(boolean b) { fallen = b; }
    public void setSize(int i) { size = i; }
    public int getSize() { return size; }
    public void setHealth(int HP) { this.HP = HP; }
    public float getHealth() { return this.HP; }
    public void setMaxSpeed(float f) { maxSpeed = f; }
    public float getMaxSpeed() { return maxSpeed; }
    public void setAcc(float f) { acc = f; }
    public float getAcc() { return acc; }
    public void setDeacc(float f) { deacc = f; }
    public float getDeacc() { return deacc; }
    public float getDx() { return dx; }
    public float getDy() { return dy; }
    public Animation getAnimation() { return ani; }
    public AABB getBounds() { return bounds; }
    public AABB getHitBounds() { return hitBounds; }
    public void setHitBounds(AABB hitBounds) { this.hitBounds = hitBounds; }
    public float getDamage() { return damage; }
    public void setDamage(float damage) { this.damage = damage; }
    public boolean isAttack() { return attack; }
    public void setAttack(boolean attack) { this.attack = attack; }
    public Sprite getSprite() { return sprite; }

    public boolean isDead() { return dead; }
    public void setDead(boolean dead) { this.dead = dead; }

    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate() {
        if (up) {
            if (currentAnimation != UP || ani.getDelay() == -1) {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        } else if (down) {
            if (currentAnimation != DOWN || ani.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        } else if (left) {
            if (currentAnimation != LEFT || ani.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        } else if (right) {
            if (currentAnimation != RIGHT || ani.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        } else if (fallen) {
            if (currentAnimation != FALLEN || ani.getDelay() == -1) {
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 10);
            }
        } else if (attack) {
            if (currentAnimation == UP) {
                setAnimation(ATTACK_UP, sprite.getSpriteArray(ATTACK_UP), 5);
            } else if (currentAnimation == DOWN) {
                setAnimation(ATTACK_DOWN, sprite.getSpriteArray(ATTACK_DOWN), 5);
            } else if (currentAnimation == RIGHT) {
                setAnimation(ATTACK_RIGHT, sprite.getSpriteArray(ATTACK_RIGHT), 5);
            } else if (currentAnimation == LEFT) {
                setAnimation(ATTACK_LEFT, sprite.getSpriteArray(ATTACK_LEFT), 5);
            } else if (ani.getDelay() == -1) {
                setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), 5);
            }
        } else if (dead) {
            if (currentAnimation != DEAD || ani.getDelay() == -1) {
                setAnimation(DEAD, sprite.getSpriteArray(DEAD), 40);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    private void setHitBoxDirection() {
        if(up) {
            hitBounds.setYOffset(-size / 2);
            hitBounds.setXOffset(0) ;
        }
        else if(down) {
            hitBounds.setYOffset(size / 2);
            hitBounds.setXOffset(0);
        }
        else if(left) {
            hitBounds.setYOffset(0);
            hitBounds.setXOffset(-size / 2);
        }
        else if(right) {
            hitBounds.setYOffset(0);
            hitBounds.setXOffset(size / 2);
        }
    }

    public void update() {
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public abstract void render(Graphics2D gp2d);
}
