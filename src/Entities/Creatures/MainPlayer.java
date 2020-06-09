package Entities.Creatures;

import Audio.AudioLoader;
import Entities.EntityManager;
import MainG.Handler;
import MainG.Window;
import MainLevel.Tiles.TileMainLevel;
import MainLevel.WorldGenerator.WorldPlat;
import Tilemaps.Animation;
import Tilemaps.Assets;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import tinysound.Sound;

public class MainPlayer extends Character {

    private double speedX, speedY, percentajeVelocity, maxSpeedY, xMove, yMove, maxPercentajeVelocity;
    private double gravity = 750;
    private int antiSpameJump = 0;
    private int antiSpamChest = 0;

    private boolean isGround = false;
    private boolean jumping = false;
    private boolean reset = false;
    private boolean buttonPressed = false;
    private boolean falling = false;
    private boolean inMinigame = false;
    private boolean showStamp = false;

    private Punto returnPoint;
    private int amountOfReturns;
    private int maxReturns;
    private double timePressed;

    private Sound backTime, jumpSound;

    private Animation animDL, animDR, animUpR, animUpL, animR, animL, animSSL, animSSR, animTimeStamp;

    public void setPosition(int[] position) {
        this.x = position[0];
        this.y = position[1];
    }

    public class Punto {

        public Punto(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float x;
        public float y;
    }

    public MainPlayer(Handler handler, EntityManager entityM, float x, float y) {
        super(handler, entityM, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATUR_HEIGHT);

        bounds.x = 40;
        bounds.y = 35;
        bounds.height = 60;
        bounds.width = 40;

        xMove = 0;
        yMove = 0;
        speedX = 125;
        speedY = 200;
        percentajeVelocity = 0;
        maxPercentajeVelocity = 0.90;
        maxSpeedY = 400;

        //Animations 
        animUpL = new Animation(100, Assets.mainPlayerUpL);
        animUpR = new Animation(100, Assets.mainPlayerUpR);

        animL = new Animation(100, Assets.mainPlayerLeft);
        animR = new Animation(100, Assets.mainPlayerRight);

        animDL = new Animation(100, Assets.mainPlayerFallingL);
        animDR = new Animation(100, Assets.mainPlayerFallingR);

        animSSL = new Animation(100, Assets.mainPlayerStandStillL);
        animSSR = new Animation(100, Assets.mainPlayerStandStillR);

        animTimeStamp = new Animation(100, Assets.timeStamp);

        timePressed = System.currentTimeMillis();
        init();
    }

    public void init() {
        backTime = AudioLoader.backInTime;
        jumpSound = AudioLoader.jumpSound;
    }

    @Override
    public void getInput() {
        if (Window.keyManager.right) {
            xMove = speedX;
        }

        if (Window.keyManager.left) {
            xMove = -speedX;
        }

        if (Window.keyManager.up) {
            if (isGround && !jumping && !falling && antiSpameJump == 0) {
                speedY = maxSpeedY;
                yMove = speedY;
                jumping = true;
                antiSpameJump = 1;
                jumpSound.play(0.5f);
            }
        } else {
            antiSpameJump = 0;
        }

        // Running
        if (Window.keyManager.shift) {
            if (percentajeVelocity <= maxPercentajeVelocity) {
                percentajeVelocity += 0.03;
            } else {
                percentajeVelocity = maxPercentajeVelocity;
            }
        } else {
            if (percentajeVelocity > 0) {
                percentajeVelocity -= 0.02;
            } else {
                percentajeVelocity = 0;
            }
        }

        // Time Travel
        if (Window.keyManager.actionTime && System.currentTimeMillis() - timePressed >= 1000) {
            if (returnPoint == null && amountOfReturns < maxReturns) {
                returnPoint = new Punto(x, y);
                timePressed = System.currentTimeMillis();
                showStamp = true;
            } else {
                if (amountOfReturns != maxReturns) {
                    backwards(returnPoint);
                    amountOfReturns++;
                    returnPoint = null;
                    showStamp = false;
                    backTime.play(0.5f);
                }
            }
        }

        if (Window.keyManager.test) {
            if (antiSpamChest == 0) {
                checkInteraction(rightHand(), leftHand());
                antiSpamChest = 1;
            } else {
                antiSpamChest = 1;
            }
        } else {
            antiSpamChest = 0;
        }
        if (Window.keyManager.reset) {
            reset = true;
            returnPoint = null;
        }

    }

