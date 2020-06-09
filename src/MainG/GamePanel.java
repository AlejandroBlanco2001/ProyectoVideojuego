package MainG;

import Audio.AudioLoader;
import GameStates.GameCamara;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import GameStates.GameStateManager;
import GameStates.MainLevel;
import GameStates.MenuState;
import Tilemaps.Assets;
import UtilLoader.MusicPlayer;
import tinysound.TinySound;

/**
 * Es la clase esencial del juego, donde se inicializan la mayoría de cosas
 *
 * @version 1.0
 */
// Ideas de los gameLoops corregidos https://gameprogrammingpatterns.com/game-loop.html#interview-with-a-cpu
public class GamePanel extends JPanel implements Runnable {

    //Dimensiones del game panel
    public static final int WIDTH_G = 1080;
    public static final int HEIGHT_G = 720;

    //Hilo del  juego y Game Loop
    private Thread hiloPrinicipal;

    // Frame principal (Ventana)
    private JFrame frame;

    //KeyManager
    public Handler handler;

    //Camara
    private GameCamara gameCamera;

    //Volatile permite solo ser usadara por un Hilo, no puede ser modificad simultaneamente por dos hilos.
    private volatile boolean running = false;
    private static int UPS = 0;
    private static int FPS = 0;
    final int NANO_POR_SEG = 1000000000; // Equivalencia de segundos en nanosegundos  
    final int PREFERED_UPS = 60; // Actualizacion por segundos deseads
    final double NANO_PER_UPS = NANO_POR_SEG / PREFERED_UPS; // Nanosegundos por actualizacion

    private boolean finishedGame = false;
    private boolean videoPlayed = true;
    //GameStateManager
    GameStateManager gsm;

    //Imagenes
    private BufferedImage image;
    private Graphics2D g;

    /**
     * Se inicializa el GamePanel
     *
     * @param width Anchura del GamePanel
     * @param height Altura del GamePanel
     */
    public GamePanel(int width, int height, JFrame fatherF) {
        super();
        setPreferredSize(new Dimension(width, height));
        setVisible(false);
        setFocusable(true);
        requestFocus();
        handler = new Handler(this);
        this.frame = fatherF;
    }

    /**
     * Funcion que se llama una vez que se cree el panel, para poder iniciar el juego
     */
    @Override
    public void addNotify() {
        super.addNotify();
        TinySound.init();
        gameStart();
    }

    private void gameStart() {
        if (running == false) {
            running = true;
            Assets imagenes = new Assets();
            hiloPrinicipal = new Thread(this, "GameThread");
            imagenes.run();
            AudioLoader.init();
            hiloPrinicipal.start();
        }
    }

    /**
     * Funcion para acabar el juego cuando el usuario pierde
     */
    private synchronized void gameOver() {
        running = false;
    }

    public synchronized void init() {
        // Contador para las actualizaciones
        long referenceUpdate = System.nanoTime();
        //  Contador para los FPS 
        long referencerTimer = System.nanoTime();
        double timePassed; // Tiempo trasncurrido por cuadro
        double delta = 0; // Cantidad de tiempo hasta actualizacion
        while (running && !finishedGame) {
            final long beginLoop = System.nanoTime(); // Cronometro que inicia el juego
            // tiempo desde el ultimo cuadro cargado, Delta Time para el movement
            timePassed = beginLoop - referenceUpdate;
            referenceUpdate = beginLoop;
            // Cuando se completa un segundo se actualiza el juego y se resta delta a 0
            if (gsm.getCurrentState() != 0 && timePassed > 1.25 * Math.exp(7)) {
                gameUpdate(timePassed); // Se llama cada cuadro
                gameDraw();
                gameDrawToScreen();
            } else {
                if (gsm.getCurrentState() == 0) {
                    gameUpdate(timePassed); // Se llama cada cuadro
                    gameDraw();
                    gameDrawToScreen();
                } else {
                    System.out.println("Cargando JUEGO");
                }
            }

            //Contador de FPS y UPS ( FPS: FRAMES PER SECONDS - UPS: UPDATES PER SECOND)
            if (System.nanoTime() - referencerTimer > NANO_POR_SEG) {
                UPS = 0;
                FPS = 0;
                referencerTimer = System.nanoTime();
            }
            if (videoPlayed) {
                isAnimationFinished();
            }
        }
        Window w = (Window) frame;
        w.setVideo();
        this.setVisible(true);
    }

    /**
     * Metodo que actualiza los Frames
     */
    public void gameUpdate(double deltaTime) {
        UPS++;
        gsm.update(deltaTime);
        if (gsm.getCurrentState() != 0) {
            videoPlayed = true;
        }
        Window.keyManager.update();
    }

    /**
     * Metodo que dibuja en la memoria de video
     */
    public void gameDraw() {
        FPS++;
        gsm.draw(g);
    }

    /**
     * Metodo que dibuja en la pantalla
     */
    public void gameDrawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH_G, HEIGHT_G, null);
        g2.dispose();
    }

    @Override
    public void run() {
        image = new BufferedImage(WIDTH_G, HEIGHT_G, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        gameCamera = new GameCamara(handler, 0, 0);
        gsm = new GameStateManager(handler, gameCamera);
        init();
    }

    public GameCamara getGameCamara() {
        return gameCamera;
    }

    @Override
    public int getWidth() {
        return WIDTH_G;
    }

    @Override
    public int getHeight() {
        return HEIGHT_G;
    }

    public void setAnimation(boolean value) {
        finishedGame = value;
    }

    public void isAnimationFinished() {
        finishedGame = getTermination();
    }

    public boolean getTermination() {
        if (gsm.getGameStates()[1] == null) {
            return false;
        }
        MainLevel level = (MainLevel) gsm.getGameStates()[1];
        return level.isGameFinished();
    }

    public GameStateManager getGsm() {
        return gsm;
    }

    public void setVideoPlayed(boolean videoPlayed) {
        this.videoPlayed = videoPlayed;
    }

    public void eraseFile() {
        MenuState aux = (MenuState) gsm.getGameStates()[0];
        aux.clearFile();
    }
}
