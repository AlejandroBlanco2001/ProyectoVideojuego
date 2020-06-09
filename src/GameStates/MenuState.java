package GameStates;

import Audio.AudioLoader;
import MainG.GameLauncher;
import Tilemaps.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import MainG.Handler;
import MainG.Window;
import Tilemaps.*;
import UI.ClickListener;
import UI.UIHelper;
import UI.UIImageButton;
import UI.UIManager;
import UtilLoader.MusicPlayer;
import UI.UIObject;
import UtilLoader.SaveGame;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import tinysound.Music;
import tinysound.Sound;

// fade in https://stackoverflow.com/questions/20346661/java-fade-in-and-out-of-images
public class MenuState extends GameState implements SaveGame {

    private Music bgMusic;
    private Sound menuUp;
    Background bg;
    Handler handler;

    private MusicPlayer musicPlayer;
    private Thread hiloMusica;

    private int currentChoice = 0;
    public long lastPressedTime = 0;

    private Animation anm;
    private boolean sw;
    static final long minPressedDelay = 150;

    private UIManager uimanager;
    public UIImageButton exit, option, newg, continu, tutorial;

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gsm, Handler handler) {
        super(gsm);
        this.handler = handler;
        sw = false;
        uimanager = new UIManager(handler);
        uimanager.addUIObject(new UIImageButton(1047f, 0f, 32, 32, Assets.minimize, new ClickListener() {
            @Override
            public void onClick() {
                GameLauncher.window.setState(GameLauncher.window.ICONIFIED);
            }
        }));
        uimanager.addUIObject(exit = new UIImageButton(50f, 550f, 256, 57, Assets.UIMenu[5], new ClickListener() {
            @Override
            public void onClick() {
                System.exit(0);
            }
        }));

        uimanager.addUIObject(continu = new UIImageButton(50f, 460f, 256, 57, Assets.UIMenu[7], new ClickListener() {
            @Override
            public void onClick() {
                musicPlayer.kill();
                loadData();
            }
        }));

        uimanager.addUIObject(newg = new UIImageButton(50f, 370f, 256, 57, Assets.UIMenu[8], new ClickListener() {
            @Override
            public void onClick() {
                musicPlayer.kill();
                clearFile("saveGeneralFile.txt");
                clearFile("savefile.txt");
                gsm.setState(1);
            }
        }));
        uimanager.addUIObject(new UIHelper(Assets.UIHelperMenu, 8000, 540, 430, 475, 200));
        anm = new Animation(100, Assets.backgroundMenu);
        init();

    }

    @Override
    public void update() {
        handleInput();
        anm.update();
        musicControl();
        if (sw) {
            uimanager.tick();
        }
    }

    public void draw(Graphics2D g) {
        if (sw) {
            g.drawImage(Assets.backgroundMenu[22], 0, 0, 1080, 720, null);
            g.drawImage(Assets.Title2, 380, -60, null);
            // Aplica colores al titulo del juego
            // Añade las opciones del menu
            if (noHovering()) {
                noMoreCurrently();
                currentChoice = 0;
            } else {
                for (int i = 1; i <= 3; i++) {
                    if (i == currentChoice) {
                        switch (currentChoice) {
                            case 1:
                                newg.setCurrent(true);
                                noMoreCurrently(newg);

                                break;
                            case 2:
                                continu.setCurrent(true);
                                noMoreCurrently(continu);
                                break;
                            case 3:
                                exit.setCurrent(true);
                                noMoreCurrently(exit);
                                break;
                        }
                    }
                }
            }
            uimanager.render(g);
        } else {
            g.drawImage(getCurrentFrame(), 0, 0, 1080, 720, null);
            if (getCurrentFrame().equals(Assets.backgroundMenu[22])) {
                sw = true;
            }
        }

    }

    @Override
    public void init() {
        while (Window.mouse == null) {
            System.out.println("Cargando");
        }
        Window.mouse.setUIManager(uimanager);
        bgMusic = AudioLoader.bgMusic;
        musicPlayer = new MusicPlayer(bgMusic);
        hiloMusica = new Thread(musicPlayer, "auxiliarThreadForMusic");
        hiloMusica.start();
        menuUp = AudioLoader.upMenu;
    }

    public void handleInput() {
        long now = System.currentTimeMillis();
        if (Window.keyManager.space) {
            gsm.setState(1);
        }
        // Opcion de continuar donde se habia dejado la partida
        if (Window.keyManager.enter) {

        }
        if (Window.keyManager.test) {
            optionPicker();
        }

        if (now - lastPressedTime < minPressedDelay) {
            return;
        }
        if (Window.keyManager.up) {
            currentChoice--;
            menuUp.play();
            if (currentChoice < 1) {
                currentChoice = 3;
            }
        }
        if (Window.keyManager.down) {
            currentChoice++;
            menuUp.play();
            if (currentChoice > 3) {
                currentChoice = 1;
            }
        }

        lastPressedTime = now;
    }

    @Override
    public void musicControl() {

    }

    BufferedImage getCurrentFrame() {

        return anm.getCurrentFrame();

    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public void getInsertData() {

    }

    public void optionPicker() {
        switch (currentChoice) {
            case 0:
                musicPlayer.kill();
                gsm.setState(2);
                break;
            case 1:
                musicPlayer.kill();
                loadData();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                musicPlayer.mute();
                break;
            case 5:
                break;
        }
    }

    @Override
    public void loadData() {
        BufferedReader br = null;
        int state = 0;
        int subState = 0;
        String stateVerification = "";
        String subStateVerification = "";
        try {
            br = new BufferedReader(new FileReader("saveGeneralFile.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stateVerification = br.readLine();
            subStateVerification = br.readLine();
        } catch (Exception e) {

        }
        // Se verifica que el archivo de TXT de guardado, tenga un state como primera linea, sino, esta es la primera vez que se inicia el juego
        if (stateVerification == null || subStateVerification == null) {
            System.out.println("PARTIDA NUEVA");
            gsm.setState(1);
        }
        state = Integer.parseInt(stateVerification);
        subState = Integer.parseInt(subStateVerification);
        // Se verifica si el State cargado por el TXT, no es nulo. Si este es nulo indica que el juego se cerro y se abrio de nuevo para crear el State, de lo contaro se carga normalmente con el reloadState.
        if (!gsm.VerificarReinicioJuego(state) && subState != 1 && subState != 2) {
            gsm.reloadState(state);  // Se recarga el juego
            gsm.getGameStates()[1].getLoadData();// Se insertan los datos del txt   
        } else {
            subState = gsm.getMinigame(subState);
            gsm.reloadState(subState);
            gsm.getGameStates()[subState].getLoadData();
        }
    }

    public boolean noHovering() {
        if (exit.isHovering() || continu.isHovering() || newg.isHovering()) {
            return true;
        } else {
            return false;
        }

    }

    public void noMoreCurrently(UIImageButton u) {
        for (UIObject object : uimanager.getObjects()) {
            if (object instanceof UIImageButton) {
                UIImageButton ui = (UIImageButton) object;
                if (!u.equals(ui)) {
                    ui.setCurrent(false);
                }
            }
        }
    }

    private void noMoreCurrently() {
        for (UIObject object : uimanager.getObjects()) {
            if (object instanceof UIImageButton) {
                UIImageButton ui = (UIImageButton) object;
                ui.setCurrent(false);
            }
        }
    }

    @Override
    public void getLoadData() {
    }

    @Override
    public void insertData() {
    }

    public Music getMusic() {
        return bgMusic;
    }

    // Codigo tomado de https://stackoverflow.com/questions/29878237/java-how-to-clear-a-text-file-without-deleting-it/42282671
    // Autor: Ankur Anand
    public void clearFile(String file) {
        FileWriter fwOb = null;
        try {
            fwOb = new FileWriter(file, false);
        } catch (IOException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        try {
            fwOb.close();
        } catch (IOException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearFile() {
        clearFile("saveGeneralFile.txt");
        clearFile("savefile.txt");
    }
    
    public void killMusic(){
    
    }
}
