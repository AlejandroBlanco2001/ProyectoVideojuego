package MainLevel.Tiles;

import Tilemaps.Animation;
import Tilemaps.Assets;
import java.awt.Graphics;

/**
 *
 * @author Omen
 */
public class TeleporterTile extends TileMainLevel {
    
    
    public TeleporterTile(int id) {
        super(Assets.enigmaMachineTeleporter, id);
        this.isInteractive = true;
    }

    
    @Override
    public void render(Graphics g, int x, int y){
        g.drawImage(texture,x-20,y-70, 100,100,null);
    }
    
    @Override
    public boolean isInteractive() {
        return isInteractive;
    }

    @Override
    public void changeTiles() {
        this.texture = Assets.spaceTeleporter;
    }

    public void changeSelection() {
        this.isInteractive = false;
    }
    
    public void returnSelection(){
        this.isInteractive = true;
    }
}
