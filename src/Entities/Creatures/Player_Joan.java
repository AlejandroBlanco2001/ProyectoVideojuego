package Entities.Creatures;

import Entities.EntityManager;
import Entities.Items.Bullet;
import FirstMinigame.Tiles.Tile;
import Tilemaps.Animation;
import MainG.Handler;
import MainG.Window;
import Tilemaps.Assets;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * @author German David
 */
public class Player_Joan extends Character {

    public Bullet[] bullets= new Bullet[100];
    public static int bullcount = 0;
    private long clock;
    private boolean punchie=false;
    private int ar1, ar2;
    
    //Attack range
    private int attackR;
    
    //Attack timer, AttackCooldown
    private long lastAttackTimer,attackCooldown = 800, attackTimer=attackCooldown;
    
    //Animations
    private Animation animDown,animUp,animR,animL;
    
    public Player_Joan(Handler handler, EntityManager entityM, float x, float y) {
        super(handler, entityM, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATUR_HEIGHT);
        
        /* 
            Bounds para la chica
            bounds.x=40;
            bounds.y=35;
            bounds.width=35;
            bounds.height=60;
        */
        
        bounds.x=40;
        bounds.y=65;
        bounds.width=52;
        bounds.height=40;
        
        attackR=20;
        
        
        //Animations           Speed en millis()
        animDown= new Animation(300,Assets.playerDown);
        animUp= new Animation(300,Assets.playerUp);
        animR= new Animation(300,Assets.playerRight);
        animL= new Animation(300,Assets.playerLeft);
    }

    @Override
    public void update() {
        
        //Actualiza los frames
        animDown.update();
        animUp.update();
        animR.update();
        animL.update();
        getInput();
        move();
        
        //Attack
        checkAttacks();
        handler.getGameCamara().centerOnEntity(this);
    }

    private void checkAttacks(){
        
        attackTimer+= System.currentTimeMillis()-lastAttackTimer;
        lastAttackTimer= System.currentTimeMillis();
        if(attackTimer <attackCooldown)
            //No hace lo de abajo
            return;
        
        Rectangle cb = getCollisionBounds(0f,0f);
        Rectangle ar = new Rectangle();
        
        ar.width=attackR;
        ar.height= attackR;
        if(Window.keyManager.up){
            ar.x=cb.x+cb.width/2 - attackR/2;
            ar.y= cb.y-attackR;
        }else if(Window.keyManager.down){
            ar.x=cb.x+cb.width/2 - attackR/2;
            ar.y= cb.y+cb.height;
        } else if(Window.keyManager.left){
            ar.x=cb.x- attackR;
            ar.y= cb.y+ cb.height/2 - attackR/2;
        } else if(Window.keyManager.right){
            ar.x=cb.x + cb.width;
            ar.y= cb.y+ cb.height/2 - attackR/2;
        } else{
            return;
        }
        
        ar1=ar.x;
        ar2=ar.y;
        punchie=true;
        attackTimer=0;
        
//        for (Entity e : ) { 
//            
//            System.out.println(""+e.getCollisionBounds(0,0));
//            if(!e.equals(this)){
//                 if(e.getCollisionBounds(0,0).intersects(ar)){
//                    e.hurt(10);
//                    return;
//                }
//           }
//        }
    }
    
    @Override
    public void die(){
        System.out.println("You lose");
    }
    
    public void getInput() {
        Xmove = 0;
        Ymove = 0;

        if (Window.keyManager.up) {   
            Ymove = -speed;
        }

        if (Window.keyManager.down) {
            Ymove = speed;
        }

        if (Window.keyManager.right) {
            Xmove = speed;
        }
        if (Window.keyManager.left) {
            Xmove = -speed;
        }
    }
    
    @Override
    public void move(){
        moveX();
        moveY();
    }
    public void moveX(){
        if(Xmove>0){
            int tx = (int)(x+Xmove+bounds.x+bounds.width)/Tile.TILEWIDTH;
            if(!collisionWithTile(tx, (int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)){
                x+=Xmove;
            }
        }else if(Xmove<0){
            int tx = (int)(x+Xmove+bounds.x)/Tile.TILEWIDTH;
            if(!collisionWithTile(tx, (int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)){
                x+=Xmove;
            }
        }
    }
    
    public void moveY(){
        if(Ymove<0){
            int ty=(int)((y+Ymove+bounds.y)/Tile.TILEHEIGHT);
            if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty)&&
                    !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)){
                y+=Ymove;
            }
                    
        }else if(Ymove>0){
            int ty=(int)((y+Ymove+bounds.y+bounds.height)/Tile.TILEHEIGHT);
            if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty)&&
                    !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)){
                y+=Ymove;
            }
        }
    }
    
    @Override
    public void render(Graphics2D g) {

        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamara().getxOffset()), (int) (y - handler.getGameCamara().getyOffset()), width, height, null);
        if(punchie){
            g.fillRect(ar1, ar2, attackR, attackR);
            punchie=false;
        }
       // g.setColor(Color.yellow);
        //g.fillRect((int) (x + bounds.x - handler.getGameCamara().getxOffset()), 
          //      (int) (y+bounds.y-handler.getGameCamara().getyOffset()), bounds.width,bounds.height);
        
        for (int i = 0; i < 100; i++) {
           if(bullets[i]!= null){ 
            bullets[i].render(g);
           }
        }        
    }
    
    
    protected boolean collisionWithTile(int x, int y){
      return handler.getWorld().getTile(x, y).isSolid();
    }
    
    //Conseguir la animación en cada movimiento
    private BufferedImage getCurrentAnimationFrame(){
        if(Xmove<0){
            return animL.getCurrentFrame();
        }else if (Xmove>0){
            return animR.getCurrentFrame();
        }else if(Ymove<0){
            return animUp.getCurrentFrame();
        }else if (Ymove>0){
            return animDown.getCurrentFrame();
        }else{
            return Assets.playerDown[0];
        }
    }
}
