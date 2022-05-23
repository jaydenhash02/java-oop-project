package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class PortalTest {
    @Test
    public void portalTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("portals", "peaceful");
        assertEquals(response.getEntities().get(0).getPosition(), new Position(0,0,0));
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(5,0,0));
        response = controller.tick(null, Direction.LEFT);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(0,0,0));
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(5,0,0));
    }
    @Test
    public void testTwoPairsOfPortals() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "peaceful");
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(3,2,0));
        response = controller.tick(null, Direction.LEFT);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(1,-2,0));
        controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        assertEquals(controller.getDungeon().getPlayer().getPosition().getX(), 4);
        assertEquals(controller.getDungeon().getPlayer().getPosition().getY(), 1);
    }
}
