/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author German David
 */
public class UIImageButton extends UIObject {

    private BufferedImage[] images;
    private BufferedImage image;
    private ClickListener clicker;
    private boolean current;

    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
        current = false;
    }

    public UIImageButton(float x, float y, int width, int height, BufferedImage image, ClickListener clicker) {
        super(x, y, width, height);
        this.image = image;
        this.clicker = clicker;
        current = false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if (!(images == null)) {
            if (hovering || current) {
                g.drawImage(images[0], (int) x, (int) y, null);
            } else {
                g.drawImage(images[1], (int) x, (int) y, null);
            }
        } else {
            if (hovering || current) {
                g.drawImage(image, (int) x, (int) y, width - 30, height - 10, null);
            } else {
                g.drawImage(image, (int) x, (int) y, width, height, null);
            }
        }
    }

    @Override
    public void onClick() {
        clicker.onClick();
    }

    public void setCurrent(boolean b) {
        current = b;
    }
    
    public void setImage(BufferedImage bf){
        this.image = bf;
    }
}
