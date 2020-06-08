/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Creatures;

import Entities.Entity;
import Entities.EntityManager;
import GameStates.MenuState;
import MainG.Handler;
import Tilemaps.Assets;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author German David
 */
public class LaserAlien extends Creature {

    private long lastAttackTimer, attackCooldown = 2000, attackTimer = 0;
    private boolean shootin = false;

    public LaserAlien(Handler handler, EntityManager manager, float x, float y, int width, int height) {
        super(handler, manager, x, y, width, height);
        //Cronometro para saber el tiempo transcurrido antes de su generación
        lastAttackTimer = System.currentTimeMillis();
        this.width = 60;
        this.height = 36;

        speed = 1;

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 60;
        bounds.height = 36;
    }

    @Override
    public void die() {
    }

    @Override
    public void update() {

        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        if (attackTimer > 2000) {
            shootRay();
        } else {
            movex();
            x += Xmove;
            if (y < 0) {
                y += speed;
            }
        }

    }

    public void movex() {
        if (x > manager.getPlayer().getX() + manager.getPlayer().getWidth() / 2) {
            Xmove = -speed;
        } else if (x < manager.getPlayer().getX() + manager.getPlayer().getWidth() / 2) {
            Xmove = speed;
        } else {
            Xmove = 0;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.LaserAlien, (int) x, 0, null);
        if (shootin) {
            g.drawImage(Assets.laser, (int) (this.getX() + this.getWidth() / 4), (int) (this.getY()), null);
        }
    }

    private void shootRay() {

        shootin = true;
        Rectangle laser = new Rectangle((int) this.x, (int) (y + this.getWidth()), 40, 720);

        for (Entity e : manager.getEntities()) {
            if (e.getCollisionBounds().intersects(laser)) {
                e.hurt(1);
            }
        }

        if (attackTimer > 4000) {
            attackTimer = 0;
            shootin = false;

        }
    }
}
