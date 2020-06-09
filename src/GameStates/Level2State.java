package GameStates;

import Entities.EntityManager;
import java.awt.Graphics2D;
import Handlers.KeyManager;
import MainG.Handler;
import MainG.Window;
import SecondMinigame.HUD;
import SecondMinigame.Level2UpManager;
import SecondMinigame.WorldSpace;
import Tilemaps.Animation;
import Tilemaps.Assets;
import java.awt.Image;
import tinysound.Music;

public class Level2State extends GameState {

    private Music bgTalkMusic, bgMusic;
    //private Background bg;
    private Handler handler;
    private WorldSpace world;
    private DialogueLoader dialogueLoader;
    private HUD hud;

    private Level2UpManager levelManager;
    public KeyManager teclas;
    private EntityManager entityManager;
    private Animation background;
    private boolean ya = true;

    public Level2State(GameStateManager gsm, Handler handler, String tag) {
        super(gsm);
        this.handler = handler;
        this.levelTag = tag;
        background = new Animation(50, Assets.spaceBackgroundPlat);
        entityManager = new EntityManager(handler, this);
        dialogueLoader = new DialogueLoader(handler);
        world = new WorldSpace(entityManager, handler);
        hud = new HUD(entityManager);
        levelManager = new Level2UpManager(this, hud, world, dialogueLoader, entityManager);
        init();
    }

    @Override
    public void init() {
        world.setHUD(hud);
        world.setLevelUpManager(levelManager);
        timePassed = System.currentTimeMillis();
    }

    @Override
    public void update() {
        // Iniciar el menu de pausa
        if (Window.keyManager.pause) {
            pauseState();
        }
        background.update();
        musicControl();
        hud.update();
        world.update();
        levelManager.update(hud.getPoint(), hud.getHealth());

    }

    @Override
    public void draw(Graphics2D g) {
        // Dibuja el fondo
        if (ya) {
            levelManager.setGraphics(g);
            ya = !ya;
        }
        g.drawImage(getCurrentFrame(), 0, 0, 1080, 720, null);
        levelManager.render();
        hud.render(g);
    }

    @Override
    public void musicControl() {
    }

    @Override
    public World getWorld() {
        return world;
    }

    public void setGameFinished() {
        // Finaliza el juego y devuelve al nivel prinicipal, se espera cambiar el setState, por el reloadState, puesto que por medio de ese se accede a este
        gsm.getGameStates()[1].getLoadData();
        MainLevel auxS = (MainLevel) gsm.getGameStates()[1];
        auxS.getLevelManager().setFinishedMinigame();
        gsm.reloadState(1);
    }

    // Indica al levelManager, en este caso, el encargado de Loader y Rellenar el TXT, de rellenar sus datos.
    @Override
    public void getInsertData() {
        levelManager.insertData();
    }

    // Indica al levelManager, en este caso, el encargado de Loader y Rellenar el TXT, de cargarlo.
    @Override
    public void getLoadData() {
        levelManager.loadData();
    }

    private Image getCurrentFrame() {
        return background.getCurrentFrame();
    }

    public GameStateManager getGsm() {
        return gsm;
    }

    public void killMusic() {
        levelManager.getMusicPlayer().kill();
    }
}
