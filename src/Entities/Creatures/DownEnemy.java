package Entities.Creatures;

import Audio.AudioLoader;
import Entities.Entity;
import Entities.EntityManager;
import Entities.Items.Bullet;
import MainG.Handler;
import ThirdMinigame.HUD;
import Tilemaps.Assets;
import java.awt.Graphics;
import java.awt.Rectangle;
import tinysound.Music;

public class DownEnemy extends Enemy {

    private HUD hud;
    private long lastAttackTimer, attackCooldown = 2000, attackTimer = 0;
    private boolean updownswitch = false;
    private Music explosionEnemy = AudioLoader.damageEnemyShip;
    
    public DownEnemy(Handler handler, EntityManager manager, float x, float y, int width, int height, HUD hud) {
        super(handler, manager, x, y, width, height, hud);
        this.hud = hud;
        this.setHealth(20);
        Ymove = (int) (Math.random() * 5 + 2);
        bounds.x=0;
        bounds.y=0;
        bounds.width=65;
        bounds.height=21;
    }

    @Override
    public void die() { 
        hud.setPoint(hud.getPoint() + 2);
        this.setActive(false);
        explosionEnemy.play(false);
    }

    @Override
    public void update() {
        move();
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer >= attackCooldown) {
            shot();
            attackTimer = 0;
        }
        checkAttacks();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.enemy, 1000, (int) this.y, width, height, null);
    }

    public void move() {
        if (updownswitch) {
            if (this.y <= 40) {
                updownswitch = !updownswitch;
            }
            this.y -= Ymove;
        } else {
            if (this.y >= 640) {
                updownswitch = !updownswitch;
            }
            this.y += Ymove;
        }
    }

    private void shot() {
        manager.addEntity(new Bullet(handler, manager, this.getX(), this.getY() + this.getHeight() / 3.3f, 100, 100, this));
    }

    private void checkAttacks() {
        if (attackTimer < attackCooldown) //No hace lo de abajo
        {
            return;
        }
        
        Rectangle cb = getCollisionBounds();

        attackTimer = 0;

        for (Entity e : manager.getEntities()) {
            if (!e.equals(this) && !(e instanceof Asteroid)) {
                if (e.getCollisionBounds().intersects(cb)) {
                    System.out.println("Me pego");
                    e.hurt(5);
                    return;
                }
            }
        }
    }

}
