package Tilemaps;

import java.awt.image.BufferedImage;

/**
 *
 * @author German David
 */

public class Animation {

    private int speed, index;
    long lastTime, timer;
    private BufferedImage[] frames;
    private float ALPHA = 1;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void update() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length) {
                index = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
    

    public void setIndex(int j){
        index=j;
    }
    
}