    @Override
    public void die() {
        reset = true;
        active = true;
        setReturnPoint(null);
    }

    @Override
    public void move() {
        moveX();
        moveY();
    }

    @Override
    public void update() {
        getInput();
        move();
        if (xMove < 0) {
            bounds.x = 40;
            bounds.y = 35;
            bounds.height = 60;
            bounds.width = 40;
        } else {
            bounds.x = 60;
            bounds.y = 35;
            bounds.height = 60;
            bounds.width = 40;
        }
        animL.update();
        animR.update();
        animUpL.update();
        animSSL.update();
        animDL.update();
        animUpR.update();
        animSSR.update();
        animDR.update();
        animR.update();
        animTimeStamp.update();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, null);
        if (showStamp) {
            g.drawImage(animTimeStamp.getCurrentFrame(), (int) returnPoint.x, (int) returnPoint.y, null);
        }
    }

    /**
     * Devuelve un valor booleano sobre el estado de colision del jugador con
     * una Tile.
     *
     * @return Dependiendo si hay o no una colision con una {@code Tile}
     * devuelve un valor {@code boolean}
     *
     * @author CodeNMore
     * @param x Posicion horizontal relativa donde se encuentra la Tile
     * @param y Posicion vertical relativa donde se encuentra la Tile
     */
    @Override
    protected boolean collisionWithTile(int x, int y) {
        try {
            return handler.getWorld().getTile(x, y).isSolid();
        } catch (Exception e) {
            System.out.println("SALISTE DEL MAPA");
            return true;
        }
    }

    /**
     * Devuelve la Tile en la posicion que se le solicite
     *
     * @author CodeNMore
     * @param x Posicion horizontal relativa de la Tile
     * @param y Posicion vertical relativa de la Tile
     */
    protected TileMainLevel getSpecificTile(int x, int y) {
        WorldPlat tempWorld = (WorldPlat) handler.getWorld();
        TileMainLevel tempTile = (TileMainLevel) tempWorld.getTile(x, y);
        return tempTile;
    }

    /**
     * Reubica al jugador en la posicion guarda en un punto
     *
     * @author Isaac Blanco
     * @param punto Punto que contiene las posiciones guardas.
     */
    public void backwards(Punto punto) {
        x = punto.x;
        y = punto.y;
    }

    public void moveX() {
        if (Window.keyManager.right) {
            if (hasRightCollision()) {
                double extraVelocity = xMove * percentajeVelocity * handler.getDeltaTime();
                x += (xMove * handler.getDeltaTime() + extraVelocity);
            } else {
                if (falling) {
                    double extraVelocity = xMove * percentajeVelocity * handler.getDeltaTime();
                    x += (xMove * handler.getDeltaTime() + extraVelocity);
                } else {
                    int tx = (int) (x + xMove * handler.getDeltaTime() + bounds.x + bounds.width) / TileMainLevel.TILEWIDTH;
                    x = tx * TileMainLevel.TILEWIDTH - bounds.x - bounds.width - 1;
                }
            }
        }
        if (Window.keyManager.left) {
            if (hasLeftCollision()) {
                double extraVelocity = xMove * percentajeVelocity * handler.getDeltaTime();
                x += xMove * handler.getDeltaTime() + extraVelocity;
            } else {
                if (falling) {
                    double extraVelocity = xMove * percentajeVelocity * handler.getDeltaTime();
                    x += xMove * handler.getDeltaTime() + extraVelocity;
                } else {
                    int tx = (int) (x + xMove * handler.getDeltaTime() + bounds.x) / TileMainLevel.TILEWIDTH;
                    x = tx * TileMainLevel.TILEWIDTH + TileMainLevel.TILEWIDTH - bounds.x;
                }
            }
        }
    }

    public void moveY() {
        fall();
        jumping();
        cheackDamage();
    }

    private Rectangle rightHand() {
        Rectangle hand = new Rectangle((int) (x + bounds.x) + 25, (int) (y + bounds.y) + 35, bounds.width / 2 + 5, bounds.height / 4);
        return hand;
    }

    private Rectangle leftHand() {
        Rectangle hand = new Rectangle((int) (x + bounds.x) - 20, (int) (y + bounds.y) + 35, bounds.width / 2 + 5, bounds.height / 4);
        return hand;
    }

