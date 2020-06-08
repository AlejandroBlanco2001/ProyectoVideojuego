package Handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Es la clase que maneja los inputs de teclado que hace el usuario
 * @version 1.0
 */
public class KeyManager implements KeyListener {

    private static boolean[] keys;
    public boolean debug,up, down, left, right, space, test, enter,pause,upf,downf,rightf,leftf,a,b,c,d,shift,reset,tes, esc;
    public long pressed, realTime,valorTime = 0;

    /**
     * Se crea un vector con todas las teclas posibles
     */
    public KeyManager() {
        keys = new boolean[256];

    }
    
    /**
     *Se asigna el codigo de la tecla correspondiente a una variable
     */
    public void update() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        right = keys[KeyEvent.VK_D];
        left = keys[KeyEvent.VK_A];
        space = keys[KeyEvent.VK_SPACE];
        shift = keys[KeyEvent.VK_SHIFT];
        reset = keys[KeyEvent.VK_R];
        debug = keys[KeyEvent.VK_L];
        
        //Flechas
        upf=keys[KeyEvent.VK_UP];
        downf=keys[KeyEvent.VK_DOWN];
        rightf=keys[KeyEvent.VK_RIGHT];
        leftf=keys[KeyEvent.VK_LEFT];
        
        test = keys[KeyEvent.VK_E];
        enter = keys[KeyEvent.VK_ENTER];
        pause = keys[KeyEvent.VK_P];
        esc = keys[KeyEvent.VK_ESCAPE];
        
        a = keys[KeyEvent.VK_A];
        b = keys[KeyEvent.VK_B];
        c = keys[KeyEvent.VK_C];
        d = keys[KeyEvent.VK_D];
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }
    
    /**
     * Hacemos que cuando el usuario presione una tecla, el booleano en la posicion de esa tecla del vector keys[] se vuelva verdadero.
     * @param ke KeyEvent sucediendo en ese momento 
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        keys[ke.getKeyCode()] = true;
    }
    
    /**
     * Hacemos que cuando el usuario presione una tecla, el booleano en la posicion de esa tecla del vector keys[] se vuelva falso.
     * @param ke KeyEvent sucediendo en ese momento 
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
    }

    public long getTime() {
        return valorTime;
    }
}
