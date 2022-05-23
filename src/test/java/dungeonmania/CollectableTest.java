package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CollectableTest {
    @Test
    public void pickUpAllCollectables() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("collectables", "peaceful");
        assertEquals(response.getInventory(), Arrays.asList());
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
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getInventory().size(), 11);
        assertEquals(response.getInventory().get(0).getType(), "health_potion");
        assertEquals(response.getInventory().get(1).getType(), "invincibility_potion");
        assertEquals(response.getInventory().get(2).getType(), "invisibility_potion");
        assertEquals(response.getInventory().get(3).getType(), "one_ring");
        assertEquals(response.getInventory().get(4).getType(), "armour");
        assertEquals(response.getInventory().get(5).getType(), "arrow");
        assertEquals(response.getInventory().get(6).getType(), "bomb");
        assertEquals(response.getInventory().get(7).getType(), "key");
        assertEquals(response.getInventory().get(8).getType(), "sword");
        assertEquals(response.getInventory().get(9).getType(), "treasure");
        assertEquals(response.getInventory().get(10).getType(), "wood");
    }
    @Test
    public void testSunStoneDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("sunStoneDoor", "peaceful");
        // pick up the sunstone
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        // check sunstone has been picked up
        assertEquals(response.getInventory().get(0).getType(), "sun_stone");
        // move to door
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        // check the position before
        assertEquals(controller.getDungeon().getPlayer().getPosition(), new Position(4,5,0));
        controller.tick(null, Direction.DOWN);
        // check position in same tile as door
        assertEquals(controller.getDungeon().getPlayer().getPosition(), new Position(4,6,0));
        // check sunstone is still in inventory
        assertEquals(response.getInventory().get(0).getType(), "sun_stone");
        response = controller.tick(null, Direction.DOWN);
        // check position after going through the door
        assertEquals(controller.getDungeon().getPlayer().getPosition(), new Position(4,7,0));
        // go through the next door
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);
        assertEquals(controller.getDungeon().getPlayer().getPosition(), new Position(5,5,0));
        assertEquals(response.getInventory().get(0).getType(), "sun_stone");
    }
    @Test
    public void testSunStoneBribe() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("sunStoneBribe", "peaceful");
        // pick up sun stone
        controller.tick(null, Direction.DOWN);
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getInventory().get(0).getType(), "sun_stone");
        // move in range of mercenary
        controller.tick(null, Direction.RIGHT);
        // assert conditions before bribing
        Mercenary mercenary = (Mercenary) controller.getDungeon().entityExist("mercenary0");
        assertEquals(mercenary.isAlly(), false);
        // bribe the mercenary
        response = controller.interact("mercenary0");
        // check that mercenary is now ally
        assertEquals(mercenary.isAlly(), true);
        // make sure sunstone is still in inventory
        assertEquals(response.getInventory().get(0).getType(), "sun_stone");
    }
}
