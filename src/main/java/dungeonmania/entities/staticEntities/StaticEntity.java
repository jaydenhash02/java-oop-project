package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.PlayerStandable;
import dungeonmania.util.Position;

public abstract class StaticEntity extends Entity implements PlayerStandable {
    private boolean playerStandable = false;
    public StaticEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public boolean getPlayerStandable() {
        return playerStandable;
    }

    public void setPlayerStandable(boolean playerStandable) {
        this.playerStandable = playerStandable;
    }
}
