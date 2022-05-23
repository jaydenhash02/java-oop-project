package dungeonmania.entities.staticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.collectableEntities.Bomb;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    private boolean activated = false;
    public FloorSwitch(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        setPlayerStandable(true);
    }

    public FloorSwitch(String id, String type, Position position, boolean isInteractable, boolean isActivated) {
        super(id, type, position, isInteractable);
        this.activated = isActivated;
        setPlayerStandable(true);
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean getActivated() {
        return this.activated;
    }
    /**
     * Detonate adjacent bombs
     * 
     * @param dungeon
     */
    public void detonateAnyAdjacentBombs(DungeonLayout dungeon) {
        if (activated == false) return;
        List<Entity> blownUpEntities = new ArrayList<Entity>();
        for (Entity e: dungeon.getAllEntities()) {
            if (e instanceof Bomb) {
                if (((Bomb)e).getPlayerStandable() == false
                    && isAdjacent(((Bomb)e).getPosition(), this.getPosition())) {
                    ((Bomb)e).blowUpSurroundings(dungeon, blownUpEntities);
                }
            }
        }
        dungeon.getAllEntities().removeAll(blownUpEntities);
    }
    /**
     * checks whether a given position is adjacent to the switch
     * 
     * @param a
     * @param b
     * @return
     */
    public boolean isAdjacent(Position a, Position b) {
        int x = a.getX() - b.getX();
        int y = a.getY() - b.getY();

        return Math.abs(x) + Math.abs(y) == 1;
    }
}