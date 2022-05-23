package dungeonmania.entities.movingEntities;

import java.util.List;
import java.util.Random;


import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;

import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.playerStates.NormalState;
import dungeonmania.playerStates.PlayerState;
import dungeonmania.util.Position;

public abstract class Mob extends MovingEntity implements Observer {

    private int stuckCount = 0;
    private boolean isStuck = false;
    private boolean alreadyChecked = false;
    private Player player;
    private PlayerState state = new NormalState(player);

    public Mob(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public abstract boolean validSetPosition(Position position, DungeonLayout dungeon);
    public abstract void mobMovement(DungeonLayout dungeon);

    /**
     * drop equipment
     * @param player
     * @param dungeon
     */
    public void dropEquipment(Player player, DungeonLayout dungeon) {
        dropRareItem(player, dungeon);
        dropArmour(player, dungeon);
    }

    /**
     * drop rare item
     * @param player
     * @param dungeon
     */
    private void dropRareItem(Player player, DungeonLayout dungeon) {
        // check luck for one_ring drop
        if (new Random(System.currentTimeMillis()).nextInt(100) < 5) {
            player.getInventory().add(dungeon.getEntityFactory().createFromType("one_ring"));
        }


        // check luck for anduril drop
        if (new Random(System.currentTimeMillis()).nextInt(100) < 5) {
            player.getInventory().add(dungeon.getEntityFactory().createFromType("anduril"));
        }
    }
    /**
     * drop armour
     * @param player
     * @param dungeon
     */
    private void dropArmour(Player player, DungeonLayout dungeon) {
        if (!(this instanceof CanWearArmour)) {
            return;
        }

        if (((CanWearArmour)this).hasArmour()) {
            player.getInventory().add(((CanWearArmour)this).getArmour());
        }
    }
    /**
     * run away position
     * @param mob
     * @param dungeon
     * @return position
     */
    public Position runAwayPosition(Mob mob, DungeonLayout dungeon) {
        Position playerPosition = dungeon.getPlayer().getPosition();
        int movementFactor = checkInSwamp(getPosition(), dungeon);

        if (getIsStuck() == true) {
            if (getStuckCount() < (movementFactor - 1)) {
                setStuckCount(getStuckCount() + 1);
            } else {
                setStuckCount(0);
                setIsStuck(false);
                setAlreadyChecked(false);
            }
            return new Position(getPosition().getX(), getPosition().getY(), 3);
        } 

        if (getIsStuck() == false) {
            // Moving right creates more space
            if (distanceBetween(new Position(getPosition().getX() + 1, getPosition().getY()), playerPosition) > 
                distanceBetween(new Position(getPosition().getX(), getPosition().getY()), playerPosition)) {
                    if (mob.validSetPosition(new Position(getPosition().getX() + 1, getPosition().getY()), dungeon) == true) {
                    return new Position(getPosition().getX() + 1, getPosition().getY(), 3);
                }
            } 
    
            // Moving left creates more space
            if (distanceBetween(new Position(getPosition().getX() - 1, getPosition().getY()), playerPosition) > 
                distanceBetween(new Position(getPosition().getX(), getPosition().getY()), playerPosition)) {
                    if (mob.validSetPosition(new Position(getPosition().getX() - 1, getPosition().getY()), dungeon) == true) {
                        return new Position(getPosition().getX() - 1, getPosition().getY(), 3);
                    }
            } 

            // Moving down creates more space
            if (distanceBetween(new Position(getPosition().getX(), getPosition().getY() + 1), playerPosition) > 
                distanceBetween(new Position(getPosition().getX(), getPosition().getY()), playerPosition)) {
                    if (mob.validSetPosition(new Position(getPosition().getX(), getPosition().getY() + 1), dungeon) == true) {
                        return new Position(getPosition().getX(), getPosition().getY() + 1, 3);
                    }
            } 
            
            if (distanceBetween(new Position(getPosition().getX(), getPosition().getY() - 1), playerPosition) > 
                distanceBetween(new Position(getPosition().getX(), getPosition().getY()), playerPosition)) {
                    // Moving  up creates more space
                    if (mob.validSetPosition(new Position(getPosition().getX(), getPosition().getY() - 1), dungeon) == true) {
                        return new Position(getPosition().getX(), getPosition().getY() - 1, 3);
                    } 
            }
        }

        // Mob is trapped - stay in current position
        return new Position(getPosition().getX(), getPosition().getY(), 3);
    }

    /**
     * distance between two positions
     * @param firstPosition
     * @param secondPosition
     * @return distance
     */
    public int distanceBetween(Position firstPosition, Position secondPosition) {
        return Math.abs(firstPosition.getX() - secondPosition.getX()) + 
                        Math.abs(firstPosition.getY() - secondPosition.getY());
    }
    /**
     * update a mob
     */
    public void update(Object object) {
        if (object instanceof Player) {
            player = (Player) object;
            state = player.getPlayerState();
        }
    }

    public PlayerState getState() {
        return state;
    }


    public void setIsStuck(boolean isStuck) {
        this.isStuck = isStuck;
    }

    public boolean getIsStuck() {
        return isStuck;
    }

    public void setStuckCount(int stuckCount) {
        this.stuckCount = stuckCount;
    }

    public int getStuckCount() {
        return stuckCount;
    }

    public void setAlreadyChecked(boolean alreadyChecked) {
        this.alreadyChecked = alreadyChecked;
    }

    public boolean getAlreadyChecked() {
        return alreadyChecked;
    }

    /**
     * Checks if current position is in a swamp and returns its movement factor
     * @param position
     * @param dungeon
     * @return
     */
    public int checkInSwamp(Position position, DungeonLayout dungeon) {
        List<Entity>allEntities = dungeon.entityAtPosition(position);
        for (Entity e : allEntities) {
            if (e instanceof SwampTile) {
                if (getAlreadyChecked() == false) {
                    setIsStuck(true);
                    setAlreadyChecked(true);
                }
                return ((SwampTile)e).getMovement_factor();
            }
        }
        return 0;
    }


    @Override
    public double attackDMG() {
        return (this.getHealth() * this.getAttack()) / 10;
    }
}

