/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Creatures;

import Entities.Entity;
import Entities.EntityManager;
import MainG.Handler;
import SecondMinigame.HUD;
import Tilemaps.Animation;
import Tilemaps.Assets;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Clase de uno de los enemigos del primer minijuego. Especificamente el enemigo
 * que se posiciona en la parte superior y dispara un laser. Hereda de la clase
 * Enemy.
 *
 * @version 1.7
 */
public class AerialEnemy extends Enemy {

    private long lastAttackTimer, attackCooldown = 2000, attackTimer = 0, laserTimer = 0, laserCooldown = 600, lastLaser;
    private boolean shootin = false;
    private HUD hud;
    private Animation anm;
    
    /**
     * Constructor de la clase AerialEnemy.
     * @param handler El handler del Enemigo.
     * @param manager El EntityManager del Enemigo.
     * @param x Coordenada en X de la posicion inicial.
     * @param y Coordenada en Y de la posicion inicial.
     * @param width Anchura del enemigo.
     * @param height Altura del enemigo.
     * @param hud Hud del minijuego.
     */
    public AerialEnemy(Handler handler, EntityManager manager, float x, float y, int width, int height, HUD hud) {
        super(handler, manager, x, y, width, height, hud);
        this.hud = hud;
        //Cronometro para saber el tiempo transcurrido antes de su generación
        lastAttackTimer = System.currentTimeMillis();
        //Dimensiones
        this.width = 110;
        this.height = 99;
        //Vida y velocidad
        this.setHealth(15);
        this.speed = 150;
        //Hitbox
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 110;
        bounds.height = 99;
        anm = new Animation(300, Assets.aerialEnemy);
    }

    @Override
    public void die() {
        this.setActive(false);
    }
    /**
     * Metodo que va actualizando lo que hace el enemigo.
     */
    @Override
    public void update() {

        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        anm.update();
        if (attackTimer > 2000) {
            shootRay();
        } else {
            movex();
            x += Xmove;
            if (y < 0) {
                y += speed * handler.getDeltaTime();
            }
        }

    }
    /**
     * Metodo que se encarga del movimiento del enemigo en X.
     * En el metodo el enemigo lo que hace es moverse en X,
     * hacia la coordenada X del jugador.
     */
    public void movex() {
        if (x > manager.getPlayer().getX() + manager.getPlayer().getWidth() / 2) {
            Xmove = (float) (-speed * handler.getDeltaTime());
        } else if (x < manager.getPlayer().getX() + manager.getPlayer().getWidth() / 2) {
            Xmove = (float) (speed * handler.getDeltaTime());
        } else {
            Xmove = 0;
        }
    }
    
    /**
     * Metodo para reenderizar al enemigo.  
     * @param g Los graficos para reenderizarlo.
     */
    @Override
    public void render(Graphics2D g) {
        g.drawImage(getCurrentAnimationFrame(), (int) x, 0, null);
        if (shootin) {
            g.drawImage(Assets.laser, (int) (this.getX() - 10), (int) (this.getY()) + 94, null);
        }
    }
    
    /**
     * Se encarga del ataque del enemigo (El rasho laser).
     */
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

    /**
     * Metodo para conseguir la animación.
     * @return La animación del movimiento.
     */
    private BufferedImage getCurrentAnimationFrame() {
        return anm.getCurrentFrame();
    }
}
