package GameStates;

import Audio.AudioLoader;
import Tilemaps.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import MainG.Handler;
import MainG.Window;
import Tilemaps.*;
import UI.ClickListener;
import UI.UIImageButton;
import UI.UIManager;
import UtilLoader.SaveGame;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import tinysound.Music;
import tinysound.Sound;

// fade in https://stackoverflow.com/questions/20346661/java-fade-in-and-out-of-images
public class MenuState extends GameState implements SaveGame {

    Music bgMusic;
    Sound menuUp;
    Background bg;
    Handler handler;

    private int currentChoice = 0;
    public long lastPressedTime = 0;

    private Animation anm;

    static final long minPressedDelay = 150;

    private UIManager uimanager;

    private String[] options = {
        "Start",
        "Continue",
        "Help",
        "Creators",
        "Story",
        "Quit",};

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gsm, Handler handler) {
        super(gsm);
        this.handler = handler;
        try {
            bg = new Background(Assets.fondoMenu, 1);
            bg.setVector(2, 0);
            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        uimanager = new UIManager(handler);
        uimanager.addUIObject(new UIImageButton(100f, 100f, 100, 100, Assets.Boss, new ClickListener() {
            @Override
            public void onClick() {
                gsm.reloadState(2);
            }
        }));
        anm = new Animation(100, Assets.backgroundMenu);
        init();
    }

    @Override
    public void update() {
        handleInput();
        anm.update();
        musicControl();
        uimanager.tick();
    }

    public void draw(Graphics2D g) {
        g.drawImage(getCurrentFrame(), 0, 0, 1080, 720, null);
        // Aplica colores al titulo del juego
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("SIRVE", 270, 70);
        g.drawString("x" + Window.mouse.getMouseX() + " y" + Window.mouse.getMouseY(), Window.mouse.getMouseX(), Window.mouse.getMouseY());
        if (Window.mouse.isLeftPressed()) {
            g.setColor(Color.blue);
        }
        // Añade las opciones del menu
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.red);
            }
            g.drawString(options[i], 270, 160 + i * 15);
        }
        uimanager.render(g);
    }

    @Override
    public void init() {
        while (Window.mouse == null) {
            System.out.println("Cargando");
        }
        Window.mouse.setUIManager(uimanager);
        bgMusic = AudioLoader.bgMusic;
        bgMusic.setVolume(0.3);
        bgMusic.play(true);
        menuUp = AudioLoader.upMenu;
    }

    public void handleInput() {
        long now = System.currentTimeMillis();
        if (now - lastPressedTime < minPressedDelay) {
            return;
        }
        if (Window.keyManager.up) {
            currentChoice--;
            menuUp.play();
            if (currentChoice < 0) {
                currentChoice = 5;
            }
        }
        if (Window.keyManager.down) {
            currentChoice++;
            menuUp.play();
            if (currentChoice > 5) {
                currentChoice = 0;
            }
        }
        // Opcion de continuar donde se habia dejado la partida
        if (Window.keyManager.enter) {
            optionPicker();
        }
        if (Window.keyManager.space) {
            bgMusic.stop();
            gsm.setState(3);
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
                bgMusic.stop();
                gsm.setState(1);
                break;
            case 1:
                getLoadData();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
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
        if (stateVerification.isEmpty() || stateVerification.isEmpty()) {
            System.out.println("PARTIDA NUEVA");
        }
        state = Integer.parseInt(stateVerification);
        subState = Integer.parseInt(subStateVerification);
        // Se verifica si el State cargado por el TXT, no es nulo. Si este es nulo indica que el juego se cerro y se abrio de nuevo para crear el State, de lo contaro se carga normalmente con el reloadState.
        if (!gsm.VerificarReinicioJuego(state)) {
            if (gsm.isOnMinigame(subState)) {
                subState = gsm.getMinigame(subState);
                gsm.reloadState(subState);
                gsm.getGameStates()[subState].getLoadData();
            } else {
                gsm.reloadState(state);  // Se recarga el juego
                gsm.getGameStates()[state].getLoadData(); // Se insertan los datos del txt
            }
        } else {
            gsm.setState(state); // Se crea el state donde termino el guardado, toca verificar a futuro, como enlazarlo con el acceso a superiores ( del 1 a 3 y a 2)
        }
    }

    @Override
    public void getLoadData() {
    }

    @Override
    public void insertData() {
    }
}
