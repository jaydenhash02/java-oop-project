package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.collectableEntities.Armour;
import dungeonmania.entities.staticEntities.StaticEntity;
import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.playerStates.InvisibleState;
import dungeonmania.util.Position;
import dungeonmania.playerStates.InvincibleState;


public class Mercenary extends Mob implements CanWearArmour {
    private boolean isAlly;
    private Armour armour;
    public static final int spawnRate = 30;  
    private int mindControl = 10;  
    public Mercenary(String id, String type, Position position, boolean isInteractable, int attack) {
        super(id, type, position, isInteractable);
        this.setAttack(attack);
        this.setHealth(50);
        this.setAlly(false);
        // 5% chance of spawning zombie with armour
        if (new Random(System.currentTimeMillis()).nextInt(100) < 5) {
            this.armour = new Armour("armour" + this.getId(), "armour", null, false);
        }

    }

    public Mercenary(String id, String type, Position position, boolean isInteractable, boolean ally, int attack) {
        super(id, type, position, isInteractable);
        this.setAttack(attack);
        this.setAlly(ally);
    }
    
    /**
     * Moves the mercenary
     * 
     */
    @Override
    public void mobMovement(DungeonLayout dungeon) {
        if (this.mindControl < 10) {
            this.mindControl++;
            if (this.mindControl == 10) {
                this.isAlly = false;
                dungeon.getPlayer().removeAlly(this);
            }
        }
        int movementFactor = checkInSwamp(getPosition(), dungeon);
        if (getIsStuck()) {
            if (getStuckCount() < (movementFactor - 1)) {
                setStuckCount(getStuckCount() + 1);
            } else {
                setStuckCount(0);
                setIsStuck(false);
                setAlreadyChecked(false);
            }
        } 
        if (!getIsStuck()) {
            if (getState() instanceof InvisibleState) {
                return;
            } else if (getState() instanceof InvincibleState) {
                Position fleePosition = runAwayPosition(this, dungeon);
                if (validSetPosition(fleePosition, dungeon) == true) {
                    setPosition(fleePosition);

                }
            } else {
                Map<Position,Position> path = dijkstra(grid(dungeon), this.getPosition());
                Position playerPosition = dungeon.getPlayer().getPosition();
                Position position = new Position(playerPosition.getX(), playerPosition.getY());
                Position next = this.getPosition();
                while (!position.equals(this.getPosition())) {
                    next = position;
                    position = path.get(position);
                }
                setPosition(next);
            }
        }

    }

    /**
     * Sets the position if valid
     * 
     */
    @Override
    public boolean validSetPosition(Position position, DungeonLayout dungeon) {
        List<Entity>allEntities = dungeon.entityAtPosition(position);
        for (Entity e : allEntities) {
            if (e instanceof StaticEntity && !((StaticEntity) e).getPlayerStandable()) {
                return false;
            }
        }
        return true;
    }


    public boolean checkBattleRadius(DungeonLayout dungeon, Player player) {
        int mercenary_x  = this.getPosition().getX();
        int mercenary_y  = this.getPosition().getY();
        int player_x = player.getPosition().getX();
        int player_y = player.getPosition().getY();
        // needs to be cardinally adjacent within 5 tiles and not on the same tile
        if ((mercenary_x == player_x && Math.abs(mercenary_y - player_y) <= 5) || 
            (mercenary_y == player_y && Math.abs(mercenary_x - player_x) <= 5)) {
            // check if the player is in a battle
            return true;
        }

        return false;
    }


    public Map<Position,Position> dijkstra(Map<Position,Integer> grid, Position source) {
        Map<Position,Double> dist = new HashMap<Position,Double>();
        Map<Position,Position> prev = new HashMap<Position,Position>();
        Queue<Position> queue = new LinkedList<Position>();

        for (Position p : grid.keySet()) {
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null); 
            queue.add(p);
        }

        dist.put(source, 0.0);
        while (!queue.isEmpty()) {
            Position u = getMinPosition(dist, queue);
            queue.remove(u);
            for (Position v : cardinallyNeighbour(u, queue)) {
                if ((dist.get(u) + grid.get(v)) < dist.get(v)) {
                    dist.put(v, (dist.get(u) + grid.get(v)));
                    prev.put(v, u);
                }
            }
        }
        return prev;
    }


    private Position getMinPosition(Map<Position,Double> map, Queue<Position> queue) {
        Double minValue = Double.POSITIVE_INFINITY;
        Position minPosition = null;
        for (Position position : queue) {
            if (map.get(position) <= minValue) {
                minValue = map.get(position);
                minPosition = position;
            }
        }

        return minPosition;
    }

    private List<Position> cardinallyNeighbour(Position position, Queue<Position> queue) {
            List<Position> adjacentPositions = new ArrayList<>();    
            int x = position.getX();
            int y = position.getY();
            if (queue.contains(new Position(x  , y-1))) {
                adjacentPositions.add(new Position(x  , y-1));
            }
            if (queue.contains(new Position(x+1, y))) {
                adjacentPositions.add(new Position(x+1, y));
            }
            if (queue.contains(new Position(x  , y+1))) {
                adjacentPositions.add(new Position(x  , y+1));
            }
            if (queue.contains(new Position(x-1, y))) {
                adjacentPositions.add(new Position(x-1, y));
            }
            return adjacentPositions;
    }

    public Map<Position, Integer> grid(DungeonLayout dungeon) {
        Map<Position,Integer> grid = new HashMap<Position,Integer>();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Entity entity : dungeon.getAllEntities()) {
            if (entity.getPosition().getX() < minX) {
                minX = entity.getPosition().getX();
            }
            if (entity.getPosition().getY() < minY) {
                minY = entity.getPosition().getY();
            }
            if (entity.getPosition().getX() > maxX) {
                maxX = entity.getPosition().getX();
            }
            if (entity.getPosition().getY() > maxY) {
                maxY = entity.getPosition().getY();
            }
        }

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Position position = new Position(i, j);
                List<Entity> allEntities = dungeon.entityAtPosition(position);
                if (allEntities.isEmpty()) {
                    grid.put(position, 1);
                }
                else {
                    boolean standable = true;
                    int movement_factor = -1;
                    for (Entity entity : allEntities) {
                        if (entity instanceof StaticEntity) {
                            if (!((StaticEntity)entity).getPlayerStandable()) {
                                standable = false;
                            }
                            if (entity instanceof SwampTile) {
                                movement_factor = ((SwampTile)entity).getMovement_factor();
                            }
                        }
                    }
                    if (standable == true) {
                        if (movement_factor != -1) {
                            grid.put(position, movement_factor);
                        }
                        else {
                            grid.put(position, 1);
                        }
                    }
                }
            }
        }
        return grid;
    }

    @Override
    public void takeDamage(MovingEntity e, double dmgModifier) {
        if (hasArmour()) {
            this.setHealth(this.getHealth() - e.attackDMG() * dmgModifier * 0.5);
        } else {
            this.setHealth(this.getHealth() - e.attackDMG() * dmgModifier);
        }
    }

    
    public boolean isAlly() {
        return isAlly;
    }

    public void setAlly(boolean isAlly) {
        this.isAlly = isAlly;
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

    public void setMindControl(int mindControl) {
        this.mindControl = mindControl;
    }
}
