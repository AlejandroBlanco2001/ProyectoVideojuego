package MainG;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import GameStates.GameStateManager;
import Handlers.detectorTeclas;
import Tilemaps.Assets;

public class GamePanel extends JPanel implements Runnable{

    public static final int WIDTH_G = 1080;
    public static final int HEIGHT_G = 720;

    // Hilo del  juego y Game Loop
    private Thread hiloPrinicipal;
    // Volatile permite solo ser usadara por un Hilo, no puede ser modificad simultaneamente por dos hilos.
    private volatile boolean running = false;
    private static int UPS = 0;
    private static int FPS = 0;
    final int NANO_POR_SEG = 1000000000; // Equivalencia de segundos en nanosegundos  
    final int PREFERED_UPS = 60; // Actualizacion por segundos deseads
    final double NANO_PER_UPS = NANO_POR_SEG / PREFERED_UPS; // Nanosegundos por actualizacion

    // game state manager
    GameStateManager gsm;

    // images
    private BufferedImage image;
    private Graphics2D g;

    // KeyListener
    detectorTeclas teclas;

    public GamePanel(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
        setVisible(false);
        setFocusable(true);
        requestFocus();
    }

    // Funcion que se llama una vez que se cree el panel, para poder iniciar el juego
    public void addNotify() {
        super.addNotify();
        gameStart();
    }

    private void gameStart() {
        Assets.init();
        if (running == false) {
            running = true;
            hiloPrinicipal = new Thread(this, "GameThread");
            image = new BufferedImage(WIDTH_G, HEIGHT_G, BufferedImage.TYPE_INT_RGB);
            g = (Graphics2D) image.getGraphics();
            gsm = new GameStateManager();
            hiloPrinicipal.start();
        }
    }

    // Volatile pero para funciones 
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

        while (running) {
            final long beginLoop = System.nanoTime(); // Cronometro que inicia el juego

            // tiempo desde el ultimo cuadro cargado
            timePassed = beginLoop - referenceUpdate;
            referenceUpdate = beginLoop;

            // Segundos que se le añaden al delta para sumar 1 seg 
            delta += timePassed / NANO_PER_UPS;

            // Cuando se completa un segundo se actualiza el juego y se resta delta a 0
            if (delta >= 1) {
                gameUpdate(); // Se llama cada cuadro
                delta--;
            }
            gameDraw();
            gameDrawToScreen();

            //Contador de FPS y UPS ( FPS: FRAMES PER SECONDS - UPS: UPDATES PER SECOND)
            if (System.nanoTime() - referencerTimer > NANO_POR_SEG) {
                UPS = 0;
                FPS = 0;
                referencerTimer = System.nanoTime();
            }
        }
        try {
            // Acaba el hilo progresivamente
            hiloPrinicipal.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Actualizar los Frames
    public void gameUpdate() {
        UPS++;
        gsm.update();
    }

    // Dibujar en la memoria de video
    public void gameDraw() {
        FPS++;
        gsm.draw(g);
    }

    // Dibujar en pantalla
    public void gameDrawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH_G * 2, HEIGHT_G * 2, null);
        g2.dispose();
    }

    @Override
    public void run() {
        init();
    }
}
