package MainLevel.Tiles;

import Tilemaps.Assets;

public class RowTile extends TileMainLevel {
    
    public RowTile(int id) {
        super(Assets.rowTile, id);
    }

    @Override
    public boolean isSolid() {
        return !isSolid;
    }

}
