package GameStates;

import MainG.Handler;
import java.awt.Graphics2D;

/**
 * La clase GameState es la clase estado, es el concepto y de esta heredan los states. Heredan las funciones, el handler y el manager.
 */
public abstract class GameState {

    protected GameStateManager gsm;
    protected Handler handler;
    protected World world;
    protected String levelTag;
    protected long timePassed;
    protected long timeDeltaTime;
    /**
     * Constructor del GameState.
     *
     * @param gsm Cada GameState debe tener un GameStateManager.
     */
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public abstract void musicControl();

    public abstract World getWorld();

    public abstract void killMusic();
    
    // Se verifica si el usuario presiono la letra P, para iniciar un menu de Pausa.
    protected void pauseState() {
        timeDeltaTime = System.currentTimeMillis() - timePassed;
        if (timeDeltaTime > 2000) { // Deley Tecla
            timePassed = System.currentTimeMillis();
            gsm.reloadState(4); // Se recarga el state, porque ya esta creado
            gsm.getGameStates()[4].init(); // Se inicia su contador para el delay de la tecla P
        }
    }
    
    public String getTag() {
        return levelTag;
    }

    public GameStateManager getGsm() {
        return gsm;
    }
   
    // Manera de acceder a los datos, de quien se encargue
    public abstract void getInsertData();

    // Manera de cargar a los datos, de quien se encargue
    public abstract void getLoadData();
}
