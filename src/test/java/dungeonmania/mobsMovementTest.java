package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Mercenary;
//import dungeonmania.entities.Entity;
//import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.List;

public class mobsMovementTest {

    public boolean isWithinAdjacentArea(Position oldPosition, Position newPosition) {
        List<Position> path = oldPosition.getAdjacentPositions();
        for (int i = 0; i < path.size(); i++) {
            if (newPosition.equals(path.get(i)) || newPosition.equals(oldPosition)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void spiderMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("mobTestMap", "standard");
        DungeonResponse response = controller.tick(null, Direction.UP);
        assertEquals(new Position(10, 9, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(14, 13, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(10, 9, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(15, 13, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(9, 9, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(15, 14, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(9, 10, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(15, 15, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(9, 11, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(14, 15, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(10, 11, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(13, 15, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(11, 11, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(13, 14, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(11, 10, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(13, 13, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(11, 10, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(14, 13, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(11, 11, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(15, 13, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(10, 11, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(15, 14, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(9, 11, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(15, 15, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(9, 10, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(14, 15, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(9, 9, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(13, 15, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(10, 9, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(13, 14, 3), response.getEntities().get(128).getPosition());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(10, 9, 3), response.getEntities().get(122).getPosition());
        assertEquals(new Position(13, 13, 3), response.getEntities().get(128).getPosition());
    }

    @Test
    public void mercenaryMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("mobTestMap", "standard");
        DungeonResponse response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 6, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 7, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 8, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 9, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 10, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 11, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 12, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 13, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(2, 13, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(1, 13, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 12, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 11, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 10, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 9, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 8, 3), response.getEntities().get(123).getPosition());
    }

    @Test
    public void zombieToastMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("mobTestMap", "standard");
        DungeonResponse response = controller.tick(null, Direction.UP);
        assertEquals(true, isWithinAdjacentArea(new Position(10, 4, 0), response.getEntities().get(124).getPosition()));
    }

    @Test
    public void zombieTrappedMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("mobTestMap", "standard");
        DungeonResponse response = controller.tick(null, Direction.UP);
        assertEquals(true, isWithinAdjacentArea(new Position(-2, -2, 0), response.getEntities().get(129).getPosition()));
        response = controller.tick(null, Direction.UP);
        assertEquals(true, isWithinAdjacentArea(new Position(-2, -2, 0), response.getEntities().get(129).getPosition()));
    }

    @Test
    public void assassinMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bossesTestMap", "hard");
        DungeonResponse response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 6, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 7, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 8, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 9, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 10, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 11, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 12, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(3, 13, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(2, 13, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.UP);
        assertEquals(new Position(1, 13, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 12, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 11, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 10, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 9, 3), response.getEntities().get(123).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 8, 3), response.getEntities().get(123).getPosition());
    }

    @Test
    public void hydraMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bossesTestMap", "hard");
        DungeonResponse response = controller.tick(null, Direction.UP);
        assertEquals(true, isWithinAdjacentArea(new Position(12, 4, 0), response.getEntities().get(128).getPosition()));
    }

    @Test
    public void mercenaryAndAssassinSpawning() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bossesTestMap", "hard");
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        for (int i = 0; i < 5; i++) {
            response = controller.tick(null, Direction.DOWN);
        }

        // Use invisibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invisibility_potion")) {
                response = controller.tick(item.getId(), Direction.DOWN);
                break;
            }
        }  

        for (int i = 0; i < 8; i++) {
            response = controller.tick(null, Direction.DOWN);
        }

        List<EntityResponse> entities = response.getEntities();
        int count = 0;
        
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getType().equals("mercenary") || entities.get(i).getType().equals("assassin")) {
                if (count == 0) {
                    // Checks position of already existing assassin
                    assertEquals(new Position(3, 11, 3), entities.get(i).getPosition());
                    count += 1;
                } else if (count == 1) {
                    // Checks position of spawned mercenary/assassin
                    assertEquals(new Position(1, 1, 3), entities.get(i).getPosition());
                    break;
                }
            }
        }
    }

    @Test
    public void mercenaryMovementInSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swampMovement", "hard");
        DungeonResponse response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(1, 4, 3), response.getEntities().get(1).getPosition());
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(1, 4, 3), response.getEntities().get(1).getPosition());
        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(2, 4, 3), response.getEntities().get(1).getPosition());
    }

    @Test
    public void assassinMovementInSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swampMovement", "hard");
        DungeonResponse response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(6, 4, 3), response.getEntities().get(2).getPosition());
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(6, 4, 3), response.getEntities().get(2).getPosition());
        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(5, 4, 3), response.getEntities().get(2).getPosition());
    }

    @Test
    public void zombieMovementInSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swampMovement", "hard");
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(7, 7, 3), response.getEntities().get(3).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(7, 7, 3), response.getEntities().get(3).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(true, isWithinAdjacentArea(new Position(7, 7, 3), response.getEntities().get(3).getPosition()));

    }

    @Test
    public void hydraMovementInSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swampMovement", "hard");
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(0, 0, 3), response.getEntities().get(4).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(0, 0, 3), response.getEntities().get(4).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(true, isWithinAdjacentArea(new Position(0, 0, 3), response.getEntities().get(4).getPosition()));

    }
    
    @Test
    public void spiderMovementInSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swampMovement", "hard");
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(10, 9, 3), response.getEntities().get(5).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(11, 9, 3), response.getEntities().get(5).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(11, 9, 3), response.getEntities().get(5).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(11, 9, 3), response.getEntities().get(5).getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(11, 10, 3), response.getEntities().get(5).getPosition());
    }

    @Test
    public void mercenaryMovementWithinBattleRadius() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battleRadius", "hard");
        DungeonLayout currDungeonLayout = controller.getDungeon();
        for (Entity e: currDungeonLayout.getAllEntities()) {
            if (e instanceof Mercenary) {
                assertEquals(e.getPosition(), new Position(6, 2));
                break;
            }
        }

        // moves two blocks because it is in battle
        controller.tick(null, Direction.LEFT);
        for (Entity e: currDungeonLayout.getAllEntities()) {
            if (e instanceof Mercenary) {
                assertEquals(e.getPosition(), new Position(4, 2));
                break;
            }
        }
    }
}

