package SecondMinigame;

import Audio.AudioLoader;
import GameStates.DialogueLoader;
import Entities.Creatures.Player;
import Entities.Entity;
import Entities.EntityManager;
import GameStates.GameState;
import GameStates.Level2State;
import GameStates.LevelUpManager;
import MainG.Window;
import Tilemaps.Background;
import UtilLoader.MusicPlayer;
import UtilLoader.SaveGame;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import tinysound.Music;

public class Level2UpManager extends LevelUpManager implements SaveGame {

    private boolean flag1 = false, flag2 = false, flag3 = false, endMinigame = false, oneTime = false;
    private int phase = 0;
    private Graphics2D g;
    private Level2State state;
    private HUD hud;
    private Music bgTalkMusic, bgMusic;
    private MusicPlayer musicPlayer;
    private Thread hiloMusica;
    private boolean ya = true;
    private boolean firstTime = true;

    public Level2UpManager(GameState state, HUD hud, WorldSpace world, DialogueLoader dialogueLoader, EntityManager entityManager) {
        super(world, entityManager);
        this.hud = hud;
        this.dialogueLoader = dialogueLoader;
        this.state = (Level2State) state;
        bgTalkMusic = AudioLoader.bgTalkMomentSpaceInvaders;
        bgMusic = AudioLoader.bgMusicSpaceInvaders;
        musicPlayer = new MusicPlayer(bgTalkMusic, bgMusic);
        init();
    }

    public void init() {
        flag1 = false;
        flag2 = false;
        flag3 = false;
        endMinigame = false;
        oneTime = false;
        phase = 0;
        ya = true;
        firstTime = true;
    }

    public void levelUpManager(int points, int health) {
        changeMusic();
        if (!endMinigame) {
            WorldSpace temporaryWorld = (WorldSpace) world.cast(this);
            if (phase == 0) {
                temporaryWorld.setGenerateEnemys(false);
            }
            if (points < 5 && !dialogueLoader.getDialogueMark() && phase == 0) {
                insertData();
                phase = -1;
            }
            if (points >= 10 && !flag1) {
                // Con esto da inicio a la generacion de los primeros enemigos
                phase = 1;
                // Se guarda un Checkpoint 1, rellenando los datos del txt
                insertData();
                dialogueLoader.setDialogueMark();
                //moveFasterBackground(state.getBg());
                flag1 = !flag1;
                temporaryWorld.clearScreenEntities();
                temporaryWorld.setGenerateEnemys(false);
            } else if (points >= 15 && !flag2) {
                // Con esto se da inicio a la generacion de los segundos enemigos
                phase = 2;
                // Se guarda un Checkpoint 2, rellenando los datos del txt
                insertData();
                dialogueLoader.setDialogueMark();
                //moveFasterBackground(state.getBg());
                flag2 = !flag2;
                temporaryWorld.clearScreenEntities();
                temporaryWorld.generateEnemys();
            } else if (points >= 35 && !flag3) {
                //Generacion del boss y solo quedan asteorides
                phase = 3;
                // Se guarda un Checkpoint 3, rellenando los datos del txt
                insertData();
                dialogueLoader.setDialogueMark();
                //moveFasterBackground(state.getBg());
                flag3 = !flag3;
                temporaryWorld.clearScreenEntities();
                temporaryWorld.generateEnemys();
            }
            // Respawn del jugador
            if (isPlayerDead()) {
                // Se cargan los datos, se limpia el mundo y se revive al jugador con los datos obtenidos.
                loadData();
                temporaryWorld.clearScreenEntities();
                getPlayer().setActive(true);
            }
        } else {
            finishLevel();
        }
    }

    public void update(int points, int health) {
        if (firstTime) {
            hiloMusica = new Thread(musicPlayer, "hiloAuxiliarMusica");
            hiloMusica.start();
            firstTime = false;
        }
        levelUpManager(points, health);
        WorldSpace temporaryWorld = (WorldSpace) world.cast(this);
        // Verifica que el Boos este muerto para acabar el juego
        if (phase == 3 && flag3 == true && temporaryWorld.getBoss() != null) {
            endMinigame = !temporaryWorld.isBossAlive();
        }
    }

    public void render() {
        WorldSpace temporaryWorld = (WorldSpace) world.cast(this);
        if (dialogueLoader.getDialogueMark()) {
            dialogueLoader.render(this.g);
        } else {
            temporaryWorld.setGenerateEnemys(true);
        }
        world.render(this.g);
    }

    @Override
    public void changeMusic() {
        if (phase == -1 && ya) {
            ya = false;
            musicPlayer.switchSong();
        }
        if(state.getGsm().getCurrentState() != 3){
            musicPlayer.kill();
        }
    }

    @Override
    public void finishLevel() {
        musicPlayer.kill();
        state.setGameFinished();
    }

    public void moveFasterBackground(Background bg) {
        bg.setVector(bg.getDx() - 2.0d, 0);
    }

    public void setLevel(Level2State state) {
        this.state = state;
    }

    public int getPhase() {
        return this.phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setGraphics(Graphics2D g) {
        this.g = g;
    }

    public boolean isPlayerDead() {
        for (Entity e : entityManager.getEntities()) {
            if (e instanceof Player) {
                Player player = (Player) e;
                if (player.isActive()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public Player getPlayer() {
        for (Entity e : entityManager.getEntities()) {
            if (e instanceof Player) {
                Player player = (Player) e;
                return player;
            }
        }
        return null;
    }

    @Override
    public void levelUpManager() {

    }

    // Metodo encargado de rellenar el TXT
    /*
        Agrega una linea para cada dato, en este caso guarda:
        1 - GameState 
        2 - Vida del Jugador
        3 - Posicion en X del Jugador
        4 - Posicion en Y del Jugador
        5 - Cantidad de puntos del HUD
    
     */
    @Override
    public void insertData() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("savefile.txt"));
            bw.write("" + 3);
            bw.newLine();
            bw.write("" + getPlayer().getHealth());
            bw.newLine();
            bw.write("" + (int) getPlayer().getX());
            bw.newLine();
            bw.write("" + (int) getPlayer().getY());
            bw.newLine();
            bw.write("" + hud.getPoint());
            bw.close();
        } catch (Exception e) {

        }
    }

    // Metodo encargado de recupear los datos del TXT, y pasarlo a los parametros indicados
    /*
        Busca linea por linea para cada dato, en este caso obtiene:

        2 - Vida del Jugador 
        3 - Posicion en X del Jugador
        4 - Posicion en Y del Jugador
        5 - Cantidad de puntos del HUD


     */
    @Override
    public void loadData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("savefile.txt"));
            br.readLine(); // Salta el GameState
            getPlayer().setHealth(Integer.parseInt(br.readLine()));
            getPlayer().setX(Integer.parseInt(br.readLine()));
            getPlayer().setY(Integer.parseInt(br.readLine()));
            hud.setPoint(Integer.parseInt(br.readLine()));
            br.close();
        } catch (Exception e) {

        }
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    
    
}
