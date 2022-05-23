package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class InteractTest {
    @Test
    public void testSpawnerDestroyed() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "peaceful");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response1 = controller.tick(null, Direction.RIGHT);

        DungeonResponse response2 = controller.tick(null, Direction.RIGHT);
        List<EntityResponse> allEntity = response1.getEntities();
        for (EntityResponse entity : allEntity) {
            if (entity.getType().equals("zombie_toast_spawner")) {
                //System.out.println(entity.getId());
                response2 = controller.interact(entity.getId());
                break;
            }
        }

        assertEquals(response2.getEntities().size(), response1.getEntities().size() - 1);
    }
    @Test
    public void testMercenaryBribed() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response1 = controller.newGame("interact", "peaceful");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);

        List<EntityResponse> allEntity = response1.getEntities();
        for (EntityResponse entity : allEntity) {
            if (entity.getType().equals("mercenary")) {
                //System.out.println(entity.getId());
                controller.interact(entity.getId());
                Entity mercenary = controller.getDungeon().entityExist(entity.getId());
                assertEquals(((Mercenary)mercenary).isAlly(), true);
                break;
            }
        }
        controller.saveGame("fml");
        controller.loadGame("fml");
    }


    @Test
    public void testAssassinBribedWithSunStoneAndOneRing() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("assassinFix", "standard");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        for (Entity e: controller.getDungeon().getAllEntities()) {
            if (e.getType().contains("assassin")) {
                String assassinID;
                assassinID = e.getId();
                assertThrows(InvalidActionException.class, () -> {
                    controller.interact(assassinID);
                });
                break;
            }
        }

        controller.tick(null, Direction.UP);
        for (Entity e: controller.getDungeon().getAllEntities()) {
            if (e.getType().contains("assassin")) {
                String assassinID;
                assassinID = e.getId();
                assertDoesNotThrow(() -> controller.interact(assassinID));
                assertEquals(((Assassin)e).isAlly(), true);
                break;
            }
        }
        
    }


    @Test
    public void testAssassinBribedWithTreasureAndOneRing() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("assassinFix", "standard");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        for (Entity e: controller.getDungeon().getAllEntities()) {
            if (e.getType().contains("assassin")) {
                String assassinID;
                assassinID = e.getId();
                assertThrows(InvalidActionException.class, () -> {
                    controller.interact(assassinID);
                });
                break;
            }
        }
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);

        for (Entity e: controller.getDungeon().getAllEntities()) {
            if (e.getType().contains("assassin")) {
                String assassinID;
                assassinID = e.getId();
                assertDoesNotThrow(() -> controller.interact(assassinID));
                assertEquals(((Assassin)e).isAlly(), true);
                break;
            }
        }
    }
    @Test
    public void testInvalidID() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "peaceful");
        assertThrows (IllegalArgumentException.class, () -> {
            controller.interact("mercenary69");
        });
    }
    @Test
    public void testExceptions() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "peaceful");
        assertThrows (InvalidActionException.class, () -> {
            controller.interact("mercenary32");
        });
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        assertThrows (InvalidActionException.class, () -> {
            controller.interact("mercenary32");
        });
        assertThrows (InvalidActionException.class, () -> {
            controller.interact("zombie_toast_spawner31");
        });
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertThrows (InvalidActionException.class, () -> {
            controller.interact("zombie_toast_spawner31");
        });
    }
}