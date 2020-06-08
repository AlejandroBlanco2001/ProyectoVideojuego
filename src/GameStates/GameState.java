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
