package com.sk.game.states;

import com.sk.game.GamePanel;
import com.sk.game.entities.Enemy;
import com.sk.game.entities.Player;
import com.sk.game.graphics.Sprite;
import com.sk.game.graphics.Font;
import com.sk.game.tiles.TileManager;
import com.sk.game.utils.*;

import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayState extends GameState {

    private Font font;
    private Player player;
    private Vector2f spawnPoint;
    private List<Enemy> enemyList = new ArrayList<>();
    private TileManager tileManager;
    private Camera camera;
    private int kill = 0;
    long startTime = System.nanoTime();

    public static Vector2f map;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        Level_1();
    }

    public void Level_1() {
        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);
        camera = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64 , GamePanel.height + 64));
        tileManager = new TileManager(camera, "tile/dungeon1.xml");
        font = new Font("font/font.png", 10, 10);

        // Creating player
        spawnPoint = new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32);
        player = new Player(new Sprite("entity/Player.png"), spawnPoint, 64, 100, 1, 1);

        // Asking the camera to focus on the player
        camera.target(player);

        // Creating enemies
        Enemy enemy0 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                new Vector2f(1543, 724f), 0, 1000000, 0,1000000);
        Enemy enemy1 = new Enemy(camera, new Sprite("entity/Goblin.png", 32, 32),
                new Vector2f(0 + (GamePanel.width / 2) - 32 + 150, (GamePanel.height / 2) - 32 + 150),
                64, 100, 0.1f, 100);
        Enemy enemy2 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                new Vector2f(0 + (GamePanel.width / 2) - 32 + 450, (GamePanel.height / 2) - 32 + 150),
                64, 100, 0.1f, 250);
        Enemy enemy3 = new Enemy(camera, new Sprite("entity/Goblin.png", 32, 32),
                new Vector2f(0 + (GamePanel.width / 2) - 32 + 750, (GamePanel.height / 2 + 1000) - 32 + 150),
                100, 200, 0.2f, 200);
        Enemy enemy4 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                new Vector2f(0 + (GamePanel.width / 2) - 32 + 643, (GamePanel.height / 2) - 32 + 1531),
                64, 200, 0.1f, 100);
        Enemy enemy5 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                new Vector2f(0 + (GamePanel.width / 2) - 32 + 660, (GamePanel.height / 2) - 32 + 768),
                64, 200, 0.1f, 150);
        Enemy enemy6 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                new Vector2f(0 + (GamePanel.width / 2) - 32 + 1500, (GamePanel.height / 2 + 1000) - 32 + 150),
                100, 400, 0.2f, 400);

        // Adding enemies to the Enemy List
        enemyList.add(enemy0);
        enemyList.add(enemy1);
        enemyList.add(enemy2);
        enemyList.add(enemy3);
        enemyList.add(enemy4);
        enemyList.add(enemy5);
        enemyList.add(enemy6);
    }

    public void Level_2() {
        if (enemyList.size() == 1) {
            Enemy enemy1 = new Enemy(camera, new Sprite("entity/Goblin.png", 32, 32),
                    new Vector2f(0 + (GamePanel.width / 2) - 32 + 150, (GamePanel.height / 2) - 32 + 150),
                    64, 100, 0.1f, 100);
            Enemy enemy2 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                    new Vector2f(0 + (GamePanel.width / 2) - 32 + 450, (GamePanel.height / 2) - 32 + 150),
                    64, 100, 0.1f, 250);
            Enemy enemy3 = new Enemy(camera, new Sprite("entity/Goblin.png", 32, 32),
                    new Vector2f(0 + (GamePanel.width / 2) - 32 + 750, (GamePanel.height / 2 + 1000) - 32 + 150),
                    100, 200, 0.2f, 200);
            Enemy enemy4 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                    new Vector2f(0 + (GamePanel.width / 2) - 32 + 643, (GamePanel.height / 2) - 32 + 1531),
                    64, 200, 0.1f, 100);
            Enemy enemy5 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                    new Vector2f(0 + (GamePanel.width / 2) - 32 + 660, (GamePanel.height / 2) - 32 + 768),
                    64, 200, 0.1f, 150);
            Enemy enemy6 = new Enemy(camera, new Sprite("entity/Wizard.png", 64, 64),
                    new Vector2f(0 + (GamePanel.width / 2) - 32 + 1500, (GamePanel.height / 2 + 1000) - 32 + 150),
                    100, 400, 0.2f, 400);
            enemyList.add(enemy1);
            enemyList.add(enemy2);
            enemyList.add(enemy3);
            enemyList.add(enemy4);
            enemyList.add(enemy5);
            enemyList.add(enemy6);
        }
    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        camera.update();
        player.update(enemyList.get(0));

        // Loop over the list of enemies to do the following things:
        for (int i = 0; i < enemyList.size(); i++){
            // If an enemy's health is different from 0, it will find and attack the Player
            if (enemyList.get(i).getHealth() != 0) {
                enemyList.get(i).update(player);
                // It will also be attacked by the player and have its health reduced
                if (player.isAttack() && player.getHitBounds().collides(enemyList.get(i).getBounds())) {
                    enemyList.get(i).setHealth((int) (enemyList.get(i).getHealth() - player.getDamage()));
                }
                // If the enemy's health is equal to 0
            } else {
                // The enemy's dead and drops its EXP. We will then check if the amount of EXP the player has is enough or not
                // If it does the player is leveled up, else vice versa
                kill++;
                enemyList.get(i).setDead(true);
                if (enemyList.size() > 1) {
                    player.setExp(player.getExp() + enemyList.get(i).expDrop);

                    while (player.getExp() >= player.getExpToNext()) {
                        int spareExp = Math.abs(player.getExp() - player.getExpToNext());
                        player.setExp(spareExp);
                        player.setExpToNext(player.expToNext += 50);
                        System.out.println("Player leveled up!!!");
                        player.setLevel(player.getLevel() + 1);
                        player.setMaxHP(player.getHealth() + 10);
                        player.setHealth((int) player.getMaxHP());
                        player.setDamage(player.getDamage() + 0.2f);
                    }
                    // Once the enemy finishes dropping EXP, it will be removed from the list
                    enemyList.remove(enemyList.get(i));
                }
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        player.input(mouse, key);
        camera.input(mouse, key);
    }

    public void render(Graphics2D gp2d) {
        tileManager.render(gp2d);
        player.render(gp2d);
        player.showHP(gp2d);
        camera.render(gp2d);

        // Render the enemies
        for (int i = 1; i < enemyList.size(); i++) {
            if (enemyList.get(i) != enemyList.get(0)) {
                enemyList.get(i).render(gp2d);
                enemyList.get(i).showHP(gp2d);
            }
        }

        // Render the display of the PlayState's information
        Sprite.drawArray(gp2d, font, GamePanel.oldFrameCount + " FPS", new Vector2f(GamePanel.width - 192, 32), 32, 32, 24, 0);
        Sprite.drawArray(gp2d, font, "Level: " + player.level, new Vector2f(50, 32), 32, 32, 24, 0);
        Sprite.drawArray(gp2d, font, player.exp + "/" + player.expToNext, new Vector2f(50, 70), 32, 32, 24, 0);

        // Rendering the states of the game
        this.GameOver(gp2d);
        this.Level_2();
    }

    public void GameOver(Graphics2D gp2d) {
        if (player.getHealth() <= 0 ) {
            long endTime = System.nanoTime();
            long totalTime = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
            float score = Math.round(((float) this.kill / totalTime) * 10000);
            gp2d.setColor(Color.darkGray);
            gp2d.fillRect(0, 0, GamePanel.width, GamePanel.height);
            Sprite.drawArray(gp2d, font, "Game Over", new Vector2f(GamePanel.width / 2 - 300, GamePanel.height / 2 - 200), 64, 64, 64, 0);
            Sprite.drawArray(gp2d, font, "Kill(s): " + this.kill, new Vector2f(GamePanel.width / 2 - 200, GamePanel.height / 2 - 100), 50, 50, 50, 0);
            Sprite.drawArray(gp2d, font, "Time: " + totalTime + "s", new Vector2f(GamePanel.width / 2 - 200, GamePanel.height / 2), 50, 50, 50, 0);
            Sprite.drawArray(gp2d, font, "Score: " + score, new Vector2f(GamePanel.width / 2 - 300, GamePanel.height / 2 + 200), 50, 100, 50, 0);
            GamePanel.running = false;
        }
    }
}
