package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

public class GameModeTest {
    @Test
    public void testPeaceful() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("encounter", "peaceful");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        //System.out.println(response.getEntities().size());
        //System.out.print(response.getEntities().getP)
        assertEquals(controller.getDungeon().getPlayer().getHealth(), 100);
        assertEquals(controller.getDungeon().getGameMode(), "peaceful");
    }
    @Test
    public void testStandard() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("encounter", "standard");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getPlayer().getHealth(), 75);
        assertEquals(controller.getDungeon().getGameMode(), "standard");
    }
    @Test
    public void testHard() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("encounter", "hard");
        assertEquals(controller.getDungeon().getPlayer().getHealth(), 80);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getPlayer().getHealth(), 42.5);
    }
}
