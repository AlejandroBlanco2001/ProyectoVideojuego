package MainG;

import Handlers.KeyManager;
import Handlers.MouseManager;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Canvas;
import java.awt.Color;
import Tilemaps.Assets;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * Window es la ventana del juego
 *
 * @version 1.0
 */
public class Window extends JFrame {

    public static KeyManager keyManager;
    public static MouseManager mouse;
    private GamePanel panel;

    /**
     * En el constructor de la ventana se le pasan las dimensiones como parametro Se le proporciana un titulo Se evita que el usuario modifique las dimensiones mediante setResizable(false) Se crea un KeyManager
     *
     * @param width Anchura de la ventana
     * @param height Altura de la ventana
     */
    public Window(int width, int height) {
        this.setLayout(new BorderLayout());
        this.panel = new GamePanel(width, height, this);
        //Se le pone un titulo y las dimensiones
        setTitle("Traveling Through Enigma: A Time Odyssey");
        setPreferredSize(new Dimension(width, height));
        setUndecorated(true);
        //Se define lo que sucede si presiona la x en la esquina superior
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(this.panel);
        
        //Evita que el usuario modifique las dimensiones
        setResizable(false);
        pack();

        //Se hace visible
        setVisible(true);
        //Se crea un keyManager para usarlo en el juego
        keyManager = new KeyManager();
        addKeyListener(keyManager);
        mouse = new MouseManager();
        addKeyListener(keyManager);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        setIconImage(Assets.CursorSpace);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Assets.CursorSpace,new Point(mouse.getMouseX(),mouse.getMouseY()), "ef"));
    }

    public void setVideo() {
        Canvas c = new Canvas();
        c.setSize(1080,720);
        c.setBackground(Color.black);
        c.setVisible(true);
        this.getContentPane().add(c);
        this.getContentPane().setVisible(true);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "lib");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        MediaPlayerFactory mpf = new MediaPlayerFactory();
        EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(this));
        emp.setVideoSurface(mpf.newVideoSurface(c));
        emp.setEnableKeyInputHandling(false);
        emp.setEnableMouseInputHandling(false);
        String file = "Intro.mp4";
        emp.prepareMedia(file);
        emp.playMedia(file);
        while (!emp.isPlaying()) {
            System.out.println("Cargando");
        }
        while (emp.isPlaying()) {
            System.out.println("CORRE");
        }
        this.getContentPane().setVisible(false);
        emp.stop();
        GamePanel game = (GamePanel) this.panel;
        game.setAnimation(false);
        game.getGsm().setState(0);
        game.setVideoPlayed(false);
        game.eraseFile();
        game.init();
    }
    // CODIGO DE https http: //www.codeurjava.com/2015/01/java-lire-les-fichiers-multimedia-video.html
    // https://www.jc-mouse.net/proyectos/reproductor-de-video-con-vlcj;
}
