package FirstMinigame.WorldGenerator;

import Entities.Static.BookInfo;
import Entities.Creatures.Player_Joan;
import Entities.Creatures.Sentinel;
import Entities.Entity;
import Entities.EntityManager;
import Entities.Static.BookPile;
import FirstMinigame.Tiles.TileLibrary;
import Tilemaps.Tile;
import GameStates.GameState;
import GameStates.World;
import MainG.Handler;
import Tilemaps.Assets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author German David
 */
public class WorldLibrary extends World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles = new int[100][100];
    //Entities
    private ArrayList<BookPile> bookpiles;
    private EntityManager entityM;
    public static int bookcount = 0;
    private boolean showHistory = true;
    Font textFont = new Font("pixelart", Font.PLAIN, 20);
    private boolean passed = false;
    

    public WorldLibrary(Handler handler, String path, GameState state) {
        super(handler);
        this.handler = handler;
        entityM = new EntityManager(handler, state);
        entityM.addEntity(new BookPile(handler, entityM, 600, 395, new BookInfo(entityM, Assets.vida, handler, 500, 100, 100, 100, 0)));
        entityM.addEntity(new BookPile(handler, entityM, 385, 385, new BookInfo(entityM, Assets.vida, handler, 500, 100, 100, 100, 1)));
        entityM.addEntity(new BookPile(handler, entityM, 3870, 1110, new BookInfo(entityM, Assets.vida, handler, 500, 100, 100, 100, 2)));
        entityM.addEntity(new BookPile(handler, entityM, 2650, 1350, new BookInfo(entityM, Assets.vida, handler, 500, 100, 100, 100, 3)));
        entityM.addEntity(new BookPile(handler, entityM, 1580, 625, new BookInfo(entityM, Assets.vida, handler, 500, 100, 100, 100, 4)));
        entityM.addEntity(new BookPile(handler, entityM, 4590, 2075, new BookInfo(entityM, Assets.vida, handler, 500, 100, 100, 100, 5)));
        entityM.addEntity(new BookPile(handler, entityM, 6757, 1650, new BookInfo(entityM, Assets.vida, handler, 500, 100, 100, 100, 6)));
        //entityM.addEntity(new Sentinel(handler, entityM,150,100,40,40));
        loadWorld(path);
        entityM.getJoan().setX(spawnX);
        entityM.getJoan().setY(spawnY);
    }

    @Override
    public void update() {
        entityM.update();
        if (checkPositionEndGame()) {
            if (!passed) {
                checkBooks();
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        //Varibales para crear las tiles visibles
        int Xstart = (int) Math.max(0, handler.getGameCamara().getxOffset() / Tile.TILEWIDTH);
        int Xend = (int) Math.min(width, (handler.getGameCamara().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
        int Ystart = (int) Math.max(0, handler.getGameCamara().getyOffset() / Tile.TILEHEIGHT);
        int Yend = (int) Math.min(height, (handler.getGameCamara().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
        g.setBackground(Color.gray);
        for (int y = Ystart; y < Yend; y++) {
            for (int x = Xstart; x < Xend; x++) {
                getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamara().getxOffset()),
                        (int) (y * Tile.TILEHEIGHT - handler.getGameCamara().getyOffset()));
            }
        }
        entityM.render(g);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        }
        Tile t = TileLibrary.tiles[tiles[x][y]];
        if (t == null) {
            return TileLibrary.floor;
        }
        return t;
    }

    private void loadWorld(String path) {
        String file = Util.loadFileAsString(path);
        //\\s+ es cada espacio en blanco
        String[] tokens = file.split("\\s+");
        width = Util.parseInt(tokens[0]);
        height = Util.parseInt(tokens[1]);
        spawnX = Util.parseInt(tokens[2]);
        spawnY = Util.parseInt(tokens[3]);
        tiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Util.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityM;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityM = entityManager;
    }

    public void setFinished(){
        entityM.getJoan().setGameFinished(true);
    }
    
    public boolean checkPositionEndGame(){
        return this.entityM.getJoan().checkEnd();
    }
    
    private void checkBooks(){
        if(bookcount < 6){
            entityM.getJoan().setX(entityM.getJoan().getX()-20);
        }
    }

    public static int getBookcount() {
        return bookcount;
    }

    public static void setBookcount(int bookcount) {
        WorldLibrary.bookcount = bookcount;
    }
    
    public void startQuiz(){
        
    }
    
    
    
}
