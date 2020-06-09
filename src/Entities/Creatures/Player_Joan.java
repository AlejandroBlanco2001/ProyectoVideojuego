package Entities.Creatures;

import Entities.Entity;
import Entities.EntityManager;
import Tilemaps.Tile;
import Entities.Static.BookPile;
import Tilemaps.Animation;
import MainG.Handler;
import MainG.Window;
import Tilemaps.Assets;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * @author German David
 */
public class Player_Joan extends Character {

    private boolean punchie = false;
    private boolean canMove = true;
    private boolean endState = false;
    private int ar1, ar2;
    private boolean gameFinished = false;

    //Attack range
    private int attackR;

    //Attack timer, AttackCooldown
    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;

    //Animations
    private Animation animDown, animUp, animR, animL;

    public Player_Joan(Handler handler, EntityManager entityM, float x, float y) {
        super(handler, entityM, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATUR_HEIGHT);

        /* 
            Bounds para la chica
            bounds.x=40;
            bounds.y=35;
            bounds.width=35;
            bounds.height=60;
         */
        
        bounds.x = 40;
        bounds.y = 65;
        bounds.width = 52;
        bounds.height = 40;

        attackR = 20;

        //Animations           Speed en millis()
        animDown = new Animation(300, Assets.playerDown);
        animUp = new Animation(300, Assets.playerUp);
        animR = new Animation(300, Assets.playerRight);
        animL = new Animation(300, Assets.playerLeft);
        this.speed-=2;
    }

    @Override
    public void update() {
        if (!gameFinished) {
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
    }

    private void checkAttacks() {

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();

        ar.width = attackR;
        ar.height = attackR;
        if (Window.keyManager.up) {
            ar.x = cb.x + cb.width / 2 - attackR / 2;
            ar.y = cb.y - attackR;
        } else if (Window.keyManager.down) {
            ar.x = cb.x + cb.width / 2 - attackR / 2;
            ar.y = cb.y + cb.height;
        } else if (Window.keyManager.left) {
            ar.x = cb.x - attackR;
            ar.y = cb.y + cb.height / 2 - attackR / 2;
        } else if (Window.keyManager.right) {
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 - attackR / 2;
        } else {
            return;
        }

        ar1 = ar.x;
        ar2 = ar.y;
        punchie = true;
        attackTimer = 0;

        for (Entity en : manager.getEntities()) {
            if (!en.equals(this)) {
                if (en.getCollisionBounds(0, 0).intersects(ar)) {
                    BookPile book = (BookPile) en;
                    book.foundBook();
                    return;
                }
            }
        }
    }

    @Override
    public void die() {
        System.out.println("You lose");
    }

    public void getInput() {
        Xmove = 0;
        Ymove = 0;
        if (canMove) {
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
    }

    @Override
    public void move() {
        if (!checkEntityCollisions(Xmove, 0f)) {
            moveX();
        }
        if (!checkEntityCollisions(0f, Ymove)) {
            moveY();
        }
    }

    public void moveX() {
        if (Xmove > 0) {
            int tx = (int) (x + Xmove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += Xmove;
            }
        } else if (Xmove < 0) {
            int tx = (int) (x + Xmove + bounds.x) / Tile.TILEWIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += Xmove;
            }
        }
    }

    public void moveY() {
        if (Ymove < 0) {
            int ty = (int) ((y + Ymove + bounds.y) / Tile.TILEHEIGHT);
            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += Ymove;
            }

        } else if (Ymove > 0) {
            int ty = (int) ((y + Ymove + bounds.y + bounds.height) / Tile.TILEHEIGHT);
            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += Ymove;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamara().getxOffset()), (int) (y - handler.getGameCamara().getyOffset()), width, height, null);
    }

    /**
     * Metodo para ver si el personaje tiene una colision con un tile que es de tipo solido.
     *
     * Cambiar este metodo a que retorne falso para atravesar paredes.
     *
     * @param x Coordenada en X
     * @param y Coordenada en Y
     * @return Retorna un booleano que indica si se está chocando con un tile que es SOLIDO.
     */
    
    @Override
    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    //Conseguir la animación en cada movimiento
    private BufferedImage getCurrentAnimationFrame() {
        if (Xmove < 0) {
            return animL.getCurrentFrame();
        } else if (Xmove > 0) {
            return animR.getCurrentFrame();
        } else if (Ymove < 0) {
            return animUp.getCurrentFrame();
        } else if (Ymove > 0) {
            return animDown.getCurrentFrame();
        } else {
            return Assets.playerDown[0];
        }
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : manager.getEntities()) {
            if (e.equals(this)) //Continua al siguiente objeto
            {
                continue;
            }
            //Si las dos hitbox se intersectan entonces si hubo colision
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public void setCanMove(boolean b) {
        this.canMove = b;
    }

    public boolean checkEnd() {
        if (this.x > 5700 && this.y > 1500 && this.y < 1800) {
            return true;
        }
        return false;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public Handler getHandler() {
        return handler;
    }
    
    
    
}
