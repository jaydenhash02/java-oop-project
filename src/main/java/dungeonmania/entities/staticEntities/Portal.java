package dungeonmania.entities.staticEntities;

import java.util.List;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Player;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private String colour;
    private Portal correspondingPortal;
    public Portal(String id, String type, Position position, boolean isInteractable, String colour) {
        super(id, type, position, isInteractable);  
        this.colour = colour;
        setPlayerStandable(true);
    }
    
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Portal getCorrespondingPortal() {
        return correspondingPortal;
    }
    /**
     * Sets link portals
     * 
     * @param dungeon
     * @param player
     */
    public void setLinkPortals(DungeonLayout dungeon, Player player, Direction direction) {
        List<Entity> allEntity = dungeon.getAllEntities();
        for (Entity entity : allEntity) {
            if (entity instanceof Portal && !entity.equals(this) && ((Portal)entity).getColour().equals(colour)) {
                this.correspondingPortal = ((Portal)entity);
                int currentX = correspondingPortal.getPosition().getX();
                int currentY = correspondingPortal.getPosition().getY();
                // UP
                Position position;
                if (direction.equals(Direction.UP)) {
                    position = new Position(currentX, currentY - 1);
                // DOWN
                } else if (direction.equals(Direction.DOWN)) {
                    position = new Position(currentX, currentY + 1);
                // LEFT
                } else if (direction.equals(Direction.LEFT)) {
                    position = new Position(currentX - 1, currentY);
                // RIGHT 
                } else if (direction.equals(Direction.RIGHT)) {
                    position = new Position(currentX + 1, currentY);
                // NONE
                } else {
                    return;
                }
                player.validSetPosition(position, dungeon, direction);
            }
        }
    }
}

