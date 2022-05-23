package dungeonmania.entities.movingEntities;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.playerStates.InvincibleState;
import dungeonmania.util.Position;
import java.util.List;

public class Spider extends Mob {

    private boolean isMovingClockwise = true;
    private Position ogPosition;
    private boolean movedUpwards;
    private int spiderCount = 0;
    public static final int spawnRate = 10;

    // max number of spiders spawned at a time is 4
    public static int maxSpiders = 4;

    public Spider(String id, String type, Position position, boolean isInteractable, int attack) {
        super(id, type, position, isInteractable);
        this.ogPosition = position;
        this.movedUpwards = false;
        this.setAttack(attack);
        this.setHealth(25); 
    }

    public Spider(String id, String type, Position position, boolean isInteractable, Position og_position, int attack) {
        super(id, type, position, isInteractable);
        this.ogPosition = og_position;
        this.movedUpwards = true;
        this.setAttack(attack);
        this.setHealth(25); 
    }

    public boolean getIsMovingClockwise() { 
        return this.isMovingClockwise;
    }

    public void setMovingClockwise() {
        this.isMovingClockwise = true;
    }

    public void setMovingAntiClockwise() {
        this.isMovingClockwise = false;
    }

    public Position getOgPosition() {
        return this.ogPosition;
    }

    public boolean getMovedUpwards() {
        return this.movedUpwards;
    }

    public void setMovedUpwards() {
        this.movedUpwards = true;
    }
    /**
     * Moves the spider
     * 
     */
    @Override
    public void mobMovement(DungeonLayout dungeon) {
        int movementFactor = checkInSwamp(getPosition(), dungeon);

        if (getIsStuck() == true) {
            if (getStuckCount() < (movementFactor - 1)) {
                setStuckCount(getStuckCount() + 1);
            } else {
                setStuckCount(0);
                setIsStuck(false);
                setAlreadyChecked(false);
            }
        } 

        if (getIsStuck() == false) {
            if (getState() instanceof InvincibleState) {
                Position fleePosition = runAwayPosition(this, dungeon);
                if (validSetPosition(fleePosition, dungeon) == true) {
                    setPosition(fleePosition);
                }
            } else {
                if (getMovedUpwards() == false) {
                    setPosition(new Position(getOgPosition().getX(), getOgPosition().getY() - 1, 3));
                    setMovedUpwards();
                    spiderCount = 1;
                } else {
                    List<Position> path = ogPosition.getAdjacentPositions();
                    // Return Adjacent positions in an array list with the following element positions:
                    // 0 1 2
                    // 7 p 3
                    // 6 5 4 
                    Position newPosition;
    
                    if (getIsMovingClockwise() == true) {
                        if (spiderCount == 7) {
                            newPosition = path.get(0);
                            spiderCount = 0;
                            // validSetPosition(newPosition, dungeon);
                        } else {
                            newPosition = path.get(spiderCount+1);
                            spiderCount += 1;
                            // validSetPosition(newPosition, dungeon);
                        }
                    } else { //getIsMovingClockwise = false
                        if (spiderCount == 0) {
                            newPosition = path.get(7);
                            spiderCount = 7;
                            // validSetPosition(newPosition, dungeon);
                        } else {
                            newPosition = path.get(spiderCount-1);
                            spiderCount -= 1;
                            // validSetPosition(newPosition, dungeon);
                        }
                    }
    
                    if (validSetPosition(newPosition, dungeon) == true) {
                        Position layeredPosition = new Position(newPosition.getX(), newPosition.getY(), 3);
                        setPosition(layeredPosition);
                    }
                }
            }
        }  
    }
    
    /**
     * Sets the position if it is valid
     * 
     */
    @Override
    public boolean validSetPosition(Position position, DungeonLayout dungeon) {
        List<Entity>allEntities = dungeon.entityAtPosition(position);
        for (Entity e : allEntities) {
            if (e instanceof Boulder) {
                if (getIsMovingClockwise() == true) {
                    setMovingAntiClockwise();
                    if (spiderCount == 0) {
                        spiderCount = 7;
                    } else {
                        spiderCount -= 1;
                    }
                } else {
                    setMovingClockwise();
                    if (spiderCount == 7) {
                        spiderCount = 0;
                    } else {
                        spiderCount += 1;
                    }
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void takeDamage(MovingEntity e, double dmgModifier) {
        this.setHealth(this.getHealth() - e.attackDMG() * dmgModifier);
    }
}
