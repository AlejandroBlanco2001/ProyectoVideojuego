package Entities.Creatures;

import Audio.AudioLoader;
import Entities.Entity;
import Entities.EntityManager;
import Tilemaps.Assets;
import MainG.Handler;
import SecondMinigame.HUD;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import tinysound.Sound;

/**
 * Los asteroides del minijuego del space invaders.
 *
 * @version 1.3
 */
public class Asteroid extends Creature {

    private HUD hud;

    //Musica cuando se le hace daño a un asteroide
    private static Sound asteroidDamage;

    /**
     * Constructor de la clase asteroide.
     *
     * @param handler Handler
     * @param manager EntityManager
     * @param x Coordenada en X
     * @param y Coordenada en Y
     * @param width Anchura
     * @param height Altura
     * @param hud El HUD
     */
    public Asteroid(Handler handler, EntityManager manager, float x, float y, int width, int height, HUD hud) {
        super(handler, manager, x, y, width, height);

        this.hud = hud;
        this.setHealth(25);

        this.width = (int) (Math.random() * 70 + 30);
        this.height = this.width;
        Xmove = (int) (Math.random() * 3 + 1);

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = this.width;
        bounds.height = this.height;
        asteroidDamage = AudioLoader.damageAsteroid;
    }

    /**
     * Metodo de lo que sucede luego que un asteroide muere. En este metodo, se le suma a la puntuacion en el HUD, aparte de sonar la música.
     */
    @Override
    public void die() {
        if (!(this.x <= 0)) {
            hud.setPoint(hud.getPoint() + 1);
            asteroidDamage.play(0);
        }
    }

    /**
     * Metodo update para que el asteroide se mueva y revise si es golpeado mientras esté activo.
     */
    @Override
    public void update() {
        move();
        checkAttacks();
        getState();
    }

    /**
     * Metodo para el movimiento del asteroide.
     */
    @Override
    public void move() {
        this.x -= Xmove;
    }

    public void getState() {
        if (this.x < -100) {
            active = false;
            die();
        }
    }

    /**
     * Metodo para reenderizar el asteroide.
     *
     * @param g Graficos para reenderizar.
     */
    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.asteroids, (int) x, (int) y, width, height, null);
    }

    /**
     * Método que revisa si algun asteroide ha sufrido algun ataque. Los ataques pueden ser de un asteroide que se choca con otro, o de una bala del jugador.
     */
    public void checkAttacks() {

        Rectangle cb = getCollisionBounds();

        for (Entity e : manager.getEntities()) {
            if (!e.equals(this)) {
                if (e.getCollisionBounds().intersects(cb)) {
                    //Por si son asteorides, se destruye el mas pequeño
                    if ((e instanceof Asteroid)) {
                        if (e.getWidth() * e.getHeight() < this.getWidth() * this.getHeight()) {
                            Asteroid asteroid = (Asteroid) e;
                            asteroid.Xmove = Xmove + 0.5f;
                        }
                    } else {
                        if (!(e instanceof Enemy)) {
                            e.hurt(1);
                            HUD.setPoint(hud.getPoint() + 1);
                            asteroidDamage.play();
                            this.setActive(false);
                        }
                        //Si no es un asteroide, le hace 5 de daño                            
                    }
                }
            }
        }
    }
}
