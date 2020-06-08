/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author German David
 */
public class UIHelper extends UIObject {

    private BufferedImage[] images;
    private long timer, cooldown, now;
    private Rectangle bounds;
    private int i;
    private UIManager manager;
    private boolean renderize;

    public UIHelper(BufferedImage[] images, long cooldown, float x, float y, int width, int height) {
        super(x, y, width, height);
        this.images = images;
        this.cooldown = cooldown;

        i = 0;
        renderize=true;
        now = System.currentTimeMillis();

        bounds = new Rectangle();
        bounds.x = (int) x;
        bounds.y = (int) y;
        bounds.width = width;
        bounds.height = height;
    }

    public UIHelper(BufferedImage[] images, long cooldown, float x, float y, int width, int height, UIManager manager) {
        super(x, y, width, height);
        this.images = images;
        this.cooldown = cooldown;
        this.manager = manager;
        i = 0;

        renderize = true;
        now = System.currentTimeMillis();

        bounds = new Rectangle();
        bounds.x = (int) x;
        bounds.y = (int) y;
        bounds.width = width;
        bounds.height = height;
    }

    @Override
    public void tick() {
        timer += System.currentTimeMillis() - now;
        now = System.currentTimeMillis();

        if (timer > cooldown) {
            if (i + 1 < images.length) {
                i++;
                timer = 0;
            } else {
                if (manager == null) {
                    i = 0;
                } else {
                    renderize = false;
                }
                timer = 0;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (renderize) {
            switch (i) {
                case 0:
                    g.drawImage(images[i], (int) x, (int) y, null);
                    break;
                case 1:
                    g.drawImage(images[i], (int) x, (int) y, null);
                    break;
                case 2:
                    g.drawImage(images[i], (int) x, (int) y, null);
                    break;
                case 3:
                    g.drawImage(images[i], (int) x, (int) y, null);
                    break;
                case 4:
                    g.drawImage(images[i], (int) x, (int) y, null);
                    break;
                case 5:
                    g.drawImage(images[i], (int) x, (int) y, null);
                    break;
                case 6:
                    g.drawImage(images[i], (int) x, (int) y, null);
                    break;
                
            }
        }
    }

    @Override
    public void onClick() {
        if (i + 1 < images.length) {
            i++;
        } else {
            if (manager == null) {
                i = 0;
            } else {
                renderize = false;
            }
        }
        timer = 0;
    }

}
