package MainLevel.Tiles;

import Tilemaps.Tile;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Omen
 */
public class TileMainLevel extends Tile {

    //STATIC STUFF
    public static int TILEWIDTH = 36, TILEHEIGHT = 36;

    public BufferedImage previousImage;
    
    public boolean isSolid = false;
    public boolean isVisible = true;
    public boolean isInteractive = false;

    //Arreglo para guardar los diferentes tipos de Tile
    public static Tile[] tiles = new Tile[256];
    public static Tile emptyTile = new EmptyTile(0);
    public static Tile retroFloor = new FloorTile(1);
    public static Tile wallTile = new WallTile(2);
    public static Tile platformTile = new PlatformTile(3);
    public static Tile chest = new Chest(4);
    public static Tile lapTile = new LapTile(5);
    public static Tile elevatorTile = new InElevator(6);
    public static Tile buttonElevatorTile = new ButtonElevator(7);
    public static Tile spikeTile = new SpikeTile(8);
    public static Tile enigmaTeleporter = new TeleporterTile(9);
    public static Tile floorDecorator1 = new FloorDecoratorTile(10);
    public static Tile floatingTile = new FloatingTile(11);
    public static Tile rightSign = new RightSign(12);
    public static Tile leveler = new LevelerTile(13);
    public static Tile pyramidFill = new PyramidFill(14);
    public static Tile pyramidFill2 = new PyramidFill2(15);
    public static Tile pyramidFill3 = new PyramidFill3(16);
    public static Tile portal = new PortalTile(17);

    //Class
    public TileMainLevel(BufferedImage texture, int id) {
        super(texture, id);
        previousImage = texture;
        tiles[id] = this;
    }

    public boolean isSpecial() {
        return false;
    }

    public boolean makeDamage() {
        return false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public boolean isSolid() {
        return isSolid;
    }

    public void buttonPressed() {
        this.isVisible = false;
        this.isSolid = true;
    }

    public void buttonRelased() {
        this.isVisible = true;
        this.isSolid = false;
    }

    public void resetElevator() {
        this.isSolid = false;
        this.isVisible = true;
    }

    // Interaccionas?
    public boolean isInteractive() {
        return isInteractive;
    }

    public void resetInteraction() {
        this.isInteractive = false;
    }

    public void changeTiles() {
        System.out.println("NADA");
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, this.TILEWIDTH, this.TILEHEIGHT, null);
    }
    
    public void reloadTexture(){
        this.texture = previousImage;
    }

}
