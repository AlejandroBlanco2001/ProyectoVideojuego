package Entities.Creatures;

import Audio.AudioLoader;
import Entities.EntityManager;
import Entities.Items.Bullet;
import Tilemaps.Assets;
import MainG.Handler;
import MainG.Window;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import tinysound.Sound;

/**
 *
 * @author German David
 */
public class Player extends Character {

    private Sound shot = AudioLoader.shot;
    public int Score;
    public Bullet bullet;
    public static int bullcount = 0;
    private long clock, now = 0;
    private float ShootSpeed = 0.3f;
    private BufferedImage[] naveStates = new BufferedImage[3];

    public int i = 0;
    private long timerAnimation = 0;
    
    /**
     * Constructor de player.
     * @param handler Handler del player.
     * @param manager EntityManager del player.
     * @param x Coordenada en X del player.
     * @param y Coordenada en Y del player.
     */
    public Player(Handler handler, EntityManager manager, float x, float y) {
        super(handler, manager, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATUR_HEIGHT);
        
        timerAnimation = System.currentTimeMillis();
        Score = 0;
        bounds.x = 16;
        bounds.y = 10;
        bounds.width = 160;
        bounds.height = 60;
        this.setSpeed(5);
        //Solo vidas pares si no quieren que aparezca una vida a la mitad
        this.setHealth(6);
        naveStates[0] = Assets.naveOn;
        naveStates[1] = Assets.naveSemiOff;
        naveStates[2] = Assets.naveOff;
    }

    @Override
    public void update() {
        clock = System.nanoTime();
        getInput();
        move();

    }
    
    /**
     * Metodo encargado del movimiento del personaje.
     */
    @Override
    public void move() {

        x += Xmove;
        //Limite Lateral Izquierdo
        if (x < -bounds.width/2  ) {
            x = -bounds.width/2;
        }
        //Limite Lateral Derecho
        if (x + bounds.width > handler.getGame().getWidth()) {
            x = handler.getGame().getWidth() - bounds.width;
        }
        y += Ymove;
        //Limite Superior
        if (y < -bounds.height) {
            y = -bounds.height + 1;
        }
        //Limite Inferior
        if (y + bounds.height > handler.getGame().getHeight()) {
            y = handler.getGame().getHeight() - bounds.height;
        }
    }
    //Metodo que transforma los input del teclado en movimiento del jugador
    @Override
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
        if (Window.keyManager.space && canShoot(clock - now)) {
            shot.play();
            manager.addEntity(new Bullet(handler, manager, this.getX() + this.getWidth() / 1.3f, this.getY() + this.getHeight() / 3.3f, 100, 100, this));
        }
    }

    @Override
    public void render(Graphics2D g) {
//        g.setColor(Color.red);
//        g.fillRect((int) x + bounds.x, (int) y + bounds.y, bounds.width, bounds.height);
        g.drawImage(getCurrentImage(), (int) (x), (int) (y), null);
    }

    public boolean canShoot(long c) {
        if ((double) c / 1000000000 >= ShootSpeed) {
            System.out.println("" + (double) c / 1000000000);
            now = clock;
            return true;
        } else {
            return false;
        }
    }

    public float getShootSpeed() {
        return ShootSpeed;
    }

    public void setShootSpeed(float ShootSpeed) {
        this.ShootSpeed = ShootSpeed;
    }

    @Override
    public void die() {
        System.out.println("You lose");
    }

    private BufferedImage getCurrentImage() {
        if (System.currentTimeMillis() - timerAnimation>= 100) {
            i++;
            if(i == 3){
                i = 0;
            }
            timerAnimation = System.currentTimeMillis();
        }
        return naveStates[i];
    }

    public int getBulletCount() {
        return bullcount;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

}