    private TileMainLevel getRightHandInteraction(Rectangle hand) {
        hand.width = hand.width * 2 - 5;
        hand.x += 20;
        return getSpecificTile(hand.x / TileMainLevel.TILEWIDTH, hand.y / TileMainLevel.TILEHEIGHT);
    }

    private TileMainLevel getLeftHandInteraction(Rectangle hand) {
        hand.width = hand.width * 2 - 5;
        hand.x += 10;
        return getSpecificTile(hand.x / TileMainLevel.TILEWIDTH, hand.y / TileMainLevel.TILEHEIGHT);
    }

    private boolean hasLeftCollision() {
        int tx = (int) (x + xMove * handler.getDeltaTime() + bounds.x) / TileMainLevel.TILEWIDTH;
        return !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / TileMainLevel.TILEHEIGHT)
                && !collisionWithTile(tx, (int) (y + bounds.y) / TileMainLevel.TILEHEIGHT);
    }

    private TileMainLevel getTileLeftTouching() {
        int tx = (int) (x + xMove * handler.getDeltaTime() + bounds.x) / TileMainLevel.TILEWIDTH;
        if (!hasLeftCollision()) {
            return getSpecificTile(tx, (int) (y + bounds.y) / TileMainLevel.TILEHEIGHT);
        } else {
            return null;
        }
    }

    private boolean hasRightCollision() {
        int tx = (int) (x + xMove * handler.getDeltaTime() + bounds.x + bounds.width) / TileMainLevel.TILEWIDTH;
        return !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / TileMainLevel.TILEHEIGHT)
                && !collisionWithTile(tx, (int) (y + bounds.y) / TileMainLevel.TILEHEIGHT);
    }

    private TileMainLevel getTileRightTouching() {
        int tx = (int) (x + xMove * handler.getDeltaTime() + bounds.x + bounds.width) / TileMainLevel.TILEWIDTH;
        if (!hasRightCollision()) {
            return getSpecificTile(tx, (int) (y + bounds.y) / TileMainLevel.TILEHEIGHT);
        } else {
            return null;
        }
    }

    private boolean hasDownCollision() {
        int ty = (int) (y + yMove * handler.getDeltaTime() + bounds.y + bounds.height) / TileMainLevel.TILEHEIGHT;
        return !collisionWithTile((int) (x + bounds.x + bounds.width) / TileMainLevel.TILEWIDTH, ty)
                && !collisionWithTile((int) (x + bounds.x) / TileMainLevel.TILEWIDTH, ty);
    }

    private TileMainLevel getTileDownTouching() {
        int ty = (int) (y + yMove * handler.getDeltaTime() + bounds.y + bounds.height) / TileMainLevel.TILEHEIGHT;
        if (!hasDownCollision()) {
            return getSpecificTile((int) (x + bounds.x) / TileMainLevel.TILEWIDTH, ty);
        } else {
            return null;
        }
    }

    private boolean hasUpCollision() {
        int ty = (int) (y + bounds.y + yMove * handler.getDeltaTime() - 2f) / TileMainLevel.TILEHEIGHT;
        return !collisionWithTile((int) (x + bounds.x + bounds.width) / TileMainLevel.TILEWIDTH, ty)
                && !collisionWithTile((int) (x + bounds.x) / TileMainLevel.TILEWIDTH, ty);
    }

    public void fall() {
        if (hasDownCollision() && !jumping) {
            double relativeY = speedY * handler.getDeltaTime();
            double relativeMaxY = maxSpeedY * handler.getDeltaTime();
            if (relativeY <= relativeMaxY) {
                yMove = speedY;
                y += yMove * handler.getDeltaTime();
                speedY += gravity * handler.getDeltaTime();
            } else {
                yMove = maxSpeedY;
                y += yMove * handler.getDeltaTime();
            }
        } else {
            if (!jumping) {
                int ty = (int) (y + yMove * handler.getDeltaTime() + bounds.y + bounds.height) / TileMainLevel.TILEHEIGHT;
                y = ty * TileMainLevel.TILEHEIGHT - bounds.y - bounds.height - 1;
            }
            falling = false;
            yMove = 100;
            isGround = true;
        }
    }

    public void jumping() {
        if (jumping) {
            if (hasUpCollision()) {
                if (speedY <= 0) {
                    jumping = false;
                    isGround = false;
                    falling = true;
                    speedY = 0;
                }
                yMove = speedY;
                y -= yMove * handler.getDeltaTime();
                speedY -= gravity * handler.getDeltaTime();
            } else {
                jumping = false;
                isGround = false;
                falling = true;
                speedY = 0;
            }
        } else {
            if (jumping && isGround) {
                int ty = (int) (y + bounds.y + yMove * handler.getDeltaTime()) / TileMainLevel.TILEHEIGHT;
                y = ty * TileMainLevel.TILEHEIGHT + TileMainLevel.TILEHEIGHT - bounds.y;
                if (!hasUpCollision()) {
                    jumping = false;
                }
            }
        }
    }

    //Conseguir la animación en cada movimiento
    private BufferedImage getCurrentAnimationFrame() {
        if (Window.keyManager.left && !(jumping || !isGround)) {
            return animL.getCurrentFrame();
        } else if (Window.keyManager.right && !(jumping || !isGround)) {
            return animR.getCurrentFrame();
        }
        if (jumping || !isGround) {
            if (!isGround) {
                if (xMove < 0) {
                    return animDL.getCurrentFrame();
                } else {
                    return animDR.getCurrentFrame();
                }
            } else if (jumping) {
                if (xMove < 0) {
                    return animUpL.getCurrentFrame();
                } else {
                    return animUpR.getCurrentFrame();
                }
            }
        }
        if (xMove > 0) {
            return animSSR.getCurrentFrame();
        } else if (xMove < 0) {
            return animSSL.getCurrentFrame();
        } else {
            return animSSL.getCurrentFrame();
        }
    }

    public void checkAttacks() {
        Rectangle cb = getCollisionBounds();
    }

    private void checkInteraction(Rectangle handR, Rectangle handL) {
        TileMainLevel auxT = getRightHandInteraction(handR);
        if (auxT != null) {
            if (checkInteracionObject(auxT)) {
                buttonPressed = true;
            }
        } else {
            buttonPressed = false;
        }
        auxT = getLeftHandInteraction(handL);
        if (auxT != null) {
            if (checkInteracionObject(auxT)) {
                buttonPressed = true;
            }
        } else {
            buttonPressed = false;
        }
    }

    private void cheackDamage() {
        TileMainLevel auxT = getTileDownTouching();
        if (auxT != null) {
            if (auxT.makeDamage()) {
                active = false;
                die();
            }
        }
        auxT = getTileLeftTouching();
        if (auxT != null) {
            if (auxT.makeDamage()) {
                active = false;
                die();
            }
        }
        auxT = getTileRightTouching();
        if (auxT != null) {
            if (auxT.makeDamage()) {
                active = false;
                die();
            }
        }
    }

    public boolean isTouchingLap() {
        TileMainLevel auxT = getTileRightTouching();
        if (auxT != null) {
            if (auxT.getId() == 5 || auxT.getId() == 17) {
                return true;
            }
        }
        return false;
    }

    public boolean checkInteracionObject(TileMainLevel tile) {
        if (tile.isInteractive()) {
            switch (tile.getId()) {
                case 9:
                    inMinigame = true;
                    return true;
                default:
                    System.out.println("Interaccion");
                    return true;
            }
        }
        return false;
    }

    // Getters y Setters
    public boolean isButtonPressed() {
        return buttonPressed;
    }

    public void setButtonPressed() {
        this.buttonPressed = !buttonPressed;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public int getAmountOfReturns() {
        return amountOfReturns;
    }

    public int getMaxReturns() {
        return maxReturns;
    }

    public void setAmountOfReturns(int amountOfReturns) {
        this.amountOfReturns = amountOfReturns;
    }

    public void setMaxReturns(int maxReturns) {
        this.maxReturns = maxReturns;
    }

    public void setReturnPoint(Punto value) {
        this.returnPoint = value;
    }

    public boolean isInMinigame() {
        return inMinigame;
    }

    public void setInMinigame(boolean inMinigame) {
        this.inMinigame = inMinigame;
    }

    public void setAnimD(Animation animD) {
        this.animDL = animD;
    }

    public double getxMove() {
        return xMove;
    }

    public double getyMove() {
        return yMove;
    }

    public void setxMove(double xMove) {
        this.xMove = xMove;
    }

    public void setyMove(double yMove) {
        this.yMove = yMove;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setShowStamp(boolean showStamp) {
        this.showStamp = showStamp;
    }
}
