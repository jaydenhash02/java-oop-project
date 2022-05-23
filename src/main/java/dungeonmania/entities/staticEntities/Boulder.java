package dungeonmania.entities.staticEntities;

import java.util.List;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.PlayerStandable;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    private boolean onSwitch = false;
    public Boulder(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        setPlayerStandable(false);
    }
    public Boulder(String id, String type, Position position, boolean isInteractable, boolean onswitch) {
        super(id, type, position, isInteractable);
        this.onSwitch = onswitch;
        setPlayerStandable(false);
    }
    /**
     * new boulder position
     * @param direction
     * @param dungeon
     * @return
     */
    public boolean newBoulderPosition(Direction direction, DungeonLayout dungeon) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        Position position;
        if (direction.equals(Direction.UP)) {
            position = new Position(currentX, currentY - 1);
            return moveBoulder(position, getPosition(), dungeon);
        } else if (direction.equals(Direction.DOWN)) {
            position = new Position(currentX, currentY + 1);
            return moveBoulder(position, getPosition(), dungeon);
        } else if (direction.equals(Direction.LEFT)) {
            position = new Position(currentX - 1, currentY);
            return moveBoulder(position, getPosition(), dungeon);
        } else if (direction.equals(Direction.RIGHT)){
            position = new Position(currentX + 1, currentY);
            return moveBoulder(position, getPosition(), dungeon);
        } else {
            return false;
        }
    }

    /**
     * Moves the boulder
     * 
     * @param newPosition
     * @param currentPosition
     * @param dungeon
     * @return
     */
    public boolean moveBoulder(Position newPosition, Position currentPosition, DungeonLayout dungeon) {
        List<Entity> entityAtNewPosition = dungeon.entityAtPosition(newPosition);
        FloorSwitch onTopOfSwitch = null;
        for (Entity entity : entityAtNewPosition) {
            if (entity instanceof PlayerStandable && ((PlayerStandable)entity).getPlayerStandable() == false) {
                return false;
            }
        }

        List<Entity> entityAtCurrentPosition = dungeon.entityAtPosition(currentPosition);
        for (Entity entity : entityAtCurrentPosition) {
            if (entity instanceof FloorSwitch) {
                ((FloorSwitch)entity).setActivated(false);
                this.onSwitch = false;
            }
        }

        for (Entity entity : entityAtNewPosition) {
            if (entity instanceof FloorSwitch) {
                this.onSwitch = true;
                ((FloorSwitch)entity).setActivated(true);
                onTopOfSwitch = ((FloorSwitch)entity);
            }
        }       

        setPosition(newPosition);
        if (onTopOfSwitch != null) {
            onTopOfSwitch.detonateAnyAdjacentBombs(dungeon);
        }
        return true;
    }

    public boolean getOnSwitch() {
        return onSwitch;
    }

    public void setOnSwitch(boolean onSwitch) {
        this.onSwitch = onSwitch;
    }
}
