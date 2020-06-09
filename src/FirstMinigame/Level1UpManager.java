package FirstMinigame;

import Entities.EntityManager;
import FirstMinigame.WorldGenerator.WorldLibrary;
import GameStates.Level1State;
import GameStates.LevelUpManager;
import GameStates.World;
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
        if (entityManager == null) {
            System.out.println("Eso");
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
            bw.write("" + entityManager.getJoan().getX());
            bw.newLine();
            bw.write("" + entityManager.getJoan().getY());
            bw.newLine();
            bw.write("" + entityManager.getJoan().getHandler().getGameCamara().getxOffset());
            bw.newLine();
            bw.write("" + entityManager.getJoan().getHandler().getGameCamara().getyOffset());
            bw.newLine();
            bw.write("" + worldLib.getBookcount());
            bw.close();
        } catch (Exception e) {
        }
    }

    // Metodo encargado de recupear los datos del TXT, y pasarlo a los parametros indicados
    /*
        Busca linea por linea para cada dato, en este caso obtiene:

        2 - Posicion en X del Jugador 
        3 - Posicion en Y del Jugador
        4 - Posicion en X de la Camara
        5 - Posicion en Y de la Camara
        6 - Cantidad de libros abiertos


     */
    public void loadData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("savefile.txt"));
            br.readLine(); // Salta el GameState
            entityManager.getJoan().setX(Float.parseFloat(br.readLine()));
            entityManager.getJoan().setY(Float.parseFloat(br.readLine()));
            entityManager.getJoan().getHandler().getGameCamara().setxOffset(Float.parseFloat(br.readLine()));
            entityManager.getJoan().getHandler().getGameCamara().setyOffset(Float.parseFloat(br.readLine()));
            worldLib.setBookcount(Integer.parseInt(br.readLine()));
            br.close();
        } catch (Exception e) {

        }
    }
}
