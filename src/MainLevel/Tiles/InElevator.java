/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainLevel.Tiles;

/**
 *
 * @author Omen
 */
public class InElevator extends ElevatorTile {

    public InElevator(int id) {
        super(id);
    }

    @Override
    public boolean isSolid(){
        return !isSolid;
    }
    
    @Override
    public boolean isVisible(){
        return isVisible;
    }
    

}
