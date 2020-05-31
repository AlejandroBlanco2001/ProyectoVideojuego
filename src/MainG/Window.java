package MainG;

import Handlers.KeyManager;
import Handlers.MouseManager;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
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
        setTitle("VENTANA DEL JUEGO");
        setPreferredSize(new Dimension(width, height));

        //Se define lo que sucede si presiona la x en la esquina superior
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
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
//        setVideo();
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
        GamePanel game = (GamePanel) this.panel;
        game.setAnimation(false);
        game.init();
    }

    // CODIGO DE https http: //www.codeurjava.com/2015/01/java-lire-les-fichiers-multimedia-video.html
    // https://www.jc-mouse.net/proyectos/reproductor-de-video-con-vlcj;
}
