package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class PersistenceTest {
    @Test
    public void testBomb() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bomb", "peaceful");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        DungeonResponse response1 = controller.saveGame("bomb");
        DungeonResponse response2 = controller.loadGame("bomb");
        assertEquals(response1.getInventory().get(0).getType(), response2.getInventory().get(0).getType());
        assertEquals(response1.getInventory().get(1).getType(), response2.getInventory().get(1).getType());
        assertEquals(response1.getEntities().get(0).getType(), response2.getEntities().get(0).getType());
        assertEquals(response1.getEntities().get(1).getType(), response2.getEntities().get(1).getType());
        assertEquals(response1.getEntities().get(2).getType(), response2.getEntities().get(2).getType());
        assertEquals(response1.getEntities().get(3).getType(), response2.getEntities().get(3).getType());
        assertEquals(response1.getEntities().get(4).getType(), response2.getEntities().get(4).getType());
        assertEquals(response1.getEntities().get(5).getType(), response2.getEntities().get(5).getType());
        assertEquals(response1.getEntities().get(6).getType(), response2.getEntities().get(6).getType());
    }

    @Test
    public void testBuild() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.build("bow");
        DungeonResponse response1 = controller.saveGame("build");
        DungeonResponse response2 = controller.loadGame("build");
        assertEquals(response1.getInventory().size(), response2.getInventory().size());
        assertEquals(response1.getBuildables(), response2.getBuildables());
    }
    @Test
    public void testCollectable() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("collectables", "peaceful");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response1 = controller.saveGame("collect");
        DungeonResponse response2 = controller.loadGame("collect");
        assertEquals(response1.getEntities().get(0).getType(), response2.getEntities().get(0).getType());
        assertEquals(response1.getInventory().get(0).getType(), response2.getInventory().get(0).getType());
        assertEquals(response1.getInventory().get(1).getType(), response2.getInventory().get(1).getType());
        assertEquals(response1.getInventory().get(2).getType(), response2.getInventory().get(2).getType());
        assertEquals(response1.getInventory().get(3).getType(), response2.getInventory().get(3).getType());
        assertEquals(response1.getInventory().get(4).getType(), response2.getInventory().get(4).getType());
        assertEquals(response1.getInventory().get(5).getType(), response2.getInventory().get(5).getType());
        assertEquals(response1.getInventory().get(6).getType(), response2.getInventory().get(6).getType());
        assertEquals(response1.getInventory().get(7).getType(), response2.getInventory().get(7).getType());
        assertEquals(response1.getInventory().get(8).getType(), response2.getInventory().get(8).getType());
        assertEquals(response1.getInventory().get(9).getType(), response2.getInventory().get(9).getType());
        assertEquals(response1.getInventory().get(10).getType(), response2.getInventory().get(10).getType());
    }

    @Test
    public void testDoorKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("playerMovement", "peaceful");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(33).getPosition(), new Position(4,5,0));
        response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(33).getPosition(), new Position(4,6,0));
        response = controller.tick(null, Direction.DOWN);
        controller.saveGame("collect1");
        DungeonResponse response2 = controller.loadGame("collect1");
        assertEquals(response2.getEntities().get(32).getPosition(), new Position(7,4,0));
    }

    @Test
    public void testCoverage() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("persistenceCoverage", "peaceful");
        controller.tick(null, Direction.DOWN);
        controller.interact("mercenary4");
        Mercenary mercenary = (Mercenary) controller.getDungeon().entityExist("mercenary4");
        assertEquals(mercenary.isAlly(), true);
        controller.tick("bomb5", null);
        controller.tick(null, Direction.DOWN);
        controller.saveGame("fml");
        controller.loadGame("fml");
        assertEquals(mercenary.isAlly(), true);
    } 
    
    @Test
    public void testNonExistentDungeon() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bomb", "peaceful");
        controller.saveGame("logma");
        assertThrows (Exception.class, () -> {
            controller.loadGame("ligma");
        });
    }

    @Test
    public void testMilestone3() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("milestone3Stuff", "peaceful");
        controller.saveGame("logma");
        assertDoesNotThrow(() -> controller.loadGame("logma"));
    }
}
