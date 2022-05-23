package dungeonmania;

import org.junit.jupiter.api.Test;

//import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
//import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//import java.util.ArrayList;

public class MovementTest {

    public static boolean entityResponsesEqual(EntityResponse e1, EntityResponse e2) {
        return e1.getId().equals(e2.getId()) &&
               e1.getType().equals(e2.getType()) &&
               e1.getPosition().equals(e2.getPosition());
    }


    public void testMovement(Direction direction, int toX, int toY) {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("movementTestBasic", "standard");
        EntityResponse player = initialResponse.getEntities().stream().filter(e -> e.getType().equals("player")).findFirst().get();
        
        DungeonResponse response = controller.tick(null, direction);
        EntityResponse expected = new EntityResponse(player.getId(), player.getType(), new Position(toX, toY), false);

        assertTrue(entityResponsesEqual(expected, response.getEntities().stream()
                    .filter(e -> e.getType().equals("player")).findFirst().get()));
    }

    @Test
    public void testMovementDown() {
        testMovement(Direction.DOWN, 1, 2);
    }

    @Test
    public void testMovementUp() {
        testMovement(Direction.UP, 1, 0);
    }

    @Test
    public void testMovementLeft() {
        testMovement(Direction.LEFT, 0, 1);
    }

    @Test
    public void testMovementRight() {
        testMovement(Direction.RIGHT, 2, 1);
    }

}