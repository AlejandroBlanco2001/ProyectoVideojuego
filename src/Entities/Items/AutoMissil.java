/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Items;

import Entities.Creatures.Boss;
import Entities.Creatures.Player;
import Entities.Entity;
import Entities.EntityManager;
import MainG.Handler;
import Tilemaps.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Los automisiles del Boss del minijuego
 */
public class AutoMissil extends Entity {

    private int bulletSpeed;
    //Puntos de la nave y el jugador
    private int xo, yo, yp, xp, m;

    private int Xmove, Ymove;
   /**
    * Al constructor como de costumbre se le pasa el handler, entity manager y coordenadas.
    * @param handler Handler
    * @param manager EntityManager
    * @param x Coordenada en X de la bala.
    * @param y Coordenada en Y de la bala.
    */
    public AutoMissil(Handler handler, EntityManager manager, float x, float y) {
        super(handler, manager, x, y, 30, 15);

        xp = (int) manager.getPlayer().getX();
        if ((xo - xp) != 0) {
            m = (int) ((yo - yp) / (xo - xp));
        }
        
        this.bulletSpeed = 1;
        //Division 0
        
        
        this.setHealth(1);  //Las balas tienen vida para ser destruidas por el jugador.

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 30;
        bounds.height = 15;
    }
    
    /**
     * Metodo para lo que se debe realizar cuando el AutoMisil muera.
     * Hasta el momento nada.
     */
    @Override
    public void die() {
    }
    
    /**
     * La bala tiene que moverse y revisar si ha sido golpeada por las balas del jugador.
     */
    @Override
    public void update() {
        move();
        checkAttacks();
    }
    
    /**
     * Metodo para reenderizar la bala
     * @param g Graficos
     */
    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.AutoMissil, (int) x, (int) y, null);
    }
    
    /**
     * Metodo para que la bala se mueva.
     */
    private void move() {
        System.out.println(Xmove);
        movex();
        movey();
        x += Xmove;
        y += Ymove;

    }
    /**
     * Metodo para el movimiento en Y del automisil.
     * Lo que hace es buscar la coordenada en Y del jugador y se mueve hacia esta.
     */
    public void movey() {
        if (y > manager.getPlayer().getY() + manager.getPlayer().getHeight() / 2) {
            Ymove = -bulletSpeed;
        } else if (y < manager.getPlayer().getY() + manager.getPlayer().getHeight() / 2) {
            Ymove = bulletSpeed;
        } else {
            Ymove = 0;
        }
    }
    
    /**
     * Metodo para el movimiento en X del AutoMisil.
     * Lo que hace es buscar la coordenada en X del jugador y se mueve hacia esta.
     */
    public void movex() {
        if (x > manager.getPlayer().getX() + manager.getPlayer().getWidth() / 2) {
            Xmove = -bulletSpeed;
        } else if (x < manager.getPlayer().getX() + manager.getPlayer().getWidth() / 2) {
            Xmove = bulletSpeed;
        } else {
            Xmove = 0;
        }
    }
    
    /**
     * Metodo para revisar si la bala se intersecta con una bala del jugador.
     */
    public void checkAttacks() {
        Rectangle cb = getCollisionBounds();
        for (Entity e : manager.getEntities()) {
            if (!(e instanceof AutoMissil) && !(e instanceof Boss)) {
                if (e.getCollisionBounds().intersects(cb)) {
                    e.hurt(1);
                    this.setActive(false);
                }
            }
        }
    }
}
