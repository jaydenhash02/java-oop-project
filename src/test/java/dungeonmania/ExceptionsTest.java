package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;

public class ExceptionsTest {
    @Test
    public void testNewGameExceptions() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows (IllegalArgumentException.class, () -> {
            controller.newGame("fakedungeon", "peaceful");
        });
        assertThrows (IllegalArgumentException.class, () -> {
            controller.newGame("simple", "HardCore");
        });
    }
    @Test
    public void testLoadGameExceptions() {
        /*DungeonManiaController controller = new DungeonManiaController();
         assertThrows (IllegalArgumentException.class, () -> {
            controller.loadGame("MyGame");
        }); */
    }
    @Test
    public void testTickExceptions() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertThrows (IllegalArgumentException.class, () -> {
            controller.tick("wood", null);
        });
        assertThrows (InvalidActionException.class, () -> {
            controller.tick("health_potion", null);
        });
    }
    @Test
    public void testBuildExceptions() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("build", "peaceful");
        assertThrows (IllegalArgumentException.class, () -> {
            controller.build("armour");
        });
        assertThrows (InvalidActionException.class, () -> {
            controller.build("bow");
        });
    }
    @Test
    public void testInteractExceptions() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("interact", "peaceful");
        
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("bow")) {
                assertThrows (IllegalArgumentException.class, () -> {
                    controller.interact(item.getId());
                });
                break;
            }
        }
        for (ItemResponse item : allItems) {
            if (item.getType().equals("mercenary")) {
                assertThrows (InvalidActionException.class, () -> {
                    controller.interact(item.getId());
                });
                break;
            }
        }
        
        for (ItemResponse item : allItems) {
            if (item.getType().equals("zombie_toast_spawner")) {
                assertThrows (InvalidActionException.class, () -> {
                    controller.interact(item.getId());
                });
                break;
            }
        }

        for (ItemResponse item : allItems) {
            if (item.getType().equals("mercenary")) {
                assertThrows (InvalidActionException.class, () -> {
                    controller.interact(item.getId());
                });
                break;
            }
        }

        controller.tick(null, Direction.RIGHT);

        for (ItemResponse item : allItems) {
            if (item.getType().equals("mercenary")) {
                assertThrows (InvalidActionException.class, () -> {
                    controller.interact(item.getId());
                });
                break;
            }
        }

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        for (ItemResponse item : allItems) {
            if (item.getType().equals("zombie_toast_spawner")) {
                assertThrows (InvalidActionException.class, () -> {
                    controller.interact(item.getId());
                });
                break;
            }
        }
    }
}
