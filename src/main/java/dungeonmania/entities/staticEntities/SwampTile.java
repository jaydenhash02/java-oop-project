package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

public class SwampTile extends StaticEntity{
    
    private int movement_factor;

    public SwampTile(String id, String type, Position position, boolean isInteractable, int movement_factor) {
        super(id, type, position, isInteractable);
        this.movement_factor = movement_factor;
        setPlayerStandable(true);
    }

    public int getMovement_factor() {
        return movement_factor;
    }

    public void setMovement_factor(int movement_factor) {
        this.movement_factor = movement_factor;
    }
}
