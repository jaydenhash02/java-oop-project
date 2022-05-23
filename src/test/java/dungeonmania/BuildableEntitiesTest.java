package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.exceptions.InvalidActionException;

public class BuildableEntitiesTest {
    @Test
    public void testCraftBow() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        //pick up 1 wood and 3 arrows
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        // store the dungeon response
        DungeonResponse response = controller.build("bow");
        // check size of inventory
        assertEquals(response.getInventory().size(), 1);
        // check that id and type are the same
        //assertEquals(response.getInventory().get(0).getId(), "bow1");
        assertEquals(response.getInventory().get(0).getType(), "bow");
    }
    @Test
    public void testMakeShieldWithTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        // pick up 2 wood and 1 treasure
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.build("shield");
        assertEquals(response.getInventory().size(), 1);
        //assertEquals(response.getInventory().get(0).getId(), "shield0");
        assertEquals(response.getInventory().get(0).getType(), "shield");
    }
    @Test
    public void testMakeShieldWithKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        // pick up 2 wood and 1 key
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.build("shield");
        assertEquals(response.getInventory().size(), 1);
        //assertEquals(response.getInventory().get(0).getId(), "shield0");
        assertEquals(response.getInventory().get(0).getType(), "shield");
    }
    @Test
    public void testUseTreasureOverKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        // pick up 2 wood, 1 treasure and 1 key
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        DungeonResponse response = controller.build("shield");
        assertEquals(response.getInventory().size(), 2);
        //assertEquals(response.getInventory().get(0).getId(), "key0");
        assertEquals(response.getInventory().get(0).getType(), "key");
        //assertEquals(response.getInventory().get(1).getId(), "shield0");
        assertEquals(response.getInventory().get(1).getType(), "shield");
    }
    @Test
    public void testLeftoverMaterials() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        // pick up 2 wood and 4 arrows
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.build("bow");
        assertEquals(response.getInventory().size(), 3);
        assertEquals(response.getInventory().get(0).getType(), "wood");
        assertEquals(response.getInventory().get(1).getType(), "arrow");
        assertEquals(response.getInventory().get(2).getType(), "bow");
    }
    @Test
    public void testBuildShieldAndBow() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        // pick up 3 wood, 3 arrows and 1 key
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.build("bow");
        DungeonResponse response = controller.build("shield");
        assertEquals(response.getInventory().size(), 2);
        assertEquals(response.getInventory().get(0).getType(), "bow");
        assertEquals(response.getInventory().get(1).getType(), "shield");
    }
    @Test
    public void testBuildSceptreWithArrowsAndTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildSceptre", "peaceful");
        // move around to pick up materials
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getInventory().size(), 4);
        response = controller.build("sceptre");
        assertEquals(response.getInventory().size(), 1);
        assertEquals(response.getInventory().get(0).getType(), "sceptre");
    }
    @Test
    public void testBuildSceptreWithWoodAndKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildSceptre", "peaceful");
        // move around to pick up materials
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getInventory().size(), 3);
        response = controller.build("sceptre");
        assertEquals(response.getInventory().size(), 1);
        assertEquals(response.getInventory().get(0).getType(), "sceptre");
    }
    @Test
    public void testBuildMidnightArmour() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build2", "peaceful");
        // move around to pick up materials
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getInventory().size(), 2);
        response = controller.build("midnight_armour");
        assertEquals(response.getInventory().size(), 1);
        assertEquals(response.getInventory().get(0).getType(), "midnight_armour");
    }
    @Test
    public void testMindControl() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("sceptre", "peaceful");
        Mercenary mercenary = (Mercenary) controller.getDungeon().entityExist("mercenary0");
        // mercenary is not ally at start
        assertEquals(mercenary.isAlly(), false);
        // pick up sceptre
        controller.tick(null, Direction.UP);
        // mind control the mercenary
        controller.interact("mercenary0");
        assertEquals(mercenary.isAlly(), true);
        // move around for 10 ticks
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        // make sure its not ally anymore
        assertEquals(mercenary.isAlly(), false);
    }
    @Test
    public void testBuildMidnightWithZombies() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("midnightZombie", "peaceful");
        // move around to pick up materials
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getInventory().size(), 2);
        // shouldn't build because there's zombies in the dungeon
        assertThrows(InvalidActionException.class, () -> {
            controller.build("midnight_armour");
        });
    }
}
