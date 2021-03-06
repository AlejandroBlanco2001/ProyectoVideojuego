package GameStates;

import Audio.AudioLoader;
import Entities.EntityManager;
import MainG.Handler;
import MainG.Window;
import MainLevel.MainLevelUpManager;
import MainLevel.WorldGenerator.WorldPlat;
import Tilemaps.Assets;
import UI.UIHelper;
import UI.UIManager;
import java.awt.Graphics2D;

public class MainLevel extends GameState {

    private EntityManager entityManager;
    private World world;
    private boolean gameChanged;
    private MainLevelUpManager levelManager;
    private String path = "Resources/Worlds/WorldThematic1.txt";
    private UIManager uimanager;
    private boolean onlyThis;
    private boolean ya, isAnimation;

    public MainLevel(GameStateManager gsm, Handler handler, String tag) {
        super(gsm);
        this.levelTag = tag;
        entityManager = new EntityManager(handler);
        this.world = new WorldPlat(handler, entityManager, path, this);
        this.levelManager = new MainLevelUpManager(world, entityManager, this);
        uimanager = new UIManager(handler);
        uimanager.addUIObject(new UIHelper(Assets.UIHelperMain, 5000, 400, 30, 475, 200));
        ya = true;
        isAnimation = false;
        gameChanged = false;
        init();
    }

    @Override
    public void init() {
        onlyThis = true;
        levelManager.setMusic(AudioLoader.musicPlayListMainLevel);
        timePassed = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (onlyThis) {
            Window.mouse.setUIManager(uimanager);
            onlyThis = false;
        }
        if (!gameChanged) {
            world.update();
            levelManager.levelUpManager();
            // Iniciar el menu de pausa
            if (Window.keyManager.pause) {
                pauseState();
            }
        }

    }

    @Override
    public void draw(Graphics2D g) {
        if (!gameChanged) {
            world.render(g);
            if (levelManager.getCurrentWorld() == 1) {
                uimanager.render(g);
                if (ya) {
                    g.drawImage(Assets.viñetaF, 0, 0, 1080, 720, null);
                    if (Window.keyManager.enter) {
                        ya = false;
                    }
                }
            }
        }
    }

    @Override
    public void musicControl() {

    }

    @Override
    public World getWorld() {
        return world;
    }

    public String pathString(int Stage) {
        switch (Stage) {
            case 1:
                return "Resources/Worlds/WorldThematic1.txt";
            case 2:
                return "Resources/Worlds/WorldThematic2.txt";
            case 3:
                return "Resources/Worlds/WorldThematic3.txt";
            case 4:
                return "Resources/Worlds/WorldThematic4.txt";
            case 5:
                return "Resources/Worlds/WorldThematic5.txt";
            case 6:
                return "Resources/Worlds/WorldThematic6.txt";
            case 7:
                return "Resources/Worlds/WorldThematic7.txt";
            case 8:
                return "Resources/Worlds/WorldThematic8.txt";
            case 9:
                return "Resources/Worlds/WorldThematic9.txt";
            case 10:
                return "Resources/Worlds/WorldThematic10.txt";
            default:
                return "ERROR";
        }
    }

    @Override
    public void getInsertData() {
        levelManager.insertData();
    }

    @Override
    public void getLoadData() {
        if (gameChanged == true) {
            gameChanged = !gameChanged;
            levelManager.setLevelSwitched(false);
            levelManager.getMusicPlayer().resume();
        }
        levelManager.loadData();
    }

    public void teleporterReached(int minigame) {
        if (minigame == 1) {
            gsm.reloadState(2);
        } else {
            gsm.reloadState(3);
        }
        gameChanged = !gameChanged;
    }

    public MainLevelUpManager getLevelManager() {
        return levelManager;
    }

    public boolean isGameFinished() {
        MainLevelUpManager manager = (MainLevelUpManager) levelManager;
        return manager.getFinishedGame();
    }

    @Override
    public void killMusic() {
        levelManager.getMusicPlayer().kill();
    }
}
