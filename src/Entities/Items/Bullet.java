package Entities.Items;

import Entities.Creatures.Asteroid;
import Entities.Creatures.Creature;
import Entities.Creatures.DownEnemy;
import Entities.Creatures.Enemy;
import Entities.Creatures.Player;
import Entities.Entity;
import Entities.EntityManager;
import Tilemaps.Assets;
import MainG.Handler;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author German David
 */
public class Bullet extends Entity {

    private int X;
    private int Y;
    private int BulletSpeed = 500;
    private Creature creature;

    public Bullet(Handler handler, EntityManager manager, float x, float y, int width, int height, Creature creature) {
        super(handler, manager, x, y, width, height);

        bounds.x = 2;
        bounds.y = 10;
        bounds.width = 46;
        bounds.height = 22;
        this.creature = creature;

    }

    @Override
    public void update() {
        move();
        checkAttacks();

    }

    public void move() {
        if (this.creature instanceof Player) {
            x += BulletSpeed*handler.getDeltaTime();
        } else {
            x -= BulletSpeed*handler.getDeltaTime();
        }

    }

    @Override
    public void render(Graphics2D g) {
        if(creature instanceof Player)
         g.drawImage(Assets.bullet, (int) x, (int) y, null);
        else if(creature instanceof DownEnemy)
            g.drawImage(Assets.pursoidBullet, (int) x, (int) y, null);
    }

    public int getBulletSpeed() {
        return BulletSpeed;
    }

    public void setBulletSpeed(int BulletSpeed) {
        this.BulletSpeed = BulletSpeed;
    }

    @Override
    public void die() {

    }

    public void checkAttacks() {
        Rectangle cb = getCollisionBounds();
        for (Entity e : manager.getEntities()) {
            if (!(e instanceof Bullet) && !(e.equals(this.creature))) {
                if (e.getCollisionBounds().intersects(cb)) {
                    if (this.creature instanceof Player) {
                        if (e instanceof Asteroid || e instanceof Enemy) {
                            e.hurt(5);
                            this.setActive(false);
                        }
                    } else if (this.creature instanceof Enemy) {
                        if (!(e instanceof Asteroid)) {
                            e.hurt(5);
                            this.setActive(false);
                        }
                    }
                }
            }
        }
    }
    
}
