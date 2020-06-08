package FirstMinigame;

import Entities.EntityManager;
import FirstMinigame.WorldGenerator.WorldLibrary;
import GameStates.DialogueLoader;
import GameStates.Level1State;
import GameStates.LevelUpManager;
import GameStates.World;
import SecondMinigame.HUD;
import SecondMinigame.WorldSpace;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Level1UpManager extends LevelUpManager {

    Graphics2D g;
    private WorldLibrary worldLib;
    private Level1State state;
    private boolean doingQuiz = false;

    @Override
    public void levelUpManager() {
    }

    @Override
    public void changeMusic() {

    }

    @Override
    public void finishLevel() {

    }

    public Level1UpManager(Level1State state, World world, EntityManager entityManager) {
        super(world, entityManager);
        this.state = (Level1State) state;
        this.worldLib = (WorldLibrary) world;
    }

    public void update() {
        if (this.entityManager == null) {
            System.out.println("DEJA DE PONERTE EN NULO MALDITA SEA");
        } else {
            if (this.entityManager.getQuizState()) {
                this.state.setGameFinished();
            }
        }
    }

    public void insertData() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("savefile.txt"));
            bw.write("" + 3);
            bw.newLine();
            //bw.write("" + getPlayer().getHealth());
            bw.newLine();
            //bw.write("" + (int) getPlayer().getX());
            bw.newLine();
            //bw.write("" + (int) getPlayer().getY());
            bw.newLine();
            //bw.write("" + hud.getPoint());
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
    public void loadData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("savefile.txt"));
            //br.readLine(); // Salta el GameState
            //getPlayer().setHealth(Integer.parseInt(br.readLine()));
            //getPlayer().setX(Integer.parseInt(br.readLine()));
            //getPlayer().setY(Integer.parseInt(br.readLine()));
            //hud.setPoint(Integer.parseInt(br.readLine()));
            br.close();
        } catch (Exception e) {

        }
    }
}
