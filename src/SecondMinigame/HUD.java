package SecondMinigame;

import Tilemaps.Assets;
import java.awt.Graphics2D;
import Entities.EntityManager;
import java.awt.Color;

public class HUD {

    private static int points = 0;
    private static int health;

    private EntityManager manager;

    public HUD(EntityManager manager) {
        this.manager = manager;
        points = 0;
    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.asteroids, 10, 10, 46, 35, null);
        int par = 0;
        int impar = 0;
        for (int i = 1; i <= manager.getPlayer().getHealth(); i++) {
            if (i % 2 == 0) {
                g.drawImage(Assets.vida, 450 + par * 30, 10, 35, 48, null);
                par += 1;
            } else {
                g.drawImage(Assets.halfLife, 460 + impar * 30, 10, 35, 48, null);
                impar += 1;
            }
        }
        g.setColor(Color.yellow);
        g.drawString(Integer.toString(points), 65, 30);
    }

    public static int getHealth() {
        return health;
    }

    public void update() {
        health = manager.getPlayer().getHealth();
        points = getPoint();
    }

    public int getPoint() {
        return this.points;
    }

    public static void setPoint(int point) {
        HUD.points = point;
    }

    public static void setHealth(int health) {
        HUD.health = health;
    }
}
