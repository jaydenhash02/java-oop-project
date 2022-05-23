package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
public class PlayerTest {
    // test up down left right movement
    @Test
    public void testMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("playerMovement", "peaceful");
        DungeonResponse response = controller.tick(null, Direction.UP);
        //System.out.println(response.getEntities().get(34).getType());
        assertEquals(response.getEntities().get(34).getPosition(), new Position(2,1,0));
        response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(34).getPosition(), new Position(2,2,0));
        response = controller.tick(null, Direction.LEFT);
        assertEquals(response.getEntities().get(34).getPosition(), new Position(1,2,0));
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(34).getPosition(), new Position(2,2,0));
    }
    // test walking into a wall
    @Test
    public void testMovementIntoWall() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("playerMovement", "peaceful");
        DungeonResponse response = controller.tick(null, Direction.UP);
        assertEquals(response.getEntities().get(34).getPosition(), new Position(2,1,0));
        response = controller.tick(null, Direction.UP);
        assertEquals(response.getEntities().get(34).getPosition(), new Position(2,1,0));
    }
    // // test walking into a closed door
    @Test
    public void testClosedDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("playerMovement", "peaceful");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(34).getPosition(), new Position(4,5,0));
        controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(34).getPosition(), new Position(4,5,0));
    }
    // // test picking up a key and walking through the door
    @Test
    public void testOpenDoor() {
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
        assertEquals(response.getEntities().get(33).getPosition(), new Position(4,7,0));
    }
    // test wrong key
    @Test
    public void testWrongKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("playerMovement", "peaceful");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(33).getPosition(), new Position(5,5,0));
        response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(33).getPosition(), new Position(5,5,0));
    }
    // test opening 2 doors
    @Test
    public void testCantPickUpMoreThanOneKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("playerMovement", "peaceful");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getInventory().size(), 1);
    }
}
