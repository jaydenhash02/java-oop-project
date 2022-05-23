package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class BoulderTest {
    @Test
    public void testBoulderPushIntoWall() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "peaceful");
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        //System.out.println(response.getEntities().get(42).getType());
        assertEquals(response.getEntities().get(42).getPosition(), new Position(3,2,0));
        assertEquals(response.getEntities().get(43).getPosition(), new Position(4,2,0));
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(42).getPosition(), new Position(4,2,0));
        assertEquals(response.getEntities().get(43).getPosition(), new Position(5,2,0));
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(42).getPosition(), new Position(4,2,0));
        assertEquals(response.getEntities().get(43).getPosition(), new Position(5,2,0));
    }
    @Test
    public void testBoulderPushIntoBoulder() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "peaceful");
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        //System.out.println(response.getEntities().get(42).getType());
        assertEquals(response.getEntities().get(42).getPosition(), new Position(3,2,0));
        assertEquals(response.getEntities().get(43).getPosition(), new Position(4,2,0));
        controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(42).getPosition(), new Position(4,1,0));
        assertEquals(response.getEntities().get(43).getPosition(), new Position(4,2,0));
        controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(42).getPosition(), new Position(4,1,0));
        assertEquals(response.getEntities().get(43).getPosition(), new Position(4,2,0));
    }
    @Test
    public void testBoulderPushOntoSwitch() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "peaceful");
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        //System.out.println(response.getEntities().get(42).getType());
        assertEquals(response.getEntities().get(42).getPosition(), new Position(3,2,0));
        assertEquals(response.getEntities().get(43).getPosition(), new Position(4,2,0));
        response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(42).getPosition(), new Position(3,3,0));
        assertEquals(response.getEntities().get(44).getPosition(), new Position(4,3,0));
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(42).getPosition(), new Position(4,3,0));
        assertEquals(response.getEntities().get(44).getPosition(), new Position(5,3,0));
    }
}
