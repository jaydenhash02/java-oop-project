package dungeonmania;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Player;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.util.Direction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BattleTest {
    @Test 
    public void killSpawnedZombie() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test_battles", "standard");
        for (int i = 0; i < 20; i++) {
            controller.tick(null, Direction.DOWN);
        }

        for (Entity e: controller.getDungeon().getAllEntities()) {
            if (e instanceof Player) {
                assertTrue(((Player)e).getHealth() < 100);
            }
        }

        for (int i = 0; i < 19; i++) {
            controller.tick(null, Direction.LEFT);
        }

        controller.tick(null, Direction.RIGHT);

        boolean zombieExist = false;
        for (Entity e: controller.getDungeon().getAllEntities()) {
            if (e instanceof ZombieToast) {
                zombieExist = true;
            }
        }
        assertTrue(zombieExist);
    }    

    @Test
    public void killAssassin() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("assassinFix", "standard");
        for (int i = 0; i < 12; i++) {
            controller.tick(null, Direction.UP);
        }
        boolean assassinExist = false;
        for (Entity e: controller.getDungeon().getAllEntities()) {
            if (e.getType().contains("assassin")) {
                assassinExist = true;
            }
        }
        
        assertEquals(assassinExist, false);
    }


    @Test
    public void playerDead() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test_battles", "standard");
        for (int i = 0; i < 38; i++) {
            controller.tick(null, Direction.DOWN);
        }

        assertTrue(controller.getDungeon().playerIsDead());


    }


}

