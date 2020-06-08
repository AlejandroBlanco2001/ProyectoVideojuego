/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Static;

import Entities.Entity;
import Entities.EntityManager;
import Entities.Static.BookPile;
import FirstMinigame.WorldGenerator.WorldLibrary;
import MainG.Handler;
import MainG.Window;
import Tilemaps.Animation;
import java.awt.Color;
import Tilemaps.Assets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author German David
 */
public class BookInfo extends StaticEntity {

    private final EntityManager manager;
    private final BufferedImage img;
    private final int id;
    private Graphics2D g;
    private static int contIni;
    private boolean sw = true;
    Font textFont = new Font("pixelart", Font.PLAIN, 20);
    private Animation openning;

    public BookInfo(EntityManager manager, BufferedImage img, Handler handler, float x, float y, int width, int height, int id) {
        super(handler, manager, x, y, width, height);
        this.manager = manager;
        this.img = img;
        this.id = id;
        openning = new Animation(100, Assets.BookOpenning);
    }

    @Override
    public void update() {
        getInput();
        openning.update();
    }

    void getInput() {
        if (this.id != 6) {
            if (Window.keyManager.enter) {
                manager.removeEntity(this);//Quitar despues, cambiar por moverse a otra posicion
                this.manager.getJoan().setCanMove(true);

            }
        } else {
            if (Window.keyManager.esc) {
                manager.removeEntity(this);//Quitar despues, cambiar por moverse a otra posicion
                this.manager.getJoan().setCanMove(true);
            }

            if (Window.keyManager.enter) {
                this.manager.verifyEnd();
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        this.manager.getJoan().setCanMove(false);
        g.drawImage(getCurrentFrame(), 200, 200, null);
    }

    @Override
    public void die() {

    }

    public int getId() {
        return id;
    }

    private Image getCurrentFrame() {

        if (sw == false) {
            if (WorldLibrary.bookcount < 6) {
                return Assets.Books[WorldLibrary.bookcount - 1];
            } else {
                if (this.id == 6) {
                    return Assets.Books[2];
                } else {
                    return Assets.Books[this.id];
                }
            }
        } else {
            if (openning.getCurrentFrame().equals(Assets.BookOpenning[5])) {
                sw = !sw;
            }
            return openning.getCurrentFrame();
        }

    }

}
