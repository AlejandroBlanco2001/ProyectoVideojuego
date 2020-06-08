package SecondMinigame;

import Entities.Creatures.AerialEnemy;
import Entities.Creatures.Asteroid;
import Entities.Creatures.Boss;
import Entities.Creatures.DownEnemy;
import Entities.Creatures.Player;
import Entities.Entity;
import Entities.EntityManager;
import Tilemaps.Tile;
import GameStates.World;
import MainG.Handler;
import MainG.Window;
import static MainG.Window.mouse;
import Tilemaps.Assets;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

/**
 *
 * @author German David, Isaac Blanco
 */
public class WorldSpace extends World {

    private EntityManager manager;
    private Handler handler;
    private HUD hud;
    private Level2UpManager managerL;

    private DownEnemy enemy1;
    private AerialEnemy enemy2;
    private Boss boss;
    private boolean e1 = false, e2 = false, generateEnemys = false, e3 = false;

    public WorldSpace(EntityManager manager, Handler handler) {
        super(handler, manager);
        this.handler = handler;
        this.manager = manager;
        
        
    }

    @Override
    public void update() {
        int j = 0;
        if (generateEnemys) {
            if (managerL.getPhase() != 3) {
                generateAsteroids(j);
                if (managerL.getPhase() == 1 || managerL.getPhase() == 2) {
                    generateEnemy1();
                    if (managerL.getPhase() == 2) {
                        generateEnemy2();
                    }
                }
            } else {
                generateBoss();
            }
        }
        
        manager.update();
    }

    @Override
    public void render(Graphics2D g) {
        manager.render(g);
    }

    public void setHUD(HUD hud) {
        this.hud = hud;
    }

    public void generateAsteroids(int j) {
        for (Entity e : manager.getEntities()) {
            if (e instanceof Asteroid) {
                if(e.isActive()){
                    j++;
                }
            }
        }
        System.out.println(j);
        if (j < 8) {
            for (int i = j; i <= 8; i++) {
                manager.addEntity(new Asteroid(handler, manager, 1080, (float) (Math.random() * 600 + 60), 100, 100, this.hud));
            }
        }
    }

    public void generateEnemy1() {
        if (!e1) {
            enemy1 = new DownEnemy(handler, manager, 950, 40, 104, 110, this.hud);
            manager.addEntity(enemy1);
            e1 = !e1;
        } else {
            if (enemy1.isActive() == false) {
                e1 = !e1;
            }
        }
    }

    public void generateEnemy2() {
        if (!e2) {
            enemy2 = new AerialEnemy(handler, manager, (int) Math.random() * 500 + 40, 0, 110, 99, this.hud);
            manager.addEntity(enemy2);
            e2 = !e2;
        } else {
            if (enemy2.isActive() == false) {
                e2 = !e2;
            }
        }
    }

    public void generateBoss() {
        if (!e3) {
            boss = new Boss(handler, manager, 450, 60, 65, 21, this.hud);
            manager.addEntity(boss);
            e3 = !e3;
        }
    }

    public void generateEnemys() {
        generateEnemys = !generateEnemys;
    }

    public void setGenerateEnemys(boolean status) {
        generateEnemys = status;
    }

    public void clearScreenEntities() {
        for (Entity e : manager.getEntities()) {
            if (!(e instanceof Player)) {
                e.setActive(false);
            }
        }
    }

    public boolean isBossAlive() {
        return boss.isActive();
    }

    public Boss getBoss(){
        return boss;
    }
    
    public void setLevelUpManager(Level2UpManager managerL) {
        this.managerL = managerL;
    }

    @Override
    public Tile getTile(int x, int y) {
        return null;
    }

}
