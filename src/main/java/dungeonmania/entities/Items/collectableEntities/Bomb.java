package dungeonmania.entities.Items.collectableEntities;

import java.util.List;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.PlayerStandable;
import dungeonmania.entities.movingEntities.Player;
import dungeonmania.entities.staticEntities.Portal;
import dungeonmania.util.Position;

public class Bomb extends CollectableEntity implements PlayerStandable {
    // if playerStandable is false that means the player has placed it down
    // and can be blown up
    private boolean playerStandable = true;

    
    public Bomb(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public Bomb(String id, String type, Position position, boolean isInteractable, boolean placed) {
        super(id, type, position, isInteractable);
        this.playerStandable = placed;
    }

    
    /**
     * Blows up surrounding entities
     * 
     * @param dungeon
     * @param blownUpEntities
     */
    public void blowUpSurroundings(DungeonLayout dungeon, List<Entity> blownUpEntities) {
        List<Position> adjacents = getPosition().getAdjacentPositions();
        for (Entity e: dungeon.getAllEntities()) {
            for (Position p: adjacents) {
                if (!(e instanceof Player) && 
                    !(e instanceof Portal) && 
                    e.getPosition().equals(p)) {
                    blownUpEntities.add(e);
                }
            }
        }
        blownUpEntities.add(this);
    }   


    @Override
    public boolean getPlayerStandable() {
        return playerStandable;
    }


    @Override
    public void setPlayerStandable(boolean playerStandable) {
        this.playerStandable = playerStandable;
    }
}
