package GameStates;

import Entities.Creatures.Player_Joan;
import Entities.EntityManager;
import FirstMinigame.WorldGenerator.WorldLibrary;
import MainG.Handler;
import FirstMinigame.Level1UpManager;
import MainG.Window;
import Tilemaps.Assets;
import UI.UIHelper;
import UI.UIManager;
import java.awt.Graphics2D;

public class Level1State extends GameState {

    Handler handler;
    private World world;
    private String path = "Resources/Worlds/World1.txt";
    private Player_Joan joan;
    private Level1UpManager levelManager;
    private DialogueLoader dialogueLoader;
    boolean showedTutorial = false;
    private EntityManager entityManager;
    private boolean doingQuiz = false;
    private UIManager uimanager;

    public Level1State(GameStateManager gsm, Handler handler, String tag) {
        super(gsm);
        this.levelTag = tag;
        this.handler = handler;
        world = new WorldLibrary(this.handler, path, this);
        dialogueLoader = new DialogueLoader(handler);
        dialogueLoader.setGameTag(this.levelTag);
        this.entityManager = this.world.getEntityM();
        uimanager= new UIManager(handler);
        uimanager.addUIObject(new UIHelper(Assets.UIHelperLvl1,5000,230,457,600,253,uimanager));
        this.levelManager = new Level1UpManager(this, world, entityManager);

        init();
    }

    @Override
    public void init() {
        timePassed = System.currentTimeMillis();
    }

    @Override
    public void update() {
        uimanager.tick();
        if (Window.keyManager.debug) {
            setGameFinished();
        }
        // Iniciar el menu de pausa
        if (Window.keyManager.pause) {
            pauseState();
        }
        world.update();
        levelManager.update();
    }

    public void setGameFinished() {
        this.gsm.setState(5);
    }

    public void setGameFinished() {
        WorldLibrary auxW = (WorldLibrary) world.cast(levelManager);
        gsm.getGameStates()[1].getLoadData();
        MainLevel auxS = (MainLevel) gsm.getGameStates()[1];
        auxS.getLevelManager().setFinishedMinigame();
        auxW.setFinished();
        gsm.reloadState(1);
    }

    @Override
    public void draw(Graphics2D g) {
        world.render(g);
        uimanager.render(g);
    }

    @Override
    public void musicControl() {

    }

    public World getWorld() {
        return world;
    }

    @Override
    public void getInsertData() {

    }

    @Override
    public void getLoadData() {
    }
}
