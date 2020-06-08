/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import MainG.Handler;
import MainG.Window;
import Tilemaps.Assets;
import UI.ClickListener;
import UI.UIImageButton;
import UI.UIManager;
import UtilLoader.SaveGame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Omen
 */
public class PauseState extends GameState implements SaveGame {

    private GameStateManager gsm;
    private Handler handler;
    private long timePassed, timeDeltaTime;
    private UIImageButton reanuda,options,menu,quit; 
    UIManager manager;
    
    public PauseState(GameStateManager gsm, Handler handler) {
        super(gsm);
        this.gsm = gsm;
        this.handler = handler;
        manager = new UIManager(handler);
        
        manager.addUIObject(reanuda = new UIImageButton(412f, 300f, 257, 57, Assets.UIMainLvl[8], new ClickListener() {
            @Override
            public void onClick() {
                gsm.reloadState(gsm.getPreviousState());
                System.out.println("Reanuda");
            }
        }));    
           
        manager.addUIObject(options = new UIImageButton(412f, 400f, 257, 57, Assets.UIMainLvl[7], new ClickListener() {
            @Override
            public void onClick() {
              
            }
        }));
        
        manager.addUIObject(menu = new UIImageButton(412f, 500f, 257, 57, Assets.MenuMain, new ClickListener() {
            @Override
            public void onClick() {
                gsm.reloadState(0);
            }
        }));     
        
        manager.addUIObject(quit = new UIImageButton(412f, 600f, 257, 57, Assets.UIMainLvl[3], new ClickListener() {
            @Override
            public void onClick() {
                System.exit(0);
            }
        }));
    }

    @Override
    public void init() {
        Window.mouse.setUIManager(manager);
        timePassed = System.currentTimeMillis();
    }

    @Override
    public void update() {
        System.out.println("Running");
        manager.tick();
        
        switch(gsm.getPreviousState()){
            case 1: 
                reanuda.setImage(Assets.UIMainLvl[8]);
                options.setImage(Assets.UIMainLvl[7]);
                menu.setImage(Assets.MenuMain);
                quit.setImage(Assets.UIMainLvl[3]);
                break;
            case 3:
                reanuda.setImage(Assets.UILvl2[4]);
                options.setImage(Assets.UILvl2[5]);
                menu.setImage(Assets.UILvl2[7]);
                quit.setImage(Assets.QuitLvl2);
                break;
            case 2:
                reanuda.setImage(Assets.UILvl1[5]);
                options.setImage(Assets.UILvl1[6]);
                menu.setImage(Assets.Menu1);
                quit.setImage(Assets.UILvl1[3]);
                break;
            case 5:
                reanuda.setImage(Assets.UILvl1[5]);
                options.setImage(Assets.UILvl1[6]);
                menu.setImage(Assets.Menu1);
                quit.setImage(Assets.UILvl1[3]);
                break;
        }
        
        timeDeltaTime = System.currentTimeMillis() - timePassed;
        if (Window.keyManager.pause && timeDeltaTime > 1000) {
            gsm.reloadState(gsm.getPreviousState());
            System.out.println("Reanuda");
        }
        if (Window.keyManager.down) {
            insertData();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        switch(gsm.getPreviousState()){
            case 1:
                g.drawImage(Assets.pauseBackgroundMain,400,280, 270, 400,null);
                break;
            case 2:
                g.drawImage(Assets.pauseBackgroundLvl1,400,280, 270, 400,null);
                break;
            case 3:
                g.drawImage(Assets.pauseBackgroundLvl2,400,280, 270, 400,null);
                break;
            case 5:
                g.drawImage(Assets.pauseBackgroundLvl1,400,280, 270, 400,null);
                break;
        }
        
        manager.render(g);
    }

    @Override
    public void musicControl() {

    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override

    public void loadData() {

    }

    @Override
    public void insertData() {
        gsm.getGameStates()[gsm.getPreviousState()].getInsertData();
        gsm.setState(0);
    }

    @Override
    public void getInsertData() {
        return;
    }

    @Override
    public void getLoadData() {
    }
}
