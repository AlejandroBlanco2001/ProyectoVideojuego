package GameStates;

import Entities.EntityManager;
import FirstMinigame.Level1UpManager;
import FirstMinigame.WorldGenerator.WorldLibrary;
import Tilemaps.Tile;
import MainG.Handler;
import MainLevel.WorldGenerator.WorldPlat;
import SecondMinigame.Level2UpManager;
import SecondMinigame.WorldSpace;

/**
 *
 * @author Omen
 */
public abstract class World {

    private Handler handler;
    public EntityManager entityM;

    public World(Handler handler, EntityManager entityM) {
        this.handler = handler;
        this.entityM = entityM;
    }

    public World(Handler handler) {
        this.handler = handler;
    }

    public abstract Tile getTile(int x, int y);

    public abstract void update();

    public abstract void render(java.awt.Graphics2D g);

    public World cast(LevelUpManager levelManager) {
        if (levelManager instanceof Level2UpManager) {
            WorldSpace world = (WorldSpace) this;
            return world;

        } else if (levelManager instanceof Level1UpManager) {
            WorldLibrary world = (WorldLibrary) this;
            return world;
        } else {
            WorldPlat world = (WorldPlat) this;
            return world;
        }
    }
    
    public EntityManager getEntityM() {
        return entityM;
    }
}
