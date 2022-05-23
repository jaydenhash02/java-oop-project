package dungeonmania.entities.staticEntities;

import java.util.*;
import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.PlayerStandable;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {
    // standard spawn rate for zombies 
    private int spawnRate = 20;

    private int attack;
    private int health;

    public ZombieToastSpawner(String id, String type, Position position, boolean isInteractable, int spawnRate) {
        super(id, type, position, isInteractable);
        this.spawnRate = spawnRate;
        setPlayerStandable(false);
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    /**
     * spawn a zombie
     * @param dungeon
     * @param spawnedZombies
     */
    public void spawnZombine(DungeonLayout dungeon, List<ZombieToast> spawnedZombies) {
        int currentTick = dungeon.getPlayer().getPlayerTickCount();
        Position position = cardinalPosition(dungeon);
        if (currentTick % spawnRate == 0) {
            if (position != null) {
                ZombieToast zombie = (ZombieToast)dungeon.getEntityFactory().createFromType("zombie_toast", position.getX(), position.getY());
                spawnedZombies.add(zombie);
            }
        }
    }
    /**
     * 
     * @param dungeon
     * @return
     */
    public Position cardinalPosition(DungeonLayout dungeon) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x  , y-1));
        adjacentPositions.add(new Position(x+1, y));
        adjacentPositions.add(new Position(x  , y+1));
        adjacentPositions.add(new Position(x-1, y));
        for (Position position : adjacentPositions) {
            boolean valid = true;
            List<Entity> entityAtPosition = dungeon.entityAtPosition(position);
            for (Entity entity : entityAtPosition) {
                if (entity.getPosition().equals(position) && entity instanceof PlayerStandable 
                    && !(((PlayerStandable)entity).getPlayerStandable())) {
                    valid = false;
                }
            }
            if (valid) {
                return position;
            }
        }
        return null;
    }
}
