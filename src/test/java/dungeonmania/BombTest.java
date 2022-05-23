package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
public class BombTest {
    @Test
    public void testBomb() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bomb", "peaceful");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getInventory().size(), 0);
        // pick up bomb
        response = controller.tick(null, Direction.RIGHT);
        // check bomb is in inventory
        assertEquals(response.getInventory().size(), 1);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);
        //System.out.println(response.getInventory().get(0).getId());
        // place bomb
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("bomb")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }
        // check bomb isnt in inventory
        assertEquals(response.getInventory().size(), 0);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        // blow up bomb
        controller.tick(null, Direction.RIGHT);
        // check 3 walls are blown up and 1 stays
        assertEquals(controller.getDungeon().entityAtPosition(new Position(1,0,0)).get(0).getType(), "wall");
        assertEquals(controller.getDungeon().entityAtPosition(new Position(2,0,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(3,0,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(2,2,0)), Arrays.asList());
    }
    @Test
    public void testMultipleBombs() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bombs", "peaceful");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("bomb")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("bomb")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        assertEquals(controller.getDungeon().entityAtPosition(new Position(2,8,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(3,8,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(4,8,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(5,8,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(6,8,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(1,8,0)).get(0).getType(), "wall");
        assertEquals(controller.getDungeon().entityAtPosition(new Position(7,8,0)).get(0).getType(), "wall");
    }
    @Test
    public void testActivateDropBomb() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulder_and_bomb", "peaceful");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        //System.out.println(response.getInventory().size());
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("bomb")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }
        // check that the walls are blown up
        assertEquals(controller.getDungeon().entityAtPosition(new Position(6,1,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(6,2,0)), Arrays.asList());
        assertEquals(controller.getDungeon().entityAtPosition(new Position(6,3,0)), Arrays.asList());
    }
}
