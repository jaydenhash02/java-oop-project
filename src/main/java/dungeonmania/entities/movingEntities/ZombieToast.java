package dungeonmania.entities.movingEntities;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.collectableEntities.Armour;
import dungeonmania.entities.staticEntities.StaticEntity;
import dungeonmania.playerStates.InvincibleState;
import dungeonmania.util.Position;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class ZombieToast extends Mob implements CanWearArmour {
    private Armour armour;

    public ZombieToast(String id, String type, Position position, boolean isInteractable, int attack) {
        super(id, type, position, isInteractable);
        this.setAttack(attack);
        this.setHealth(40);
        Random random = new Random(System.currentTimeMillis());
        int i = random.nextInt(100);
        // 5% chance of spawning zombie with armour
        if (i < 5) {
            this.armour = new Armour("armour" + this.getId(), "armour", null, false);
        }
    }

    /**
     * Moves the zombie toast
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
                    setPosition(new Position(fleePosition.getX(), fleePosition.getY(), 3));
                }
            } else {
                Random rand = new Random();
                Position currentPosition = getPosition();
                List<Position> neighbours = currentPosition.getAdjacentPositions();
                List<Position> moveablePositions = new ArrayList<Position>();    
                
                for (int i = 0; i < neighbours.size(); i++) {
                    if (i != 0 && i != 2 && i != 4 && i != 6) { //Prevents zombie from moving diagonally
                        if (validSetPosition(neighbours.get(i), dungeon) == true) {
                            moveablePositions.add(neighbours.get(i));
                        }
                    }
                }
    
                if (moveablePositions.size() == 0) {
                    setPosition(new Position(currentPosition.getX(), currentPosition.getY(), 3));
                } else {
                    int random = rand.nextInt(moveablePositions.size() + 1);
    
                    if (random == 0) {
                        setPosition(new Position(currentPosition.getX(), currentPosition.getY(), 3));
                    } else if (random == 1) {
                        setPosition(new Position(moveablePositions.get(0).getX(), moveablePositions.get(0).getY(), 3));
                    } else if (random == 2) {
                        setPosition(new Position(moveablePositions.get(1).getX(), moveablePositions.get(1).getY(), 3));
                    } else if (random == 3) {
                        setPosition(new Position(moveablePositions.get(2).getX(), moveablePositions.get(2).getY(), 3));
                    } else {
                        setPosition(new Position(moveablePositions.get(3).getX(), moveablePositions.get(3).getY(), 3));
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
            if (e != null) {
                if (e instanceof StaticEntity && !((StaticEntity) e).getPlayerStandable())
                    return false;
            }   
        }
        return true;
    }

    @Override
    public Armour getArmour() {
        return armour;
    }

    @Override
    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    @Override
    public boolean hasArmour() {
        return (armour != null);
    }

    @Override
    public void takeDamage(MovingEntity e, double dmgModifier) {
        if (hasArmour()) {
            this.setHealth(this.getHealth() - e.attackDMG() * dmgModifier * 0.5);
        } else {
            this.setHealth(this.getHealth() - e.attackDMG() * dmgModifier);
        }
    }
}