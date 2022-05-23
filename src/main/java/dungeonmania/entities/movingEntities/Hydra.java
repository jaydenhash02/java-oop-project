package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.PortableEntity;
import dungeonmania.entities.Items.collectableEntities.rareCollectableEntities.Anduril;
import dungeonmania.entities.staticEntities.StaticEntity;
import dungeonmania.playerStates.InvincibleState;
import dungeonmania.util.Position;

public class Hydra extends Mob {

    public static int spawnRate = 50;
        
    private boolean canRegrowHead = true;


    public Hydra(String id, String type, Position position, boolean isInteractable, int attack) {
        super(id, type, position, isInteractable);
        this.setAttack(attack);
        this.setHealth(80);
    }

    public void setCanRegrowHead(boolean canRegrowHead) {
        this.canRegrowHead = canRegrowHead;
    }   
    /**
     * regrow head
     * @return true if hydra can regrow
     */
    public boolean regrowHead() {
        // 50% chance of regrowing a head
        boolean chance = new Random().nextInt(50) == 0;
        return chance && canRegrowHead;
    }

    @Override
    public void takeDamage(MovingEntity e, double dmgModifier) {
        Player p;
        // check if player has an anduril which will suppress this hydra's regrowing ability
        if (e instanceof Player) {
            p = (Player)e;
            for (PortableEntity ent: p.getInventory()) {
                if (ent instanceof Anduril) {
                    setCanRegrowHead(false);
                    break;
                }
            }
        }

        if (regrowHead()) {
            this.setHealth(this.getHealth() + e.attackDMG() * dmgModifier);
        } else {
            this.setHealth(this.getHealth() - e.attackDMG() * dmgModifier);
        }
    }

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
    
}
