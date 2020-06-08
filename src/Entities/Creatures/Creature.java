package Entities.Creatures;

import Entities.Entity;
import Entities.EntityManager;
import MainG.GamePanel;
import MainG.Handler;
import java.awt.Graphics;

public abstract class Creature extends Entity{
    
    protected float Xmove=0,Ymove=0;
    protected int health;
    protected float speed;
    
    public static final int DEFAULT_HEALTH=10;
    public static final float DEFAULT_SPEED=5.0f;
    public static final int DEFAULT_CREATURE_WIDTH=131;
    public static final int DEFAULT_CREATUR_HEIGHT=110;
    
    
    public Creature(Handler handler,EntityManager manager,float x, float y,int width, int height) {
        super(handler,manager,x,y,width,height);
        speed=DEFAULT_SPEED;
    }

    public void move(){
        x +=Xmove;
        y+=Ymove;
    }
//Getters and setters
    public float getXmove() {
        return Xmove;
    }

    public void setXmove(float Xmove) {
        this.Xmove = Xmove;
    }

    public float getYmove() {
        return Ymove;
    }

    public void setYmove(float Ymove) {
        this.Ymove = Ymove;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    
}
